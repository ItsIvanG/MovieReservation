import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SeatButton {
    public int seatStatus=0;
    //0 = available, 1 = added to cart, 2 = taken :<
    public JPanel panel;
    private JButton seatButton;
    SeatButton(String SEATID, MovieDetails m, int SHOWID){
        seatButton.setText(SEATID);

        try{ //GET TAKEN SEATS
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://src\\MovieReserv.accdb");
            Statement st = conn.createStatement();

            String sql = "Select * from TICKET where show_id='"+SHOWID+"'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                if(SEATID.equalsIgnoreCase(rs.getString(2))){
                    seatStatus=2;
                }
            }
        }catch (Exception e){
            System. out.println(e.getMessage());
        }

        if (seatStatus==2){
            seatButton.setBackground(Color.RED);
        }
        seatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(seatStatus==0){
                    m.addSeatToCart(SEATID);
                    seatButton.setBackground(Color.GREEN);
                    seatStatus=1;
                } else if (seatStatus==1) {
                    m.removeSeatFromCart(SEATID);
                    seatButton.setBackground(Color.WHITE);
                    seatStatus=0;
                }
            }
        });
    }
}
