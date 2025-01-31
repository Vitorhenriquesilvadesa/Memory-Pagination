package org.helios;

public interface AbstractMemoryPaginator {

    public abstract void insertPage(MemoryPage page);
    public abstract MemoryPage getNextPage();
    public abstract MemoryPage getPreviousPage();
    public abstract int getPageCount();
    public abstract int getCurrentPage();
    public abstract boolean getInstruction(int instruction);
}
