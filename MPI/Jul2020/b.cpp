//#include <stdio.h>
//#include <mpi.h>
//#include <stdlib.h>
//#include <math.h>
//
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
//	if (rank == master)
//		value = 10;//rand() % 51 + 50;
//
//	MPI_Bcast(&value, 1, MPI_INT, master, MPI_COMM_WORLD);
//	printf("Proces [%d]: %d\n", rank, value);
//	fflush(stdout);
//
//	int* podaci = (int*)malloc(sizeof(int) * value);
//	if (rank == master)
//		for (int i = 0; i < value; i++)
//			podaci[i] = i;
//
//	MPI_Bcast(podaci, value, MPI_INT, master, MPI_COMM_WORLD);
//	printf("Proces [%d] podaci: [", rank);
//	for (int i = 0; i < value; i++)
//		printf("\t%d", podaci[i]);
//	printf("\t]\n");
//	fflush(stdout);
//
//	// I Način => p == size
//	int* p_ti_stepen = (int*)malloc(sizeof(int) * value);
//	MPI_Reduce(podaci, p_ti_stepen, value, MPI_INT, MPI_PROD, master, MPI_COMM_WORLD);
//	if (master == rank)
//	{
//		printf("p-ti stepen podataka: [");
//		for (int i = 0; i < value; i++)
//			printf("\t%d", p_ti_stepen[i]);
//		printf("\t]\n");
//		fflush(stdout);
//	}
//
//	int* proizvod = (int*)malloc(sizeof(int) * value);
//	MPI_Reduce(podaci, proizvod, value, MPI_INT, MPI_SUM, master, MPI_COMM_WORLD);
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
//			podaci[i] = 1;
//
//	MPI_Scan(podaci, p_ti_stepen, value, MPI_INT, MPI_PROD, MPI_COMM_WORLD);
//	printf("Proces [%d] rank-ti stepen podataka: [", rank);
//	for (int i = 0; i < value; i++)
//		printf("\t%d", p_ti_stepen[i]);
//	printf("\t]\n");
//	fflush(stdout);
//
//	if (rank == 0)
//		for (int i = 0; i < value; i++)
//			podaci[i] = 0;
//
//	MPI_Scan(podaci, proizvod, value, MPI_INT, MPI_SUM, MPI_COMM_WORLD);
//	printf("Proces [%d] proizvod rank * podaci: [", rank);
//	for (int i = 0; i < value; i++)
//		printf("\t%d", proizvod[i]);
//	printf("\t]\n");
//	fflush(stdout);
//
//	free(p_ti_stepen);
//	free(proizvod);
//	free(podaci);
//
//	MPI_Finalize();
//	return 0;
//}