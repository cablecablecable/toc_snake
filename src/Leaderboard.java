import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class Leaderboard extends JFrame
{
    private JPanel panel;
    private JList<String> jlist;
    private JButton mainMenuButton;
    private DefaultListModel<String> list_model = new DefaultListModel<>();
    private String game_size;

    public Leaderboard(JFrame game_frame, String game_size)
    {
        this.game_size = game_size;
        Point parent_location = game_frame.getLocationOnScreen();

        setContentPane(panel);
        setTitle("Leaderboard");
        setSize(300, 650);
        setLocation(parent_location.x + game_frame.getWidth(), parent_location.y);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        populate_list_model();
        jlist.setModel(list_model);

        mainMenuButton.addActionListener(e ->
        {
            new MainMenu();
            game_frame.dispose();
            this.dispose();
        });
    }

    private void populate_list_model()
    {
        list_model.addElement("RANK  NAME  SCORE");
        list_model.addElement(" ");

        ArrayList<LeaderboardEntry> list = read_leaderboard_file();

        switch (game_size)
        {
            case "6x6" -> list = list
                    .stream()
                    .filter(e -> e.getGame_size().equals("6x6"))
                    .collect(Collectors.toCollection(ArrayList::new));

            case "10x10" -> list = list
                    .stream()
                    .filter(e -> e.getGame_size().equals("10x10"))
                    .collect(Collectors.toCollection(ArrayList::new));

            case "16x16" -> list = list
                    .stream()
                    .filter(e -> e.getGame_size().equals("16x16"))
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        list.sort(Comparator.comparingInt(LeaderboardEntry::getScore).reversed());

        for (int i = 0; (i < 10) && (i < list.size()); i++)
        {
            list_model.addElement((i + 1) + "           " + list.get(i).getUsername() + "           " + list.get(i).getScore());
        }
    }

    private ArrayList<LeaderboardEntry> read_leaderboard_file()
    {
        ArrayList<LeaderboardEntry> list = new ArrayList<>();
        try (Scanner input_stream = new Scanner(new File("leaderboard.csv")))
        {
            while (input_stream.hasNextLine())
            {
                String[] line = input_stream.nextLine().split(",");
                list.add(new LeaderboardEntry(Integer.parseInt(line[0]), line[1], line[2]));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public static void write_to_leaderboard(LeaderboardEntry entry)
    {
        try (PrintWriter output_stream = new PrintWriter(new FileWriter("leaderboard.csv", true)))
        {
            output_stream.println(entry);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
