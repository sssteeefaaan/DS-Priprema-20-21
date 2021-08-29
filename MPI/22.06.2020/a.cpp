//#include <mpi.h>
//#include <stdio.h>
//#include <stdlib.h>
//#include <math.h>
//
//bool isPrime(int);
//
//int main(int argc, char** argv)
//{
//    int rank, size, root = 0;
//    int N, local_sum, sum;
//
//    struct {
//        int value;
//        int rank;
//    } local_primes, primes;
//
//    MPI_Init(&argc, &argv);
//    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
//    MPI_Comm_size(MPI_COMM_WORLD, &size);
//
//    if (rank == root)
//    {
//        printf("Unesite N:");
//        fflush(stdout);
//        scanf_s("%d", &N);
//    }
//
//    MPI_Bcast(&N, 1, MPI_INT, root, MPI_COMM_WORLD);
//
//    local_sum = 0;
//    local_primes.value = 0;
//    local_primes.rank = rank;
//
//    for (int i = rank; i < N; i+=size) {
//        for (int k = 0; k < size; k++){
//            for (int j = k; j < N; j += size){
//                local_sum += i + j;
//                if (isPrime(i + j))
//                    local_primes.value++;
//            }
//        }
//    }
//
//    MPI_Reduce(&local_primes, &primes, 1, MPI_2INT, MPI_MINLOC, root, MPI_COMM_WORLD);
//    MPI_Bcast(&primes, 1, MPI_2INT, root, MPI_COMM_WORLD);
//
//    MPI_Reduce(&local_sum, &sum, 1, MPI_INT, MPI_SUM, primes.rank, MPI_COMM_WORLD);
//
//    if (rank == primes.rank)
//    {
//        printf("Suma iznosi: %d\n", sum);
//        printf("Proces sa najmanjim brojem prostih brojeva: %d\n", primes.rank);
//        printf("Broj prostih brojeva: %d", primes.value);
//    }
//
//    MPI_Finalize();
//    return 0;
//}
//
//bool isPrime(int number)
//{
//    if (number <= 1)
//        return false;
//
//    int root = sqrt(number);
//
//    for (int i = 2; i < root; i++)
//        if (number % i == 0)
//            return false;
//
//    return true;
//}