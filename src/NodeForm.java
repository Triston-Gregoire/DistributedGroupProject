/*
 * @Author Triston Gregoire
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class NodeForm {
    private JPanel panel1;
    private JButton registerButton1;
    private JButton requestFileButton;
    private JButton updateRegistrationButton;
    private String suggestedIP;
    private Server server;

    public NodeForm() {
        registerButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String addressPort = (String) JOptionPane.showInputDialog("Input Super Peer IP address and local port number separated by a dash i.e 192.168.1.1-4000");
                suggestedIP = addressPort;
                String[] address = addressPort.split("-");
                List<FileCB> options = Utility.pullResource();
//                String resource = (String)JOptionPane.showInputDialog(NodeForm.this,"Choose file to seed", "Select a file",JOptionPane.PLAIN_MESSAGE, null, s);
//                List<String> resourceList = new ArrayList<>();
//                resourceList.add(resource);
                try {
                    server = new Server(address[0], Integer.parseInt(address[1]), 6002, options);
                    server.register(options);
                    server.start();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        requestFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = (String) JOptionPane.showInputDialog(null, "Input Super Peer IP address and local port number separated by a dash i.e 192.168.1.1-4000", suggestedIP);
                String[] address = input.split("-");
                try {
                    Client client = new Client(address[0], Integer.parseInt(address[1]));
                    String resource = (String) JOptionPane.showInputDialog("Input file and extension to download");
                    //testing loop
//                    for (int i = 0; i < 10; i++) {
//                        client.setSock(new Socket(address[0], Integer.parseInt(address[1])));
//                        client.request(resource);
//                    }
                    client.request(resource);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });
        updateRegistrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (server.getSock().isClosed()) {
                    try {
                        server.setSock(new Socket(server.getIP(), server.getSuperPeerPort()));
                        server.register(Utility.pullResource());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    try {
                        server.register(Utility.pullResource());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

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
