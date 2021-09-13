//#include <mpi.h>
//#include <stdio.h>
//#include <stdlib.h>
//#include <math.h>
//
//#define n 8
//
//int main(int argc, char** argv)
//{
//	int rank, size, buff[n];
//	MPI_Status status;
//
//	MPI_Init(&argc, &argv);
//	MPI_Comm_size(MPI_COMM_WORLD, &size);
//	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
//
//	if (rank == 0)
//		for (int i = 0; i < n; i++)
//			buff[i] = i;
//
//	int koraci = log2(size);
//	int bar = 0;
//
//	for (int i = 1 << (koraci - 1); i > 0; i >>= 1)
//	{
//		bar |= i;
//		if ((rank & bar) == rank)
//		{
//			if ((rank ^ i) > rank)
//				MPI_Send(&buff[i], i, MPI_INT, rank ^ i, 0, MPI_COMM_WORLD);
//			else
//				MPI_Recv(&buff[0], i, MPI_INT, rank ^ i, 0, MPI_COMM_WORLD, &status);
//
//			printf("Proces [%d] korak %d: [", rank, (int)(koraci - 1 - log2(i)));
//			for (int j = 0; j < i; j++)
//				printf("\t%d", buff[j]);
//			printf("\t]\n");
//			fflush(stdout);
//		}
//	}
//	// MPI_Scatter(&buffSend[0], 1, MPI_INT, &buffRecv[0], 1, MPI_INT, 0, MPI_COMM_WORLD);
//
//	MPI_Finalize();
//	return 0;
//}