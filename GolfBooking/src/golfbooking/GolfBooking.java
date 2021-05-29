/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package golfbooking;

import com.sun.jna.Pointer;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author CH Lai
 */
public class GolfBooking extends javax.swing.JFrame {

    Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/res/north.png"));
    ScheduledExecutorService ses = null;
    ScheduledExecutorService sesGolf = null;
    public static long timeDiff = 0;

    boolean bStarted = false;
    public static long count = 0;
    public static long timeTarget = 0;
    List<Pointer> hwnsChrome = null;

    /**
     * Creates new form GolfBooking
     */
    public GolfBooking() {
        initComponents();
        jPanelContent.setSize(this.getWidth(), this.getHeight());
        ImageIcon icon = new ImageIcon(getClass().getResource("/res/ksc.png"));
        this.setIconImage(icon.getImage());
        setByCurrentDate();
//        timeDiff = JnaUtil.timeDiff();
    }

    final Runnable refreshSingleWindow = new Runnable() {
        @Override
        public void run() {
            try {
                if (ses != null) {
                    ses.shutdown();
                }
                ses = null;
                jLabelCountDown.setForeground(Color.GREEN);
                jLabelCountDown.setText("Task Excuted");
                int mode = jComboBoxAlgo.getSelectedIndex();
                if (mode == 0) {
                    JnaUtil.refreshAllGoogle();
                } else if (mode == 1) {
                    JnaUtil.refreshGolfBookingSingleWindow(timeTarget);
                } else if (mode == 2) {
                    JnaUtil.refreshChromeTabs(0);
                }
                if (sesGolf != null) {
                    sesGolf.shutdown();
                }
                sesGolf = null;
                count = 0;
            } catch (Exception e) {
                System.out.println("ERROR - unexpected exception function");
            }

        }
    };

    public void countDown() {

        try {  // Let no Exception reach the ScheduledExecutorService.
            long currentTime = System.currentTimeMillis();
            String passed = "Remaining";
            long timeRemain = timeTarget - currentTime;
            if (timeRemain < 0) {
                passed = "Overdue";
                timeRemain = -timeRemain;
            }
            long hour = timeRemain / 3600000;
            long minutes = 0;
            minutes = (timeRemain - hour * 3600000) / 60000;
            double sec = (timeRemain - hour * 3600000 - minutes * 60000) * 1.0 / 1000.0;
            if (hour == 0 && minutes == 0 && sec < 10) {
                if (sesGolf == null) {
                    sesGolf = Executors.newScheduledThreadPool(1);
                    sesGolf.schedule(refreshSingleWindow, timeTarget - System.currentTimeMillis() - timeDiff, TimeUnit.MILLISECONDS);
                }
                jLabelCountDown.setForeground(Color.red);
            } else {
                jLabelCountDown.setForeground(jLabel1.getForeground());
            }
            String strHour = (hour > 0) ? String.format("%dH:", hour) : "";
            String strMin = (minutes > 0) ? String.format("%dM:", minutes) : "";
            String result = String.format("%s -  %s %s %.3fS", passed, strHour, strMin, sec);
//            System.out.println(result);
            count++;
            jLabelCountDown.setText(result);
        } catch (Exception e) {
            System.out.println("ERROR - unexpected exception function");
        }
    }

    private void setByCurrentDate() {
        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minutes = cal.get(Calendar.MINUTE);
        int seconds = 0;
        if (hour > 9) {
            if (hour == 9 && minutes < 30) {
                hour = 9;
                minutes = 30;
            } else {
                hour = 17;
                minutes = 0;
            }
        } else {
            hour = 9;
            minutes = 30;
        }
        jTextFieldyear.setText("" + cal.get(Calendar.YEAR));
        jTextFieldmonth.setText("" + (cal.get(Calendar.MONTH) + 1));
        jTextFieldday.setText("" + cal.get(Calendar.DATE));
        jTextFieldhour.setText("" + hour);
        jTextFieldminute.setText("" + minutes);
        jTextFieldsecond.setText("" + seconds);

    }

    public void updateFrameDisplay() {
        this.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelContent = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jTextFieldyear = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jTextFieldmonth = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jTextFieldday = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jTextFieldhour = new javax.swing.JTextField();
        jComboBoxAlgo = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldminute = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldsecond = new javax.swing.JTextField();
        jLabelCountDown = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jTextFieldLag = new javax.swing.JTextField();
        jButtonStart = new javax.swing.JButton();
        jButtonStop = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jButtonWeb = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Booking");
        setMinimumSize(new java.awt.Dimension(450, 350));
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jPanelContent.setMinimumSize(new java.awt.Dimension(450, 350));
        jPanelContent.setLayout(null);

        jPanel1.setLayout(new java.awt.GridLayout(5, 2));

        jLabel3.setText("Year");
        jPanel1.add(jLabel3);

        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        jTextFieldyear.setText("2021");
        jPanel3.add(jTextFieldyear);

        jButton4.setText("9:30");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton4);

