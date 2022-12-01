import javax.swing.*;
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

    public String movieCode;

    public Header head;

    public List<String> dateList=new ArrayList<String>();
    public List<String> timeList=new ArrayList<String>();
    public List<String> hallList=new ArrayList<String>();

    public String cinemahall;
    public MovieDetails(String a, Header h){
        head=h;

        movieCode=a;
        try{
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://src\\MovieReserv.accdb");
            Statement st = conn.createStatement();
            // GET MOVIE DETAILS
            String sql = "Select * from movie where movie_id='"+movieCode+"'";
//            String sql = "Select * from movie";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){

                movieTitle.setText(rs.getString(2));
                movieDesc.setText("<html>"+rs.getString(3)+"</html>");
                System.out.println("\n"+rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4));
            }
            // GET SHOW DATES
//             sql = "Select distinct FORMAT(show_date, 'Short Date') from show_time where movie_id='"+movieCode+"'";
            sql = "Select distinct show_date from show_time where movie_id='"+movieCode+"'";
            rs = st.executeQuery(sql);
            while(rs.next()){
                dateList.add(rs.getString(1));

//                System.out.println("\n"+rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5));
            }
            sql = "Select distinct FORMAT(show_date, 'Short Date') from show_time where movie_id='"+movieCode+"'";
            rs = st.executeQuery(sql);
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
                    Statement st = conn.createStatement();
                    String sql = "Select show_time from show_time where movie_id='"+movieCode+"' and show_date='"+dateList.get(dateBox.getSelectedIndex())+"'";
                    ResultSet rs = st.executeQuery(sql);
                    while(rs.next()){

                        timeList.add(rs.getString(1));

    //                System.out.println("\n"+rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5));
                    }
                    sql = "Select FORMAT(show_time, 'Medium Time') from show_time where movie_id='"+movieCode+"' and show_date='"+dateList.get(dateBox.getSelectedIndex())+"'";
//                    sql = "Select show_time from show_time where movie_id='"+movieCode+"' and show_date='"+dateList.get(dateBox.getSelectedIndex())+"'";
                    rs = st.executeQuery(sql);
                    while(rs.next()){
                        timeBox.addItem(rs.getString(1));


//                System.out.println("\n"+rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5));
                    }
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
                    Statement st = conn.createStatement();
                    Statement stCinemaHall = conn.createStatement();
                    String sql = "Select cinema_hall from show_time where movie_id='"+movieCode+"' and show_date='"+dateList.get(dateBox.getSelectedIndex())+"' and show_time='"+timeList.get(timeBox.getSelectedIndex())+"'";
                    ResultSet rs = st.executeQuery(sql);
                    ResultSet rsCinemaHall;
                    while(rs.next()){
                        hallList.add(rs.getString(1));
                        //                System.out.println("\n"+rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5));
                        /////////////list to box w description
                        System.out.println("LAST CINEMA HALL ADDED: "+hallList.get(hallList.size()-1));
                        String getCinemaDescCommand = "Select cinema_description from cinema_room where cinema_hall='"+hallList.get(hallList.size()-1)+"'";

                        rsCinemaHall = stCinemaHall.executeQuery(getCinemaDescCommand);
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
    }

}
