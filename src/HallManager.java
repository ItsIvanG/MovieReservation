import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HallManager {
    public JPanel panel;
    private JButton backButton;
    private JComboBox hallwayCombobox;
    private JButton deleteButton;
    private JTextField hallcodeField;
    private JTextField hallDescField;
    private JPanel seatsPanel;
    private JTextField hallNumberOfSeatsField;
    private JTextField hallSeatsPerRowField;
    private JButton previewButton;
    private JButton applyButton;
    private List<String> hallList = new ArrayList<>();
    private String[] rowCodes = {"A","B","C","D","E","F","G","H","I","J"};
    public HallManager(Header h){

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                h.seeMovieMenu(h);
            }
        });

        try{
            Connection conn = DriverManager.getConnection(connectionClass.connectionString);
            PreparedStatement pst = conn.prepareStatement("select * from cinema_room");
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                hallwayCombobox.addItem("["+rs.getString("cinema_hall")+"] "+rs.getString("cinema_description"));
                hallList.add(rs.getString("cinema_hall"));
            }
            hallwayCombobox.addItem("+ Add new hall...");
            System.out.println(hallList);
            viewHallDetails(hallList.get(0));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        hallwayCombobox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(hallwayCombobox.getSelectedIndex()<hallList.size()){
                    viewHallDetails(hallList.get(hallwayCombobox.getSelectedIndex()));
                }else{
                    hallcodeField.setText("");
                    hallDescField.setText("");
                    hallNumberOfSeatsField.setText("");
                    hallSeatsPerRowField.setText("");
                    previewSeats(0,1);
                }
            }
        });
        previewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previewSeats(Integer.parseInt(hallNumberOfSeatsField.getText()),Integer.parseInt(hallSeatsPerRowField.getText()));
            }
        });
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(hallList.contains(hallcodeField.getText())){
                    try{
                        Connection conn = DriverManager.getConnection(connectionClass.connectionString);
                        PreparedStatement pst = conn.prepareStatement("update cinema_room set no_of_seats=?,cinema_description=?,seatsperrow=? where cinema_hall=?");
                        pst.setInt(1, Integer.parseInt(hallNumberOfSeatsField.getText()));
                        pst.setString(2, hallDescField.getText());
                        pst.setInt(3,Integer.parseInt(hallSeatsPerRowField.getText()));
//                        pst.setDouble(4,Double.parseDouble(movieRateField.getText()));
                        pst.setString(4,hallList.get(hallwayCombobox.getSelectedIndex()));
                        pst.execute();
                        JOptionPane.showMessageDialog(null, "Cinema hall code already exists! Updating existing record.");
                        h.seeHallManager();
                    }catch(Exception x){
                        System.out.println(x.getMessage());
                    }
                }else{
                    try{
                        Connection conn = DriverManager.getConnection(connectionClass.connectionString);
                        PreparedStatement pst = conn.prepareStatement("insert into cinema_room(cinema_hall,no_of_seats,cinema_description,seatsperrow) values(?,?,?,?)");
                        pst.setString(1, hallcodeField.getText());
                        pst.setInt(2, Integer.parseInt(hallNumberOfSeatsField.getText()));
                        pst.setString(3, hallDescField.getText());
                        pst.setInt(4,Integer.parseInt(hallSeatsPerRowField.getText()));
//                        pst.setDouble(5,Double.parseDouble(movieRateField.getText()));
                        pst.execute();
                        JOptionPane.showMessageDialog(null, "Cinema hall code does not exist! Inserting new record.");
                        h.seeHallManager();
                    }catch(Exception x){
                        System.out.println(x.getMessage());
                    }
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Connection conn = DriverManager.getConnection(connectionClass.connectionString);
                    PreparedStatement pst = conn.prepareStatement("delete from cinema_room where cinema_Hall=?");
                    pst.setString(1, hallList.get(hallwayCombobox.getSelectedIndex()));

                    pst.execute();
                    h.seeHallManager();
                    JOptionPane.showMessageDialog(null, "Cinema hall deleted.");
                }catch (Exception x){
                    System.out.println(x.getMessage());
                    if(x.getMessage().equals("UCAExc:::5.0.1 integrity constraint violation: foreign key no action; SHOW_TIME_CINEMA_ROOMSHOW_TIME table: SHOW_TIME")){
                        JOptionPane.showMessageDialog(null, "Cinema hall already has show times associated and cannot be deleted.","Error",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
    public void viewHallDetails(String id){
        try{
            Connection conn = DriverManager.getConnection(connectionClass.connectionString);
            PreparedStatement pst = conn.prepareStatement("select * from cinema_room where cinema_hall=?");
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                hallcodeField.setText(rs.getString("cinema_hall"));
                hallDescField.setText(rs.getString("cinema_description"));
                hallNumberOfSeatsField.setText(rs.getString("no_of_seats"));
                hallSeatsPerRowField.setText(rs.getString("seatsperrow"));
//                movieRateField.setText(rs.getString("rateadd"));
                previewSeats(Integer.parseInt(hallNumberOfSeatsField.getText()),Integer.parseInt(hallSeatsPerRowField.getText()));
            }

        } catch (Exception x){
            System.out.println(x.getMessage());
        }
    }
    public void previewSeats(int noOfSeats, int seatsPerRow){
        seatsPanel.removeAll();
        seatsPanel.setLayout(new GridLayout(0,seatsPerRow));

        int currentSeat=0;
        int currentRow=0;
        int currentSeatReset=0;
        while(currentSeat<noOfSeats){

            String seatIDdebug = rowCodes[currentRow]+(currentSeatReset+1);
            JButton n = new JButton(seatIDdebug);

            seatsPanel.add( n);
            System.out.println(seatIDdebug+" added");
            currentSeat++;
            currentSeatReset++;

            if(currentSeat%seatsPerRow==0&&currentSeat!=0){
                currentRow++;
                currentSeatReset=0;
            }
        }

        seatsPanel.revalidate();
        seatsPanel.repaint();
    }

}
