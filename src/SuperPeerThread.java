import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SuperPeerThread extends Thread {
    CopyOnWriteArrayList<PeerCB> peers;
    CopyOnWriteArrayList<SuperPeerCB> neighbors;

    Socket sock;
    PrintWriter writer;
    BufferedReader reader;


    public SuperPeerThread(CopyOnWriteArrayList peerTable, CopyOnWriteArrayList superPeerTable, Socket sock) throws IOException {
        this.peers = peerTable;
        this.neighbors = superPeerTable;
        this.sock = sock;
        this.writer = new PrintWriter(sock.getOutputStream(), true);
        this.reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
    }

    @Override
    public void run() {
        try {
            ServiceType type = categorize();
            service(type);

            //debug output
            for (PeerCB peer : peers) {
                List<String> resource = peer.getResource();
                for (String s : resource) {
                    if ("resource.txt".equalsIgnoreCase(s)){
                        System.out.println(resource);
                    }
                }
            }

            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ServiceType categorize() throws IOException {
        String inputString;
        inputString = reader.readLine();
        ServiceType type = ServiceType.valueOf(inputString);
        return type;
    }

    public void service(ServiceType type) throws IOException {
        switch (type){
            case QUERY: handleQuery();
                break;
            case REQUEST: handleRequest();
                break;
            case RESPONSE: handleResponse();
                break;
            case REGISTER: handleRegistration(ServiceType.REGISTER);
            break;
            case REGISTER_SUPER: handleRegistration(ServiceType.REGISTER_SUPER);
            default: System.out.println("Invalid communication type!");
        }
    }
    public void handleQuery() throws IOException {
        System.out.println("HANDLING QUERY");
       String input;
       //assert input == ServiceType.QUERY.getValue();
       while ((input = reader.readLine()) != null){
           for (PeerCB peer : peers) {
               List<String> resources = peer.getResource();
               for (String resource : resources) {
                   if (input.equalsIgnoreCase(resource)){
                       System.out.printf("RESOURCE: %s FOUND. SENDING RESPONSE%n", input);
                       writer.println(ServiceType.RESPONSE.getValue());
                       writer.println(peer.getIP());
                       writer.println(peer.getPort());
                       return;
                   }
               }
           }
       }
       System.out.println("COULD NOT FIND RESOURCE");
       writer.println(ServiceType.RESPONSE.getValue());
       writer.println("N-A");
    }
    public void handleResponse() throws IOException {
        System.out.println("HANDLING RESPONSE");
        List <String> resultSet = new ArrayList<String>();
        String temp;
        while ((temp = reader.readLine()) != null){
            resultSet.add(temp);
        }
        assert resultSet.size() == 2 : "Result set unexpectedly large at size: " + resultSet.size();
        for (String str : resultSet) {
            writer.println();
        }


    }
    public void handleRequest() throws IOException {
        InetAddress result = null;
        String input;
        while ((input = reader.readLine()) != null) {
            System.out.printf("HANDLING REQUEST FOR: %s%n", input);
            for (PeerCB peer : peers) {
                List<String> resourceList = peer.getResource();
                for (String str : resourceList) {
                    if (str.equalsIgnoreCase(input)){
                        writer.println(peer.getIP());
                        writer.println(peer.getPort());
                        return;
                    }
                }
            }
            List<String> remoteResult = queryNeighbors(input);
            if (remoteResult != null) {
                writer.println(remoteResult.get(0));
                writer.println(remoteResult.get(1));
                writer.println(remoteResult.get(2));
                writer.println(ServiceType.END.getValue());
            }
        }
    }

    public void handleRegistration(ServiceType type) throws IOException {
        System.out.println("HANDLING REGISTRATION");
        if (ServiceType.REGISTER == type){
            String str;
            List<String> inputList = new ArrayList<>();
            while ((str = reader.readLine()) != null) {
                //System.out.println("Resource: " + resource);
                inputList.add(str);
            }
            //String ip = inputList.get(0);
            String resource = inputList.get(0);
            String port = inputList.get(1);
            PeerCB newPeer = new PeerCB(sock, resource, port);
            peers.add(newPeer);
        }
        else if(ServiceType.REGISTER_SUPER == type){
            String str;
            List<String > inputList = new ArrayList<>();
            while ((str = reader.readLine()) != null){
                inputList.add(str);
            }
            String ip = inputList.get(0);
            String port = inputList.get(1);
            SuperPeerCB superPeerCB = new SuperPeerCB(ip, port);
            neighbors.add(superPeerCB);
        }
    }

    private List<String> queryNeighbors(String input) throws IOException {
        System.out.println("SENDING QUERY TO NEIGHBORING SUPER PEERS");
        for (SuperPeerCB neighbor : neighbors) {
            Socket querySocket = new Socket(neighbor.getIP(), neighbor.getPort());
            PrintWriter out = new PrintWriter(querySocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(querySocket.getInputStream()));
            out.println(ServiceType.QUERY.getValue());
            out.println(input);

            String temp;
            List<String> responseList = new ArrayList<>();
            while((temp = in.readLine()) != null){
                responseList.add(temp);
            }
            System.out.println("RESPONSE FROM QUERY RECEIVED");
            if (responseList.size() > 2 && Utility.checkIPv4(responseList.get(1))){
                return responseList;
            }
            else{
                System.out.println("EXPECTED IPv4 ADDRESS BUT FOUND: " + responseList.get(0));
            }
        }
        return null;
    }


}
