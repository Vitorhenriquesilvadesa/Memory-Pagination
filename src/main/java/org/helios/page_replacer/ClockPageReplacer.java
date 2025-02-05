package org.helios.page_replacer;

import org.helios.MemoryPage;
import org.helios.RandomAccessMemory;
import org.helios.SwapMemory;

import java.util.Map;

public class ClockPageReplacer extends AbstractPageReplacer {

    private Node head;
    private Node tail;

    private Node current;
    private int queueSize = 0;

    public ClockPageReplacer(RandomAccessMemory memory, SwapMemory swapMemory) {
        super(memory, swapMemory);
    }

    private void enqueue(int pageLine) {
        if(head == null) {
            head = new Node(pageLine);
            tail = head;
            head.next = tail;
            current = head;
        }
        else if (tail == head){
            tail = new Node(pageLine);
            head.next = tail;
        }
        else {
            Node next = new Node(pageLine);
            tail.next = next;
            tail = next;
        }
        tail.next = head;
        queueSize++;
    }

    @Override
    public void addPage(int memoryPageLine) {
        enqueue(memoryPageLine);
    }

    @Override
    public void updateArrivalsPage(int memoryPageLine) {
            current.pageLine = memoryPageLine;
            current = current.next;
    }

    @Override
    public int chooseUselessPage() {

        for (int i = 0; i < queueSize; i++) {
            MemoryPage page = memory.getLineAsPage(current.pageLine);
            if (page.getAccessBit() == 1){
                page.setAccessBit(0);
                current = current.next;
            }
            else{
                return current.pageLine;
            }
            current = current.next;
        }
        return current.pageLine;
    }


    private static class Node {
        private Node next;
        private int pageLine;

        private Node(int pageLine) {
            this.next = null;
            this.pageLine = pageLine;
        }
    }
}
