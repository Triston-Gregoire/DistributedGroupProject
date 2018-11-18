import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerThread extends Thread {
    private Socket sock;
    private List<String> resources;
    public ServerThread(Socket sock, List<String> resourceList){
        setSock(sock);
        setResources(resourceList);
    }
    @Override
    public void run() {
        try {
            String str;
            BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            List<String> inputList = new ArrayList<>();
            while ((str = reader.readLine()) != null){
                inputList.add(str);
                if(str.equals(ServiceType.END.getValue()))
                    break;
            }
            for (String s : inputList) {
                System.out.println(s);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("C:\\Users\\Triston C Gregoire\\Desktop\\");
            sb.append(inputList.get(1));
            String fileName = sb.toString();

            InputStream fileInputStream = new FileInputStream(new File(fileName));
            byte[] buffer = new byte[1024];

            OutputStream outputStream = sock.getOutputStream();

            int count = fileInputStream.read(buffer);
            while (count != -1){
                outputStream.write(buffer);
                count = fileInputStream.read(buffer);
            }
            sock.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSock() {
        return sock;
    }

    public void setSock(Socket sock) {
        this.sock = sock;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }
}
