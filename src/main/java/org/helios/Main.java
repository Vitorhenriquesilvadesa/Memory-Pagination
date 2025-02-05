package org.helios;

import org.helios.page_replacer.*;

import java.util.*;

import static org.helios.page_replacer.PageReplacerType.CLOCK;

public class Main {

    private static SwapMemory swapMemory = new SwapMemory();
    private static RandomAccessMemory randomAccessMemory = new RandomAccessMemory();
    private static Map<Integer, Integer> MMU = new HashMap<>();
    private static  final int NUMBER_INSTRUCTIONS = 1000;
    private static PageReplacerWrapper pageReplacer = new PageReplacerWrapper(CLOCK, randomAccessMemory, swapMemory);


    public static void main(String[] args) {
        
        // Populate Memory And Update MMU
        int randomMemoryLine = 0;
        for(int line : getRandomLines()) {
            randomAccessMemory.setLine(randomMemoryLine, swapMemory.getLine(line));
            MMU.put(randomMemoryLine, line);
            pageReplacer.addPage(randomMemoryLine);
            randomMemoryLine++;

        }


        printMemory(randomAccessMemory);
        System.out.println("\n");
        printMemory(swapMemory);
        // Ao Selecionar o Alooritimo, executa 1000 Instruções Aleatorias:
        
        
        for (int countInstrunction = 0; countInstrunction < NUMBER_INSTRUCTIONS; countInstrunction++) {

            // A cada 10 Intruções rodadas, reseta o bit de acesso de todas as paginas da RAM
            if (countInstrunction % 10 == 0) {
                resetAccessBit();
            }
            runInstruction();
        }

        System.out.println("\n");
        printMemory(randomAccessMemory);
        System.out.println("\n");
        printMemory(swapMemory);




    }


    private static void printMemory(AbstractMemory memory) {
        for (int i = 0; i < memory.getRowCount(); i++) {
            System.out.println(Arrays.toString(memory.getLineAsPage(i).toArray()));
        }
    }

    private static void resetAccessBit() {
        for (int i = 0; i < randomAccessMemory.getRowCount(); i++) {
            MemoryPage page = randomAccessMemory.getLineAsPage(i);
            page.setAccessBit(0);

        }
    }

    private static void runInstruction(){
        Random random = new Random();
        boolean hasInstruction = false;
        int randomInstruction =  random.nextInt(1, 100);
        for (int i = 0; i < randomAccessMemory.getRowCount(); i++) {
            MemoryPage page = randomAccessMemory.getLineAsPage(i);
            // Instrução encontrada dentro da memoria Ram:
            if (page.getInstruction() == randomInstruction){
                // Seta o Bit de Acesso para 1
                page.setAccessBit(1);

                // 50% Porcento de chance de ser verdadeiro
                // Caso Verdade adiciona um valor em data, e seta bit de moficação para 1
                if (random.nextInt(100) < 50){
                    page.setData(page.getData() + 1);
                    page.setModificationBit(1);

                }
                hasInstruction = true;
                break;
            }
        }
        // Caso não instrução não esteja na memoria ram, Ele fara a busca dentro de swap
        if (!hasInstruction){
            pageReplacer.replacePage(randomInstruction);
        }

    }

    private static List<Integer> getRandomLines() {
        int sizeY = swapMemory.getRowCount();
        int n = randomAccessMemory.getRowCount();

        List<Integer> numbers = new ArrayList<>();

        for (int i = 0; i <= sizeY; i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers);

        return new ArrayList<>(numbers.subList(0, n));
    }
}