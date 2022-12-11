import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class showItem {
    public JPanel panel;
    private JButton EDITButton;
    private JLabel timeLabel;
    private JLabel movieLabel;

    showItem(String time,String movie, ShowManage sm, int showID){

        timeLabel.setText(time);
        movieLabel.setText(movie);
        EDITButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new editShowDialog(sm, showID).setVisible(true);
            }
        });
    }
}
