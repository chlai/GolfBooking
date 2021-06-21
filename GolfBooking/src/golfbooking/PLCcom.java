/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package golfbooking;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author User
 */
public class PLCcom {
   
    
    byte[] toPLC = null;
    byte[] fromPLC = null;

    public PLCcom() {
        toPLC = new byte[103];
        fromPLC = new byte[71];
    }
    
    public String byteArrayString(){
        String result = "";
        for(int i =0; i < toPLC.length; i++){
            result = result + "["+ i  +"]"+  byte2String(toPLC[i]) ;
        }
        return result;
    }
    public void setValue(int index, int bitPos, int value){
        toPLC[index] = setBitValue(toPLC[index], bitPos, value);
    }
    
    //PC to PLC start
    public void eSTOP(int value){
        toPLC[1] = setBitValue(toPLC[1], 0, value);
    }
    public void resetPLC(int value){
        toPLC[1] = setBitValue(toPLC[1], 1, value);
    }
    public void forceOnDQ0_31(int index, int value){
        if(index >31)return;
        int cind = index%8;
        int byteId = -1;
        int start = 2;
        if(index <8){
             byteId = start;
        }else if(index <16){
             byteId = start+1;
        }else if(index <24){
             byteId = start+2;
        }else if(index <32){
             byteId = start+3;
        }
         toPLC[byteId] = setBitValue(toPLC[byteId], cind, value);
    }
    public void forceOffDQ0_31(int index, int value){
        if(index >31)return;
        int cind = index%8;
        int byteId = -1;
       int start = 10;
        if(index <8){
             byteId = start;
        }else if(index <16){
             byteId = start+1;
        }else if(index <24){
             byteId = start+2;
        }else if(index <32){
             byteId = start+3;
        }
         toPLC[byteId] = setBitValue(toPLC[byteId], cind, value);
    }
    public void forceAutoDQ0_31(int index, int value){
        if(index >31)return;
        int cind = index%8;
        int byteId = -1;
        int start = 10;
        if(index <8){
             byteId = start;
        }else if(index <16){
             byteId = start+1;
        }else if(index <24){
             byteId = start+2;
        }else if(index <32){
             byteId = start+3;
        }
         toPLC[byteId] = setBitValue(toPLC[byteId], cind, value);
    }
    public void forceOpenDI0_31(int index, int value){
        if(index >31)return;
        int cind = index%8;
        int byteId = -1;
        int start = 14;
        if(index <8){
             byteId = start;
        }else if(index <16){
             byteId = start+1;
        }else if(index <24){
             byteId = start+2;
        }else if(index <32){
             byteId = start+3;
        }
         toPLC[byteId] = setBitValue(toPLC[byteId], cind, value);
    }
    public void forceCloseDI0_31(int index, int value){
        if(index >31)return;
        int cind = index%8;
        int byteId = -1;
        int start = 18;
        if(index <8){
             byteId = start;
        }else if(index <16){
             byteId = start+1;
        }else if(index <24){
             byteId = start+2;
        }else if(index <32){
             byteId = start+3;
        }
         toPLC[byteId] = setBitValue(toPLC[byteId], cind, value);
    }
    public void setPLCProtocol(PLCProtocol cmd, int value){
        int index = -1;
        int bitPos = -1;
  
        switch (cmd) {
            case Start_RFID:
                index = 22;
                bitPos = 0;
                break;
            case Get_RFID:
                break;
            case MOVE_AXIS_POS:
                index = 24;
                bitPos = 0;
                break;
            case STOP_AXIS_MOVE:
                index = 24;
                bitPos = 1;
                break;
            case GET_AXIS_POS:
                break;
            case START_SMALL_BRUSH:
                index = 28;
                bitPos = 0;
                break;
            case STOP_SMALL_BRUSH:
                index = 28;
                bitPos = 1;
                break;
            case GET_SMALL_BRUSH_INUSE:
                break;
            case START_LARGE_BRUSH:
                index = 30;
                bitPos = 0;
                break;
            case STOP_LARGE_BRUSH:
                index = 30;
                bitPos = 1;
                break;
            case GET_LARGE_BRUSH_INUSE:
                break;
            case PUSH_SMALL_RFT:
                index = 32;
                bitPos = 0;
                break;
            case PULL_SMALL_RFT:
                index = 32;
                bitPos = 1;
                break;
            case STOP_SMALL_RFT:
                index = 32;
                bitPos = 2;
                break;
            case GET_PUSH_SMALL_RFT_INUSE:
                break;
            case GET_PULL_SMALL_RFT_INUSE:
                break;
            case PUSH_LARGE_RFT:
                index = 34;
                bitPos = 0;
                break;
            case PULL_LARGE_RFT:
                index = 34;
                bitPos = 1;
                break;
            case STOP_LARGE_RFT:
                index = 34;
                bitPos = 2;
                break;
            case GET_PUSH_LARGE_RFT_INUSE:
                break;
            case GET_PULL_LARGE_RFT_INUSE:
                break;
            case PUSH_MOVE_CAMERA:
                index = 36;
                bitPos = 0;
                break;
            case PULL_MOVE_CAMERA:
                index = 36;
                bitPos = 1;
                break;
            case STOP_MOVE_CAMERA:
                index = 36;
                bitPos = 2;
                break;
            case GET_PUSH_MOVE_CAMERA_INUSE:
                break;
            case GET_PULL_MOVE_CAMERA_INUSE:
                break;
            case IDLE:
                break;
            case TERMINATE:
                break;
            case OPEN_UR_VALVE:
                index = 26;
                bitPos = 0;
                break;
            case CLOSE_UR_VALVE:
                index = 24;
                bitPos = 1;
                break;
            default:
                break;
        }
        if(index >= 0 && bitPos >= 0){
            toPLC[index] = setBitValue(toPLC[index], bitPos, value);
        }
    }
    public void RESET_AXIS_DRIVE(int value) {
        int index = 24;
        toPLC[index] = setBitValue(toPLC[index], 2, value);
    }
    public void RESET_SMALL_RFT_DRIVE(int value) {
        int index = 32;
        toPLC[index] = setBitValue(toPLC[index], 3, value);
    }
    public void RESET_LARGE_RFT_DRIVE(int value) {
        int index = 34;
        toPLC[index] = setBitValue(toPLC[index], 3, value);
    }
    public void RESET_CAMERA_DRIVE(int value) {
        int index = 36;
        toPLC[index] = setBitValue(toPLC[index], 3, value);
    }
    public void TOOL_PUSH_TIMER(int value){
        toPLC[39] = byte8int(value);
    }							
    public void TOOL_PULL_TIMER(int value){
        toPLC[40] = byte8int(value);
    }
    //id 1 to 32 1,2 is reserved by tool push/pull timer
    public void setTimer(int timerId, int value){
        toPLC[38+timerId] = byte8int(value);
    }	
    public void AXIS_POS(int value){
        byte[] bval = byte16int(value);
        int index = 71;
        toPLC[index] =bval[0];
        toPLC[index+1] =bval[1];
    }
    public void moveAxis(int distance){
        
        AXIS_POS(distance);
    }
    //id=1 is reversed by AXIS_POS
    public void setFunctionValue(int id, int value){
        byte[] bval = byte16int(value);
        int index = 70+id;
        toPLC[index] =bval[0];
        toPLC[index+1] =bval[1];
    }
  //PC to PLC end
  
