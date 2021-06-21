package golfbooking;

import java.nio.ByteBuffer;
import java.util.function.Consumer;

public class HandleByteHelper {

    public static int getValue(ByteBuffer buffer, int maxSide, int base) {
        int value = 0;
        boolean hasValue = false;
        for(int i=0; i<maxSide; i++) {
            int charValue = buffer.get();
            if (charValue == ' ') {
                if (hasValue) break;
                else continue;
            }
            hasValue = true;
            int current = getValue(charValue, base);
            if (current < 0) return -1;
            value *= base;
            value += current;
        }
        return value;
    }

    public static void setValue(ByteBuffer buffer, int maxSide, int base, int value) {
        if (value < 0) value = 0;
        int written = loopToWrite(value, base, 0, maxSide, buffer::put);
        for(int i=written; i<maxSide; i++)  buffer.put((byte) ' ');
    }

    private static int loopToWrite(int value, int base, int currentLevel, int maxSide, Consumer<Byte> writer) {
        int current = value % base;
        int next = value / base;

        int written = -1;
        if (next != 0) {
            written = loopToWrite(next, base, currentLevel+1, maxSide, writer);
            if (written < 0) return -1;
        }
        if (currentLevel >= maxSide) return -1;

        writer.accept(convertToChar(current));
        return written < 0 ? currentLevel+1 : written;
    }

    private static int getValue(int charValue, int base) {
        int digit = charValue - '0';
        if (digit < 0) return -1;
        if (digit < 10) return digit >= base ? -1 : digit;

        int charData = charValue - 'A' >= 26 ? charValue - 'a' : charValue;
        if (charData < 0 || charData >= 26) return -1;
        return 10 + charData;
    }

    private static byte convertToChar(int value) {
        if (value < 10) return (byte) (value + '0');
        return (byte) ((value - 10) + 'a');
    }
}
