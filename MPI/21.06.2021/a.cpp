//#include <stdio.h>
//#include <stdlib.h>
//#include <string>
//#include <mpi.h>
//
//int main(int argc, char** argv)
//{
//	int rank, size, root = 0;
//	int k, m, l;
//	int* A = nullptr, * b = nullptr, * c = nullptr, * sum = nullptr;
//	int* local_A = nullptr, * local_b = nullptr, * local_c = nullptr, * local_sum = nullptr;
//	struct _2INT {
//		int value;
//		int rank;
//	} max, local_max;
//	MPI_Status status;
//
//	MPI_Init(&argc, &argv);
//	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
//	MPI_Comm_size(MPI_COMM_WORLD, &size);
//
//	if (rank == root) {
//		printf("Unesite k:\n");
//		fflush(stdout);
//		scanf_s("%d", &k);
//
//		printf("Unesite m:\n");
//		fflush(stdout);
//		scanf_s("%d", &m);
//
//		A = (int*)malloc(sizeof(int) * k * m);
//		for (int i = 0; i < k; i++)
//			for (int j = 0; j < m; j++)
//				A[i * m + j] = i * m + (j + 1);
//
//		printf("A:\n");
//		for (int i = 0; i < k; i++)
//		{
//			printf("|");
//			for (int j = 0; j < m; j++)
//				printf("\t%d", A[i * m + j]);
//			printf("|\n");
//		}
//
//		b = (int*)malloc(sizeof(int) * m);
//		for (int i = 0; i < m; i++)
//			b[i] = 1;
//
//		printf("b = [");
//		for (int i = 0; i < m; i++)
//			printf("\t%d", b[i]);
//		printf("]\n");
//
//		fflush(stdout);
//	}
//
//	MPI_Bcast(&k, 1, MPI_INT, root, MPI_COMM_WORLD);
//	MPI_Bcast(&m, 1, MPI_INT, root, MPI_COMM_WORLD);
//
//	l = m / size;
//
//	local_A = (int*)malloc(sizeof(int) * k * l);
//	local_b = (int*)malloc(sizeof(int) * l);
//
//	if (rank == root)
//	{
//		// Svaki proces dobija po l kolona matrice Akxm
//		// m = size * l
//
//		for (int p = 0; p < size; p++) {
//			if (p != root)
//			{
//				for (int j = 0; j < l; j++)
//					for (int i = 0; i < k; i++)
//						MPI_Send(&A[i * m + p * l + j], 1, MPI_INT, p, 0, MPI_COMM_WORLD);
//			}
//			else
//			{
//				for (int j = 0; j < l; j++)
//					for (int i = 0; i < k; i++)
//						local_A[i * l + j] = A[i * m + root * l + j];
//			}
//		}
//	}
//	else {
//		for (int j = 0; j < l; j++)
//			for (int i = 0; i < k; i++)
//				MPI_Recv(&local_A[i * l + j], 1, MPI_INT, root, 0, MPI_COMM_WORLD, &status);
//	}
//
//	MPI_Scatter(b, l, MPI_INT, local_b, l, MPI_INT, root, MPI_COMM_WORLD);
//
//	local_c = (int*)malloc(sizeof(int) * l);
//	local_sum = (int*)malloc(sizeof(int) * k);
//	local_max.rank = rank;
//	local_max.value = INT16_MIN;
//
//	for (int i = 0; i < k; i++)
//		local_sum[i] = 0;
//	for (int i = 0; i < l; i++)
//		local_c[i] = 0;
//
//	for (int j = 0; j < l; j++) {
//		for (int i = 0; i < k; i++) {
//			local_c[j] += local_A[i * l + j] * local_b[j];
//			local_sum[i] += local_A[i * l + j];
//
//			if (local_A[i * l + j] > local_max.value)
//				local_max.value = local_A[i * l + j];
//		}
//	}
//
//	printf("Proces [%d]\nlocal_A:\n", rank);
//	for (int i = 0; i < k; i++)
//	{
//		printf("|");
//		for (int j = 0; j < l; j++)
//			printf("\t%d", local_A[i * l + j]);
//		printf("|\n");
//	}
//
//	printf("local_b = [");
//	for (int i = 0; i < l; i++)
//		printf("\t%d", local_b[i]);
//	printf("]\n");
//
//	printf("local_max = %d\n", local_max.value);
//	printf("local_sum = [");
//	for (int i = 0; i < k; i++)
//		printf("\t%d", local_sum[i]);
//	printf("]\n");
//
//	fflush(stdout);
//
//	MPI_Reduce(&local_max, &max, 1, MPI_2INT, MPI_MAXLOC, root, MPI_COMM_WORLD);
//	MPI_Bcast(&max, 1, MPI_2INT, root, MPI_COMM_WORLD);
//
//	if (max.rank == rank) {
//		c = (int*)malloc(sizeof(int) * m);
//		sum = (int*)malloc(sizeof(int) * k);
//	}
//
//	MPI_Gather(local_c, l, MPI_INT, c, l, MPI_INT, max.rank, MPI_COMM_WORLD);
//	MPI_Reduce(local_sum, sum, k, MPI_INT, MPI_SUM, max.rank, MPI_COMM_WORLD);
//
//	if (rank == max.rank)
//	{
//		printf("Proces [%d] ima kolonu sa maksimalnim elementom matrice A i to je element: %d\n", max.rank, max.value);
//
//		printf("Rez: [");
//		for (int i = 0; i < m; i++)
//			printf("\t%d", c[i]);
//		printf("]\n");
//
//		printf("Sum: [");
//		for (int i = 0; i < k; i++)
//			printf("\t%d", sum[i]);
//		printf("]\n");
//
//		free(sum);
//	}
//
//	free(local_A);
//	free(local_b);
//	free(local_c);
//	free(local_sum);
//
//	if (root == rank)
//	{
//		free(A);
//		free(b);
//		free(c);
//	}
//
//	MPI_Finalize();
//	return 0;
//}