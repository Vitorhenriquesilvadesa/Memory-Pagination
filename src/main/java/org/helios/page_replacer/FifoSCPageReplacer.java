package org.helios.page_replacer;

import org.helios.MemoryPage;
import org.helios.RandomAccessMemory;
import org.helios.SwapMemory;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class FifoSCPageReplacer extends AbstractPageReplacer{
    public FifoSCPageReplacer(RandomAccessMemory memory, SwapMemory swapMemory) {
        super(memory, swapMemory);
    }

    @Override
    public void addPage(int memoryPageLine) {
        pageLineQueue.add(memoryPageLine);
    }

    private final Queue<Integer> pageLineQueue = new LinkedList<>();

    @Override
    public void updateArrivalsPage(int memoryPageLine) {
        addPage(memoryPageLine);
    }

    @Override
    public int chooseUselessPage() {

        for (int i = 0; i < pageLineQueue.size(); i++) {
            int pageLine = pageLineQueue.poll();
            MemoryPage memoryPage = memory.getLineAsPage(pageLine);

            if (memoryPage.getAccessBit() == 1){
                memoryPage.setAccessBit(0);
                pageLineQueue.add(pageLine);
            }
            else {
                return pageLine;
            }
        }


        return pageLineQueue.poll();
    }
}
