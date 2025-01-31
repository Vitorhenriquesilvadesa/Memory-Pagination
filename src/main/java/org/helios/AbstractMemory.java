package org.helios;

public abstract class AbstractMemory {

    private final int[][] memory;

    private final int sizeX;
    private final int sizeY;

    public AbstractMemory(int sizeX, int sizeY) {
        this.memory = new int[sizeX][sizeY];
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        populateMemory();
    }

    protected abstract void populateMemory();

    public int[] getLine(int line) {
        return memory[line];
    }

    public MemoryPage getLineAsPage(int line) {
        return new MemoryPage(memory[line]);
    }

    public void setLine(int line, MemoryPage memoryPage) {
        memory[line] = memoryPage.toArray();
    }

    public void setLine(int line, int[] memoryPage) {
        memory[line] = memoryPage;
    }

    public int getRowCount() {
        return sizeX;
    }

    public int getColumnCount() {
        return sizeY;
    }
}
