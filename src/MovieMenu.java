import javax.swing.*;
import java.awt.*;
import java.sql.*;
public class MovieMenu {

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        panel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        panel.setLayout(new GridLayout(0,2));

        frame.setSize(500,800);
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Now Showing");
        frame.setVisible(true);

        frame.add(new MovieItem("a","b").movieItemPanel);
        frame.add(new MovieItem("a","b").movieItemPanel);
        frame.add(new MovieItem("a","b").movieItemPanel);
        try{
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\Ivan\\Documents\\MovieReserv.accdb");
            Statement st = conn.createStatement();

            String sql = "Select * from movie";

            ResultSet rs = st.executeQuery(sql);


            while(rs.next()){
                System.out.println("\n"+rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4));

            }
        } catch (Exception e){
            System. out.println(e.getMessage());
        }

    }
}
