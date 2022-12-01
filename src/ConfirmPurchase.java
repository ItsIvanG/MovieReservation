import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ConfirmPurchase {
    public JPanel panel;
    private JLabel seatsOrderingLabel;
    private JLabel showIDLabel;
    private JButton backButton;

    public ConfirmPurchase(List<String> i, int ShowID, String m, Header h){
        for (String x:
             i) {
            seatsOrderingLabel.setText(seatsOrderingLabel.getText()+x+", ");
        }
        showIDLabel.setText(String.valueOf(ShowID));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                h.seeMovieDetails(m,h);
            }
        });
    }
}
