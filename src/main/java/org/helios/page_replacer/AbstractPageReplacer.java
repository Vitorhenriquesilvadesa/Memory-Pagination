package org.helios.page_replacer;

import org.helios.MemoryPage;
import org.helios.RandomAccessMemory;
import org.helios.SwapMemory;

import java.util.Map;

public abstract class AbstractPageReplacer {

    // TODO: Escolher nome melhor para isso
    protected final RandomAccessMemory memory;
    protected final SwapMemory swapMemory;
    protected final  Map<Integer, Integer> MMU;

    public AbstractPageReplacer(RandomAccessMemory memory, SwapMemory swapMemory, Map<Integer, Integer> MMU) {
        this.memory = memory;
        this.swapMemory = swapMemory;
        this.MMU = MMU;
    }

    // Esse metodo é para atualizar/adicionar a nova pagina que foi selecionando dentro de SWAP na tabela de pagina

    public abstract void updateArrivalsPage(int memoryPage);

    // Escolhe a pagina que sera removida da memoria ram, ou seja, "PAGINA UNUTIL"
    // Cada Algoritimo terá sua estretégia de determinar qual a pagina mais inutil dentro da RAM
    public abstract int chooseUselessPage();


    // Faz a busca da instrução dentro de SWAP, e realiza a troca de pagina
    public void replacePage(int instruction){
        int requestedPageLine = getPageInSwap(instruction);


        if(requestedPageLine == -1){
            throw new RuntimeException("No page found for instruction " + instruction);
        }


        // solicita a linha da pagina mais inutil dentro da memoria RAM:
        int lineUselessPage = chooseUselessPage();
        MemoryPage uselessPage = memory.getLineAsPage(lineUselessPage);

//        System.out.println("USELESS PAGE: " + lineUselessPage);

        // Caso a pagina (uselessPage) escolhida possua o bit de Modificado = 1, Atualiza pagina dentro de Swap
        if (uselessPage.getModificationBit() == 1){
            updatePageInSwapMemory(lineUselessPage);
        }
            // Seta Pagina encontrada dentro de Swap na Memoria Ram
            memory.setLine(lineUselessPage, swapMemory.getLine(requestedPageLine));
            updateArrivalsPage(lineUselessPage);
            MMU.put(lineUselessPage, requestedPageLine);
    }


    // Percorre a Memoria Swap, retortando a linha da pagina que corespondente a da instrução requisitada.
    private int getPageInSwap(int instruction) {
        for (int line = 0; line < swapMemory.getRowCount(); line++) {
            MemoryPage page = swapMemory.getLineAsPage(line);
            if (page.getInstruction() == instruction){
                return line;
            }
        }
        return -1;
    }

    public void updatePageInSwapMemory(int lineUselessPage){
        int uselessSwapPageLine = MMU.get(lineUselessPage);
        MemoryPage uselessPage = memory.getLineAsPage(lineUselessPage);

        swapMemory.setLine(uselessSwapPageLine, uselessPage);
    }




}
