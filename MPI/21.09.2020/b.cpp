//#include <mpi.h>
//#include <stdio.h>
//#include <stdlib.h>
//#include <math.h>
//
//int main(int argc, char** argv)
//{
//	int rank, size, root = 0;
//	int koraci, mask;
//	int* niz = nullptr;
//	MPI_Status status;
//
//	MPI_Init(&argc, &argv);
//	MPI_Comm_size(MPI_COMM_WORLD, &size);
//	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
//
//	koraci = log2(size);
//	niz = (int*)malloc(sizeof(int) * (size + 1));
//
//	niz[0] = rank;
//	for (int i = 0; i < size; i++)
//		niz[i + 1] = -1;
//
//	MPI_Barrier(MPI_COMM_WORLD);
//
//	for (int i = 0; i < koraci; i++)
//	{
//		int mask = size >> (koraci - i);
//
//		if (rank & mask) {
//			MPI_Send(niz, mask, MPI_INT, rank ^ mask, 0, MPI_COMM_WORLD);
//			niz[mask] = -1;
//		}
//		else
//			MPI_Recv(&niz[mask], mask, MPI_INT, rank ^ mask, 0, MPI_COMM_WORLD, &status);
//
//		printf("Proces [%d] u koraku %d sadrzi [", rank, i);
//		int j = 0;
//		while (niz[j] != -1)
//			printf("\t%d", niz[j++]);
//		printf("\t]\n");
//		fflush(stdout);
//	}
//
//	free(niz);
//
//	MPI_Finalize();
//	return 0;
//}