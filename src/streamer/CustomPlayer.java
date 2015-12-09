/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package streamer;

/**
 *
 * @author shayanyousefian & negarbayati
 */

import java.awt.List;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javazoom.jl.player.Player;

public class CustomPlayer {

private Player player;
private FileInputStream FIS;
private BufferedInputStream BIS;
private InputStream NIS;
private Socket socket;
private boolean canResume;
private String path;
private int total;
private int stopped;
private boolean valid;
private boolean socketAvailable;
private int portNumber;
private SocketAddress bindpoint;
private byte[] buffer;
private int streamSize;

public CustomPlayer(){
    player = null;
    FIS = null;
    valid = false;
    BIS = null;
    path = null;
    total = 0;
    stopped = 0;
    canResume = true;
    socketAvailable = false;
    buffer = new byte[0xFFFFFFF];
}

public ArrayList<String> getMetaData(int port) {
    try {
         Socket metaSocket = new Socket("localhost", port);
         BufferedReader in = new BufferedReader(new
            InputStreamReader(metaSocket.getInputStream()));
         System.out.print("Received string:");

         while (!in.ready()) {}
         String liner;
         ArrayList<String> data = new ArrayList<String>();
         while((liner = in.readLine())!=null){
             data.add(liner);
             System.out.println(liner);
         }

         in.close();
         metaSocket.close();
         return data;
      }
      catch(Exception e) {
         System.out.println("Whoops! metaSocket exception "+ e);
      }
    return null;
}

public void setSocket(int port) {
    portNumber = port;
    try{
        socket = new Socket("localhost", port);
        BIS = new BufferedInputStream(socket.getInputStream());
        streamSize = BIS.available();
        ;
        
        new Thread(
                new Runnable(){
                    public void run(){
                        try{
                            buffer = getBytes(BIS);
                            NIS = new ByteArrayInputStream(buffer);
                        }catch(Exception e){
                            JOptionPane.showMessageDialog(null, "Error1 playing mp3 file"+ e);
                            valid = false;
                        }
                    }
                }
        ).start();
        socketAvailable = true;
    }catch(Exception e) {
         JOptionPane.showMessageDialog(null, "Error Streaming failed"+ e);
         valid = false;   
    }
} 

public boolean canResume(){
    return canResume;
}

public void setPath(String path){
    this.path = path;
}

public void pause(){
    try{
        if(socketAvailable) {
            //stopped = socket.getInputStream().available();
            stopped = NIS.available();
            //stopped = player.getPosition();
            socket.close();
            System.out.println("stopped="+stopped);
            
            System.out.println("position="+player.getPosition()+"s");
            //bindpoint = socket.getLocalSocketAddress();
            
        }else{
            stopped = FIS.available();
            BIS = null;
        }
        BIS = null;
        player.close();
        player = null;
        FIS = null;
        
        if(valid) canResume = true;
    }catch(Exception e){
        System.out.println("paused error!");
    }
}

public void resume(){
    if(!canResume) return;
    if(play(total-stopped)) canResume = false;
}
public int getProgress() {
    return 0;
}
public int getTime() {
    if(player==null) return -1;
    return player.getPosition();
}

public boolean play(int pos){
    valid = true;
    canResume = false;
    try{
        if(!socketAvailable) {
            FIS = new FileInputStream(path);
            total = FIS.available();
            if(pos > -1) FIS.skip(pos);
            BIS = new BufferedInputStream(FIS);
            player = new Player(BIS);
        }else{
//            BIS.skip(pos);
            System.out.println("buffersize="+ total);
            System.out.println("playfrom="+ stopped);
            total = NIS.available();
            buffer = getSubByte(buffer, pos);
            System.out.println("slicebuffersize="+ buffer.length);
            //InputStream is = null;
            NIS = new ByteArrayInputStream(buffer);
            //is.skip(is.available() - stopped);
            //BIS = new BufferedInputStream(is);
            //is.skip(pos);
            player = new Player(NIS);
            //System.out.println("position="+player.getPosition()+"s");
            //player.play();
//            player.play(pos);
        }
        
        new Thread(
                new Runnable(){
                    public void run(){
                        try{
                            player.play();
                        }catch(Exception e){
                            JOptionPane.showMessageDialog(null, "Error1 playing mp3 file"+ e);
                            valid = false;
                        }
                    }
                }
        ).start();
    }catch(Exception e){
        JOptionPane.showMessageDialog(null, "Error2 playing mp3 file"+ e);
        valid = false;
    }
    return valid;
}

public static byte[] getBytes(InputStream is) throws IOException {

    int len;
    int size = 1024;
    byte[] buf;

    if (is instanceof ByteArrayInputStream) {
      size = is.available();
      buf = new byte[size];
      len = is.read(buf, 0, size);
    } else {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      buf = new byte[size];
      while ((len = is.read(buf, 0, size)) != -1) {
        bos.write(buf, 0, len);        
      }
      buf = bos.toByteArray();
    }
    return buf;
  }
/**
   * Gets the subarray from <tt>array</tt> that starts at <tt>offset</tt>.
   */
  public static byte[] getSubByte(byte[] array, int offset) {
    return getSubByte(array, offset, array.length - offset);
  }

  /**
   * Gets the subarray of length <tt>length</tt> from <tt>array</tt>
   * that starts at <tt>offset</tt>.
   */
  public static byte[] getSubByte(byte[] array, int offset, int length) {
    byte[] result = new byte[length];
    System.arraycopy(array, offset, result, 0, length);
    return result;
  }

}