import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton logInButton;
    public JPanel panel;
    private JButton backButton;

    public Login(Header h){

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                h.seeMovieMenu(h);
            }
        });

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tryLogin(h);
            }
        });
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tryLogin(h);
            }
        });
    }
    public void tryLogin(Header h){
        System.out.println("Attempting login "+emailField.getText()+" | "+new String(passwordField.getPassword()));
        try{
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://src\\MovieReserv.accdb");
            PreparedStatement pst = conn.prepareStatement("Select * from customer where customer_email=? and password=?");
            pst.setString(1, emailField.getText());
            pst.setString(2, new String(passwordField.getPassword()));

            ResultSet rs = pst.executeQuery();
            boolean found=false;
            while(rs.next()){
                JOptionPane.showMessageDialog(null, "Logged in as: "+rs.getString(2));
                h.customerEmail=rs.getString(1);
                h.customerName=rs.getString(2);
                h.customerContactNo=rs.getString(3);
                h.customerNameLabel.setText(h.customerName);
                h.seeMovieMenu(h);
                h.checkLoginStatus(h);
                found=true;
            }
            if(!found)
                JOptionPane.showMessageDialog(null, "Credentials not found.");
        }
        catch (Exception x){
            System. out.println(x.getMessage());
        }
    }
}


