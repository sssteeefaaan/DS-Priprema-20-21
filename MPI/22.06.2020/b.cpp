#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

bool isPrime(int);

int main(int argc, char** argv)
{
    int rank, size, root = 0;
    int N, local_sum, sum;

    struct {
        int value;
        int rank;
    } local_primes, primes;

    MPI_Status status;

    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    if (rank == root)
    {
        printf("Unesite N:");
        fflush(stdout);
        scanf_s("%d", &N);
    }

    // MPI_Bcast(&N, 1, MPI_INT, root, MPI_COMM_WORLD);
    if (rank == root)
    {
        for (int p = 0; p < size; p++)
            if (p != root)
                MPI_Send(&N, 1, MPI_INT, p, 0, MPI_COMM_WORLD);
    }
    else
        MPI_Recv(&N, 1, MPI_INT, root, 0, MPI_COMM_WORLD, &status);

    local_sum = 0;
    local_primes.value = 0;
    local_primes.rank = rank;

    for (int i = rank; i < N; i += size) {
        for (int k = 0; k < size; k++) {
            for (int j = k; j < N; j += size) {
                local_sum += i + j;
                if (isPrime(i + j))
                    local_primes.value++;
            }
        }
    }

    // MPI_Reduce(&local_primes, &primes, 1, MPI_2INT, MPI_MINLOC, root, MPI_COMM_WORLD);
    if (rank == root)
    {
        primes = local_primes;
        for (int p = 0; p < size; p++) {
            if (p != root) {
                MPI_Recv(&local_primes, 1, MPI_2INT, p, 0, MPI_COMM_WORLD, &status);
                if (primes.value > local_primes.value)
                    primes = local_primes;
            }
        }
    }
    else
        MPI_Send(&local_primes, 1, MPI_2INT, root, 0, MPI_COMM_WORLD);

    // MPI_Bcast(&primes, 1, MPI_2INT, root, MPI_COMM_WORLD);
    if (rank == root)
    {
        for (int p = 0; p < size; p++)
            if (p != root)
                MPI_Send(&primes, 1, MPI_2INT, p, 0, MPI_COMM_WORLD);
    }
    else
        MPI_Recv(&primes, 1, MPI_2INT, root, 0, MPI_COMM_WORLD, &status);

    // MPI_Reduce(&local_sum, &sum, 1, MPI_INT, MPI_SUM, primes.rank, MPI_COMM_WORLD);
    if (rank == primes.rank)
    {
        sum = local_sum;
        for (int p = 0; p < size; p++)
        {
            if (p != primes.rank) {
                MPI_Recv(&local_sum, 1, MPI_INT, p, 0, MPI_COMM_WORLD, &status);
                sum += local_sum;
            }
        }
    }
    else
        MPI_Send(&local_sum, 1, MPI_INT, primes.rank, 0, MPI_COMM_WORLD);

    if (rank == primes.rank)
    {
        printf("Suma iznosi: %d\n", sum);
        printf("Proces sa najmanjim brojem prostih brojeva: %d\n", primes.rank);
        printf("Broj prostih brojeva: %d", primes.value);
    }

    MPI_Finalize();
    return 0;
}

bool isPrime(int number)
{
    if (number <= 1)
        return false;

    int root = sqrt(number);

    for (int i = 2; i < root; i++)
        if (number % i == 0)
            return false;

    return true;
}