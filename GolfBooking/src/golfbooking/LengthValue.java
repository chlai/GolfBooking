package golfbooking;

import java.nio.ByteBuffer;

public class LengthValue {
    public final static int K_SIZE = 5;

    public static Integer get(ByteBuffer buffer) {
        buffer.get();
        return HandleByteHelper.getValue(buffer, K_SIZE-1, 10);
    }

    public static void put(Object obj, ByteBuffer buffer) {
        if (!(obj instanceof Integer)) return;
        int value = (Integer) obj;
        value = Math.max(0, value);
        buffer.put((byte) 'a');
        HandleByteHelper.setValue(buffer, K_SIZE-1, 10, value);
    }
}
