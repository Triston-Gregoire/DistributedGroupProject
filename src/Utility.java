import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Utility {
    public static final int THIRTY_SECONDS = 30000;
    public static boolean checkIPv4(final String ip) {
        boolean isIPv4;
        try {
            final InetAddress inet = InetAddress.getByName(ip);
            isIPv4 = inet.getHostAddress().equals(ip)
                    && inet instanceof Inet4Address;
        }
        catch (final UnknownHostException e) {
            isIPv4 = false;
        }
        return isIPv4;
    }
    public static String pullResource(int num){
        File[] files = new File("./res").listFiles();
        StringBuilder sb = new StringBuilder();

        for (File file : files) {
            if (file.isFile()){
                sb.append(file.getName());
                sb.append(ServiceType.FILE_SEPARATOR.getValue());
            }
        }
        String result = sb.toString();
        System.out.println(result);
        return result;
    }
    public static List<String> pullResource(){
        File[] files = new File("./res").listFiles();
        List<String> result = new ArrayList<>();
        for (File file : files) {
            if (file.isFile()){
                result.add(file.getName());
            }
        }
        return result;
    }
    public static String space(){return " ";}
    public static String newLine(){return "\n";};
}
