//#include <stdio.h>
//#include <mpi.h>
//#include <stdlib.h>
//#include <math.h>
//
//int main(int argc, char** argv)
//{
//	int rank, size, master = 0;
//	MPI_Status stat;
//	int value;
//
//	MPI_Init(&argc, &argv);
//	MPI_Comm_size(MPI_COMM_WORLD, &size);
//	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
//
//	int koraci = log2(size);
//	int bar = 0;
//	value = rand();
//
//	for (int i = 1; i < (1 << koraci); i <<= 1)
//	{
//		bar |= i;
//		if ((rank & bar) == rank)
//		{
//			if ((rank ^ i) > rank)
//				MPI_Send(&value, 1, MPI_INT, rank ^ i, 0, MPI_COMM_WORLD);
//			else {
//				MPI_Recv(&value, 1, MPI_INT, rank ^ i, 0, MPI_COMM_WORLD, &stat);
//				printf("Proces [%d] --(korak = %d)--> Proces [%d]: %d\n", rank ^ i, (int)log2(i) + 1, rank, value);
//				fflush(stdout);
//			}
//		}
//	}
//
//	MPI_Finalize();
//	return 0;
//}