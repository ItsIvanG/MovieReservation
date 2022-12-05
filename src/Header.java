import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Header {
    private JPanel panel;
    private JPanel contentPanel;
    public JLabel customerNameLabel;
    private JButton signInButton;
    private JButton registerButton;
    private JButton signOutButton;
    private JButton myTicketsButton;

    public String movieCode;
    public String customerEmail="bianca.santos.a@bulsu.edu.ph";
    public String customerName;
    public String customerContactNo;

    public List<String> purchasingSeats=new ArrayList<String>();
    public int selectedShowID;
    public Header() {

        Header h = this;
        customerNameLabel.setText(customerEmail);
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
                checkLoginStatus(h);
            }
        });
        myTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seeTickets(h);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Header h = new Header();
        h.checkLoginStatus(h);
        frame.setSize(1000,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Now Showing");
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setContentPane(h.panel);
        h.contentPanel.setLayout(new GridLayout(0,1));

        h.contentPanel.add(new MovieMenuScroll(h).scrollPane);
        h.contentPanel.revalidate();
        h.contentPanel.repaint();

    }

    public void seeMovieDetails(String m, Header h){
        h.contentPanel.remove(0);
        h.contentPanel.add(new MovieDetails(movieCode,h).panel);
        h.contentPanel.revalidate();
        h.contentPanel.repaint();
    }

    public void seeMovieMenu(Header h){
        h.contentPanel.remove(0);
        h.contentPanel.add(new MovieMenuScroll(h).scrollPane);
        h.contentPanel.revalidate();
        h.contentPanel.repaint();
    }

    public void checkLoginStatus(Header h){
        if(customerEmail.equals("")){
            signOutButton.setVisible(false);
            myTicketsButton.setVisible(false);
            signInButton.setVisible(true);
            registerButton.setVisible(true);
            seeMovieMenu(h);
        } else{
            signOutButton.setVisible(true);
            myTicketsButton.setVisible(true);
            signInButton.setVisible(false);
            registerButton.setVisible(false);
        }
    }

    public void confirmPurchaseScreen(Header h){
        h.contentPanel.remove(0);
        h.contentPanel.add(new ConfirmPurchase(purchasingSeats, selectedShowID,movieCode, h).panel);
        h.contentPanel.revalidate();
        h.contentPanel.repaint();
    }

    public void seeTickets(Header h){
        h.contentPanel.remove(0);
        h.contentPanel.add(new myTickets(h, customerEmail).panel);
        h.contentPanel.revalidate();
        h.contentPanel.repaint();
    }
}

