package org.helios.page_replacer;

import org.helios.RandomAccessMemory;
import org.helios.SwapMemory;

public class PageReplacerProxy {
    private final AbstractPageReplacer pageReplacer;
    public PageReplacerProxy(PageReplacerType type, RandomAccessMemory memory, SwapMemory swapMemory) {
        pageReplacer = PageReplacerFactory.create(type, memory, swapMemory);
    }


    public void addPage(final int pageLine) {
        pageReplacer.addPage(pageLine);
        pageReplacer.addPage(pageLine);
    }

    public void replacePage(final int instruction) {
        pageReplacer.replacePage(instruction);
    }
}
