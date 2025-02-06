package org.helios.page_replacer;
import org.helios.MemoryPage;
import org.helios.RandomAccessMemory;
import org.helios.SwapMemory;
import java.util.LinkedList;
import java.util.Queue;

public class NRUPageReplacer extends AbstractPageReplacer{
    Queue<Integer> pageLineTable = new LinkedList<>();
    public NRUPageReplacer(RandomAccessMemory memory, SwapMemory swapMemory) {
        super(memory, swapMemory);
    }

    @Override
    public void addPage(int memoryPageLine) {
        pageLineTable.add(memoryPageLine);
    }

    @Override
    public void updateArrivalsPage(int memoryPageLine) {
        addPage(memoryPageLine);
    }


    @Override
    public int chooseUselessPage() {
        int[] pageClasses = new int[4];
        for (Integer pageLine : pageLineTable) {
            MemoryPage page = memory.getLineAsPage(pageLine);
            int pageClass = getPageClass(page);
            // Classe mais baixa, já retorna a linha da pagina:
            if (pageClass == 0) {
                pageLineTable.remove(pageLine);
                return pageLine;
            }
            pageClasses[pageClass] = pageLine;
        }

        // Seleciona o Primeiro de menor classe existente e retorna
        for (int i = 0; i < 3; i++) {
            int uselessPage = pageClasses[i];
            if (uselessPage != 0)  {
                pageLineTable.remove(uselessPage);
                return  uselessPage;
            };
        }
        // Caso nenhum atenda, retorna o primeiro da fila
        return pageLineTable.poll();
        }

    private int getPageClass(MemoryPage page) {
        return (page.getAccessBit() << 1) | page.getModificationBit();
    }




}
