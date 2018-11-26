/*
 * @Author Triston Gregoire
 */
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerThread extends Thread {
    private Socket sock;
    private List<FileCB> resources;
    ServerThread(Socket sock, List<FileCB> resourceList){
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
            //sb.append("C:\\Users\\Triston C Gregoire\\Desktop\\");
            sb.append("./res/");
            sb.append(inputList.get(1));
            String fileName = sb.toString();

            InputStream fileInputStream = new FileInputStream(new File(fileName));
            byte[] buffer = new byte[8192];

            OutputStream outputStream = sock.getOutputStream();
            int count;
            while ((count = fileInputStream.read(buffer)) > 0){
                outputStream.write(buffer,0 , count);
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

    public List<FileCB> getResources() {
        return resources;
    }

    public void setResources(List<FileCB> resources) {
        this.resources = resources;
    }
}
