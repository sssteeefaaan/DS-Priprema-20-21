//#include <mpi.h>
//#include <stdio.h>
//#include <stdlib.h>
//#include <math.h>
//
//int main(int argc, char** argv)
//{
//	int rank, size, root = 0;
//	int val, * x = nullptr, * y = nullptr, * z = nullptr, n;
//
//	MPI_Status status;
//	MPI_Init(&argc, &argv);
//	MPI_Comm_size(MPI_COMM_WORLD, &size);
//	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
//
//	if (rank == root)
//		val = rank;
//
//	MPI_Bcast(&val, 1, MPI_INT, root, MPI_COMM_WORLD);
//	printf("Proces [%d] val: %d\n", rank, val);
//
//	if (rank == root)
//	{
//		printf("Uneti n: ");
//		fflush(stdout);
//		scanf_s("%d", &n);
//	}
//
//	MPI_Bcast(&n, 1, MPI_INT, root, MPI_COMM_WORLD);
//	x = (int*)malloc(sizeof(int) * n);
//
//	if(rank == root)
//		for (int i = 0; i < n; i++)
//			x[i] = i + 1;
//
//	MPI_Bcast(x, n, MPI_INT, root, MPI_COMM_WORLD);
//	printf("Proces [%d] x: [", rank);
//	for (int i = 0; i < n; i++)
//		printf("\t%d", x[i]);
//	printf("\t]\n");
//	fflush(stdout);
//
//	y = (int*)malloc(sizeof(int) * n);
//	MPI_Scan(x, y, n, MPI_INT, MPI_PROD, MPI_COMM_WORLD);
//	printf("Proces [%d]: %d-ti stepen vektora: [", rank, rank + 1);
//	for (int i = 0; i < n; i++)
//		printf("\t%d", y[i]);
//	printf("\t]\n");
//	fflush(stdout);
//
//	z = (int*)malloc(sizeof(int) * n);
//	MPI_Scan(x, z, n, MPI_INT, MPI_SUM, MPI_COMM_WORLD);
//	printf("Proces [%d]: %d * vektor: [", rank, rank + 1);
//	for (int i = 0; i < n; i++)
//		printf("\t%d", z[i]);
//	printf("\t]\n");
//	fflush(stdout);
//
//	free(x);
//	free(y);
//	free(z);
//
//	MPI_Finalize();
//	return 0;
//}