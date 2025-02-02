package org.helios.page_replacer;

import org.helios.RandomAccessMemory;
import org.helios.SwapMemory;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class FifoPageReplacer extends AbstractPageReplacer{
    private Queue<Integer> linePages = new LinkedList<>();

    public FifoPageReplacer(RandomAccessMemory memory, SwapMemory swapMemory, Map<Integer, Integer> MMU) {
        super(memory, swapMemory, MMU);
    }

    @Override
    public void updateArrivalsPage(int pageLine) {
        linePages.add(pageLine);
    }

    @Override
    public int chooseUselessPage() {
        return linePages.poll();
    }
}
