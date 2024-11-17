#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>  // For usleep
#include <time.h>    // For better time precision

#define N 256  // Total number of elements to distribute

// Function to convert seconds to milliseconds (ms)
double sec_to_ms(double sec) {
    return sec * 1000.0;  // Convert seconds to milliseconds
}

// Function to convert seconds to nanoseconds (ns)
double sec_to_ns(double sec) {
    return sec * 1000000000.0;  // Convert seconds to nanoseconds
}

// Structure to hold timing info for each process
typedef struct {
    int rank;
    double scatter_time;
    double reduction_time;
} ProcessTimeInfo;

// Comparison function for sorting by scatter time (decreasing order)
int compare_scatter(const void *a, const void *b) {
    ProcessTimeInfo *time_info_a = (ProcessTimeInfo *)a;
    ProcessTimeInfo *time_info_b = (ProcessTimeInfo *)b;
    return (time_info_b->scatter_time > time_info_a->scatter_time) - 
           (time_info_b->scatter_time < time_info_a->scatter_time);
}

// Comparison function for sorting by reduction time (increasing order)
int compare_reduction(const void *a, const void *b) {
    ProcessTimeInfo *time_info_a = (ProcessTimeInfo *)a;
    ProcessTimeInfo *time_info_b = (ProcessTimeInfo *)b;
    return (time_info_a->reduction_time > time_info_b->reduction_time) - 
           (time_info_a->reduction_time < time_info_b->reduction_time);
}

int main(int argc, char *argv[]) {
    int rank, size;
    int array[N];
    int *recv_data;
    int local_sum = 0;
    int global_sum = 0;
    double avg = 0.0;

    // Timing info storage
    ProcessTimeInfo *timing_info = NULL;

    MPI_Init(&argc, &argv);

    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    MPI_Comm_size(MPI_COMM_WORLD, &size);

    if (rank == 0) {
        // Initialize array with values 1 to N
        for (int i = 0; i < N; i++) {
            array[i] = i + 1;
        }
    }

    // Dynamically allocate memory for receiving data
    recv_data = (int*)malloc(N / size * sizeof(int));

    timing_info = (ProcessTimeInfo *)malloc(size * sizeof(ProcessTimeInfo));

    double start_time, end_time;

    // Scattering operation
    start_time = MPI_Wtime();
    MPI_Scatter(array, N / size, MPI_INT, recv_data, N / size, MPI_INT, 0, MPI_COMM_WORLD);
    end_time = MPI_Wtime();
    
    // Store the scatter time for each process
    timing_info[rank].rank = rank;
    timing_info[rank].scatter_time = end_time - start_time;

    // Print data received and scattering time
    printf("Process %d received %d elements: ", rank, N / size);
    for (int i = 0; i < N / size; i++) {
        printf("%d ", recv_data[i]);
    }
    printf("\n");

    // Format scattering time (convert to ms or ns)
    if (timing_info[rank].scatter_time >= 0.001) {
        printf("Process %d - Scattering time: %.3f ms\n", rank, sec_to_ms(timing_info[rank].scatter_time));
    } else {
        printf("Process %d - Scattering time: %.0f ns\n", rank, sec_to_ns(timing_info[rank].scatter_time));
    }

    // Reduction operation (sum of received data)
    for (int i = 0; i < N / size; i++) {
        local_sum += recv_data[i];
    }

    // Timing the reduction operation
    start_time = MPI_Wtime();
    MPI_Reduce(&local_sum, &global_sum, 1, MPI_INT, MPI_SUM, 0, MPI_COMM_WORLD);
    end_time = MPI_Wtime();

    // Store the reduction time for each process
    timing_info[rank].reduction_time = end_time - start_time;

    // Format reduction time (convert to ms or ns)
    if (timing_info[rank].reduction_time >= 0.001) {
        printf("Process %d - Reduction time: %.3f ms\n", rank, sec_to_ms(timing_info[rank].reduction_time));
    } else {
        printf("Process %d - Reduction time: %.0f ns\n", rank, sec_to_ns(timing_info[rank].reduction_time));
    }

    // Wait for a short period to simulate work (e.g., usleep) for rank 0
    if (rank == 0) {
        usleep(100000);  // 100 milliseconds for rank 0 to simulate more work
    }

    // Gather results and print global sum and average on rank 0
    if (rank == 0) {
        avg = global_sum / (double)N;
        printf("\nGlobal sum: %d\n", global_sum);
        printf("Average: %.6f\n", avg);

        // Sort the timing info
        qsort(timing_info, size, sizeof(ProcessTimeInfo), compare_scatter);   // Sort scatter times (decreasing)
        qsort(timing_info, size, sizeof(ProcessTimeInfo), compare_reduction); // Sort reduction times (increasing)

        // Print scatter times (in decreasing order)
        printf("\nScatter Times (Decreasing Order):\n");
        for (int i = 0; i < size; i++) {
            if (timing_info[i].scatter_time >= 0.001) {
                printf("Process %d - %.3f ms\n", timing_info[i].rank, sec_to_ms(timing_info[i].scatter_time));
            } else {
                printf("Process %d - %.0f ns\n", timing_info[i].rank, sec_to_ns(timing_info[i].scatter_time));
            }
        }

        // Print reduction times (in increasing order)
        printf("\nReduction Times (Increasing Order):\n");
        for (int i = 0; i < size; i++) {
            if (timing_info[i].reduction_time >= 0.001) {
                printf("Process %d - %.3f ms\n", timing_info[i].rank, sec_to_ms(timing_info[i].reduction_time));
            } else {
                printf("Process %d - %.0f ns\n", timing_info[i].rank, sec_to_ns(timing_info[i].reduction_time));
            }
        }
    }

    // Clean up memory
    free(recv_data);
    free(timing_info);

    MPI_Finalize();

    return 0;
}

