import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private Socket sock;
    private byte[] buffer = new byte[8192];
    public Client(String ip, int port) throws IOException {
        System.out.println(ip);
        System.out.println(port);
        this.sock = new Socket(ip, port);

    }
    public void request(String resource) throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        OutputStream outputStream = sock.getOutputStream();
        PrintWriter out = new PrintWriter(outputStream, true);
        out.println(ServiceType.REQUEST.getValue());
        out.println(resource);

        String response;
        List<String> responseList= new ArrayList<String>();
        while ((response = reader.readLine()) != null){
            responseList.add(response);
            if (response.equals(ServiceType.END.getValue())){
                break;
            }
        }

        for (String str : responseList) {
            System.out.println(str);
        }
        String ip = responseList.get(1);
        int port = Integer.parseInt(responseList.get(2));
        transfer(ip, port, resource);



        sock.close();
    }
    public void transfer(String ip, int port, String resource) throws IOException {
        Socket peerSocket = new Socket(ip, port);
        InputStream inputStream = peerSocket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(peerSocket.getInputStream()));
        PrintWriter writer = new PrintWriter(peerSocket.getOutputStream(),true);

        writer.println(ServiceType.RESOURCE.getValue());
        writer.println(resource);
        writer.println(ServiceType.END.getValue());
        String input;

        File video = new File("test_pass.mp4");
        FileOutputStream fileOutputStream = new FileOutputStream(video);
        int count;
        while ((count = inputStream.read(buffer)) > 0){
            fileOutputStream.write(buffer, 0, count);
        }
        fileOutputStream.close();
        sock.close();
    }

    public void register() throws IOException {
        OutputStream outputStream = sock.getOutputStream();
        PrintWriter out = new PrintWriter(outputStream, true);
        out.println(ServiceType.REGISTER.getValue());
        //out.println(sock.getLocalAddress().getHostAddress());
        out.println("test.mp4");
        out.println(6002);

        sock.close();
    }
}
