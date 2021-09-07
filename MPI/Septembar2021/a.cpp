//#include <mpi.h>
//#include <stdio.h>
//#include <stdlib.h>
//
//#define k 6
//#define m 3
//#define n 5
//// size == 3
//#define l 2
//
//int main(int argc, char** argv)
//{
//	int rank, size, master = 0;
//
//	int A[k][m], B[m][n], C[k][n];
//	int local_A[l][m], local_C[l][n], local_column_prod[m], column_prod[m];
//
//	struct temp_max {
//		int value;
//		int rank;
//	} in, out;
//
//	for (int i = 0; i < m; i++)
//		local_column_prod[i] = 1;
//
//	MPI_Init(&argc, &argv);
//	MPI_Comm_size(MPI_COMM_WORLD, &size);
//	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
//
//	if (rank == master)
//	{
//		printf("Matrica A:\n");
//		for (int i = 0; i < k; i++) {
//			printf("|\t");
//			for (int j = 0; j < m; j++)
//			{
//				A[i][j] = i + j;
//				printf("%d\t", A[i][j]);
//			}
//			printf("|\n");
//		}
//
//		printf("Matrica B:\n");
//		for (int i = 0; i < m; i++) {
//			printf("|\t");
//			for (int j = 0; j < n; j++)
//			{
//				B[i][j] = i + j;
//				printf("%d\t", B[i][j]);
//			}
//			printf("|\n");
//		}
//
//		fflush(stdout);
//	}
//
//	MPI_Scatter(&A[0][0], l* m, MPI_INT, &local_A[0][0], l* m, MPI_INT, master, MPI_COMM_WORLD);
//	MPI_Bcast(&B[0][0], m* n, MPI_INT, master, MPI_COMM_WORLD);
//
//	in = temp_max{ INT16_MIN, rank };
//
//	for (int i = 0; i < l; i++)
//	{
//		for (int j = 0; j < n; j++)
//		{
//			local_C[i][j] = 0;
//			for (int q = 0; q < m; q++)
//				local_C[i][j] += local_A[i][q] * B[q][j];
//
//			if (local_C[i][j] > in.value)
//				in.value =  local_C[i][j];
//		}
//
//		for (int j = 0; j < m; j++)
//			local_column_prod[j] *= local_A[i][j];
//	}
//
//	MPI_Reduce(&in, &out, 1, MPI_2INT, MPI_MAXLOC, master, MPI_COMM_WORLD);
//	MPI_Bcast(&out, 1, MPI_2INT, master, MPI_COMM_WORLD);
//
//	MPI_Gather(&local_C[0][0], l* n, MPI_INT, &C[0][0], l* n, MPI_INT, out.rank, MPI_COMM_WORLD);
//	MPI_Reduce(&local_column_prod[0], &column_prod[0], m, MPI_INT, MPI_PROD, out.rank, MPI_COMM_WORLD);
//
//	if (rank == out.rank)
//	{
//		printf("Proces [%d] sadrzi maksimum matrice C i to je %d\n", out.rank, out.value);
//
//		printf("Matrica C:\n");
//		for (int i = 0; i < k; i++)
//		{
//			printf("|\t");
//			for (int j = 0; j < n; j++)
//				printf("%d\t", C[i][j]);
//			printf("|\n");
//		}
//
//		printf("Proizvod kolona matrice A:\n[\t");
//		for (int i = 0; i < m; i++)
//			printf("%d\t", column_prod[i]);
//		printf("]\n");
//	}
//
//	MPI_Finalize();
//	return 0;
//}