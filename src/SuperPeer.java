/*
 * @Author Triston Gregoire
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class SuperPeer extends Thread{
    private ServerSocket serverSocket;
    CopyOnWriteArrayList<PeerCB> peerTable = new CopyOnWriteArrayList<>();
    CopyOnWriteArrayList<SuperPeerCB> superPeerTable = new CopyOnWriteArrayList<>();
    MainFrame frame;
    int port;
    public SuperPeer(int port, MainFrame frame){
        this.port = port;
        this.frame = frame;
    }

   /* public static void main(String[] args) {
        SuperPeer s = new SuperPeer();
        SuperPeerCB superPeerCB = new SuperPeerCB("192.168.1.66", 6000);
        SuperPeerCB superPeerCB1 = new SuperPeerCB("192.168.1.84", 6000);
        s.superPeerTable.add(superPeerCB);
        s.superPeerTable.add(superPeerCB1);
        try {
            s.listen(6000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */

    @Override
    public void run() {
        listen();
    }

    void listen(int port){
        try {
            serverSocket = new ServerSocket(port);
            do {
                Socket sock = serverSocket.accept();
                SuperPeerThread thread = new SuperPeerThread(peerTable, superPeerTable, sock, frame);
                thread.start();

            } while (true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    void listen(){
        try {
            serverSocket = new ServerSocket(port);
            do {
                Socket sock = serverSocket.accept();
                SuperPeerThread thread = new SuperPeerThread(peerTable, superPeerTable, sock, frame);
                thread.start();

            } while (true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
