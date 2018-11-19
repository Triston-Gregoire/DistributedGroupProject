import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommunicationDriver {
    private static int serverPort;
    private static int superPeerPort;
    private static String superPeerIP;
    private static List<String> resourceList;
    public static void main(String[] args) {
        CommunicationDriver.setServerPort(6002);
        CommunicationDriver.setSuperPeerPort(6000);
        CommunicationDriver.setSuperPeerIP("192.168.1.76");
        CommunicationDriver.setResourceList(new ArrayList<>());
        CommunicationDriver.getResourceList().add("test.mp4");

        try {
            Server server = new Server(getServerPort(), getResourceList());
            server.start();
            Client client = new Client(getSuperPeerIP(), getSuperPeerPort());
            client.register();
            client.request("test.mp4");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getServerPort() {
        return serverPort;
    }

    public static void setServerPort(int port) {
        serverPort = port;
    }

    public static int getSuperPeerPort() {
        return superPeerPort;
    }

    public static void setSuperPeerPort(int port) {
        superPeerPort = port;
    }

    public static String getSuperPeerIP() {
        return superPeerIP;
    }

    public static void setSuperPeerIP(String ip) {
        superPeerIP = ip;
    }

    public static List<String> getResourceList() {
        return resourceList;
    }

    public static void setResourceList(List<String> list) {
        resourceList = list;
    }
}
