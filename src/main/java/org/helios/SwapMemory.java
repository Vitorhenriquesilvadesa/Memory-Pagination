package org.helios;

import java.util.Arrays;
import java.util.Random;

public class SwapMemory extends AbstractMemory {


    public SwapMemory() {
        super(100, 6);
    }

    @Override
    protected void populateMemory() {
        Random random = new Random();

        for (int i = 0; i < getRowCount(); i++) {
            int[] line = getLine(i);

            line[PageDataType.Number.ordinal()] = i;
            line[PageDataType.Instruction.ordinal()] = i + 1;
            line[PageDataType.Data.ordinal()] = random.nextInt(0, 50);
            line[PageDataType.AccessBit.ordinal()] = 0;
            line[PageDataType.ModificationBit.ordinal()] = 0;
            line[PageDataType.AgingTime.ordinal()] = random.nextInt(100, 9999);
        }
    }
}
