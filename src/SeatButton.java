import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SeatButton {
    public int seatStatus=0;
    public JPanel panel;
    private JButton seatButton;
    SeatButton(String SEATID, MovieDetails m, int SHOWID){
        seatButton.setText(SEATID);
        seatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(seatStatus==0){
                    m.addSeatToCart(SEATID);
                    seatButton.setBackground(Color.GREEN);
                    seatStatus=1;
                } else if (seatStatus==1) {
                    m.removeSeatFromCart(SEATID);
                    seatButton.setBackground(Color.WHITE);
                    seatStatus=0;
                }
            }
        });
    }
}
