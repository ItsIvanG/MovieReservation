import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class MovieDetails {
    private JLabel movieTitle;
    private JLabel movieDesc;
    public JPanel panel;
    private JButton backButton;
    private JComboBox dateBox;
    private JComboBox timeBox;
    private JComboBox hallBox;
    private JButton confirmButton;
    private JPanel seatsPanel;
    private JLabel selectedSeatsLabel;
    private JLabel showIDLabel;
    private JLabel ticketPriceLabel;
    private JLabel timeLabel;

    public String movieCode;

    public Header head;

    private MovieDetails m = this;
    private int seatsPerRow;

    public List<String> dateList=new ArrayList<String>();
    public List<String> timeList=new ArrayList<String>();
    public List<String> hallList=new ArrayList<String>();
    private String[] rowCodes = {"A","B","C","D","E","F","G","H","I","J"};
    public List<String> selectedSeats=new ArrayList<String>();
    private int noOfSeats;
    public GridLayout seatsLayout = new GridLayout(0,10);
    public int ShowID;
    private double rateAdd;
    public MovieDetails(String a, Header h){
        head=h;
        movieCode=a;


        try{
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://src\\MovieReserv.accdb");
//            Statement st = conn.createStatement();
            // GET MOVIE DETAILS
            PreparedStatement sql = conn.prepareStatement("Select * from movie where movie_id=?");
            sql.setString(1,movieCode);
//            String sql = "Select * from movie";
            ResultSet rs = sql.executeQuery();
            while(rs.next()){

                movieTitle.setText(rs.getString(2));
                movieDesc.setText("<html>"+rs.getString(3)+"</html>");
                System.out.println("\n"+rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4));
            }
            // GET SHOW DATES
//             sql = "Select distinct FORMAT(show_date, 'Short Date') from show_time where movie_id='"+movieCode+"'";
            sql = conn.prepareStatement("Select distinct show_date from show_time where movie_id=?");
            sql.setString(1,movieCode);
            rs = sql.executeQuery();
            while(rs.next()){
                dateList.add(rs.getString(1));

//                System.out.println("\n"+rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5));
            }
            sql = conn.prepareStatement("Select distinct FORMAT(show_date, 'Short Date') from show_time where movie_id=?");
            sql.setString(1,movieCode);
            rs = sql.executeQuery();
            while(rs.next()){
                dateBox.addItem(rs.getString(1));
//                System.out.println("\n"+rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5));
            }
            System.out.println(dateList);
        } catch (Exception e){
            System. out.println(e.getMessage());
        }

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                head.seeMovieMenu(head);
            }
        });
        dateBox.addActionListener(new ActionListener() { ////// GET SHOW TIMES
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("DATE: "+dateList.get(dateBox.getSelectedIndex()));
                timeBox.removeAllItems();
                timeList.clear();
                try{
                    Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
                    Connection conn = DriverManager.getConnection("jdbc:ucanaccess://src\\MovieReserv.accdb");
//                    Statement st = conn.createStatement();
                    PreparedStatement sql = conn.prepareStatement("Select distinct show_time from show_time where movie_id=? and show_date=?");
                    sql.setString(1,movieCode);
                    sql.setString(2,dateList.get(dateBox.getSelectedIndex()));
                    ResultSet rs = sql.executeQuery();
                    while(rs.next()){

                        timeList.add(rs.getString(1));
                        timeBox.addItem(convertToShortTime(rs.getString(1)));

                    }
//                    timeLabel.setText("");  --------- TIME PARSE DEBUG LABEL
//                    for (String x:
//                         timeList) {
//                        timeLabel.setText(timeLabel.getText()+" "+x);
//                    }
//                    sql = conn.prepareStatement("Select distinct FORMAT(show_time, 'Medium Time') from show_time where movie_id=? and show_date=?");
//                    sql.setString(1,movieCode);
//                    sql.setString(2,dateList.get(dateBox.getSelectedIndex()));
////                    sql = "Select show_time from show_time where movie_id='"+movieCode+"' and show_date='"+dateList.get(dateBox.getSelectedIndex())+"'";
//                    rs = sql.executeQuery();
//                    while(rs.next()){
//                        timeBox.addItem(rs.getString(1));
////                System.out.println("\n"+rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5));
//                    }
                }catch (Exception x){
                    System. out.println(x.getMessage());
                }
                System.out.println(timeList);


            }
        });
        timeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { ////GET CINEMAS
                System.out.println("GETTING CINEMAS");
                hallBox.removeAllItems();
                hallList.clear();
                try{
                    Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
                    Connection conn = DriverManager.getConnection("jdbc:ucanaccess://src\\MovieReserv.accdb");
//                    Statement st = conn.createStatement();
                    PreparedStatement pst = conn.prepareStatement("Select cinema_hall from show_time where movie_id=? and show_date=? and show_time=?");
                    pst.setString(1,movieCode);
                    pst.setString(2,dateList.get(dateBox.getSelectedIndex()) );
                    pst.setString(3, timeList.get(timeBox.getSelectedIndex()));
//                    Statement stCinemaHall = conn.createStatement();
//                    String sql = "Select cinema_hall from show_time where movie_id='"+movieCode+"' and show_date='"+dateList.get(dateBox.getSelectedIndex())+"' and show_time='"+timeList.get(timeBox.getSelectedIndex())+"'";
                    ResultSet rs = pst.executeQuery();
                    ResultSet rsCinemaHall;
                    while(rs.next()){
                        hallList.add(rs.getString(1));
                        //                System.out.println("\n"+rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5));
                        /////////////list to box w description
                        System.out.println("LAST CINEMA HALL ADDED: "+hallList.get(hallList.size()-1));
//                        String getCinemaDescCommand = "Select cinema_description from cinema_room where cinema_hall='"+hallList.get(hallList.size()-1)+"'";
                        PreparedStatement pstCinemaHall = conn.prepareStatement("Select cinema_description from cinema_room where cinema_hall=?");
                        pstCinemaHall.setString(1, hallList.get(hallList.size()-1));
                        rsCinemaHall = pstCinemaHall.executeQuery();
                        while(rsCinemaHall.next()){
                            hallBox.addItem(rsCinemaHall.getString(1));
                            //                System.out.println("\n"+rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5));
                        }
                    }
                    System.out.println(hallList);
                }catch (Exception x){
                    System. out.println(x.getMessage());
                }
//                try{
//                    Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
//                    Connection conn = DriverManager.getConnection("jdbc:ucanaccess://src\\MovieReserv.accdb");
//                    Statement st = conn.createStatement();
//
//                    String sql = "Select cinema_hall from show_time where movie_id='"+movieCode+"' and show_date='"+dateList.get(dateBox.getSelectedIndex())+"' and show_time='"+timeList.get(timeBox.getSelectedIndex())+"'";
//                    ResultSet rs = st.executeQuery(sql);
//
//                    while(rs.next()){
//                        hallList.add(rs.getString(1));
//                        //                System.out.println("\n"+rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5));
//                        /////////////list to box w description
//                        System.out.println("LAST CINEMA HALL ADDED: "+hallList.get(hallList.size()-1));
//                         sql = "Select cinema_description from cinema_room where cinema_hall='"+hallList.get(hallList.size()-1)+"'";
//
//                        rs = st.executeQuery(sql);
//                        while(rs.next()){
//                            hallBox.addItem(rs.getString(1));
//                            //                System.out.println("\n"+rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5));
//                        }
//                    }
//                    System.out.println(hallList);
//
//
//
//                }catch (Exception x){
//                    System. out.println(x.getMessage());
//                }
            }
        });
        hallBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //////// GENERATE SEATS
                selectedSeats.clear();

                try
                {
                    Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
                    Connection conn = DriverManager.getConnection("jdbc:ucanaccess://src\\MovieReserv.accdb");
//                    Statement st = conn.createStatement();
                    ///GET CINEMA RATE
                    PreparedStatement sql = conn.prepareStatement("Select rateAdd from cinema_room where cinema_hall=?");
                    sql.setString(1,hallList.get(hallBox.getSelectedIndex()));
                    ResultSet rs = sql.executeQuery();
                    while(rs.next()){
                        rateAdd = rs.getDouble(1);
                    }

                    ticketPriceLabel.setText("CINEMA RATE: "+rateAdd);

                    sql = conn.prepareStatement("Select * from cinema_room where cinema_hall=?");
                    sql.setString(1,hallList.get(hallBox.getSelectedIndex()));
                    //"Select * from cinema_room where cinema_hall='"+hallList.get(hallBox.getSelectedIndex())+"'"
                    rs = sql.executeQuery();
                    while(rs.next()){

                        noOfSeats=rs.getInt(3);
                        seatsPerRow=rs.getInt(5);
                    }
                    System.out.println("SELECTED CINEMA: "+hallList.get(hallBox.getSelectedIndex())+" WHERE NO. OF SEATS: "+noOfSeats+" AND SEATSPERROW: "+seatsPerRow);
                    seatsPanel.removeAll();
                    seatsLayout = new GridLayout(0,seatsPerRow);
                    seatsPanel.setLayout(seatsLayout);
                    seatsPanel.setBorder(BorderFactory.createEmptyBorder(50,100,50,100));

                    ///SET SHOWID
                    sql = conn.prepareStatement("Select show_id from show_time where cinema_hall=? and movie_id=? and show_date=? and show_time=?");
                    sql.setString(1,hallList.get(hallBox.getSelectedIndex()));
                    sql.setString(2,movieCode);
                    sql.setString(3,dateList.get(dateBox.getSelectedIndex()));
                    sql.setString(4,timeList.get(timeBox.getSelectedIndex()));
//                     sql = "Select show_id from show_time where cinema_hall='"+hallList.get(hallBox.getSelectedIndex())+"' and movie_id='"+movieCode+"' and show_date='"+dateList.get(dateBox.getSelectedIndex())+"' and show_time='"+timeList.get(timeBox.getSelectedIndex())+"'";
                    rs = sql.executeQuery();
                    while(rs.next()){

                        ShowID=rs.getInt(1);

                    }
                    System.out.println("SHOW ID: "+ShowID);
                    showIDLabel.setText("ShowID: "+Integer.toString(ShowID));


                }catch (Exception x){
                    System. out.println(x.getMessage());
                }

                /////ADD SEATS TO PANEL

                int currentSeat=0;
                int currentRow=0;
                int currentSeatReset=0;
                while(currentSeat<noOfSeats){
                    String seatIDdebug = rowCodes[currentRow]+(currentSeatReset+1);
                    seatsPanel.add(new SeatButton(seatIDdebug,m, ShowID).panel);
                    System.out.println(seatIDdebug+" added");
                    currentSeat++;
                    currentSeatReset++;

                    if(currentSeat%seatsPerRow==0&&currentSeat!=0){
                        currentRow++;
                        currentSeatReset=0;
                    }
                }
