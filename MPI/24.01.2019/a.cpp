//#include <mpi.h>
//#include <stdlib.h>
//#include <stdio.h>
//
//int main(int argc, char** argv)
//{
//	int rank, size, root = 0;
//	int* a = nullptr, * b = nullptr,
//		* local_a = nullptr, * local_b = nullptr,
//		* dist_a = nullptr, * dist_b = nullptr;
//	int m, l, scal, local_scal;
//
//	MPI_Init(&argc, &argv);
//	MPI_Comm_size(MPI_COMM_WORLD, &size);
//	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
//
//	if (rank == root)
//	{
//		printf("Unesite m: ");
//		fflush(stdout);
//		scanf_s("%d", &m);
//
//		a = (int*)malloc(sizeof(int) * m);
//		b = (int*)malloc(sizeof(int) * m);
//
//		for (int i = 0; i < m; i++)
//		{
//			a[i] = i;
//			b[i] = 1;
//		}
//	}
//
//	MPI_Bcast(&m, 1, MPI_INT, root, MPI_COMM_WORLD);
//
//	l = m / size;
//	local_a = (int*)malloc(sizeof(int) * l);
//	local_b = (int*)malloc(sizeof(int) * l);
//
//	if (rank == root)
//	{
//		dist_a = (int*)malloc(sizeof(int) * m);
//		dist_b = (int*)malloc(sizeof(int) * m);
//
//		for (int i = 0; i < size; i++)
//		{
//			for (int j = 0; j < l; j++) {
//				dist_a[i * l + j] = a[j * size + i];
//				dist_b[i * l + j] = b[j * size + i];
//			}
//		}
//	}
//
//	MPI_Scatter(dist_a, l, MPI_INT, local_a, l, MPI_INT, root, MPI_COMM_WORLD);
//	MPI_Scatter(dist_b, l, MPI_INT, local_b, l, MPI_INT, root, MPI_COMM_WORLD);
//
//	local_scal = 0;
//	for (int i = 0; i < l; i++)
//		local_scal += local_a[i] * local_b[i];
//
//	MPI_Reduce(&local_scal, &scal, 1, MPI_INT, MPI_SUM, root, MPI_COMM_WORLD);
//
//	if (rank == root)
//	{
//		printf("Rezultat iznosi: %d\n", scal);
//
//		free(a);
//		free(b);
//		free(dist_a);
//		free(dist_b);
//	}
//
//	free(local_a);
//	free(local_b);
//
//	MPI_Finalize();
//	return 0;
//}