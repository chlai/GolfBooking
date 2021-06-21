package golfbooking;

 

import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.Arrays;

public enum PLCProtocol {
    Start_RFID((byte) 0x01, new Type[] {}, new Type[] { SingleReturn.class }),
    Get_RFID((byte) 0x02, new Type[] {}, new Type[] { LengthReturn.class}),
    MOVE_AXIS_POS((byte) 0x0d, new Type[] { LengthValue.class }, new Type[] { SingleReturn.class }),
    STOP_AXIS_MOVE((byte) 0x0e, new Type[] {}, new Type[] { SingleReturn.class }),
    GET_AXIS_POS((byte) 0x0f, new Type[] {}, new Type[] { LengthReturn.class }),

    START_SMALL_BRUSH((byte) 0x10, new Type[]{}, new Type[] { SingleReturn.class }),
    STOP_SMALL_BRUSH((byte) 0x11, new Type[]{}, new Type[] { SingleReturn.class }),
    GET_SMALL_BRUSH_INUSE((byte) 0x12, new Type[]{}, new Type[] { SingleReturn.class }),
    START_LARGE_BRUSH((byte) 0x18, new Type[]{}, new Type[] { SingleReturn.class }),
    STOP_LARGE_BRUSH((byte) 0x19, new Type[]{}, new Type[] { SingleReturn.class }),
    GET_LARGE_BRUSH_INUSE((byte) 0x1a, new Type[]{}, new Type[] { SingleReturn.class }),

    PUSH_SMALL_RFT((byte) 0x20, new Type[]{}, new Type[] { SingleReturn.class }),
    PULL_SMALL_RFT((byte) 0x21, new Type[]{}, new Type[] { SingleReturn.class }),
    STOP_SMALL_RFT((byte) 0x22, new Type[]{}, new Type[] { SingleReturn.class }),
    GET_PUSH_SMALL_RFT_INUSE((byte) 0x23, new Type[]{}, new Type[] { SingleReturn.class }),
    GET_PULL_SMALL_RFT_INUSE((byte) 0x24, new Type[]{}, new Type[] { SingleReturn.class }),

    PUSH_LARGE_RFT((byte) 0x28, new Type[]{}, new Type[] { SingleReturn.class }),
    PULL_LARGE_RFT((byte) 0x29, new Type[]{}, new Type[] { SingleReturn.class }),
    STOP_LARGE_RFT((byte) 0x2a, new Type[]{}, new Type[] { SingleReturn.class }),
    GET_PUSH_LARGE_RFT_INUSE((byte) 0x2b, new Type[]{}, new Type[] { SingleReturn.class }),
    GET_PULL_LARGE_RFT_INUSE((byte) 0x2c, new Type[]{}, new Type[] { SingleReturn.class }),

    PUSH_MOVE_CAMERA((byte) 0x30, new Type[]{}, new Type[] { SingleReturn.class }),
    PULL_MOVE_CAMERA((byte) 0x31, new Type[]{}, new Type[] { SingleReturn.class }),
    STOP_MOVE_CAMERA((byte) 0x32, new Type[]{}, new Type[] { SingleReturn.class }),
    GET_PUSH_MOVE_CAMERA_INUSE((byte) 0x33, new Type[]{}, new Type[] { SingleReturn.class }),
    GET_PULL_MOVE_CAMERA_INUSE((byte) 0x34, new Type[]{}, new Type[] { SingleReturn.class }),

    OPEN_UR_VALVE((byte) 0x36, new Type[]{}, new Type[] { SingleReturn.class }),
    CLOSE_UR_VALVE((byte) 0x37, new Type[]{}, new Type[] { SingleReturn.class }),
    SEND_DATA_TEST((byte) 0x3a, new Type[] { LengthValue.class }, new Type[] { SingleReturn.class }),
    IDLE((byte) 0x3e, new Type[]{}, new Type[] { LengthReturn.class}),
    TERMINATE((byte) 0x3f, new Type[]{}, new Type[] { LengthReturn.class});

    public final byte index;
    public final Type[] inputType;
    public final Type[] outputType;

    PLCProtocol(byte index, Type[] inputType, Type[] outputType) {
        this.index = index;
        this.inputType = inputType;
        this.outputType = outputType;
    }

    public static PLCProtocol get(byte index) {
        return Arrays.stream(values()).filter(s -> s.index == index).findFirst().orElse(null);
    }

    public static int getTypeSize(Type type) {
        if (type == LengthReturn.class) return LengthReturn.K_SIZE;
        if (type == SingleReturn.class) return SingleReturn.K_SIZE;
        if (type == LengthValue.class) return LengthValue.K_SIZE;
        return 0;
    }

    public static String getTypeName(Type type) {
        if (type == LengthReturn.class) return "Length Type";
        if (type == SingleReturn.class) return "Single Value Type";
        if (type == LengthValue.class) return "Length Type";
        return "None";
    }

    public static Object getValue(Type type, ByteBuffer buffer) {
        if (type == LengthReturn.class) return LengthReturn.get(buffer);
        if (type == SingleReturn.class) return SingleReturn.get(buffer);
        if (type == LengthValue.class) return LengthValue.get(buffer);
        return null;
    }

    public static void setValue(ByteBuffer buffer, Type type, Object obj) {
        if (type == LengthReturn.class) {
            LengthReturn.put(obj, buffer);
        } else if (type == SingleReturn.class) {
            SingleReturn.put(obj, buffer);
        } else if (type == LengthValue.class) {
            LengthValue.put(obj, buffer);
        }
    }
}