        jPanel1.add(jPanel3);

        jPanel6.setLayout(new java.awt.GridLayout(1, 0));

        jLabel2.setText("Month");
        jPanel6.add(jLabel2);

        jPanel1.add(jPanel6);

        jPanel7.setLayout(new java.awt.GridLayout(1, 0));

        jTextFieldmonth.setText("5");
        jPanel7.add(jTextFieldmonth);

        jButton5.setText("17:00");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton5);

        jPanel1.add(jPanel7);

        jLabel4.setText("Day");
        jPanel1.add(jLabel4);

        jPanel8.setLayout(new java.awt.GridLayout(1, 0));

        jTextFieldday.setText("16");
        jPanel8.add(jTextFieldday);

        jButton3.setText("Now");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel8.add(jButton3);

        jPanel1.add(jPanel8);

        jPanel4.setLayout(new java.awt.GridLayout(1, 0));

        jLabel1.setText("Hour");
        jPanel4.add(jLabel1);

        jPanel1.add(jPanel4);

        jPanel5.setLayout(new java.awt.GridLayout(1, 0));

        jTextFieldhour.setText("9");
        jPanel5.add(jTextFieldhour);

        jComboBoxAlgo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mult-window", "Single Window", "Tabs", "Threads" }));
        jComboBoxAlgo.setSelectedIndex(3);
        jPanel5.add(jComboBoxAlgo);

        jPanel1.add(jPanel5);

        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        jLabel5.setText("Minute");
        jPanel2.add(jLabel5);

        jTextFieldminute.setText("30");
        jTextFieldminute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldminuteActionPerformed(evt);
            }
        });
        jPanel2.add(jTextFieldminute);

        jPanel1.add(jPanel2);

        jPanel9.setLayout(new java.awt.GridLayout(1, 0));

        jLabel6.setText("Sec");
        jPanel9.add(jLabel6);

        jTextFieldsecond.setText("0");
        jPanel9.add(jTextFieldsecond);

        jPanel1.add(jPanel9);

        jPanelContent.add(jPanel1);
        jPanel1.setBounds(10, 10, 420, 160);

        jLabelCountDown.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabelCountDown.setText("Counter Down: ");
        jPanelContent.add(jLabelCountDown);
        jLabelCountDown.setBounds(30, 180, 360, 27);

        jPanel10.setLayout(new java.awt.GridLayout(1, 0));

        jTextFieldLag.setText("1500");
        jPanel10.add(jTextFieldLag);

        jButtonStart.setText("Start");
        jButtonStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStartActionPerformed(evt);
            }
        });
        jPanel10.add(jButtonStart);

        jButtonStop.setText("Stop");
        jButtonStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStopActionPerformed(evt);
            }
        });
        jPanel10.add(jButtonStop);

        jPanelContent.add(jPanel10);
        jPanel10.setBounds(10, 220, 420, 50);

        jTabbedPane1.addTab("Main", jPanelContent);

        jButtonWeb.setText("Web");
        jButtonWeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonWebActionPerformed(evt);
            }
        });
        jPanel11.add(jButtonWeb);

        jButton1.setText("Test");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel11.add(jButton1);

        jTabbedPane1.addTab("Test", jPanel11);

        getContentPane().add(jTabbedPane1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldminuteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldminuteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldminuteActionPerformed

    private void jButtonStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStartActionPerformed
        // TODO add your handling code here:
        jLabelCountDown.setForeground(jLabel1.getForeground());
        jLabelCountDown.setText("");
        int year, month, day, hour, minute, second;
        year = Integer.parseInt(jTextFieldyear.getText());
        month = Integer.parseInt(jTextFieldmonth.getText());
        day = Integer.parseInt(jTextFieldday.getText());
        hour = Integer.parseInt(jTextFieldhour.getText());
        minute = Integer.parseInt(jTextFieldminute.getText());
        second = Integer.parseInt(jTextFieldsecond.getText());
        Date target = JnaUtil.timeAt(year, month, day, hour, minute, second);
        timeTarget = target.getTime();
        timeDiff = Long.parseLong(jTextFieldLag.getText());

        if ((timeTarget - System.currentTimeMillis()) < 0) {
            JOptionPane.showMessageDialog(this, "Expired");
            return;
        }
//        if(timer.isRunning()){
//            timer.stop();
//        } else{
//        timer.start();
//        }

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    countDown();
                } catch (Exception e) {
                    System.out.println("ERROR - unexpected exception function");
                }

            }
        };

        ses = Executors.newScheduledThreadPool(1);
        ses.scheduleWithFixedDelay(runnable, 10, 100, TimeUnit.MILLISECONDS);

