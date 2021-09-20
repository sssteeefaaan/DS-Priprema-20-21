#include <stdio.h>
#include <mpi.h>
#include <stdlib.h>
#include <math.h>

int main(int argc, char** argv)
{
	int rank, size;
	MPI_Status stat;

	MPI_Init(&argc, &argv);
	MPI_Comm_size(MPI_COMM_WORLD, &size);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	int koraci = log2(size),
		korak,
		bar = 0,
		value = rand();

	for (int i = 0; i < koraci; i++)
	{
		korak = 1 << i;
		bar |= korak;
		if ((rank & bar) == rank)
		{
			if ((rank ^ korak) > rank)
				MPI_Send(&value, 1, MPI_INT, rank ^ korak, 0, MPI_COMM_WORLD);
			else {
				MPI_Recv(&value, 1, MPI_INT, rank ^ korak, 0, MPI_COMM_WORLD, &stat);
				printf("Proces [%d] --(korak = %d)--> Proces [%d] vrednost: %d\n", rank ^ korak, i + 1, rank, value);
				fflush(stdout);
			}
		}
	}

	MPI_Finalize();
	return 0;
}