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
            }
        });
    }
}
