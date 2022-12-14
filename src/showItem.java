import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class showItem {
    public JPanel panel;
    private JButton EDITButton;
    private JLabel timeLabel;
    private JLabel movieLabel;
    private JButton DELbutton;

    showItem(Time time,String movie, ShowManage sm, int showID){

        timeLabel.setText(dateTimeConvert.toShortTime(time));
        System.out.println(showID+": "+ time);

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
                new editShowDialog(sm, showID, false).setVisible(true);
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
                    if(x.getMessage().startsWith("UCAExc:::5.0.1 integrity constraint violation: foreign key no action;")){
                        JOptionPane.showMessageDialog(null, "Show time already has tickets registered and cannot be deleted.","Error",JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });
    }
}
