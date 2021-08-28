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
//	if (rank == root)
//	{
//		/*printf("Unesite %d celih brojeva: ", size);
//		fflush(stdout);*/
//
//		niz = (int*)malloc(sizeof(int) * size);
//
//		for (int i = 0; i < size; i++)
//			niz[i] = i;
//	}
//
//	MPI_Scatter(niz, 1, MPI_INT, &value, 1, MPI_INT, root, MPI_COMM_WORLD);
//	printf("Proces [%d] sadrzi %d\n", rank, value);
//
//	if (rank == root)
//		free(niz);
//
//	MPI_Finalize();
//	return 0;
//}