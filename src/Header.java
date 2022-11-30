import javax.swing.*;
import java.awt.*;

public class Header {
    private JPanel panel;
    private JPanel contentPanel;
    private JLabel customerName;
    private JButton signInButton;
    private JButton registerButton;

    public String movieCode;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Header h = new Header();
        frame.setSize(1000,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Now Showing");
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setContentPane(h.panel);
        h.contentPanel.setLayout(new GridLayout(0,1));

        h.contentPanel.add(new MovieMenu(h).panel);

    }

    public void seeMovieDetails(String m, Header h){
        h.contentPanel.remove(0);
        h.contentPanel.add(new MovieDetails(movieCode,h).panel);
        h.contentPanel.revalidate();
        h.contentPanel.repaint();
    }

    public void seeMovieMenu(Header h){
        h.contentPanel.remove(0);
        h.contentPanel.add(new MovieMenu(h).panel);
        h.contentPanel.revalidate();
        h.contentPanel.repaint();
    }

}

