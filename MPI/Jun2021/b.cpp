#include <stdio.h>
#include <mpi.h>
#include <stdlib.h>

#define k 3
#define m 4
#define l 2

int main(int argc, char** argv)
{
	int rank, size, master = 0;
	int A[k][m], b[m], c[k];
	int local_A[k][l], local_b[l], local_c[k], local_row_sum[k], row_sum[k];

	struct {
		int value;
		int rank;
	} in, out;

	MPI_Request req;
	MPI_Status stat;

	MPI_Init(&argc, &argv);
	MPI_Comm_size(MPI_COMM_WORLD, &size);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	if (rank == master) {
		for (int i = 0; i < m; i++)
		{
			for (int j = 0; j < k; j++)
				A[j][i] = i + j * k;

			b[i] = i;
		}

		for (int i = 0; i < k; i++)
		{
			printf("|");
			for (int j = 0; j < m; j++)
				printf("\t%d", A[i][j]);
			printf("\t|\n");
		}

		fflush(stdout);
	}

	if (rank == master)
	{
		for (int p = 0; p < size; p++)
			for (int i = 0; i < k; i++)
				MPI_Isend(&A[i][p * l], l, MPI_INT, p, 0, MPI_COMM_WORLD, &req);
	}

	for (int i = 0; i < k; i++)
		MPI_Recv(&local_A[i][0], l, MPI_INT, master, 0, MPI_COMM_WORLD, &stat);

	// MPI_Scatter(&b[0], l, MPI_INT, &local_b[0], l, MPI_INT, master, MPI_COMM_WORLD);
	if (rank == master)
		for (int p = 0; p < size; p++)
			MPI_Isend(&b[p * l], l, MPI_INT, p, 0, MPI_COMM_WORLD, &req);

	MPI_Recv(&local_b[0], l, MPI_INT, master, 0, MPI_COMM_WORLD, &stat);

	in = { INT16_MIN, rank };

	for (int i = 0; i < k; i++)
	{
		local_c[i] = 0;
		local_row_sum[i] = 0;
		for (int j = 0; j < l; j++)
		{
			local_c[i] += local_A[i][j] * local_b[j];
			local_row_sum[i] += local_A[i][j];

			if (in.value < local_A[i][j])
				in.value = local_A[i][j];
		}
	}

	// MPI_Reduce(&in, &out, 1, MPI_2INT, MPI_MAXLOC, master, MPI_COMM_WORLD);
	MPI_Isend(&in, 1, MPI_2INT, master, 0, MPI_COMM_WORLD, &req);
	if (rank == master)
	{
		MPI_Recv(&out, 1, MPI_2INT, 0, 0, MPI_COMM_WORLD, &stat);
		for (int p = 1; p < size; p++)
		{
			MPI_Recv(&in, 1, MPI_2INT, p, 0, MPI_COMM_WORLD, &stat);
			if (out.value < in.value)
				out = in;
		}
	}

	// MPI_Bcast(&out, 1, MPI_2INT, master, MPI_COMM_WORLD);
	if (rank == master)
		for (int p = 0; p < size; p++)
			MPI_Isend(&out, 1, MPI_2INT, p, 0, MPI_COMM_WORLD, &req);

	MPI_Recv(&out, 1, MPI_2INT, master, 0, MPI_COMM_WORLD, &stat);

	// MPI_Reduce(&local_row_sum[0], &row_sum[0], k, MPI_INT, MPI_SUM, out.rank, MPI_COMM_WORLD);
	MPI_Isend(&local_row_sum[0], k, MPI_INT, out.rank, 0, MPI_COMM_WORLD, &req);
	if (rank == out.rank)
	{
		MPI_Recv(&row_sum[0], k, MPI_INT, 0, 0, MPI_COMM_WORLD, &stat);
		for (int p = 1; p < size; p++)
		{
			MPI_Recv(&local_row_sum[0], k, MPI_INT, p, 0, MPI_COMM_WORLD, &stat);
			for (int i = 0; i < k; i++)
				row_sum[i] += local_row_sum[i];
		}
	}

	// MPI_Reduce(&local_c[0], &c[0], k, MPI_INT, MPI_SUM, out.rank, MPI_COMM_WORLD);
	MPI_Isend(&local_row_sum[0], k, MPI_INT, out.rank, 0, MPI_COMM_WORLD, &req);
	if (rank == out.rank)
	{
		MPI_Recv(&c[0], k, MPI_INT, 0, 0, MPI_COMM_WORLD, &stat);
		for (int p = 1; p < size; p++)
		{
			MPI_Recv(&local_c[0], k, MPI_INT, p, 0, MPI_COMM_WORLD, &stat);
			for (int i = 0; i < k; i++)
				c[i] += local_c[i];
		}
	}

	if (rank == out.rank)
	{
		printf("Proces [%d] sadrzi maksimum elemenata matrice A, to je: %d\n", out.rank, out.value);

		printf("vector c = [");
		for (int i = 0; i < k; i++)
			printf("\t%d", c[i]);
		printf("\t]\n");

		printf("Suma elemenata vrsta matrice A:\n");
		for (int i = 0; i < k; i++)
			printf("%d\n", row_sum[i]);
	}

	MPI_Finalize();
	return 0;
}