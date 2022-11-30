import javax.swing.*;

public class MovieItem{
    private JLabel movieTitle;
    private JButton openMovie;
    private JLabel movieDesc;
    public JPanel movieItemPanel;


    public MovieItem(String a, String b){
        movieTitle.setText(a);
        movieDesc.setText(b);
    }
}
