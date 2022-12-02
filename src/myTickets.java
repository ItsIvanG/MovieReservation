import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class myTickets {
    public JPanel panel;
    private JButton backButton;
    private JList ticketList;
    private JScrollPane ticketPanel;
    private DefaultListModel<String> ticketListModel = new DefaultListModel<>();
    public myTickets(Header h, String email){

        String movieName="";
        String cinemaHall="";
        String showDate="";
        String showTime="";
        String seatID="";

        ticketList.setModel(ticketListModel);
        try { //// GET TICKETS
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://src\\MovieReserv.accdb");
            PreparedStatement pst = conn.prepareStatement("select * from payment where customeremail=?");
            pst.setString(1,email);
            ResultSet rs = pst.executeQuery();
            System.out.println("rs success");
            while (rs.next()) {


                PreparedStatement pstTicket = conn.prepareStatement("select * from ticket where payment_id=?");
                pstTicket.setString(1, rs.getString("Payment_ID"));
                ResultSet rsTicket = pstTicket.executeQuery();



                while(rsTicket.next()){
                    ///// get seat ID
                    seatID=rsTicket.getString("seat_id");
                    System.out.println("rsTicket success");

                    PreparedStatement pstShow = conn.prepareStatement("select * from show_time where show_id=?");
                    pstShow.setString(1, rsTicket.getString("show_id"));
                    ResultSet rsShow = pstShow.executeQuery();
                    System.out.println("rsShow success");
                    while(rsShow.next()){
                        ///// set showdate/time
                        showDate=dateTimeConvert.toShortDate(rsShow.getDate("show_date"));
                        showTime=dateTimeConvert.toShortTime(rsShow.getTime("show_time"));

                        ///// get movie name
                        PreparedStatement pstMovie = conn.prepareStatement("select * from movie where movie_id=?");
                        pstMovie.setString(1, rsShow.getString("movie_id"));
                        ResultSet rsMovie = pstMovie.executeQuery();
                        System.out.println("rsMovie success");

                        while(rsMovie.next()){
                            movieName=rsMovie.getString("movie_name");
                        }
                        ///// get cinema hall
                        pstMovie=conn.prepareStatement("select cinema_description from cinema_room where cinema_hall=?");
                        pstMovie.setString(1,rsShow.getString("cinema_hall"));
                        rsMovie=pstMovie.executeQuery();
                        System.out.println("rsMovie2 success");
                        while(rsMovie.next()){
                            cinemaHall=rsMovie.getString(1);
                        }

                        ticketListModel.addElement(movieName+" | "+cinemaHall+" | "+ showDate+" : "+showTime+" | "+seatID);
                    }
                }

            }

        } catch(Exception e){
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
