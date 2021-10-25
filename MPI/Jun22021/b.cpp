#include <mpi.h>
#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char** argv)
{
	MPI_Init(&argc, &argv);

	int rank, size, master = 0, * numbers = nullptr;
	MPI_Status stat;

	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Comm_size(MPI_COMM_WORLD, &size);

	numbers = (int*)malloc(sizeof(int) * size );
	memset(numbers, -1, size);

	if (rank == master)
		for (int i = 0; i < size; i++)
			numbers[i] = i;

	printf("Inicijalizacija, proces [%d]: numbers = [", rank);
	for (int i = 0; i < size && numbers[i] != -1; i++)
		printf("\t%d", numbers[i]);
	printf("\t]\n");

	fflush(stdout);
	MPI_Barrier(MPI_COMM_WORLD);
	if (rank == master)
	{
		printf("\n----------------------------------------------\n\n");
		fflush(stdout);
	}
	MPI_Barrier(MPI_COMM_WORLD);

	struct temp {
		int rank;
		int value;
	} in = { rank, -1 }, out;
	MPI_Scatter(&numbers[0], 1, MPI_INT, &in.value, 1, MPI_INT, master, MPI_COMM_WORLD);

	printf("Distribucija, proces [%d]: numbers = [\t%d\t]\n", rank, in.value);
	fflush(stdout);
	MPI_Barrier(MPI_COMM_WORLD);
	if (rank == master)
	{
		printf("\n----------------------------------------------\n\n");
		fflush(stdout);
	}
	MPI_Barrier(MPI_COMM_WORLD);

	MPI_Reduce(&in, &out, 1, MPI_2INT, MPI_MAXLOC, master, MPI_COMM_WORLD);
	MPI_Bcast(&out, 1, MPI_2INT, master, MPI_COMM_WORLD);

	int sum;
	MPI_Reduce(&in.value, &sum, 1, MPI_INT, MPI_SUM, out.rank, MPI_COMM_WORLD);

	if (rank == out.rank)
	{
		printf("Proces [%d] sadrzi maksimum: %d\nSuma iznosi: %d\n", rank, out.value, sum);
		fflush(stdout);
	}

	MPI_Finalize();
	return 0;
}