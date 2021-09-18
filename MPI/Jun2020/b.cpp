#include <mpi.h>
#include <stdlib.h>
#include <stdio.h>

#define N 10

bool isPrime(int num);

int main(int argc, char** argv)
{
	int rank, size, master = 0;
	MPI_Status stat;
	MPI_Request req;

	MPI_Init(&argc, &argv);
	MPI_Comm_size(MPI_COMM_WORLD, &size);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	int local_sum = 0, sum;
	struct {
		int primes;
		int rank;
	} in = { 0, rank }, out;

	// Oponasa i petlju (mpi + i petlja)
	for (int i = rank; i < N; i += size)
	{
		// Oponaša j petlju
		for (int k = 0; k < size; k++) // Oponaša mpi
		{
			for (int j = k; j < N; j += size) // Oponaša i
			{
				local_sum += i + j;
				if (isPrime(i + j))
					in.primes++;
			}
		}
		// Oponaša j petlju
	}
	// Oponasa i petlju (mpi + i petlja)

	// MPI_Reduce(&in, &out, 1, MPI_2INT, MPI_MINLOC, master, MPI_COMM_WORLD);
	MPI_Isend(&in, 1, MPI_2INT, master, 0, MPI_COMM_WORLD, &req);
	if (rank == master)
	{
		MPI_Recv(&out, 1, MPI_2INT, 0, 0, MPI_COMM_WORLD, &stat);
		for (int p = 1; p < size; p++)
		{
			MPI_Recv(&in, 1, MPI_2INT, p, 0, MPI_COMM_WORLD, &stat);
			if (out.primes > in.primes)
				out = in;
		}
	}

	// MPI_Bcast(&out, 1, MPI_2INT, master, MPI_COMM_WORLD);
	if (rank == master)
		for (int p = 0; p < size; p++)
			MPI_Isend(&out, 1, MPI_2INT, p, 0, MPI_COMM_WORLD, &req);
	MPI_Recv(&out, 1, MPI_2INT, master, 0, MPI_COMM_WORLD, &stat);

	// MPI_Reduce(&local_sum, &sum, 1, MPI_INT, MPI_SUM, out.rank, MPI_COMM_WORLD);
	MPI_Isend(&local_sum, 1, MPI_INT, out.rank, 0, MPI_COMM_WORLD, &req);
	if (rank == out.rank)
	{
		MPI_Recv(&sum, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &stat);
		for (int p = 1; p < size; p++)
		{
			MPI_Recv(&local_sum, 1, MPI_INT, p, 0, MPI_COMM_WORLD, &stat);
			sum += local_sum;
		}
	}

	if (rank == out.rank)
	{
		printf("Proces [%d] ima najmanje sabiraka koji su prosti brojevi (%d)\n", rank, out.primes);
		printf("Rezultat iznosi: %d\n", sum);
		fflush(stdout);
	}

	MPI_Finalize();
	return 0;
}

bool isPrime(int numb)
{
	if (numb <= 1)
		return false;

	if (numb <= 3)
		return true;

	if (numb % 2 == 0 || numb % 3 == 0)
		return false;

	for (int i = 5; i * i <= numb; i += 6)
		if (numb % i == 0 || numb % (i + 2) == 0)
			return false;

	return true;
}