package org.helios.page_replacer;

import org.helios.RandomAccessMemory;
import org.helios.SwapMemory;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class FifoPageReplacer extends AbstractPageReplacer{
    private Queue<Integer> linePages = new LinkedList<>();

    public FifoPageReplacer(RandomAccessMemory memory, SwapMemory swapMemory) {
        super(memory, swapMemory);
    }

    @Override
    public void addPage(int memoryPageLine) {
        linePages.add(memoryPageLine);
    }

    @Override
    protected void updateArrivalsPage(int pageLine) {
        addPage(pageLine);
    }

    @Override
    public int chooseUselessPage() {
        return linePages.poll();
    }
}
