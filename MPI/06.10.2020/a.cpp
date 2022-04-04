#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(int argc, char** argv)
{
	int rank, size, root = 0;
	int levels, val;

	MPI_Status status;
	MPI_Init(&argc, &argv);
	MPI_Comm_size(MPI_COMM_WORLD, &size);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	levels = log2(size);
	val = rank;

	for (int i = 0; i < levels; i++)
	{
		if (rank < (2 << i))
		{
			if ((rank & (1 << i)) == 0) {
				MPI_Send(&val, 1, MPI_INT, rank ^ (1 << i), 0, MPI_COMM_WORLD);
				printf("Proces [%d] - (korak = %d) -> Proces [%d]\n", rank, i + 1, rank ^ (1 << i));
				fflush(stdout);
			}
			else
			{
				MPI_Recv(&val, 1, MPI_INT, rank ^ (1 << i), 0, MPI_COMM_WORLD, &status);
				//printf("Proces [%d], korak [%d]: %d\n", rank, i + 1, val);
			}
		}
	}

	MPI_Finalize();
	return 0;
}