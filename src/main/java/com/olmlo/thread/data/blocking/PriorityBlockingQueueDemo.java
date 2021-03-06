package com.olmlo.thread.data.blocking;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * Main class of the example. Executes five threads that
 * store their events in a common priority queue and writes
 * them in the console to verify the correct operation of the
 * PriorityBlockingQueue class
 *
 */
public class PriorityBlockingQueueDemo {

    /**
     * @param args
     */
    public static void main(String[] args) {
        /* Priority queue to store the events */
        PriorityBlockingQueue<Event2> queue = new PriorityBlockingQueue<>();

        /* An array to store the five Thread objects */
        Thread taskThreads[] = new Thread[5];

        /* Create the five threads to execute five tasks */
        for (int i = 0; i < taskThreads.length; i++) {
            PriorityBlockingQueueTask task = new PriorityBlockingQueueTask(i, queue);
            taskThreads[i] = new Thread(task);
        }

        /* Start the five threads */
        for (int i = 0; i < taskThreads.length; i++) {
            taskThreads[i].start();
        }

        /* Wait for the finalization of the five threads */
        for (int i = 0; i < taskThreads.length; i++) {
            try {
                taskThreads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /* Write the events in the console */
        System.out.printf("Main: Queue Size: %d\n", queue.size());
        for (int i = 0; i < taskThreads.length * 1000; i++) {
            Event2 event = queue.poll();
            System.out.printf("Thread %s: Priority %d\n", event.getThread(), event.getPriority());
        }
        System.out.printf("Main: Queue Size: %d\n", queue.size());
        System.out.printf("Main: End of the program\n");
    }
}

class Event2 implements Comparable<Event2> {

    /**
     * Number of the thread that generates the event
     */
    private int thread;
    /**
     * Priority of the thread
     */
    private int priority;

    /**
     * Constructor of the thread. It initializes its attributes
     * @param thread Number of the thread that generates the event
     * @param priority Priority of the event
     */
    public Event2(int thread, int priority) {
        this.thread = thread;
        this.priority = priority;
    }

    /**
     * Method that returns the number of the thread that generates the
     * event
     * @return The number of the thread that generates the event
     */
    public int getThread() {
        return thread;
    }

    /**
     * Method that returns the priority of the event
     * @return The priority of the event
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Method that compares two events and decide which has more priority
     */
    @Override
    public int compareTo(Event2 e) {
        if (this.priority > e.getPriority()) {
            return -1;
        } else if (this.priority < e.getPriority()) {
            return 1;
        } else {
            return 0;
        }
    }
}

class PriorityBlockingQueueTask implements Runnable {

    /**
     * Id of the task
     */
    private int id;

    /**
     * Priority queue to store the events
     */
    private PriorityBlockingQueue<Event2> queue;

    /**
     * Constructor of the class. It initializes its attributes
     * @param id Id of the task 
     * @param queue Priority queue to store the events
     */
    public PriorityBlockingQueueTask(int id, PriorityBlockingQueue<Event2> queue) {
        this.id = id;
        this.queue = queue;
    }

    /**
     * Main method of the task. It generates 1000 events and store
     * them in the queue
     */
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            Event2 event = new Event2(id, i);
            queue.add(event);
        }
    }
}
