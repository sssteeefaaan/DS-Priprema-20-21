//#include <mpi.h>
//#include <stdio.h>
//#include <stdlib.h>
//#include <float.h>
//
//#define n 3
//#define k 5 // mora biti jednako broju procesa == size
//
//int main(int argc, char** argv)
//{
//	int rank, size, master = 0;
//
//	double A[n][k], b[k], c[n];
//	double local_A[n], local_b, local_c[n];
//	
//	struct {
//		double val;
//		int rank;
//	}in, out;
//
//	double row_prod[n];
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
//			for (int j = 0; j < k; j++)
//				printf("\t%f", A[i][j]);
//			printf("\t|\n");
//		}
//
//		printf("b = [");
//		for (int i = 0; i < k; i++)
//			printf("\t%f", b[i]);
//		printf("\t]\n");
//
//		fflush(stdout);
//	}
//
//	for(int i = 0; i < n; i++)
//		MPI_Scatter(&A[i][0], 1, MPI_DOUBLE, &local_A[i], 1, MPI_DOUBLE, master, MPI_COMM_WORLD);
//
//	MPI_Scatter(&b[0], 1, MPI_DOUBLE, &local_b, 1, MPI_DOUBLE, master, MPI_COMM_WORLD);
//
//	in = { DBL_MIN, rank };
//
//	for (int i = 0; i < n; i++)
//	{
//		local_c[i] = local_A[i] * local_b;
//		if (local_A[i] < in.val)
//			in.val = local_A[i];
//	}
//
//	MPI_Reduce(&in, &out, 1, MPI_DOUBLE_INT, MPI_MINLOC, master, MPI_COMM_WORLD);
//	MPI_Bcast(&out, 1, MPI_DOUBLE_INT, master, MPI_COMM_WORLD);
//
//	MPI_Reduce(&local_c[0], &c[0], n, MPI_DOUBLE, MPI_SUM, out.rank, MPI_COMM_WORLD);
//	MPI_Reduce(&local_A[0], &row_prod[0], n, MPI_DOUBLE, MPI_PROD, out.rank, MPI_COMM_WORLD);
//
//	if(rank == out.rank)
//	{
//		printf("*****Proces [%d]\n", rank);
//		printf("Minimalna vrednost matrice A: %f\n", out.val);
//
//		printf("c = [");
//		for (int i = 0; i < n; i++)
//			printf("\t%f", c[i]);
//		printf("\t]\n");
//
//		printf("proizvod elemenata vrste = [");
//		for (int i = 0; i < n; i++)
//			printf("\t%f", row_prod[i]);
//		printf("\t]\n");
//	}
//	MPI_Finalize();
//	return 0;
//}