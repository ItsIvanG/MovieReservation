import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class ConfirmPurchase {
    public JPanel panel;
    private JLabel seatsOrderingLabel;
    private JLabel showIDLabel;
    private JButton backButton;
    private JLabel movieLabel;
    private JLabel dateLabel;
    private JLabel cinemaHallLabel;
    private JLabel timeLabel;
    private JButton confirmButton;
    private JLabel priceLabel;
    private JLabel ticketsLabel;
    private JRadioButton overTheCounterRadioButton;
    private JRadioButton creditCardRadioButton;
    private JRadioButton GCashRadioButton;
    private String cinemaHallCode;
    private double cinemaRate;
    private double ticketsTotalPrice;
    private double moviePrice;
    public ConfirmPurchase(List<String> i, int ShowID, String m, Header h){

        /////get movie details
        try{
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://src\\MovieReserv.accdb");
            PreparedStatement pst = conn.prepareStatement("Select * from MOVIE where MOVIE_ID=?");

            pst.setString(1, m);

            ResultSet rs = pst.executeQuery();

            while(rs.next()){
                movieLabel.setText(rs.getString(2));
                moviePrice = rs.getDouble("movie_price");

            }

            ////// get show details
            pst = conn.prepareStatement("select * from show_time where show_id=?");
            pst.setString(1, Integer.toString(ShowID));
            rs = pst.executeQuery();

            PreparedStatement pstCinema;
            ResultSet rsCinema;

            while(rs.next()){
                dateLabel.setText(dateTimeConvert.toShortDate(rs.getDate(4)));
                timeLabel.setText(dateTimeConvert.toShortTime(rs.getTime(3)));

                //cinema description
                cinemaHallCode=rs.getString(5);
                pstCinema=conn.prepareStatement("Select * from cinema_room where cinema_hall=?");
                pstCinema.setString(1,cinemaHallCode);
                rsCinema = pstCinema.executeQuery();
                while (rsCinema.next()) {
                    cinemaHallLabel.setText(rsCinema.getString("cinema_description"));
                    cinemaRate=rsCinema.getDouble("rateAdd");
                    System.out.println("CINEMA DESC:____"+rsCinema.getString(1)+"\nRATE: "+cinemaRate);
                }
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        for (String x:
             i) {
            seatsOrderingLabel.setText(seatsOrderingLabel.getText()+x+", ");
        }

        showIDLabel.setText(String.valueOf(ShowID));


        ////calculate price
        ticketsTotalPrice = i.size()*(moviePrice*cinemaRate);
        priceLabel.setText(priceLabel.getText()+ticketsTotalPrice);
        ticketsLabel.setText(ticketsLabel.getText()+i.size()+"x");

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                h.seeMovieDetails(m,h);
            }
        });
    }
}
