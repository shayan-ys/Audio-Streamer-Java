/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package streamer;

/**
 *
 * @author negarbayati
 */

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketAddress;
import javax.swing.JOptionPane;
import javazoom.jl.player.Player;

public class CustomPlayer {

private Player player;
private FileInputStream FIS;
private BufferedInputStream BIS;
private Socket socket;
private boolean canResume;
private String path;
private int total;
private int stopped;
private boolean valid;
private boolean socketAvailable;
private int portNumber;
private SocketAddress bindpoint;

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
}

public void setSocket(int port) {
    portNumber = port;
    try{
        socket = new Socket("localhost", port);
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
            stopped = socket.getInputStream().available();
            bindpoint = socket.getLocalSocketAddress();
            
        }else{
            stopped = FIS.available();
            player.close();
            player = null;
        }
        FIS = null;
        BIS = null;
        if(valid) canResume = true;
    }catch(Exception e){
        System.out.println("paused error!");
    }
}

public void resume(){
    if(!canResume) return;
    if(play(total-stopped)) canResume = false;
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
            if(socket.isClosed())
                socket.bind(bindpoint);
            if(player==null) {
                InputStream stream = socket.getInputStream();
                stream.skip(pos);
                player = new Player(stream);
            }else{
                player.notify();
            }
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

}