//                for (int i = 0; i < noOfSeats/seatsPerRow; i++) {
//                    for (int x = 0; x < seatsPerRow; x++) {
//
//                        seatsPanel.add(new SeatButton(seatIDdebug,m, ShowID).panel);
//                        System.out.println(seatIDdebug+" added");
//                    }
//                }

                seatsPanel.revalidate();
                seatsPanel.repaint();
            }
        });

    }
    public void addSeatToCart(String seatID){
        System.out.println("ADDED TO CART, SEATiD: "+seatID);
        selectedSeats.add(seatID);
        selectedSeatsLabel.setText("Selected seats: ");
        for (String x:
                selectedSeats) {
            selectedSeatsLabel.setText(selectedSeatsLabel.getText()+x+", ");
        }

    }
    public void removeSeatFromCart(String seatID){
        System.out.println("REMOVED FROM CART, SEATiD: "+seatID);
        selectedSeats.remove(seatID);
        selectedSeatsLabel.setText("Selected seats: ");
        for (String x:
                selectedSeats) {
            selectedSeatsLabel.setText(selectedSeatsLabel.getText()+x+", ");
        }

    }
    public String convertToShortTime(String inputStr){
        String res="";
//        System.out.println("CONVERTING "+inputStr.substring(11,13));
        System.out.println(Integer.parseInt(inputStr.substring(11,13)));
        if(Integer.parseInt(inputStr.substring(11,13))>12){
             int hour = Integer.parseInt(inputStr.substring(11,13))-12;
             res=hour+inputStr.substring(13,16)+" PM";
        }else{
             res=inputStr.substring(11,16)+" AM";
        }
//        res=inputStr.substring(10,16)+" AM";
        return res;
    }
}
