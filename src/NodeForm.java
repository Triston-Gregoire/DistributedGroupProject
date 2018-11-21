import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NodeForm {
    private JPanel panel1;
    private JButton registerButton1;
    private JButton requestFileButton;

    public NodeForm() {
        registerButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String addressPort =  (String)JOptionPane.showInputDialog("Input Super Peer IP address and local port number separated by a dash i.e 192.168.1.1-4000");
                String[] address = addressPort.split("-");
                String resource = (String)JOptionPane.showInputDialog("Input file name and extension");
                List<String> resourceList = new ArrayList<>();
                resourceList.add(resource);
                try {
                    Server server = new Server(address[0],Integer.parseInt(address[1]), 6002, resourceList);
                    server.register();
                    server.start();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        requestFileButton.addActionListener(e -> {
            String input = (String)JOptionPane.showInputDialog("Input Super Peer IP address and local port number separated by a dash i.e 192.168.1.1-4000");
            String[] address = input.split("-");
            try {
                Client client = new Client(address[0], Integer.parseInt(address[1]));
                String resource = (String)JOptionPane.showInputDialog("Input file and extension to download");
                client.request(resource);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("NodeForm");
        frame.setContentPane(new NodeForm().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
