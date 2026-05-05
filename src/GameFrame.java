import javax.swing.*;

public class GameFrame extends JFrame
{
    public GameFrame(int width, int height, int scale, int game_speed, String username)
    {
        setContentPane(new SnakeGamePanel(width, height, scale, game_speed, username));
        setSize(width, height);
        setTitle("Snake");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);

    }
}
