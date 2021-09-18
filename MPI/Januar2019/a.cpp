//#include <mpi.h>
//#include <stdio.h>
//#include <stdlib.h>
//
//#define m 16
//
//int main(int argc, char** argv)
//{
//	int rank, size, master = 0;
//	int a[m], b[m], scal;
//	int* local_a = nullptr, * local_b = nullptr, local_scal;
//
//	MPI_Init(&argc, &argv);
//	MPI_Comm_size(MPI_COMM_WORLD, &size);
//	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
//
//	if (rank == master) {
//		for (int i = 0; i < m; i++)
//		{
//			a[i] = i;
//			b[i] = 1;
//		}
//	}
//
//	int l = m / size;
//	local_a = (int*)malloc(sizeof(int) * l);
//	local_b = (int*)malloc(sizeof(int) * l);
//
	//for (int i = 0; i < l; i++) {
	//	MPI_Scatter(&a[size * i], 1, MPI_INT, &local_a[i], 1, MPI_INT, master, MPI_COMM_WORLD);
	//	MPI_Scatter(&b[size * i], 1, MPI_INT, &local_b[i], 1, MPI_INT, master, MPI_COMM_WORLD);
	//}
//
//
//	printf("Proces [%d]\n", rank);
//	printf("local_a = [");
//
//	local_scal = 0;
//	for (int i = 0; i < l; i++)
//	{
//		printf("\t%d", local_a[i]);
//		local_scal += local_a[i] * local_b[i];
//	}
//
//	printf("\t]\n");
//	fflush(stdout);
//
//	MPI_Reduce(&local_scal, &scal, 1, MPI_INT, MPI_SUM, master, MPI_COMM_WORLD);
//
//	if (rank == master)
//		printf("a dot b = %d\n", scal);
//
//	free(local_a);
//	free(local_b);
//
//	MPI_Finalize();
//
//	return 0;
//}