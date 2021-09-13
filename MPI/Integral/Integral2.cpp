#include <mpi.h>
#include <stdlib.h>
#include <stdio.h>

int main(int argc, char** argv)
{
	int rank, size, master = 0, N;
	double dx, x, local_sum, sum;
	MPI_Init(&argc, &argv);
	MPI_Comm_size(MPI_COMM_WORLD, &size);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	if (master == rank)
	{
		printf("Broj odmeraka: ");
		fflush(stdout);
		scanf_s("%d", &N);
	}

	MPI_Bcast(&N, 1, MPI_INT, master, MPI_COMM_WORLD);
	dx = 1.0 / (double)N;
	x = local_sum = 0;
	for (int i = rank; i < N; i += size)
	{
		x = dx * ((double)i + 0.5);
		local_sum += 4.0 / (1.0 + x * x);
	}
	local_sum *= dx;
	MPI_Reduce(&local_sum, &sum, 1, MPI_DOUBLE, MPI_SUM, master, MPI_COMM_WORLD);

	if (rank == master)
		printf("Integral iznosi: %.32f\n", sum);

	MPI_Finalize();
	return 0;
}