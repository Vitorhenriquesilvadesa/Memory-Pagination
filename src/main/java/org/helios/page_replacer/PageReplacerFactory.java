package org.helios.page_replacer;

import org.helios.RandomAccessMemory;
import org.helios.SwapMemory;


public class PageReplacerFactory {
    public static AbstractPageReplacer create(PageReplacerType type, RandomAccessMemory memory, SwapMemory swapMemory) {
        AbstractPageReplacer pageReplacer = null;
        switch (type) {
            case NRU -> pageReplacer = new NRUPageReplacer(memory, swapMemory);
            case FIFO -> pageReplacer = new FifoPageReplacer(memory, swapMemory);
            case FIFO_SC -> pageReplacer = new FifoSCPageReplacer(memory, swapMemory);
            case CLOCK -> pageReplacer = new ClockPageReplacer(memory, swapMemory);
            case LRU -> pageReplacer = new LRUPageReplacer(memory, swapMemory);
            case WS_CLOCK -> pageReplacer = new WSClockPageReplacer(memory, swapMemory);
        }
        return pageReplacer;
    }
}
