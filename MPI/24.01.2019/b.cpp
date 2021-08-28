#include <mpi.h>
#include <stdlib.h>
#include <stdio.h>

int main(int argc, char** argv)
{
	int rank, size, root = 0;
	int* a = nullptr, * b = nullptr,
		* local_a = nullptr, * local_b = nullptr,
		* dist_a = nullptr, * dist_b = nullptr;
	int m, l, scal, local_scal;
	MPI_Status status;

	MPI_Init(&argc, &argv);
	MPI_Comm_size(MPI_COMM_WORLD, &size);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	if (rank == root)
	{
		printf("Unesite m: ");
		fflush(stdout);
		scanf_s("%d", &m);

		a = (int*)malloc(sizeof(int) * m);
		b = (int*)malloc(sizeof(int) * m);

		for (int i = 0; i < m; i++)
		{
			a[i] = i;
			b[i] = 1;
		}
	}

	// MPI_Bcast(&m, 1, MPI_INT, root, MPI_COMM_WORLD);
	if (rank == root) {
		for (int p = 0; p < size; p++)
			if (p != root)
				MPI_Send(&m, 1, MPI_INT, p, 0, MPI_COMM_WORLD);
	}
	else
		MPI_Recv(&m, 1, MPI_INT, root, 0, MPI_COMM_WORLD, &status);

	l = m / size;
	local_a = (int*)malloc(sizeof(int) * l);
	local_b = (int*)malloc(sizeof(int) * l);

	if (rank == root)
	{
		dist_a = (int*)malloc(sizeof(int) * m);
		dist_b = (int*)malloc(sizeof(int) * m);

		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < l; j++) {
				dist_a[i * l + j] = a[j * size + i];
				dist_b[i * l + j] = b[j * size + i];
			}
		}
	}

	// MPI_Scatter(dist_a, l, MPI_INT, local_a, l, MPI_INT, root, MPI_COMM_WORLD);
	if (rank == root)
	{
		for (int p = 0; p < size; p++)
			if (p != root)
				MPI_Send(&dist_a[p * l], l, MPI_INT, p, 0, MPI_COMM_WORLD);
			else
				for (int i = 0; i < l; i++)
					local_a[i] = dist_a[root * l + i];
	}
	else
		MPI_Recv(local_a, l, MPI_INT, root, 0, MPI_COMM_WORLD, &status);

	// MPI_Scatter(dist_b, l, MPI_INT, local_b, l, MPI_INT, root, MPI_COMM_WORLD);
	if (rank == root)
	{
		for (int p = 0; p < size; p++)
			if (p != root)
				MPI_Send(&dist_b[p * l], l, MPI_INT, p, 0, MPI_COMM_WORLD);
			else
				for (int i = 0; i < l; i++)
					local_b[i] = dist_b[root * l + i];
	}
	else
		MPI_Recv(local_b, l, MPI_INT, root, 0, MPI_COMM_WORLD, &status);

	local_scal = 0;
	for (int i = 0; i < l; i++)
		local_scal += local_a[i] * local_b[i];

	// MPI_Reduce(&local_scal, &scal, 1, MPI_INT, MPI_SUM, root, MPI_COMM_WORLD);
	if (rank == root)
	{
		scal = local_scal;
		for (int p = 0; p < size; p++) {
			if (p != root) {
				MPI_Recv(&local_scal, 1, MPI_INT, p, 0, MPI_COMM_WORLD, &status);
				scal += local_scal;
			}
		}
	}
	else
		MPI_Send(&local_scal, 1, MPI_INT, root, 0, MPI_COMM_WORLD);

	if (rank == root)
	{
		printf("Rezultat iznosi: %d\n", scal);

		free(a);
		free(b);
		free(dist_a);
		free(dist_b);
	}

	free(local_a);
	free(local_b);

	MPI_Finalize();
	return 0;
}