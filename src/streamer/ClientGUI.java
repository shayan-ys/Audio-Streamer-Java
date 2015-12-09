/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package streamer;

import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;

/**
 *
 * @author Shayanyousefian
 */
public class ClientGUI extends javax.swing.JFrame {

    /**
     * Creates new form ClientGUI
     */
    public static CustomPlayer player;
    private long timerLastAmount=0;
    private long timerMostCurrent=0;
    private long ms;
    private int progressAmount;
    private Thread progress;
    private Thread timer;
    private static int trackLength;
    
    public ClientGUI() {
        
        initComponents();
//        trackLength = player.getMetaLength(1236);
//        Server serv = new Server();
//        serv.streamMetaData();
//        Server.streamAudio();
        //player.play(-1);
        //player.pause();
    }

    public String timeFormater(long millis){
        String mss = String.format("%02d:%02d:%02d",
        TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
        TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1),
        (millis/100)%100);
        return mss;
    }
    public void updateVars(String mode) {
        updateTime(mode);
        updateProgress(mode);
    }
    
    public void updateTime(String mode) {
        // Playing
        if(mode == "resume"){
            timer = new Thread(
                new Runnable(){
                    public void run(){
                        try{
                            while(true){
                                ms = timerLastAmount + player.getTime();
                                if(ms>timerMostCurrent) timerMostCurrent = ms;
                                //ms += timerLastAmount;
                                jLabel1.setText("time is:"+ timeFormater(ms));
                            }
                        }catch(Exception e){
                            JOptionPane.showMessageDialog(null, "Error1 displaying time resuming"+ e);
                        }
                    }
                }
            );
            timer.start();
        
        // PAUSING
        }else{
            try{
                timer.stop();
                timer.interrupt();
                timer = null;
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error2 timer pausing "+ e);
            }
            System.out.println("timerLastAmount="+timerLastAmount);
            System.out.println("timerMostCurrent="+timerMostCurrent);
            System.out.println("ms="+ms);
            timerLastAmount = ms;
            timerMostCurrent = -1;
        }
    }
    public void updateProgress(String mode) {
        // Playing
        System.out.println("ms="+ ms);
        System.out.println("trlength="+ trackLength);
        if(mode == "resume"){
            progress = new Thread(
                new Runnable(){
                    public void run(){
                        try{
                            while(true){
                                progressAmount = (int)(ms*100) / trackLength;
                                jLabel2.setText("progress is:"+ String.format("%02d",progressAmount) +"%");
                            }
                        }catch(Exception e){
                            JOptionPane.showMessageDialog(null, "Error1 displaying progress resuming"+ e);
                        }
                    }
                }
            );
            progress.start();
        
        // PAUSING
        }else{
            try{
                progress.stop();
                progress.interrupt();
                progress = null;
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error2 progress pausing "+ e);
            }
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Play");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Pause");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("jLabel1");

        jLabel2.setText("jLabel2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jButton1)
                        .addGap(45, 45, 45)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(jLabel1)))
                .addContainerGap(173, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(60, 60, 60))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(8, 8, 8)
                .addComponent(jLabel2)
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addContainerGap(163, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        updateVars("resume");
        player.resume();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        updateVars("save");
        player.pause();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        player = new CustomPlayer();
        trackLength = player.getMetaLength(1236);
      //  player.setPath("/Users/negarbayati/Desktop/still.mp3");
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                player = new CustomPlayer();
                new ClientGUI().setVisible(true);
//                player.setPath("/Users/Shayanyousefian/Documents/Eclipse_workspace/Audio-Streamer-Java/still.mp3");
                player.setSocket(1234);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
