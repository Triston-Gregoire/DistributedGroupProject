/*
 * @Author Triston Gregoire
 */
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Utility {
    public static final int THIRTY_SECONDS = 30000;
    public static final int SIGFIGS = 2;
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
    public static List<FileCB> pullResource(){
        File[] files = new File("./res").listFiles();
        List<FileCB> result = new ArrayList<>();
        for (File file : files) {
            if (file.isFile()){
                FileCB newFile = new FileCB(file.getName(), file.length());
                result.add(newFile);
            }
        }
        return result;
    }
    public static float getPercentage(int n, int total){
        float propoartion = ((float) n) / ((float) total);
        return propoartion * 100;
    }
    public static void initializeOutput() throws FileNotFoundException {
        File results = new File("results.csv");
        FileOutputStream poopStream = new FileOutputStream(results);
        StringBuilder sb = new StringBuilder();
        //sb.append(",");
        sb.append("Transfer ID");
        sb.append(",");
        sb.append("Transfer Time (seconds)");
        sb.append(",");
        sb.append("Transfer Rate (MB/s)");
        sb.append(",");
        sb.append("File Size (MB)");
        sb.append("\n");
        PrintWriter pw = new PrintWriter(results);
        pw.write(sb.toString());
        pw.close();
    }

    public static void appendToFile(Object[] metrics) throws IOException {
        File file = new File("results.csv");
        BufferedReader bufferedReader = new BufferedReader((new FileReader(file)));
        String input;
        int count = 0;
        while ((input = bufferedReader.readLine()) != null){
            count++;
        }
        FileWriter writer = new FileWriter(file, true);
        PrintWriter pw = new PrintWriter(writer);
        StringBuilder sb = new StringBuilder();
        sb.append(count);
        sb.append(",");
        sb.append(metrics[0]);
        sb.append(",");
        sb.append(metrics[1]);
        sb.append(",");
        sb.append(metrics[2]);
        sb.append("\n");
        pw.write(sb.toString());
        pw.close();
    }
    public static double round(double num, int digits){
        BigDecimal bigDecimal = new BigDecimal(num);
        bigDecimal = bigDecimal.setScale(digits, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
    public static String space(){return " ";}
    public static String newLine(){return "\n";};
}
