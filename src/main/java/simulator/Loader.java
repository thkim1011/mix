package simulator;

import main.Word;

/**
 * Created by Tae Hyung Kim on 2017-05-05.
 */
public class Loader {
    public void load(int address, int field, int command, Simulate simulator) {
        Word partial = partialField(simulator.getWord(address), field);

        switch(command) {
            case 8: // LDA
                simulator.setRegister("A", partial);
                break;
            case 9: // LD1
                simulator.setRegister("I1", partial);
            break;
            case 10: // LD2
                simulator.setRegister("I2", partial);
            break;
            case 11: // LD3
                simulator.setRegister("I3", partial);
            break;
            case 12: // LD4
                simulator.setRegister("I4", partial);
            break;
            case 13: // LD5
                simulator.setRegister("I5", partial);
            break;
            case 14: // LD6
                simulator.setRegister("I6", partial);
            break;
            case 15: // LDX
                simulator.setRegister("X", partial);
            break;
            default:
                throw new IllegalArgumentException("LOAD: Command must be between 8 and 15.");

        }
    }

    public Word partialField(Word word, int field) {
        int left = field / 8;
        int right = field % 8;
        Word partial = new Word();
        if (left == 0) {
            partial.setSign(word.getSign());
            left = 1;
        }

        for(int i = right, pos = 5; i >= left; i--, pos--) {
            partial.setByte(pos, word.getByte(i));
        }

        return partial;
    }
}
