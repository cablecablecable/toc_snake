import javax.swing.*;

public class GameFrame extends JFrame
{
    public GameFrame(int width, int height, int scale)
    {
        setContentPane(new SnakeGamePanel(width, height, scale));
        setSize(width, height);
        setTitle("Snake");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
