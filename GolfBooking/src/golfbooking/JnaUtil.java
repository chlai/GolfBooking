package golfbooking;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.BaseTSD.LONG_PTR;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.RECT;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.User32Util;
 
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import org.apache.commons.net.time.TimeTCPClient;
import org.apache.commons.net.time.TimeUDPClient;


/**
 * static methods to allow Java to call Windows code. user32.dll code is as
 * specified in the JNA interface User32.java
 *
 * @author Pete S
 *
 */
public class JnaUtil {

    private static final User32 user32 = User32.INSTANCE;
    private static Pointer callBackHwnd;
    public static void quitWindow(Pointer hwnd){
        User32.INSTANCE.SendMessage(hwnd, WinUser.WM_QUIT,null, null);

    }
    public static boolean windowExists(final String startOfWindowName) {
        return !user32.EnumWindows(new User32.WNDENUMPROC() {
            @Override
            public boolean callback(Pointer hWnd, Pointer userData) {
                byte[] windowText = new byte[512];
                user32.GetWindowTextA(hWnd, windowText, 512);
                String wText = Native.toString(windowText).trim();

                if (!wText.isEmpty() && wText.startsWith(startOfWindowName)) {
                    return false;
                }
                return true;
            }
        }, null);
    }

    public static List<String> getAllWindowNames() {
        final List<String> windowNames = new ArrayList<String>();
        user32.EnumWindows(new User32.WNDENUMPROC() {

            @Override
            public boolean callback(Pointer hWnd, Pointer arg) {
                byte[] windowText = new byte[512];
                user32.GetWindowTextA(hWnd, windowText, 512);
                String wText = Native.toString(windowText).trim();
                if (!wText.isEmpty()) {
                    windowNames.add(wText);
                }
                return true;
            }
        }, null);

        return windowNames;
    }

    public static boolean windowExists(Pointer hWnd) {
        return user32.IsWindow(hWnd);
    }

    public static Pointer getWinHwnd(final String startOfWindowName) {
        callBackHwnd = null;

        user32.EnumWindows(new User32.WNDENUMPROC() {
            @Override
            public boolean callback(Pointer hWnd, Pointer userData) {
                byte[] windowText = new byte[512];
                user32.GetWindowTextA(hWnd, windowText, 512);
                String wText = Native.toString(windowText).trim();

                if (!wText.isEmpty() && wText.startsWith(startOfWindowName)) {
                    callBackHwnd = hWnd;
                    return false;
                }
                return true;
            }
        }, null);
        return callBackHwnd;
    }

    public static Pointer getWinHwndEndWith(final String EndOfWindowName) {
        callBackHwnd = null;

        user32.EnumWindows(new User32.WNDENUMPROC() {
            @Override
            public boolean callback(Pointer hWnd, Pointer userData) {
                byte[] windowText = new byte[512];
                user32.GetWindowTextA(hWnd, windowText, 512);
                String wText = Native.toString(windowText).trim();

                if (!wText.isEmpty() && wText.endsWith(EndOfWindowName)) {
                    callBackHwnd = hWnd;
                    return false;
                }
                return true;
            }
        }, null);
        return callBackHwnd;
    }

    public static Pointer getWinHwndContainsExclude(final String contains, final String exclude) {
        callBackHwnd = null;

        user32.EnumWindows(new User32.WNDENUMPROC() {
            @Override
            public boolean callback(Pointer hWnd, Pointer userData) {
                byte[] windowText = new byte[512];
                user32.GetWindowTextA(hWnd, windowText, 512);
                String wText = Native.toString(windowText).trim();
                if (!wText.isEmpty() && wText.contains(contains) && wText.indexOf(exclude) < 0) {
                    callBackHwnd = hWnd;
                    return false;
                }
                return true;
            }
        }, null);
        return callBackHwnd;
    }

    public static List<Pointer> getAllWinHwndContains(final String contains) {

        List<Pointer> allWnd = new ArrayList<>();
        user32.EnumWindows(new User32.WNDENUMPROC() {
            @Override
            public boolean callback(Pointer hWnd, Pointer userData) {
                byte[] windowText = new byte[512];
                user32.GetWindowTextA(hWnd, windowText, 512);
                String wText = Native.toString(windowText).trim();
                
                if (!wText.isEmpty() && wText.contains(contains)) {
                    allWnd.add(hWnd);
                    return true;
                }
                return true;
            }
        }, null);
        return allWnd;
    }

    public static boolean setForegroundWindow(Pointer hWnd) {
        return user32.SetForegroundWindow(hWnd) != 0;
    }

    public static Pointer getForegroundWindow() {
        return user32.GetForegroundWindow();
    }

