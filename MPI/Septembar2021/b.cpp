#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>

#define k 6
#define m 7
#define n 8
// size == 3
#define l 2

int main(int argc, char** argv)
{
	int rank, size, master = 0;

	int A[k][m], B[m][n], C[k][n];
	int local_A[l][m], local_C[l][n], local_column_prod[m], column_prod[m];

	MPI_Request req;
	MPI_Status stat;

	struct temp_max {
		int value;
		int rank;
	} in, out;

	for (int i = 0; i < m; i++)
		local_column_prod[i] = 1;

	MPI_Init(&argc, &argv);
	MPI_Comm_size(MPI_COMM_WORLD, &size);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	if (rank == master)
	{
		printf("Matrica A:\n");
		for (int i = 0; i < k; i++) {
			printf("|\t");
			for (int j = 0; j < m; j++)
			{
				A[i][j] = i + j;
				printf("%d\t", A[i][j]);
			}
			printf("|\n");
		}

		printf("Matrica B:\n");
		for (int i = 0; i < m; i++) {
			printf("|\t");
			for (int j = 0; j < n; j++)
			{
				B[i][j] = i + j;
				printf("%d\t", B[i][j]);
			}
			printf("|\n");
		}

		fflush(stdout);
	}

	// MPI_Scatter(&A[0][0], l * m, MPI_INT, &local_A[0][0], l * m, MPI_INT, master, MPI_COMM_WORLD);
	// MPI_Bcast(&B[0][0], m * n, MPI_INT, master, MPI_COMM_WORLD);
	if (rank == master)
	{
		for (int p = 0; p < size; p++)
		{
			MPI_Isend(&A[p * l][0], l * m, MPI_INT, p, 0, MPI_COMM_WORLD, &req);
			MPI_Isend(&B[0][0], m* n, MPI_INT, p, 0, MPI_COMM_WORLD, &req);
		}
	}
	MPI_Recv(&local_A[0], l* m, MPI_INT, master, 0, MPI_COMM_WORLD, &stat);
	MPI_Recv(&B[0][0], m* n, MPI_INT, master, 0, MPI_COMM_WORLD, &stat);

	in = temp_max{ INT16_MIN, rank };

	for (int i = 0; i < l; i++)
	{
		for (int j = 0; j < n; j++)
		{
			local_C[i][j] = 0;
			for (int q = 0; q < m; q++)
				local_C[i][j] += local_A[i][q] * B[q][j];

			if (local_C[i][j] > in.value)
				in.value = local_C[i][j];
		}

		for (int j = 0; j < m; j++)
			local_column_prod[j] *= local_A[i][j];
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

	// MPI_Gather(&local_C[0][0], l * n, MPI_INT, &C[0][0], l * n, MPI_INT, out.rank, MPI_COMM_WORLD);
	MPI_Isend(&local_C[0][0], l* n, MPI_INT, out.rank, 0, MPI_COMM_WORLD, &req);
	if (rank == out.rank)
		for (int p = 0; p < size; p++)
			MPI_Recv(&C[p * l][0], l * n, MPI_INT, p, 0, MPI_COMM_WORLD, &stat);

	// MPI_Reduce(&local_column_prod[0], &column_prod[0], m, MPI_INT, MPI_PROD, out.rank, MPI_COMM_WORLD);
	MPI_Isend(&local_column_prod[0], m, MPI_INT, out.rank, 0, MPI_COMM_WORLD, &req);
	if (rank == out.rank)
	{
		MPI_Recv(&column_prod[0], m, MPI_INT, 0, 0, MPI_COMM_WORLD, &stat);

		for (int p = 1; p < size; p++)
		{
			MPI_Recv(&local_column_prod[0], m, MPI_INT, p, 0, MPI_COMM_WORLD, &stat);
			for (int i = 0; i < m; i++)
				column_prod[i] *= local_column_prod[i];
		}

		printf("Proces [%d] sadrzi maksimum matrice C i to je %d\n", out.rank, out.value);

		printf("Matrica C:\n");
		for (int i = 0; i < k; i++)
		{
			printf("|\t");
			for (int j = 0; j < n; j++)
				printf("%d\t", C[i][j]);
			printf("|\n");
		}

		printf("Proizvod kolona matrice A:\n[\t");
		for (int i = 0; i < m; i++)
			printf("%d\t", column_prod[i]);
		printf("]\n");
	}

	MPI_Finalize();
	return 0;
}