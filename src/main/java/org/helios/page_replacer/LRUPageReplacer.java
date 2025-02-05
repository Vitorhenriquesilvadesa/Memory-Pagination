package org.helios.page_replacer;

import org.helios.MemoryPage;
import org.helios.RandomAccessMemory;
import org.helios.SwapMemory;

import java.util.ArrayList;
import java.util.Map;

public class LRUPageReplacer extends AbstractPageReplacer{
    private final ArrayList<Integer> pageLineList = new ArrayList<>();

    public LRUPageReplacer(RandomAccessMemory memory, SwapMemory swapMemory) {
        super(memory, swapMemory);
    }

    @Override
    public void addPage(int memoryPageLine) {
        pageLineList.add(memoryPageLine);
    }

    @Override
    public void updateArrivalsPage(int memoryPageLine) {
        addPage(memoryPageLine);
    }

    @Override
    public int chooseUselessPage() {
        Integer pageLine = pageLineList.getFirst();
        int lessTime = -1;
        for (int i = 0; i < pageLineList.size(); i++) {
            int currentPageLine = pageLineList.get(i);
            MemoryPage memoryPage = memory.getLineAsPage(i);
            if (memoryPage.getAgingTime() > lessTime) {
                lessTime = memoryPage.getAgingTime();
                pageLine = currentPageLine;
            }
        }
        pageLineList.remove(pageLine);
        return pageLine;
    }


}
