package org.helios;

public class MemoryPage {

    private final int[] page;

    public MemoryPage(int[] page) {
        this.page = page;
    }

    public int getNumber() {
        return page[PageDataType.Number.ordinal()];
    }

    public void setNumber(int number) {
        page[PageDataType.Number.ordinal()] = number;
    }

    public int getInstruction() {
        return page[PageDataType.Instruction.ordinal()];
    }

    public void setInstruction(int instruction) {
        page[PageDataType.Instruction.ordinal()] = instruction;
    }

    public int getData() {
        return page[PageDataType.Data.ordinal()];
    }

    public void setData(int data) {
        page[PageDataType.Data.ordinal()] = data;
    }

    public int getAccessBit() {
        return page[PageDataType.AccessBit.ordinal()];
    }

    public void setAccessBit(int bit) {
        page[PageDataType.AccessBit.ordinal()] = bit;
    }

    public int getModificationBit() {
        return page[PageDataType.ModificationBit.ordinal()];
    }

    public void setModificationBit(int bit) {
        page[PageDataType.ModificationBit.ordinal()] = bit;
    }

    public int getAgingTime() {
        return page[PageDataType.AgingTime.ordinal()];
    }

    public void setAgingTime(int time) {
        page[PageDataType.AgingTime.ordinal()] = time;
    }

    public int[] toArray() {
        return page;
    }
}
