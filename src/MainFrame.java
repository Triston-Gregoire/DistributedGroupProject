import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame {
    private JPanel panel1;
    private JButton startSuperPeerButton;
    private JButton shutdownButton;
    private JTextArea superPeerList;
    private JTextArea subNodeList;

    public int port = 6000;
    public static int peerCount = 0;

    public MainFrame() {
        startSuperPeerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SuperPeer superPeer = new SuperPeer(port, MainFrame.this);
                String str = (String)JOptionPane.showInputDialog("Input list of IPs for other super peers seperated by \'/\' ");
                String[] neighbors = str.split("/");
                int count = 0;
                for (String neighbor : neighbors) {
                    StringBuilder sb = new StringBuilder("SuperPeer ");
                    sb.append(count);
                    sb.append(Utility.space());
                    sb.append(" IP - ").append(neighbor);
                    sb.append(" PORT - ").append(port);
                    sb.append(Utility.newLine());
                    SuperPeerCB sp = new SuperPeerCB(neighbor, port);
                    superPeer.superPeerTable.add(sp);
                    superPeerList.append(sb.toString());
                    System.out.println(neighbor);
                    count++;
                }
                superPeer.start();
                //superPeer.listen(port);

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainFrame");
        frame.setContentPane(new MainFrame().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    public void addNode(PeerCB peer){
        StringBuilder sb = new StringBuilder("Peer ");
        sb.append(Integer.toString(peerCount));
        sb.append(Utility.space()).append(peer.getIP());
        sb.append(Utility.space()).append("-");
        sb.append(Utility.space()).append(peer.getPort());
        sb.append(Utility.newLine());
        getSubNodeList().append(sb.toString());
    }

    public JTextArea getSubNodeList() {
        return subNodeList;
    }

    public void setSubNodeList(JTextArea subNodeList) {
        this.subNodeList = subNodeList;
    }
}
