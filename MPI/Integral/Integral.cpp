//#include <mpi.h>
//#include <stdio.h>
//#include <stdlib.h>
//
//int main(int argc, char** argv)
//{
//	int rank, size, root = 0;
//	int N;
//	double precision, x, Fx, local_sum, sum;
//
//	MPI_Init(&argc, &argv);
//	MPI_Comm_size(MPI_COMM_WORLD, &size);
//	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
//
//	if (rank == root)
//	{
//		printf("Unesite N: ");
//		fflush(stdout);
//		scanf_s("%d", &N);
//	}
//
//	MPI_Bcast(&N, 1, MPI_INT, root, MPI_COMM_WORLD);
//
//	precision = 1 / (double) N;
//	local_sum = 0;
//	for (int i = rank; i < N; i += size)
//	{
//		x = precision * ((double)i + 0.5);
//		Fx = 4.0 / (1.0 + x * x);
//		local_sum += Fx;//precision * Fx;
//	}
//	local_sum *= precision;
//
//	MPI_Reduce(&local_sum, &sum, 1, MPI_DOUBLE, MPI_SUM, root, MPI_COMM_WORLD);
//
//	if (rank == root)
//		printf("Suma iznosi: %.32f", sum);
//
//	MPI_Finalize();
//	return 0;
//}