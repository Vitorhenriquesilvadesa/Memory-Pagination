package org.helios;

import java.util.*;

public class Main {

    private static SwapMemory swapMemory = new SwapMemory();
    private static RandomAccessMemory randomAccessMemory = new RandomAccessMemory();

    public static void main(String[] args) {
        randomAccessMemory.populateFrom(swapMemory, getRandomLines());

        for (int i = 0; i < randomAccessMemory.getRowCount(); i++) {
            System.out.println(Arrays.toString(randomAccessMemory.getLineAsPage(i).toArray()));
        }

        System.out.println("\n");

        for (int i = 0; i < swapMemory.getRowCount(); i++) {
            System.out.println(Arrays.toString(swapMemory.getLineAsPage(i).toArray()));
        }
    }


    private static List<Integer> getRandomLines() {
        int sizeY = swapMemory.getRowCount();
        int n = randomAccessMemory.getRowCount();

        List<Integer> numbers = new ArrayList<>();

        for (int i = 0; i <= sizeY; i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers);

        return new ArrayList<>(numbers.subList(0, n));
    }
}