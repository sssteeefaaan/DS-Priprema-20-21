#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define n 32
#define s 4

int main(int argc, char** argv)
{
	srand(time(NULL));

	int rank, size, master = 0;

	MPI_Status stat;
	MPI_Request req;

	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Comm_size(MPI_COMM_WORLD, &size);

	int value, values[n], local_values[n];

	if (rank == master)
	{
		value = rand() % 10 + 1;
		for (int i = 0; i < n; i++)
			values[i] = rand() % 10 + 1;
	}

	// MPI_Bcast(&value, 1, MPI_INT, master, MPI_COMM_WORLD);
	// MPI_Bcast(&value, 1, MPI_INT, master, MPI_COMM_WORLD);
	// Broadcast vrednosti svim procesima koriscenjem neblokirajuceg senda
	if (rank == master)
		for (int p = 0; p < size; p++)
			MPI_Isend(&value, 1, MPI_INT, p, 0, MPI_COMM_WORLD, &req);
	MPI_Recv(&value, 1, MPI_INT, master, 0, MPI_COMM_WORLD, &stat);

	// MPI_Bcast(&value, 1, MPI_INT, master, MPI_COMM_WORLD);
	// Broadcast vrednosti svim procesima koriscenjem blokirajuceg senda
	if (rank == master) {
		for (int p = 0; p < size; p++)
			if (p != master) // Kako master ne bi slao sam sebi i blokirao se
				MPI_Send(&value, 1, MPI_INT, p, 0, MPI_COMM_WORLD);
	}
	else
		MPI_Recv(&value, 1, MPI_INT, master, 0, MPI_COMM_WORLD, &stat);

	MPI_Bcast(&(values[0]), n, MPI_INT, master, MPI_COMM_WORLD);
	// Broadcast niza vrednosti duzine n, svim procesima koriscenjem neblokirajuceg senda
	// Ovaj pristup moze iskljucivo kada su memorijske lokacije pocetne adrese sukcesivne
	// Ukupno size broj send operacija
	if (rank == master)
		for (int p = 0; p < size; p++)
			MPI_Isend(&values[0], n, MPI_INT, p, 0, MPI_COMM_WORLD, &req);
	
	MPI_Recv(&values[0], n, MPI_INT, master, 0, MPI_COMM_WORLD, &stat);

	// MPI_Bcast(&value, 1, MPI_INT, master, MPI_COMM_WORLD);
	// Broadcast niza vrednosti duzine n, svim procesima koriscenjem blokirajuceg senda
	// Ovaj pristup moze iskljucivo kada su memorijske lokacije pocetne adrese sukcesivne
	// Ukupno size-1 broj send operacija
	if (rank == master) {
		for (int p = 0; p < size; p++)
			if (p != master) // Kako master ne bi slao sam sebi i blokirao se
				MPI_Send(&values[0], n, MPI_INT, p, 0, MPI_COMM_WORLD);
	}
	else
		MPI_Recv(&values[0], n, MPI_INT, master, 0, MPI_COMM_WORLD, &stat);

	// MPI_Bcast(&values[0], n, MPI_INT, master, MPI_COMM_WORLD);
	// Broadcast niza vrednosti duzine n, svim procesima koriscenjem neblokirajuceg senda
	// Ovaj pristup se koristi kada memorijske adrese nisu sukcesivne, pa saljemo jednu po jednu
	// Ukupno size * n broj send operacija
	if (rank == master)
		for (int p = 0; p < size; p++)
			for (int i = 0; i < n; i++)
				MPI_Isend(&values[i], 1, MPI_INT, p, 0, MPI_COMM_WORLD, &req);

	for (int i = 0; i < n; i++)
			MPI_Recv(&values[i], 1, MPI_INT, master, 0, MPI_COMM_WORLD, &stat);

	// MPI_Bcast(&value, 1, MPI_INT, master, MPI_COMM_WORLD);
	// Broadcast niza vrednosti duzine n, svim procesima koriscenjem blokirajuceg senda
	// Ovaj pristup se koristi kada memorijske adrese nisu sukcesivne, pa saljemo jednu po jednu
	// Ukupno (size - 1) * n broj send operacija
	if (rank == master) {
		for (int p = 0; p < size; p++)
			if (p != master) // Kako master ne bi slao sam sebi i blokirao se
				for (int i = 0; i < n; i++)
					MPI_Send(&values[i], 1, MPI_INT, p, 0, MPI_COMM_WORLD);
	}
	else
		for (int i = 0; i < n; i++)
			MPI_Recv(&values[i], 1, MPI_INT, master, 0, MPI_COMM_WORLD, &stat);


	// Iz niza vrednosti values[n] se salje po sendCount sukcesivnih vrednosti svakom od procesa
	// MPI_Scatter(&values[0], s, MPI_INT, &values[0], s, MPI_INT, master, MPI_COMM_WORLD);
	// Neblokirajuci send
	if (rank == master)
	{
		for (int p = 0; p < size; p++)
			MPI_Isend(&values[p * s /*sendCount*/], s /*sendCount*/, MPI_INT, p, 0, MPI_COMM_WORLD, &req);
	}
	MPI_Recv(&values[0], s, MPI_INT, master, 0, MPI_COMM_WORLD, &stat);

	// Iz niza vrednosti values[n] se salje po sendCount sukcesivnih vrednosti svakom od procesa
	// MPI_Scatter(&values[0], 1, MPI_INT, &values[0], 1, MPI_INT, master, MPI_COMM_WORLD);
	// Blokirajuci send
	if (rank == master)
	{
		for (int p = 0; p < size; p++)
			if(p != master)
				MPI_Send(&values[p * s /*sendCount*/], s /*sendCount*/, MPI_INT, p, 0, MPI_COMM_WORLD);
	}
	else
		MPI_Recv(&values[0], s, MPI_INT, master, 0, MPI_COMM_WORLD, &stat);

	MPI_Gather(&values[0], s, MPI_INT, &values[0], s, MPI_INT, master, MPI_COMM_WORLD);
	MPI_Isend(&values[0], s, MPI_INT, master, 0, MPI_COMM_WORLD, &req);
	if (rank == master)
	{
		for (int p = 0; p < size; p++)
			MPI_Recv(&values[p * s], s, MPI_INT, p, 0, MPI_COMM_WORLD, &stat);
	}

	MPI_Gather(&values[0], s, MPI_INT, &values[0], s, MPI_INT, master, MPI_COMM_WORLD);
	if (rank == master)
	{
		for (int p = 0; p < size; p++)
		{
			if (p != master)
				MPI_Recv(&values[p * s], s, MPI_INT, p, 0, MPI_COMM_WORLD, &stat);
			else
				for (int i = 0; i < s; i++)
					values[master * s + i] = values[i]; // values mora da bude lokalno za svaki proces, a values sa leve strane niz u koji se skupljaju podaci
		}
	}
	else
		MPI_Send(&values[0], s, MPI_INT, master, 0, MPI_COMM_WORLD);

	struct lokacija {
		int rank;
		int value;
	};
	// MPI_2INT
	// MPI_INTDOUBLE

	MPI_Reduce(&local_values[0], &values[0], n, MPI_INT, MPI_SUM, master, MPI_COMM_WORLD);
	MPI_Isend(&local_values[0], n, MPI_INT, master, 0, MPI_COMM_WORLD, &req);
	if (rank == master)
	{
		int temp_values[n];
		for (int i = 0; i < n; i++)
			temp_values[i] = values[i]= 0;

		for (int p = 0; p < size; p++)
		{
			MPI_Recv(&temp_values[0], n, MPI_INT, p, 0, MPI_COMM_WORLD, &stat);
			for (int i = 0; i < n; i++)
				values[i] += temp_values[i];
		}
	}

	MPI_Reduce(&local_values[0], &values[0], n, MPI_INT, MPI_SUM, master, MPI_COMM_WORLD);
	MPI_Isend(&local_values[0], n, MPI_INT, master, 0, MPI_COMM_WORLD, &req);
	if (rank == master)
	{
		MPI_Recv(&values[0], n, MPI_INT, master, 0, MPI_COMM_WORLD, &stat);
		for (int p = 0; p < size; p++)
		{
			if (p != master)
			{
				MPI_Recv(&local_values[0], n, MPI_INT, p, 0, MPI_COMM_WORLD, &stat);
				for (int i = 0; i < n; i++)
					values[i] += local_values[i];
			}
		}
	}

	MPI_Reduce(&local_values[0], &values[0], n, MPI_INT, MPI_SUM, master, MPI_COMM_WORLD);
	if (rank == master)
	{
		for (int i = 0; i < n; i++)
			values[i] = local_values[i];

		for (int p = 0; p < size; p++)
		{
			if (p != master)
			{
				MPI_Recv(&local_values[0], n, MPI_INT, p, 0, MPI_COMM_WORLD, &stat);
				for (int i = 0; i < n; i++)
					values[i] += local_values[i];
			}
		}
	}
	else
		MPI_Send(&local_values[0], n, MPI_INT, master, 0, MPI_COMM_WORLD);

	MPI_Reduce(&local_values[0], &values[0], n, MPI_INT, MPI_PROD, master, MPI_COMM_WORLD);
	MPI_Isend(&local_values[0], n, MPI_INT, master, 0, MPI_COMM_WORLD, &req);
	if (rank == master)
	{
		MPI_Recv(&values[0], n, MPI_INT, master, 0, MPI_COMM_WORLD, &stat);
		for (int p = 0; p < size; p++)
		{
			if (p != master)
			{
				MPI_Recv(&local_values[0], n, MPI_INT, p, 0, MPI_COMM_WORLD, &stat);
				for (int i = 0; i < n; i++)
					values[i] *= local_values[i];
			}
		}
	}

	struct lokacija min_loc_in = { rank, rand() % 100 },
		min_loc_out,
		max_loc_in = { rank, rand() % 100 },
		max_loc_out;

	MPI_Reduce(&min_loc_in, &min_loc_out, 1, MPI_2INT, MPI_MINLOC, master, MPI_COMM_WORLD);
	MPI_Isend(&min_loc_in, 1, MPI_2INT, master, 0, MPI_COMM_WORLD, &req);
	if (rank == master)
	{
		MPI_Recv(&min_loc_out, 1, MPI_2INT, master, 0, MPI_COMM_WORLD, &stat);
		for (int p = 0; p < size; p++)
		{
			if (p != master)
			{
				MPI_Recv(&min_loc_in, 1, MPI_2INT, p, 0, MPI_COMM_WORLD, &stat);
				
				if (min_loc_in.value < min_loc_out.value)
					min_loc_out = min_loc_in;
			}
		}
	}

	MPI_Reduce(&max_loc_in, &max_loc_out, 1, MPI_2INT, MPI_MAXLOC, master, MPI_COMM_WORLD);
	MPI_Isend(&max_loc_in, 1, MPI_2INT, master, 0, MPI_COMM_WORLD, &req);
	if (rank == master)
	{
		MPI_Recv(&max_loc_out, 1, MPI_2INT, master, 0, MPI_COMM_WORLD, &stat);
		for (int p = 0; p < size; p++)
		{
			if (p != master)
			{
				MPI_Recv(&max_loc_in, 1, MPI_2INT, p, 0, MPI_COMM_WORLD, &stat);

				if (max_loc_in.value > max_loc_out.value)
					max_loc_out = max_loc_in;
			}
		}
	}

	MPI_Scan(&local_values[0], &values[0], n, MPI_INT, MPI_SUM, MPI_COMM_WORLD);
	if (rank == 0)
		for (int i = 0; i < n; i++)
			values[i] = local_values[i];
	else
	{
		MPI_Recv(&values[0], n, MPI_INT, rank - 1, 0, MPI_COMM_WORLD, &stat);
		
		for (int i = 0; i < n; i++)
			values[i] += local_values[i];
	}

	if(rank < size - 1)
		MPI_Send(&values[0], n, MPI_INT, rank + 1, 0, MPI_COMM_WORLD);

	int x[n], y[n];
	for (int i = 0; i < n; i++)
		x[i] = 10 + i;

	MPI_Finalize();
	return 0;
}