/*
 * @Author Triston Gregoire
 */
public class SuperPeerCB {
    private String IP;
    private int port;

    public SuperPeerCB(String address, int port){
        setIP(address);
        setPort(port);
    }
    public SuperPeerCB(String address, String port){
        setIP(address);
        setPort(port);
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
    public void setPort(String port){
        this.port = Integer.parseInt(port);
    }
}
