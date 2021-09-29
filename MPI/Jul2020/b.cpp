//#include <stdio.h>
//#include <mpi.h>
//#include <stdlib.h>
//#include <math.h>
//#include <time.h>
//
//int main(int argc, char** argv)
//{
//	srand(time(NULL));
//
//	int rank, size, master = 0;
//	MPI_Status stat;
//	int value;
//
//	MPI_Init(&argc, &argv);
//	MPI_Comm_size(MPI_COMM_WORLD, &size);
//	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
//
//	if (rank == master)
//	{
//		printf("Unesite velicinu niza: ");
//		fflush(stdout);
//		scanf_s("%d", &value);
//	}
//
//	MPI_Bcast(&value, 1, MPI_INT, master, MPI_COMM_WORLD);
//	printf("Proces [%d]: %d\n", rank, value);
//	fflush(stdout);
//
//	int* x = (int*)malloc(sizeof(int) * value);
//	if (rank == master)
//		for (int i = 0; i < value; i++)
//			x[i] = rand() % 11;
//
//	MPI_Bcast(x, value, MPI_INT, master, MPI_COMM_WORLD);
//	printf("Proces [%d] podaci: [", rank);
//	for (int i = 0; i < value; i++)
//		printf("\t%d", x[i]);
//	printf("\t]\n");
//	fflush(stdout);
//
//	// I Način => p == size
//	int* stepen = (int*)malloc(sizeof(int) * value);
//	MPI_Reduce(x, stepen, value, MPI_INT, MPI_PROD, master, MPI_COMM_WORLD);
//	if (master == rank)
//	{
//		printf("p-ti stepen podataka: [");
//		for (int i = 0; i < value; i++)
//			printf("\t%d", stepen[i]);
//		printf("\t]\n");
//		fflush(stdout);
//	}
//
//	int* proizvod = (int*)malloc(sizeof(int) * value);
//	MPI_Reduce(x, proizvod, value, MPI_INT, MPI_SUM, master, MPI_COMM_WORLD);
//	if (master == rank)
//	{
//		printf("Proizvod size * podaci: [");
//		for (int i = 0; i < value; i++)
//			printf("\t%d", proizvod[i]);
//		printf("\t]\n");
//		fflush(stdout);
//	}
//
//	// II Način => p == rank
//	if (rank == 0)
//		for (int i = 0; i < value; i++)
//			x[i] = 1;
//
//	MPI_Scan(x, stepen, value, MPI_INT, MPI_PROD, MPI_COMM_WORLD);
//	printf("Proces [%d] rank-ti stepen podataka: [", rank);
//	for (int i = 0; i < value; i++)
//		printf("\t%d", stepen[i]);
//	printf("\t]\n");
//	fflush(stdout);
//
//	if (rank == 0)
//		for (int i = 0; i < value; i++)
//			x[i] = 0;
//
//	MPI_Scan(x, proizvod, value, MPI_INT, MPI_SUM, MPI_COMM_WORLD);
//	printf("Proces [%d] proizvod rank * podaci: [", rank);
//	for (int i = 0; i < value; i++)
//		printf("\t%d", proizvod[i]);
//	printf("\t]\n");
//	fflush(stdout);
//
//	free(stepen);
//	free(proizvod);
//	free(x);
//
//	MPI_Finalize();
//	return 0;
//}