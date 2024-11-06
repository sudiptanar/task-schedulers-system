package com.sn.scheduler.logics.tasks;

import com.sn.scheduler.model.Task;
import lombok.Getter;

import java.time.Instant;
import java.util.Arrays;

@Getter
public class BubbleSortTask extends Task {
    private final int[] arrayToSort;

    public BubbleSortTask(String id, String description, String permanentTaskId, int priority, Instant scheduledTime, int[] arrayToSort) {
        super(id, description, permanentTaskId, Priority.getByInt(priority), scheduledTime, null, null, null, false, Status.SCHEDULED, 0);
        this.arrayToSort = arrayToSort;
    }

    @Override
    public void run() {
        bubbleSort(arrayToSort);
        System.out.println("Bubble Sort Completed: " + Arrays.toString(arrayToSort));
    }

    // Bubble Sort Algorithm
    private void bubbleSort(int[] array) {
        int n = array.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }
}
