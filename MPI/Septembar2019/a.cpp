//#include <mpi.h>
//#include <stdio.h>
//#include <stdlib.h>
//
//#define n 10
//#define k 10
//#define s 2
//// size = s * k
//
//int main(int argc, char** argv)
//{
//	int rank, size, master = 0;
//	int A[n][k], b[k], c[n];
//	int local_A[n][s], local_b[s], local_c[n];
//	int local_row_prod[n], row_prod[n];
//
//	struct {
//		int val;
//		int rank;
//	}in, out;
//
//	MPI_Init(&argc, &argv);
//	MPI_Comm_size(MPI_COMM_WORLD, &size);
//	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
//
//	if (rank == master)
//	{
//		for (int i = 0; i < n; i++)
//			for (int j = 0; j < k; j++)
//				A[i][j] = i * k + j;
//
//		for (int i = 0; i < k; i++)
//			b[i] = 1;
//
//		printf("A:\n");
//		for (int i = 0; i < n; i++)
//		{
//			printf("|");
//			for(int j = 0;j < k; j++)
//				printf("\t%d", A[i][j]);
//			printf("\t|\n");
//		}
//
//		printf("b = [");
//		for (int i = 0; i < n; i++)
//			printf("\t%d", b[i]);
//		printf("\t]\n");
//	}
//
//	for (int i = 0; i < n; i++)
//		MPI_Scatter(&A[i][0], s, MPI_INT, &local_A[i][0], s, MPI_INT, master, MPI_COMM_WORLD);
//	MPI_Scatter(&b[0], s, MPI_INT, &local_b[0], s, MPI_INT, master, MPI_COMM_WORLD);
//
//	in = { INT16_MIN, rank };
//	for (int i = 0; i < n; i++)
//	{
//		local_row_prod[i] = 1;
//		local_c[i] = 0;
//		for (int j = 0; j < s; j++)
//		{
//			local_row_prod[i] *= local_A[i][j];
//			local_c[i] += local_A[i][j] * local_b[j];
//
//			if (local_A[i][j] > in.val)
//				in.val = local_A[i][j];
//		}
//	}
//
//	MPI_Reduce(&in, &out, 1, MPI_2INT, MPI_MAXLOC, master, MPI_COMM_WORLD);
//	MPI_Bcast(&out, 1, MPI_2INT, master, MPI_COMM_WORLD);
//
//	MPI_Reduce(&local_c[0], &c[0], n, MPI_INT, MPI_SUM, out.rank, MPI_COMM_WORLD);
//	MPI_Reduce(&local_row_prod[0], &row_prod[0], n, MPI_INT, MPI_PROD, out.rank, MPI_COMM_WORLD);
//
//	if(rank == out.rank)
//	{
//		printf("Proces [%d]\n", rank);
//
//		printf("Maksimalna vrednost elemenata matrice A iznosi: %d\n", out.val);
//
//		printf("Proizvod elemenata vrste matrice A = [");
//		for (int i = 0; i < n; i++)
//			printf("\t%d", row_prod[i]);
//		printf("\t]\n");
//		
//		printf("c = [");
//		for (int i = 0; i < n; i++)
//			printf("\t%d", c[i]);
//		printf("\t]\n");
//	}
//
//	MPI_Finalize();
//	return 0;
//}