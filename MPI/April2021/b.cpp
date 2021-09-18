#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>
#include <float.h>

#define n 3
#define k 5 // mora biti jednako broju procesa == size

int main(int argc, char** argv)
{
	int rank, size, master = 0;

	double A[n][k], b[k], c[n];
	double local_A[n], local_b, local_c[n];

	struct {
		double val;
		int rank;
	}in, out;

	double row_prod[n];

	MPI_Status stat;
	MPI_Request req;

	MPI_Init(&argc, &argv);
	MPI_Comm_size(MPI_COMM_WORLD, &size);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	if (rank == master)
	{
		for (int i = 0; i < n; i++)
			for (int j = 0; j < k; j++)
				A[i][j] = i * k + j;

		for (int i = 0; i < k; i++)
			b[i] = 1;

		printf("A:\n");
		for (int i = 0; i < n; i++)
		{
			printf("|");
			for (int j = 0; j < k; j++)
				printf("\t%f", A[i][j]);
			printf("\t|\n");
		}

		printf("b = [");
		for (int i = 0; i < k; i++)
			printf("\t%f", b[i]);
		printf("\t]\n");

		fflush(stdout);
	}

	// for (int i = 0; i < n; i++)
	//	MPI_Scatter(&A[i][0], 1, MPI_DOUBLE, &local_A[i], 1, MPI_DOUBLE, master, MPI_COMM_WORLD);
	if (rank == master)
		for (int p = 0; p < size; p++)
			for (int i = 0; i < n; i++)
				MPI_Isend(&A[i][p], 1, MPI_DOUBLE, p, 0, MPI_COMM_WORLD, &req);

	for (int i = 0; i < n; i++)
		MPI_Recv(&local_A[i], 1, MPI_DOUBLE, master, 0, MPI_COMM_WORLD, &stat);

	// MPI_Scatter(&b[0], 1, MPI_DOUBLE, &local_b, 1, MPI_DOUBLE, master, MPI_COMM_WORLD);
	if (rank == master)
		for (int p = 0; p < size; p++)
			MPI_Isend(&b[p], 1, MPI_DOUBLE, p, 0, MPI_COMM_WORLD, &req);

	MPI_Recv(&local_b, 1, MPI_DOUBLE, master, 0, MPI_COMM_WORLD, &stat);

	in = { DBL_MIN, rank };

	for (int i = 0; i < n; i++)
	{
		local_c[i] = local_A[i] * local_b;
		if (local_A[i] < in.val)
			in.val = local_A[i];
	}

	// MPI_Reduce(&in, &out, 1, MPI_DOUBLE_INT, MPI_MINLOC, master, MPI_COMM_WORLD);
	MPI_Isend(&in, 1, MPI_DOUBLE_INT, master, 0, MPI_COMM_WORLD, &req);
	if (rank == master)
	{
		MPI_Recv(&out, 1, MPI_DOUBLE_INT, 0, 0, MPI_COMM_WORLD, &stat);
		for (int p = 1; p < size; p++)
		{
			MPI_Recv(&in, 1, MPI_DOUBLE_INT, p, 0, MPI_COMM_WORLD, &stat);
			if (in.val < out.val)
				out = in;
		}
	}

	// MPI_Bcast(&out, 1, MPI_DOUBLE_INT, master, MPI_COMM_WORLD);
	if (rank == master)
		for (int p = 0; p < size; p++)
			MPI_Isend(&out, 1, MPI_DOUBLE_INT, p, 0, MPI_COMM_WORLD, &req);

	MPI_Recv(&out, 1, MPI_DOUBLE_INT, master, 0, MPI_COMM_WORLD, &stat);

	// MPI_Reduce(&local_c[0], &c[0], n, MPI_DOUBLE, MPI_SUM, out.rank, MPI_COMM_WORLD);
	MPI_Isend(&local_c[0], n, MPI_DOUBLE, out.rank, 0, MPI_COMM_WORLD, &req);
	if (rank == out.rank)
	{
		MPI_Recv(&c[0], n, MPI_DOUBLE, 0, 0, MPI_COMM_WORLD, &stat);
		for (int p = 1; p < size; p++)
		{
			MPI_Recv(&local_c[0], n, MPI_DOUBLE, p, 0, MPI_COMM_WORLD, &stat);
			for (int i = 0; i < n; i++)
				c[i] += local_c[i];
		}
	}

	// MPI_Reduce(&local_A[0], &row_prod[0], n, MPI_DOUBLE, MPI_PROD, out.rank, MPI_COMM_WORLD);
	MPI_Isend(&local_A[0], n, MPI_DOUBLE, out.rank, 0, MPI_COMM_WORLD, &req);
	if (rank == out.rank)
	{
		MPI_Recv(&row_prod[0], n, MPI_DOUBLE, 0, 0, MPI_COMM_WORLD, &stat);
		for (int p = 1; p < size; p++)
		{
			MPI_Recv(&local_A[0], n, MPI_DOUBLE, p, 0, MPI_COMM_WORLD, &stat);
			for (int i = 0; i < n; i++)
				row_prod[i] *= local_A[i];
		}
	}

	if (rank == out.rank)
	{
		printf("*****Proces [%d]\n", rank);
		printf("Minimalna vrednost matrice A: %f\n", out.val);

		printf("c = [");
		for (int i = 0; i < n; i++)
			printf("\t%f", c[i]);
		printf("\t]\n");

		printf("proizvod elemenata vrste = [");
		for (int i = 0; i < n; i++)
			printf("\t%f", row_prod[i]);
		printf("\t]\n");
	}
	MPI_Finalize();
	return 0;
}