    public static String getPacketString(byte[] input, int from) {
        int len = input.length;
        byte[] byteint = Arrays.copyOfRange(input, from, len);
        String s = new String(byteint);
        return s;
    }

    public static String byte2String(byte b1){
            String s1 = String.format("%8s", Integer.toBinaryString(b1 & 0xFF)).replace(' ', '0');
            return s1;
    }
    public static byte byte8int(int n) {
        return (byte) n;
    }
    
    public static byte[] byte16int(int n) {
        byte[] result = new byte[2];
        result[1] = (byte) n;
        result[0] = (byte) (n >>> 8);
        return result;
    }
    
    public static int byte2int8(byte b){
        return b & 0xFF;
    }
   
    public static int byte2int16(byte b1, byte b2) {
//        return (b2<<8)+b1;
        return ((b1 << 8) & 0x0000ff00) | (b2 & 0x000000ff);
        //return (int) (((b2 & 0xFF) << 8) | (b1 & 0xFF));
    }

    public static byte setBitValue(byte b, int at, int value) {
        if (value == 1) {
            b = (byte) (b | (1 << at));
        } else {
            b = (byte) (b & ~(1 << at));
        }
        return b;
    }
    
    public static byte setBytebyString( String bString){
      
        byte b = 0x00;
        int j = 0 ; 
        for(int i=7; i>=0;i--){
            if(bString.charAt(i) == '0'){
                b = setBitValue(b,j, 0 );
            } else{
                b = setBitValue(b,j, 1);
            }
            j++;
        }
        return b;
    }
    
   

    public static void main(String args[]) {
        char ch = 'a';
        int ascii = ch;
        
        
        PLCcom plc = new PLCcom();
//        plc.toPLC[0] =plc.byte8int(255);
//        for(int i =1; i < plc.toPLC.length; i++){
//            plc.toPLC[i] =plc.byte8int(i);
//        }
        byte test = PLCcom.setBytebyString("00001111");
        
        byte[] b16 = byte16int(12345);
        int i16 = byte2int16(b16[0], b16[1]);
        
         byte test1 = setBitValue(test, 7, 1);
         System.out.println(byte2String(test1));
         
         byte test3 = plc.toPLC[0];
         byte test4 = setBitValue(test3, 3, 1);
         System.out.println("Source: " + byte2String(test3) + "  Result: "+byte2String(test4) );
        System.out.println("Byte 2 integer " +byte2String(test)  + ": " + byte2int8(test));
        plc.forceOnDQ0_31(7, 1);
        plc.forceOnDQ0_31(0, 1);
        plc.forceOnDQ0_31(8, 1);
        plc.forceOnDQ0_31(15, 1);
        plc.forceOnDQ0_31(16, 1);
        plc.forceOnDQ0_31(23, 1);
        plc.forceOnDQ0_31(24, 1);
        plc.forceOnDQ0_31(31, 1);
        System.out.println();
        System.out.println(getPacketString(plc.toPLC, 0));
        System.out.println(plc.byteArrayString());
        
    }
}
