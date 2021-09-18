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
//	buff[0] = rank;
//
//	int koraci = log2(size);
//	int bar = 7;
//
//	for (int i = 1; i < (1 << koraci); i <<= 1)
//	{
//		if ((rank & bar) == rank)
//		{
//			if ((rank ^ i) < rank)
//				MPI_Send(&buff[0], i, MPI_INT, rank ^ i, 0, MPI_COMM_WORLD);
//			else
//				MPI_Recv(&buff[i], i, MPI_INT, rank ^ i, 0, MPI_COMM_WORLD, &status);
//
//			printf("Proces [%d] korak %d: [", rank, (int)(log2(i)));
//			for (int j = 0; j < i; j++)
//				printf("\t%d", buff[j]);
//			printf("\t]\n");
//			fflush(stdout);
//		}
//		bar ^= i;
//	}
//	
//	// MPI_Gather(&buffSend[0], 1, MPI_INT, &buffRecv[0], 1, MPI_INT, 0, MPI_COMM_WORLD);
//
//	MPI_Finalize();
//	return 0;
//}