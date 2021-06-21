package golfbooking;

import java.nio.ByteBuffer;

public class LengthReturn {
    public final static int K_SIZE = 5;

    public static Integer get(ByteBuffer buffer) {
        return HandleByteHelper.getValue(buffer, K_SIZE, 10);
    }

    public static void put(Object obj, ByteBuffer buffer) {
        if (!(obj instanceof Integer)) return;
        int value = (Integer) obj;
        value = Math.max(0, value);
        HandleByteHelper.setValue(buffer, K_SIZE, 10, value);
    }
}
