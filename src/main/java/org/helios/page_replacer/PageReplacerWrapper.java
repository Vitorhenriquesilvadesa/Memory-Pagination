package org.helios.page_replacer;

import org.helios.RandomAccessMemory;
import org.helios.SwapMemory;

import java.util.Random;

public class PageReplacerWrapper {
    private AbstractPageReplacer pageReplacer;
    public PageReplacerWrapper(PageReplacerType type, RandomAccessMemory memory, SwapMemory swapMemory) {
        switch (type) {
            case NRU -> pageReplacer = new NRUPageReplacer(memory, swapMemory);
            case FIFO -> pageReplacer = new FifoPageReplacer(memory, swapMemory);
            case FIFO_SC -> pageReplacer = new FifoSCPageReplacer(memory, swapMemory);
            case CLOCK -> pageReplacer = new ClockPageReplacer(memory, swapMemory);
            case LRU -> pageReplacer = new LRUPageReplacer(memory, swapMemory);
            case WS_CLOCK -> pageReplacer = new WSClockPageReplacer(memory, swapMemory);
        }
    }
    
    public void addPage(final int pageLine) {
        pageReplacer.addPage(pageLine);
    }

    public void replacePage(final int instruction) {
        pageReplacer.replacePage(instruction);
    }
}
