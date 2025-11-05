import java.util.*;

public class RoundRobinLab {

    static class Process {
        int id;
        int arrivalTime;
        int burstTime;
        int remainingTime;
        int completionTime;
        int turnaroundTime;
        int waitingTime;

        public Process(int id, int arrivalTime, int burstTime) {
            this.id = id;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.remainingTime = burstTime;
        }
    }

    /**
     * TODO 1, 2, 3: Implement Round Robin Scheduling
     *
     * This method simulates Round Robin scheduling with the given time quantum.
     */
    public static void scheduleRoundRobin(List<Process> processes, int timeQuantum) {
        int currentTime = 0;
        
        // TODO 1: Create the ready queue and scheduling loop
        // Create an ArrayList to hold the ready queue (simulating a FIFO queue)
        ArrayList<Process> readyQueue = new ArrayList<>();
        
        // Add all processes to the ready queue initially
        for (Process p : processes) {
            readyQueue.add(p);
        }

        // Loop that continues while the queue is not empty
        while (!readyQueue.isEmpty()) {
            
            // TODO 2: Process execution logic
            // Remove the first process from the queue
            Process current = readyQueue.remove(0);

            // Calculate how much time this process will run 
            // (minimum of quantum and remaining time)
            int executeTime = Math.min(timeQuantum, current.remainingTime);

            // Update currentTime by adding the execution time
            currentTime += executeTime;

            // Subtract execution time from the process's remainingTime
            current.remainingTime -= executeTime;

            // Check if process is done
            if (current.remainingTime > 0) {
                // If remainingTime > 0, add process back to the end of the queue
                readyQueue.add(current);
            } else {
                // If remainingTime == 0, set the process completionTime to currentTime
                current.completionTime = currentTime;
            }
        }
        
        // TODO 3: Calculate metrics after all processes complete
        // Loop through all processes to calculate metrics
        for (Process p : processes) {
            // turnaroundTime = completionTime - arrivalTime
            p.turnaroundTime = p.completionTime - p.arrivalTime;
            // waitingTime = turnaroundTime - burstTime
            p.waitingTime = p.turnaroundTime - p.burstTime;
        }
    }

    /**
     * Calculate and display metrics (FULLY PROVIDED)
     */
    public static void calculateMetrics(List<Process> processes, int timeQuantum) {
        System.out.println("========================================");
        System.out.println("Round Robin Scheduling Simulator");
        System.out.println("========================================\n");
        System.out.println("Time Quantum: " + timeQuantum + "ms");
        System.out.println("----------------------------------------");
        System.out.println("Process | Arrival | Burst | Completion | Turnaround | Waiting");

        double totalTurnaround = 0;
        double totalWaiting = 0;

        for (Process p : processes) {
            System.out.printf("   %d    |    %d    |   %d   |     %d     |     %d     |    %d\n",
                    p.id, p.arrivalTime, p.burstTime, p.completionTime,
                    p.turnaroundTime, p.waitingTime);
            totalTurnaround += p.turnaroundTime;
            totalWaiting += p.waitingTime;
        }

        System.out.println();
        System.out.printf("Average Turnaround Time: %.2fms\n", totalTurnaround / processes.size());
        System.out.printf("Average Waiting Time: %.2fms\n", totalWaiting / processes.size());
        System.out.println("========================================\n\n");
    }

    /**
     * Main method (FULLY PROVIDED)
     */
    public static void main(String[] args) {
        List<Process> processes1 = new ArrayList<>();
        processes1.add(new Process(1, 0, 7));
        processes1.add(new Process(2, 0, 4));
        processes1.add(new Process(3, 0, 2));

        scheduleRoundRobin(processes1, 3);
        calculateMetrics(processes1, 3);

        List<Process> processes2 = new ArrayList<>();
        processes2.add(new Process(1, 0, 7));
        processes2.add(new Process(2, 0, 4));
        processes2.add(new Process(3, 0, 2));

        scheduleRoundRobin(processes2, 5);
        calculateMetrics(processes2, 5);
    }
}