// ses.scheduleAtFixedRate(runnable, 100, 200, TimeUnit.MILLISECONDS);
        System.out.println("Seconds to Go: " + (timeTarget - System.currentTimeMillis()) / 1000.0);
    }//GEN-LAST:event_jButtonStartActionPerformed

    private void jButtonStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStopActionPerformed
        // TODO add your handling code here:
        jLabelCountDown.setText("");
        count = 0;
        if (ses != null) {
            ses.shutdown();
        }
        if (sesGolf != null) {
            sesGolf.shutdown();
        }
        sesGolf = null;
        ses = null;
    }//GEN-LAST:event_jButtonStopActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        Calendar cal = Calendar.getInstance();

        jTextFieldyear.setText("" + cal.get(Calendar.YEAR));
        jTextFieldmonth.setText("" + (cal.get(Calendar.MONTH) + 1));
        jTextFieldday.setText("" + cal.get(Calendar.DATE));
        jTextFieldhour.setText("" + cal.get(Calendar.HOUR_OF_DAY));
        if (cal.get(Calendar.SECOND) > 30) {
            jTextFieldminute.setText("" + (cal.get(Calendar.MINUTE) + 1));
            jTextFieldsecond.setText("0");
        } else {
            jTextFieldminute.setText("" + (cal.get(Calendar.MINUTE)));
            jTextFieldsecond.setText("30");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY, 9);
        cal.set(Calendar.MINUTE, 30);
        jTextFieldyear.setText("" + cal.get(Calendar.YEAR));
        jTextFieldmonth.setText("" + (cal.get(Calendar.MONTH) + 1));
        jTextFieldday.setText("" + cal.get(Calendar.DATE));
        jTextFieldhour.setText("" + cal.get(Calendar.HOUR_OF_DAY));
        jTextFieldminute.setText("" + cal.get(Calendar.MINUTE));
        jTextFieldsecond.setText("0");
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 17);
        cal.set(Calendar.MINUTE, 0);

        jTextFieldyear.setText("" + cal.get(Calendar.YEAR));
        jTextFieldmonth.setText("" + (cal.get(Calendar.MONTH) + 1));
        jTextFieldday.setText("" + cal.get(Calendar.DATE));
        jTextFieldhour.setText("" + cal.get(Calendar.HOUR_OF_DAY));
        jTextFieldminute.setText("" + cal.get(Calendar.MINUTE));
        jTextFieldsecond.setText("0");
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButtonWebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonWebActionPerformed

        // TODO add your handling code here:
        try {
            int row = 2, col = 4;
            Robot robot = new Robot();
            hwnsChrome = null;
            for (int i = 0; i < row * col; i++) {
//                Runtime.getRuntime().exec("chrome");
                Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start chrome"});
//                Pointer hwnd = JnaUtil.getForegroundWindow();
                Thread.sleep(500);
            }
            hwnsChrome = JnaUtil.getAllWinHwndContains("Google Chrome");
            Dimension objDimension = Toolkit.getDefaultToolkit().getScreenSize();
            int bwidth = objDimension.width / col;
            int bheight = (objDimension.height - 35) / row;
            int counter = 0;
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (counter >= hwnsChrome.size()) {
                        break;
                    }
                    JnaUtil.setForegroundWindow(hwnsChrome.get(counter));
                    Thread.sleep(300);
                    JnaUtil.setWindowPos(hwnsChrome.get(counter), bwidth * j, bheight * i, bwidth, bheight);
                    Thread.sleep(100);
                    counter++;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(GolfBooking.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AWTException ex) {
            Logger.getLogger(GolfBooking.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(GolfBooking.class.getName()).log(Level.SEVERE, null, ex);
        }
        Pointer hwnd = JnaUtil.getForegroundWindow();

    }//GEN-LAST:event_jButtonWebActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        try {
            URL myURL = new URL("https://booking.kscgolf.org.hk/");
            URLConnection myURLConnection = myURL.openConnection();
            myURLConnection.connect();

        } catch (MalformedURLException e) {
            // new URL() failed
            // ...
        } catch (IOException e) {
            // openConnection() failed
            // ...
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        GolfBooking golfbooking = new GolfBooking();
        Dimension objDimension = Toolkit.getDefaultToolkit().getScreenSize();
        int iCoordX = (objDimension.width - golfbooking.getWidth()) / 2;
        int iCoordY = (objDimension.height - golfbooking.getHeight()) / 2;
        golfbooking.setLocation(iCoordX, iCoordY);
        golfbooking.setVisible(true);
        golfbooking.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButtonStart;
    private javax.swing.JButton jButtonStop;
    private javax.swing.JButton jButtonWeb;
    private javax.swing.JComboBox<String> jComboBoxAlgo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    public static javax.swing.JLabel jLabelCountDown;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelContent;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextFieldLag;
    private javax.swing.JTextField jTextFieldday;
    private javax.swing.JTextField jTextFieldhour;
    private javax.swing.JTextField jTextFieldminute;
    private javax.swing.JTextField jTextFieldmonth;
    private javax.swing.JTextField jTextFieldsecond;
    private javax.swing.JTextField jTextFieldyear;
    // End of variables declaration//GEN-END:variables
}
