package org.helios.page_replacer;

import org.helios.MemoryPage;
import org.helios.RandomAccessMemory;
import org.helios.SwapMemory;


public class WSClockPageReplacer extends AbstractPageReplacer {
    private Node head;
    private Node tail;
    private int queueSize = 0;
    private final int maxAgingTime = 40;

    private Node current;
    private void push(int pageLine){
        if (head == null){
            head = new Node(pageLine);
            head.next = head;
            tail = head;
            current = head;
        }
        else if (tail == head){
            tail = new Node(pageLine);
            tail.next = tail;
            head.next = tail;
        }
        else {
            Node newNode = new Node(pageLine);
            tail.next = newNode;
            tail = newNode;
        }
        tail.next = head;
        queueSize++;
    }


    public WSClockPageReplacer(RandomAccessMemory memory, SwapMemory swapMemory) {
        super(memory, swapMemory);
    }

    @Override
    public void addPage(int memoryPageLine) {
        push(memoryPageLine);
    }

    @Override
    public void updateArrivalsPage(int memoryPageLine) {
        current.pageLine = memoryPageLine;
    }

    @Override
    public int chooseUselessPage() {
        for (int i = 0; i < queueSize; i++) {
            int currentPageLine =  current.pageLine;
            MemoryPage page = memory.getLineAsPage(currentPageLine);
            if (page.getAccessBit() == 1){
                page.setAccessBit(0);
            }
            else if (page.getAgingTime() > maxAgingTime){
                    // M = 0 -> Não tem necessidade de Atualizar em SWAP e
                    // deve ser substituida
                    if (page.getModificationBit() == 0){
                        return currentPageLine;
                    }
                    // M = 1 -> Agenda a Atualização em Swap
                    schedulePageToUpdateInSwap(currentPageLine);
            }

            current = current.next;
        }
        // Deu uma volta completa, agora ira buscar o primeiro com M = 0
        for (int i = 0; i < queueSize; i++) {
            int currentPageLine =  current.pageLine;
            MemoryPage page = memory.getLineAsPage(currentPageLine);
            if (page.getModificationBit() == 0){
                return currentPageLine;
            }
        }
        return current.pageLine;
    }

    private static class Node{
        private Node next;
        private int pageLine;



        public Node(int pageLine) {
            this.next = null;
            this.pageLine = pageLine;
        }
    }
}
