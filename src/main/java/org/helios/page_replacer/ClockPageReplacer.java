package org.helios.page_replacer;

import org.helios.MemoryPage;
import org.helios.RandomAccessMemory;
import org.helios.SwapMemory;

import java.util.Map;

public class ClockPageReplacer extends AbstractPageReplacer {

    CircularQueue<Integer> queue = new CircularQueue<>();
    private Node<Integer> current;
    private boolean done;

    public ClockPageReplacer(RandomAccessMemory memory, SwapMemory swapMemory, Map<Integer, Integer> MMU) {
        super(memory, swapMemory, MMU);
    }

    @Override
    public void updateArrivalsPage(int memoryPageLine) {

        if(done){
            current.data = memoryPageLine;
            current = current.next;
            done = false;
        }
        else {
            queue.enqueue(memoryPageLine);
        }
        if (current == null) {
            current = queue.head;
        }
    }

    @Override
    public int chooseUselessPage() {

        for (int i = 0; i < queue.size; i++) {
            MemoryPage page = memory.getLineAsPage(current.data);
            if (page.getAccessBit() == 1){
                page.setAccessBit(0);
                current = current.next;
            }
            else{
                done = true;
                return current.data;
            }
            current = current.next;
        }
        return queue.head.data;
    }






    private static class CircularQueue<T> {
        private Node<T> head;
        private Node<T> tail;
        private int size = 0;

        public void enqueue(T element) {
            if (head == null) {
                head = new Node<>(element, null);
            }
            else if (tail == null) {
                tail = new Node<>(element, null);
                head.next = tail;
            }
            else {
                tail.next = new Node<>(element, null);
                tail = tail.next;
                tail.next = head;
            }
            size++;
        }
    }
    private static class Node<T> {
        private Node<T> next;
        private T data;
        public Node(final T data, final Node<T> next) {
            this.data = data;
            this.next = next;
        }
    }
}
