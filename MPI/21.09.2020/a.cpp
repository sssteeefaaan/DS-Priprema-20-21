#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

// Zadatak se radi na foricu da se binarne vrednosti rank-ova procesa u hiperkubu razlikuju za po jedan bit
// Npr. susedi procesa 0 => (000)2 zapravo: [(100)2, (010)2, (001)2] = [4, 2, 1]
// 
// Odnosno za korak i, posmatracemo bit ciju poziciju dobijamo and-ovanjem sa vrednoscu mask(i) = log2(size) >> (i + 1)
// Sender ce nam biti proces sa rankom p, tako da je p & mask(i) => 0
// Receiver ce nam biti proces sa rankom q, tako da je q & mask(i) => 1
// Odavde sledi da je p < q (Definisano je u zadatku da se krece od procesa 0, odnosno sa najmanjom vrednoscu)
// 
// Konacno da bi znali destinaciju gde se salje, odnosno odakle se prima podatak
// Koristimo uslov da je p xor mask(i) = q <=> q xor mask(i) = p
// 
// Broj podataka za korak i je takodje mask(i)

int main(int argc, char** argv)
{
	int rank, size, root = 0;
	int koraci, mask;
	int* niz = nullptr;
	MPI_Status status;

	MPI_Init(&argc, &argv);
	MPI_Comm_size(MPI_COMM_WORLD, &size);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	koraci = log2(size);
	niz = (int*)malloc(sizeof(int) * (size + 1));

	if (rank == root)
	{
		/*printf("Unesite %d celih brojeva: ", size);
		fflush(stdout);*/
		for (int i = 0; i < size; i++)
			niz[i] = i;
		niz[size] = -1;
	}
	else
		for (int i = 0; i <= size; i++)
			niz[i] = -1;

	MPI_Barrier(MPI_COMM_WORLD);

	for (int i = 0; i < koraci; i++)
	{
		int mask = size >> (i + 1);

		if (rank & mask)
			MPI_Recv(niz, mask, MPI_INT, rank ^ mask, 0, MPI_COMM_WORLD, &status);
		else
		{
			MPI_Send(&niz[mask], mask, MPI_INT, rank ^ mask, 0, MPI_COMM_WORLD);
			niz[mask] = -1;
		}

		printf("Proces [%d] u koraku %d sadrzi [", rank, i);
		int j = 0;
		while (niz[j] != -1)
			printf("\t%d", niz[j++]);
		printf("\t]\n");
		fflush(stdout);
	}

	free(niz);

	MPI_Finalize();
	return 0;
}