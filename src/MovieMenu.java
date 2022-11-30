import javax.swing.*;
import java.awt.*;

public class MovieMenu {

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        panel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        panel.setLayout(new GridLayout(0,1));

        frame.setSize(500,800);
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Now Showing");
        frame.setVisible(true);

        frame.add(new MovieItem("a","b").movieItemPanel);

    }
}
