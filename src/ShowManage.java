import org.jdatepicker.impl.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class ShowManage {
    public JPanel panel;
    private JButton backButton;
    private JPanel datePanelFrame;
    private JComboBox hallwayCombobox;
    private JPanel showPanel;
    private Date showDate;
    private java.util.List<String> hallList =new ArrayList<>();
    public ShowManage(Header h){

        UtilDateModel model = new UtilDateModel();

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);

        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePanelFrame.setLayout(new GridLayout(0,1));

        datePanelFrame.add(datePicker);

        try{
            Connection conn = DriverManager.getConnection(connectionClass.connectionString);
            PreparedStatement pst = conn.prepareStatement("select * from cinema_room");
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                hallwayCombobox.addItem("["+rs.getString("cinema_hall")+"] "+rs.getString("cinema_description"));
                hallList.add(rs.getString("cinema_hall"));
            }

            System.out.println(hallList);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                h.seeMovieMenu(h);
            }
        });

        datePicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDate = new utilToSqlDate().convertJavaDateToSqlDate((java.util.Date) datePicker.getModel().getValue());
                System.out.println(showDate.toString());
                seeShows();


            }
        });
        hallwayCombobox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seeShows();
            }
        });
    }

    void seeShows(){
        showPanel.setLayout(new GridLayout(0,1));
        showPanel.removeAll();
        try{
            Connection conn = DriverManager.getConnection(connectionClass.connectionString);
            PreparedStatement pst = conn.prepareStatement("select * from show_time where cinema_hall=? and show_date=?");
            pst.setString(1,hallList.get(hallwayCombobox.getSelectedIndex()));
            pst.setString(2, String.valueOf(showDate));
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                showPanel.add(new showItem(dateTimeConvert.toShortTime(rs.getTime("show_time")),rs.getString("movie_id"), this, rs.getInt("show_id")).panel);
                System.out.println("added "+rs.getString("show_time"));
            }


        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        showPanel.revalidate();
        showPanel.repaint();
    }
}
