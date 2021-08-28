//#include <mpi.h>
//#include <stdio.h>
//#include <stdlib.h>
//#include <math.h>
//
//int main(int argc, char** argv)
//{
//	int rank, size, root = 0;
//	int value;
//	int* niz = nullptr;
//	MPI_Status status;
//
//	MPI_Init(&argc, &argv);
//	MPI_Comm_size(MPI_COMM_WORLD, &size);
//	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
//
//	if (rank == root) {
//		niz = (int*)malloc(sizeof(int) * (size + 1));
//		niz[size] = -1;
//	}
//
//	value = rank;
//
//	MPI_Gather(&value, 1, MPI_INT, niz, 1, MPI_INT, root, MPI_COMM_WORLD);
//
//	if(rank == root){
//
//		printf("Proces [%d] sadrzi [", rank);
//		int j = 0;
//		while (niz[j] != -1)
//			printf("\t%d", niz[j++]);
//		printf("\t]\n");
//		fflush(stdout);
//
//		free(niz);
//	}
//
//	MPI_Finalize();
//	return 0;
//}