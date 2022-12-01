import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Header {
    private JPanel panel;
    private JPanel contentPanel;
    public JLabel customerNameLabel;
    private JButton signInButton;
    private JButton registerButton;
    private JButton signOutButton;

    public String movieCode;
    public String customerEmail="";
    public String customerName;

    public Header() {
        Header h = this;
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               contentPanel.remove(0);
               contentPanel.add(new Register(h).panel);
               contentPanel.revalidate();
               contentPanel.repaint();
            }
        });
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                h.contentPanel.remove(0);
                h.contentPanel.add(new Login(h).panel);
                h.contentPanel.revalidate();
                h.contentPanel.repaint();
            }
        });
        signOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customerEmail="";
                customerName="";
                customerNameLabel.setText("");
                checkLoginStatus();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Header h = new Header();
        h.checkLoginStatus();
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

    public void checkLoginStatus(){
        if(customerEmail.equals("")){
            signOutButton.setVisible(false);
            signInButton.setVisible(true);
            registerButton.setVisible(true);
        } else{
            signOutButton.setVisible(true);
            signInButton.setVisible(false);
            registerButton.setVisible(false);
        }
    }



}

