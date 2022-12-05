import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class myPurchases {
    private JButton backButton;
    private JList purchaseList;
    public JPanel panel;

    private DefaultListModel<String> purchaseListModel = new DefaultListModel<>();

    public myPurchases(Header h,String email){
        purchaseList.setModel(purchaseListModel);

        try{
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection(connectionClass.connectionString);
            PreparedStatement pst = conn.prepareStatement("select * from payment where customeremail=?");
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                purchaseListModel.addElement(rs.getString("payment_id")+" "+dateTimeConvert.toShortDate(rs.getDate("payment_datetime")));
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }



        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                h.seeMovieMenu(h);
            }
        });
    }

}
