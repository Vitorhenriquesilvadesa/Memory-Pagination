package org.helios.page_replacer;

import org.helios.MemoryPage;
import org.helios.RandomAccessMemory;
import org.helios.SwapMemory;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public abstract class AbstractPageReplacer {

    // TODO: Escolher nome melhor para isso
    protected final RandomAccessMemory memory;
    protected final SwapMemory swapMemory;
    protected final Map<Integer, Integer> MMU = new HashMap<>();
    private final Stack<Integer> pagesToUpdateInSwap = new Stack<>();

    public AbstractPageReplacer(RandomAccessMemory memory, SwapMemory swapMemory) {
        this.memory = memory;
        this.swapMemory = swapMemory;
    }




    // Faz a busca da instrução dentro de SWAP, e realiza a troca de pagina
    public void replacePage(int instruction) {
        int requestedPageLine = findPageInSwap(instruction);


        if (requestedPageLine == -1) {
            throw new RuntimeException("No page found for instruction " + instruction);
        }


        // solicita a linha da pagina mais inutil dentro da memoria RAM:
        int lineUselessPage = chooseUselessPage();
        MemoryPage uselessPage = memory.getLineAsPage(lineUselessPage);


//        System.out.println("USELESS PAGE: " + lineUselessPage);

        // Caso a pagina (uselessPage) escolhida possua o bit de Modificado = 1, Atualiza pagina dentro de Swap
        if (uselessPage.getModificationBit() == 1) {
            schedulePageToUpdateInSwap(lineUselessPage);
        }
        // Seta Pagina encontrada dentro de Swap na Memoria Ram
        memory.setLine(lineUselessPage, swapMemory.getLine(requestedPageLine));
        updateArrivalsPage(lineUselessPage);
        MMU.put(lineUselessPage, requestedPageLine);
        updatePagesInSwap();

    }

    // Percorre a Memoria Swap, retortando a linha da pagina que corespondente a da instrução requisitada.
    private int findPageInSwap(int instruction) {
        for (int line = 0; line < swapMemory.getRowCount(); line++) {
            MemoryPage page = swapMemory.getLineAsPage(line);
            if (page.getInstruction() == instruction) {
                return line;
            }
        }
        return -1;
    }
    
    private void updatePageInSwapMemory(int memoryPageLine) {
        MemoryPage page = memory.getLineAsPage(memoryPageLine);
        int swapPageLine = MMU.get(memoryPageLine);
        page.setModificationBit(0);
        page.setAccessBit(0);
        swapMemory.setLine(swapPageLine, page);
    }
    
    protected void schedulePageToUpdateInSwap(final Integer pageId) {
        pagesToUpdateInSwap.push(pageId);
    }

    private void updatePagesInSwap() {
        while (!pagesToUpdateInSwap.isEmpty()) {
            updatePageInSwapMemory(pagesToUpdateInSwap.pop());
        }
    }

    // Adiciona novas paginas na tabela de paginas
    public abstract void addPage(int memoryPageLine);

    // Esse metodo é para atualizar a nova pagina que foi selecionando dentro de SWAP na tabela de paginas
    protected abstract void updateArrivalsPage(int memoryPage);

    // Escolhe a pagina que sera removida da memoria ram, ou seja, "PAGINA UNUTIL"
    // Cada Algoritimo terá sua estretégia de determinar qual a pagina mais inutil dentro da RAM
    protected abstract int chooseUselessPage();




}
