import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MovieDetails {
    private JLabel movieTitle;
    private JLabel movieDesc;
    public JPanel panel;
    private JButton backButton;
    private JComboBox dateBox;
    private JComboBox comboBox2;
    private JComboBox comboBox3;

    public String movieCode;

    public Header head;

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
             sql = "Select distinct FORMAT(show_date, 'Short Date') from show_time where movie_id='"+movieCode+"'";
             rs = st.executeQuery(sql);
            while(rs.next()){
                dateBox.addItem(rs.getString(1));

//                System.out.println("\n"+rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5));
            }
        } catch (Exception e){
            System. out.println(e.getMessage());
        }

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                head.seeMovieMenu(head);
            }
        });
    }

}