    public static String getForegroundWindowText() {
        Pointer hWnd = getForegroundWindow();
        int nMaxCount = 512;
        byte[] lpString = new byte[nMaxCount];
        int getWindowTextResult = user32
                .GetWindowTextA(hWnd, lpString, nMaxCount);
        if (getWindowTextResult == 0) {
            return "";
        }

        return Native.toString(lpString);
    }

    public static boolean isForegroundWindow(Pointer hWnd) {
        return user32.GetForegroundWindow().equals(hWnd);
    }

    public static boolean setForegroundWindow(String startOfWindowName) {
        Pointer hWnd = getWinHwnd(startOfWindowName);
        return user32.SetForegroundWindow(hWnd) != 0;
    }

    public static Rectangle getWindowRect(Pointer hWnd) throws JnaUtilException {
        if (hWnd == null) {
            throw new JnaUtilException(
                    "Failed to getWindowRect since Pointer hWnd is null");
        }
        Rectangle result = null;
        RECT rect = new RECT();
        boolean rectOK = user32.GetWindowRect(hWnd, rect);
        if (rectOK) {
            int x = rect.left;
            int y = rect.top;
            int width = rect.right - rect.left;
            int height = rect.bottom - rect.top;
            result = new Rectangle(x, y, width, height);
        }

        return result;
    }

    /**
     * set window at x and y position with w and h width. Set on top of z-order
     *
     * @param hWnd
     * @param x
     * @param y
     * @param w
     * @param h
     * @return boolean -- did it work?
     */
    public static boolean setWindowPos(Pointer hWnd, int x, int y, int w, int h) {
        int uFlags = 0;
        return user32.SetWindowPos(hWnd, User32.HWND_TOP, x, y, w, h, uFlags);
    }

    public static boolean moveWindow(Pointer hWnd, int x, int y, int nWidth,
            int nHeight) {
        boolean bRepaint = true;
        return user32.MoveWindow(hWnd, x, y, nWidth, nHeight, bRepaint);
    }

    public static Rectangle getWindowRect(String startOfWindowName)
            throws JnaUtilException {
        Pointer hWnd = getWinHwnd(startOfWindowName);
        if (hWnd != null) {
            return getWindowRect(hWnd);
        } else {
            throw new JnaUtilException("Failed to getWindowRect for \""
                    + startOfWindowName + "\"");
        }
    }

    public static Pointer getWindow(Pointer hWnd, int uCmd) {
        return user32.GetWindow(hWnd, uCmd);
    }
//    public static boolean setWindowVisible(Pointer hWnd){
//        return user32.setWindowVisible(hWnd, 5);
//    }

    public static String getWindowText(Pointer hWnd) {
        int nMaxCount = 512;
        byte[] lpString = new byte[nMaxCount];
        int result = user32.GetWindowTextA(hWnd, lpString, nMaxCount);
        if (result == 0) {
            return "";
        }
        return Native.toString(lpString);
    }

    public static Pointer getOwnerWindow(Pointer hWnd) {
        return user32.GetWindow(hWnd, User32.GW_OWNER);
    }

    public static String getOwnerWindow(String childTitle) {
        Pointer hWnd = getWinHwnd(childTitle);
        Pointer parentHWnd = getOwnerWindow(hWnd);
        if (parentHWnd == null) {
            return "";
        }
        return getWindowText(parentHWnd);

    }

    public static Pointer getNextWindow(Pointer hWnd) {
        if (hWnd == null) {
            return null;
        }

        return user32.GetWindow(hWnd, User32.GW_HWNDNEXT);
    }

    public static boolean isWindowVisible(Pointer hWnd) {
        return user32.IsWindowVisible(hWnd);
    }
    
    public static boolean setWindowVisible(Pointer hWnd){
        user32.SetWindowPos(hWnd, hWnd, 0, 0, 0, 0, 0)  ;
        return user32.setWindowVisible(hWnd,5);
        
    }

    public static Pointer getParent(Pointer hWnd) {
        return user32.GetParent(hWnd);
    }

    public static Pointer getRoot(Pointer hWnd) {
        return user32.GetAncestor(hWnd, User32.GA_ROOT);
    }

    public static LONG_PTR getWindowLongPtr(Pointer hWndP, int nIndex) {
        HWND hwnd = new HWND(hWndP);
        return user32.GetWindowLongPtr(hwnd, nIndex);
    }
// main method to test the library

