package org.helios;

import java.util.List;

public class RandomAccessMemory extends AbstractMemory {

    public RandomAccessMemory() {
        super(10, 6);
    }

    @Override
    protected void populateMemory() {

    }

    public void populateFrom(AbstractMemory memory, List<Integer> lines) {
        int i = 0;

        for(int line : lines) {
            setLine(i++, memory.getLine(line));
        }
    }
}
