#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>

#define N 8

int main(int argc, char *argv[]) {
    int rank, size;
    int array[N];
    int local_sum = 0;
    int global_sum = 0;
    double avg = 0.0;

    MPI_Init(&argc, &argv);

    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    if (rank == 0) {
        for (int i = 0; i < N; i++) {
            array[i] = i + 1; 
        }
    }

    double start_time, end_time;

 
    start_time = MPI_Wtime();
    MPI_Bcast(array, N, MPI_INT, 0, MPI_COMM_WORLD);
    end_time = MPI_Wtime();

    printf("Process %d received data: ", rank);
    for (int i = 0; i < N; i++) {
        printf("%d ", array[i]);
    }
    printf("\n");

    printf("Process %d - Broadcasting time: %f seconds\n", rank, end_time - start_time);

    
    for (int i = rank; i < N; i += size) {  
        local_sum += array[i];
    }

    
    start_time = MPI_Wtime();
    MPI_Reduce(&local_sum, &global_sum, 1, MPI_INT, MPI_SUM, 0, MPI_COMM_WORLD);
    end_time = MPI_Wtime();

    printf("Process %d - Reduction time: %f seconds\n", rank, end_time - start_time);

    if (rank == 0) {
        avg = (double)global_sum / N;
        printf("Average: %f\n", avg);
    }

    MPI_Finalize();

    return 0;
}

