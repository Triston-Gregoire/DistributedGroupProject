import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

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
    public static String space(){return " ";}
    public static String newLine(){return "\n";};
}
