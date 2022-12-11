import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class showItem {
    public JPanel panel;
    private JButton EDITButton;
    private JLabel timeLabel;
    private JLabel movieLabel;
    private JButton DELbutton;

    showItem(String time,String movie, ShowManage sm, int showID){

        timeLabel.setText(time);

        try{
            Connection conn = DriverManager.getConnection(connectionClass.connectionString);
            PreparedStatement pst = conn.prepareStatement("select * from movie where movie_id=?");
            pst.setString(1,movie);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                movieLabel.setText("["+movie+"] "+rs.getString("movie_name"));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        EDITButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new editShowDialog(sm, showID).setVisible(true);
            }
        });
        DELbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Connection conn = DriverManager.getConnection(connectionClass.connectionString);
                    PreparedStatement pst = conn.prepareStatement("delete from show_time where show_id=?");
                    pst.setInt(1,showID);
                    pst.execute();
                    sm.seeShows();
                }catch (Exception x){
                    System.out.println(x.getMessage());
                }

            }
        });
    }
}
