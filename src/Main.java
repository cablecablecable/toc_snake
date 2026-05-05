import javax.swing.*;

public class Main
{
    public static void main(String[] args)
    {
//        GameFrame gameFrame = new GameFrame(800, 800, 6);
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        new MainMenu();
    }
}
