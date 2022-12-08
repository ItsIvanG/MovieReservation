
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MovieItem{
    private JLabel movieTitle;
    private JButton openMovie;
    private JLabel movieDesc;
    public JPanel movieItemPanel;
    private JLabel movieCodeLabel;
    private JLabel moviePhoto;

    public Header h;
    public MovieItem(String a, String b, String m, Header x, String photo){
        h = x;
        System.out.println("PHOTO: "+photo);

        ImageIcon img = new ImageIcon(photo);
        movieTitle.setText(a);
        movieDesc.setText(b);
        movieCodeLabel.setText(m);
        moviePhoto.setIcon(img);

        openMovie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Clicked on "+movieTitle.getText());
                h.movieCode = movieCodeLabel.getText();
                System.out.println("Current movie code: "+h.movieCode);
                h.seeMovieDetails(h);

            }
        });
    }
}
