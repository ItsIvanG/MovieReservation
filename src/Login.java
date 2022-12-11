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
                tryLogin(h, emailField.getText(),new String(passwordField.getPassword()));
            }
        });
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tryLogin(h, emailField.getText(),new String(passwordField.getPassword()));
            }
        });
    }
    public void tryLogin(Header h,String email, String pass){
        System.out.println("Attempting login "+email+" | "+pass);
        try{
//            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection(connectionClass.connectionString);
            PreparedStatement pst = conn.prepareStatement("Select * from account where account_email=? and password=?");
            pst.setString(1, email);
            pst.setString(2, pass);

            ResultSet rs = pst.executeQuery();
            boolean found=false;
            while(rs.next()){
//                JOptionPane.showMessageDialog(null, "Logged in as: "+rs.getString(2));
                h.customerEmail=rs.getString(1);
                h.customerName=rs.getString(2);
                h.customerContactNo=rs.getString(3);
                h.customerNameLabel.setText(h.customerName);
                h.isAdmin=rs.getBoolean("admin");

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


