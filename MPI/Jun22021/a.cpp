//#include <mpi.h>
//#include <math.h>
//#include <stdio.h>
//#include <stdlib.h>
//
//int main(int argc, char** argv)
//{
//	MPI_Init(&argc, &argv);
//
//	int rank, size, master = 0, * numbers = nullptr;
//	MPI_Status stat;
//
//	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
//	MPI_Comm_size(MPI_COMM_WORLD, &size);
//
//	numbers = (int*)malloc(sizeof(int) * size);
//
//	printf("Inicijalizacija, proces [%d]: numbers = [", rank);
//	for (int i = 0; i < size; i++)
//	{
//		if (rank == master)
//		{
//			numbers[i] = i;
//			printf("\t%d", numbers[i]);
//		}
//		else
//			numbers[i] = -1;
//	}
//	printf("\t]\n");
//
//	fflush(stdout);
//	MPI_Barrier(MPI_COMM_WORLD);
//	if (rank == master)
//	{
//		printf("\n----------------------------------------------\n\n");
//		fflush(stdout);
//	}
//	MPI_Barrier(MPI_COMM_WORLD);
//
//	int steps = log2(size), step, mask = 0;
//
//	for (int i = 0; i < steps; i++)
//	{
//		step = size >> (i + 1);
//		mask |= step;
//
//		if ((rank & mask) == rank)
//		{
//			if (rank < (rank ^ step))
//				MPI_Send(&numbers[step], step, MPI_INT, rank ^ step, 0, MPI_COMM_WORLD);
//			else
//				MPI_Recv(&numbers[0], step, MPI_INT, rank ^ step, 0, MPI_COMM_WORLD, &stat);
//
//		}
//
//		printf("Korak %d, proces [%d]: numbers = [", i, rank);
//		for (int j = 0; j < step && numbers[j] != -1; j++)
//			printf("\t%d", numbers[j]);
//		printf("\t]\n");
//
//		fflush(stdout);
//		MPI_Barrier(MPI_COMM_WORLD);
//		if (rank == master)
//		{
//			printf("\n----------------------------------------------\n\n");
//			fflush(stdout);
//		}
//		MPI_Barrier(MPI_COMM_WORLD);
//	}
//
//	MPI_Finalize();
//	return 0;
//}