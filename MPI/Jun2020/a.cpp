//#include <mpi.h>
//#include <stdlib.h>
//#include <stdio.h>
//
//#define N 10
//
//bool isPrime(int num);
//
//int main(int argc, char** argv)
//{
//	int rank, size, master = 0;
//
//	MPI_Init(&argc, &argv);
//	MPI_Comm_size(MPI_COMM_WORLD, &size);
//	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
//
//	int local_sum = 0, sum;
//	struct {
//		int primes;
//		int rank;
//	} in = { 0, rank }, out;
//
//	// Testing purposes
//	int* buff = (int*) malloc(sizeof(int) * N * N / size);
//	int l = 0;
//	// Testing purposes
//
//	// Oponasa i petlju (mpi + i petlja)
//	for (int i = rank; i < N; i+=size)
//	{
//		// Oponaša j petlju
//		for (int k = 0; k < size; k++) // Oponaša mpi
//		{
//			for (int j = k; j < N; j+=size) // Oponaša i
//			{
//				buff[l++] = i + j;
//				local_sum += buff[l - 1];
//				if (isPrime(buff[l - 1]))
//					in.primes++;
//			}
//		}
//		// Oponaša j petlju
//	}
//	// Oponasa i petlju (mpi + i petlja)
//
//	MPI_Reduce(&in, &out, 1, MPI_2INT, MPI_MINLOC, master, MPI_COMM_WORLD);
//	MPI_Bcast(&out, 1, MPI_2INT, master, MPI_COMM_WORLD);
//
//	MPI_Reduce(&local_sum, &sum, 1, MPI_INT, MPI_SUM, out.rank, MPI_COMM_WORLD);
//
//	if (rank == out.rank)
//	{
//		printf("Proces [%d] ima najmanje sabiraka koji su prosti brojevi (%d)\n", rank, out.primes);
//		printf("Rezultat iznosi: %d\n", sum);
//		fflush(stdout);
//	}
//
//	// Testing purposes
//	printf("Proces [%d] sabirci = [", rank);
//	for (int i = 0; i < l; i++)
//		printf("\t%d", buff[i]);
//	printf("\t]\n");
//	fflush(stdout);
//
//	free(buff);
//	// Testing purposes
//	MPI_Finalize();
//	return 0;
//}
//
//bool isPrime(int numb)
//{
//	if (numb <= 1)
//		return false;
//
//	if (numb <= 3)
//		return true;
//
//	if (numb % 2 == 0 || numb % 3 == 0)
//		return false;
//
//	for (int i = 5; i * i <= numb; i += 6)
//		if (numb % i == 0 || numb % (i + 2) == 0)
//			return false;
//
//	return true;
//}