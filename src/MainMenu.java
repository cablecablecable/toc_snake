import javax.swing.*;

public class MainMenu extends JFrame
{
    private JPanel panel;
    private JComboBox<String> game_size_combobox;
    private JComboBox<String> game_speed_combobox;
    private JButton play_button;
    private JTextField username_field;

    public MainMenu()
    {
        setContentPane(panel);
        setTitle("Main Menu");
        setSize(700, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        username_field.setText("AAA");

        String[] game_sizes = {"10x10", "6x6", "16x16"};
        String[] game_speeds = {"Normal", "Slow", "Fast"};

        for (var size : game_sizes)
            game_size_combobox.addItem(size);

        for (var speeds : game_speeds)
            game_speed_combobox.addItem(speeds);

        play_button.addActionListener(e ->
        {
            int game_speed = 200;
            int game_scale = 6;

            if (game_speed_combobox.getSelectedItem() == null || game_size_combobox.getSelectedItem() == null)
                return;

            switch ((String) game_speed_combobox.getSelectedItem())
            {
                case "Slow" -> game_speed = 300;
                case "Normal" -> game_speed = 200;
                case "Fast" -> game_speed = 80;
            }

            switch ((String) game_size_combobox.getSelectedItem())
            {
                case "5x5" -> game_scale = 6;
                case "10x10" -> game_scale = 10;
                case "16x16" -> game_scale = 16;
            }

            new GameFrame(800, 800, game_scale, game_speed, username_field.getText());
            this.dispose();
        });
    }
}