    public static void refreshAllGoogle() {
        try {
            String wndName = "";
            Robot robot = new Robot();
            List<Pointer> pointers = getAllWinHwndContains("Google Chrome");
            long timenow = 0;
            String milsec = "";
            Date date = new Date();
            long start = System.currentTimeMillis();
            for (;(System.currentTimeMillis()-start)<3000;) {
//                System.out.println("Round - " +System.currentTimeMillis() );
                for (Pointer pointer : pointers) {
                    timenow =System.currentTimeMillis();
                    date.setTime(timenow);
                    milsec = "" + timenow;
                    milsec =" " +  milsec.substring(10);
                    System.out.println("Each - " + timenow + "   " + date  +milsec);
                    wndName = getWindowText(pointer);
                    setForegroundWindow(pointer);
                    robot.delay(10);
                    robot.keyPress(KeyEvent.VK_F5);
                    robot.keyRelease(KeyEvent.VK_F5);
                    robot.delay(5);
                   
//                    System.out.println(wndName);
                }
            }
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
//            for (Pointer pointer : pointers) {
//                wndName = getWindowText(pointer);
//                System.out.println(wndName);
//            }
            System.out.println("Time ellapse: " + timeElapsed / 100.0);
        } catch (AWTException ex) {
            Logger.getLogger(JnaUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void refreshAt(long timeToRefresh, long interval, List<Pointer> hwnds){
        
        
        
    }

    public static void refreshGolfBookingSingleWindow(long target) {
        
        Pointer hWnd = getWinHwndEndWith("Google Chrome");
        boolean foo = setForegroundWindow(hWnd);
        target = target + 100;
        try {
            Robot robot = new Robot();
//            robot.setAutoDelay(3);
            long start = System.currentTimeMillis();
            long finish = 0;
            long counter = 0;
//            target = target-10;
            for (;(finish-start)<8000;) {
                setForegroundWindow(hWnd);
                robot.keyPress(KeyEvent.VK_F5);
                robot.keyRelease(KeyEvent.VK_F5);
                Thread.sleep(20);
                finish = System.currentTimeMillis();
                if(finish>target) break;
                counter++;
            }
            captureBooking(hWnd);
            long timeElapsed = finish - start;
            System.out.println("Time ellapse: " + timeElapsed / 1000.0 + "Key Pressed: " + counter);
        } catch (AWTException ex) {
            Logger.getLogger(JnaUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(JnaUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void refreshChromeTabs(int mode) {

        Pointer hWnd = getWinHwndEndWith("Google Chrome");
        boolean foo = setForegroundWindow(hWnd);
        System.out.println(getWindowText(hWnd));
        try {
            Robot robot = new Robot();
//            robot.setAutoDelay(3);
            long start = System.currentTimeMillis();
             
            for (;(System.currentTimeMillis()-start)<2000;) {
                setForegroundWindow(hWnd);
                if (mode == 0) {
                    robot.keyPress(KeyEvent.VK_F5);
                    robot.keyRelease(KeyEvent.VK_F5);
                } else if (mode == 1) {
                    robot.keyPress(KeyEvent.VK_END);
                    robot.keyRelease(KeyEvent.VK_END);
                } else if (mode == 2) {
                    robot.keyPress(KeyEvent.VK_HOME);
                    robot.keyRelease(KeyEvent.VK_HOME);
                }
                robot.delay(5);
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_TAB);
                robot.delay(5);
                robot.keyRelease(KeyEvent.VK_TAB);
                robot.keyRelease(KeyEvent.VK_CONTROL);
                robot.delay(100);
            }
            System.out.println("Time ellapse: " + (System.currentTimeMillis()-start) / 1000.0);
        } catch (AWTException ex) {
            Logger.getLogger(JnaUtil.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }

    static public Date timeAt(int year, int month, int day, int hour, int minute, int second) {
        Calendar cal = Calendar.getInstance();
//        cal.setTimeZone(TimeZone.getTimeZone("HKT"));
        cal.set(year, month - 1, day, hour, minute,second);
 
        return cal.getTime();
    }

    static public long timeDiff() {
            long timeElapsed= 0;
            long sysTime = System.currentTimeMillis();
            long webTime = timeIs().getTime();
            timeElapsed = webTime - sysTime;
            return timeElapsed;
    }

    static public Date timeIs() {
        try {
            TimeUDPClient client = new TimeUDPClient();
            // We want to timeout if a response takes longer than 60 seconds
            client.setDefaultTimeout(1000);
            client.open();
            
            Date dd = client.getDate(InetAddress.getByName("118.143.17.82"));
 
            client.close();
            return dd;
        } catch (SocketException ex) {
            Logger.getLogger(JnaUtil.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(JnaUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JnaUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static final void timeTCP(String host) throws IOException
         {
             TimeTCPClient client = new TimeTCPClient();
         try {
               // We want to timeout if a response takes longer than 60 seconds
               client.setDefaultTimeout(1000);
           client.connect(host);
               System.out.println(client.getDate());
         } finally {
               client.disconnect();
         }
         }
     
         public static final void timeUDP(String host) throws IOException
         {
             TimeUDPClient client = new TimeUDPClient();
     
             // We want to timeout if a response takes longer than 60 seconds
             client.setDefaultTimeout(1000);
             client.open();
              Date dd = client.getDate(InetAddress.getByName(host));
              long nowtime = System.currentTimeMillis();
             System.out.println("Time "+ nowtime + "tcp time " +dd.getTime());
             client.close();
         }
    public static final void mainnew(String[] args) {
        try {
            
            timeTCP("time-a-g.nist.gov");
            Date d = timeIs();
            System.out.println("Time is: " + d.getTime());
            //timeTCP(args[0]);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        if (args.length == 1) {
            try {
                timeTCP(args[0]);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        } else if (args.length == 2 && args[0].equals("-udp")) {
            try {
                timeUDP(args[1]);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        } else {
            System.err.println("Usage: TimeClient [-udp] <hostname>");
            System.exit(1);
        }

    }
    public static void main(String[] args) throws InterruptedException {
        List<String> wnames = getAllWindowNames();
        for (String wname : wnames) {
            if (wname.contains("Google Chrome")) {
                System.out.println(wname);
            }
        }
        List<Pointer> hwnsChrome =  getAllWinHwndContains("Google Chrome");
        
        Date time = timeIs();
        long tt = System.currentTimeMillis();
        
        long dif =  timeDiff();
        System.out.println("Difference " + dif/1000.0);

//        refreshGolfBookingSingleWindow((System.currentTimeMillis() / 1000) * 1000 + 1500);
//        refreshAllGoogle();
//        refreshGolfBookingSingleWindow();
//        refreshChromeTabs(0);
    }
    public static void captureBooking(Pointer bookWnd){
        try {
            Rectangle rect = getWindowRect(bookWnd);
            Robot robot = new Robot();
            
            BufferedImage img = robot.createScreenCapture(rect);
            ImageIcon icon = new ImageIcon(img);
            final JLabel label = new JLabel(icon);
            Thread.sleep(5);
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    JOptionPane.showMessageDialog(null, label);
                }
            });   
        } catch (JnaUtilException e) {
            e.printStackTrace();
        } catch (InterruptedException ex) {
            Logger.getLogger(JnaUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AWTException ex) {
            Logger.getLogger(JnaUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    // main method to test the library
    public static void screencapture(String[] args) throws InterruptedException {
        List<String> winNameList = getAllWindowNames();
//      for (String winName : winNameList) {
//         System.out.println(winName);
//      }

        String[] testStrs = {"Untitled-Notepad", "Untitled - Notepad",
            "Untitled  -  Notepad", "Time", "Java - Epic", "Fubars rule!",
            "The First Night", "New Tab", "Citrix X", "EHR PROD - SVC"};
        for (String testStr : testStrs) {
            Pointer hWnd = getWinHwnd(testStr);
            boolean isWindow = windowExists(hWnd);
            if (isWindow) {
                System.out.printf("%-22s %5b %16s %b%n", testStr,
                        windowExists(testStr), hWnd, isWindow);
            }
        }

        String notePad = "Untitled - Notepad";
        Pointer hWnd = getWinHwnd(notePad);
        System.out
                .println("is it foreground window? " + isForegroundWindow(hWnd));
        boolean foo = setForegroundWindow(notePad);
        System.out.println("foregroundwindow: " + foo);
        Thread.sleep(400);
        System.out
                .println("is it foreground window? " + isForegroundWindow(hWnd));
        Thread.sleep(1000);
        System.out.println("here A");
        try {
            Rectangle rect = getWindowRect(notePad);
            System.out.println("rect: " + rect);
            Robot robot = new Robot();
            System.out.println("here B");

            BufferedImage img = robot.createScreenCapture(rect);
            System.out.println("here C, img is " + img);
            Thread.sleep(500);
            ImageIcon icon = new ImageIcon(img);
            System.out.println("here D. icon is null? " + icon);
            Thread.sleep(500);
            final JLabel label = new JLabel(icon);
            System.out.println("here E. label is null? " + label);
            Thread.sleep(500);
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    System.out.println("here F");
                    JOptionPane.showMessageDialog(null, label);
                    System.out.println("here G");
                }
            });

        } catch (AWTException e) {
            e.printStackTrace();
        } catch (JnaUtilException e) {
            e.printStackTrace();
        }
    }

}
