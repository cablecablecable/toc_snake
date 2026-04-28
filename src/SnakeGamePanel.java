import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class SnakeGamePanel extends JPanel implements ActionListener
{
    final int screen_width;
    final int screen_height;
    final int game_unit;
    final int game_scale;

    char snake_direction = 'L';
    int apples_eaten;
    int max_apples = 5;
    boolean game_running;
    final int update_delay = 200;

    Deque<Point> snake;
    List<Point> apples;
    Timer timer;
    Point old_snake_tail;
    JButton button = new JButton("PlAY AGAIN");

    public SnakeGamePanel(int width, int height, int game_scale)
    {
        this.setPreferredSize(new Dimension(width, height));
        this.setFocusable(true);
        this.setBackground(Color.black);
        this.addKeyListener(new MyKeyAdapter());
        this.setLayout(null);
        this.screen_width = width;
        this.screen_height = height;
        button.setVisible(false);
        this.add(button);
        button.setBounds(width / 3, (height / 2) + 100, 300, 100);
        button.addActionListener(e ->
        {
            snake_direction = 'L';
            apples_eaten = 0;
            button.setVisible(false);
            start_game();
        });



        this.game_scale = game_scale;
        this.game_unit = width / game_scale; // width and height of each square in the game

        start_game();
    }

    private void start_game()
    {
        snake = new LinkedList<>();
        apples = new ArrayList<>();

        snake.add(new Point(game_scale - 2, game_scale - 2));
        snake.add(new Point(game_scale - 3, game_scale - 2));
        generate_apple();


        game_running = true;
        timer = new Timer(update_delay, this::actionPerformed);
        timer.start();
    }

    // game loop, runs every x milliseconds specified by update_delay
    @Override
    public void actionPerformed(ActionEvent event)
    {
        if (game_running)
        {
            snake_move();
            check_if_body_collision();
            check_if_edge_collision();
            check_if_apple_eaten();
        }
        else
        {
            timer.stop();
            return;
        }
        if ((game_scale * game_scale) - snake.size() >= max_apples)
        {
            while (apples.size() < max_apples)
                generate_apple();
        }

        repaint();
    }

    // UI logic
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (check_if_game_win())
        {
            game_win(g);
            return;
        }

        if (game_running)
            draw_game(g);
        else
            game_over(g);
    }

    private void draw_game(Graphics g)
    {
        for (int x = 0; x < game_scale; x++)
        {
            for (int y = 0; y < game_scale; y++)
            {
                Point current_point = new Point(x, y);

                if (current_point.equals(snake.getFirst()))
                {
                    g.setColor(new Color(61, 112, 48));
                    g.fillRect(x * game_unit, y * game_unit, game_unit, game_unit);
                    continue;
                }

                if (is_snake(current_point))
                {
                    g.setColor(Color.GREEN);
                    g.fillRect(x * game_unit, y * game_unit, game_unit, game_unit);
                    continue;
                }

                if (is_apple(current_point))
                {
                    g.setColor(Color.RED);
                    g.fillOval(x * game_unit, y * game_unit, game_unit, game_unit);
                }
            }
        }
    }

    private void snake_move()
    {
        // save this so if an apple is eaten it can be added back
        old_snake_tail = snake.removeLast();

        Point snake_head = snake.getFirst();

        switch (snake_direction)
        {
            case 'U' -> snake.addFirst(new Point(snake_head.x, snake_head.y - 1));
            case 'L' -> snake.addFirst(new Point(snake_head.x - 1, snake_head.y));
            case 'D' -> snake.addFirst(new Point(snake_head.x, snake_head.y + 1));
            case 'R' -> snake.addFirst(new Point(snake_head.x + 1, snake_head.y));
        }
    }

    private void check_if_apple_eaten()
    {
        for (int i = 0; i < apples.size(); i++)
        {
            if (apples.get(i).equals(snake.getFirst()))
            {
                snake.addLast(old_snake_tail);
                apples.remove(i);
                apples_eaten++;
                return;
            }
        }
    }

    private void check_if_body_collision()
    {
        boolean passed_head = false;
        for (var snake_point : snake)
        {
            if (!passed_head)
            {
                passed_head = true;
                continue;
            }

            if (snake_point.equals(snake.getFirst()))
                game_running = false;
        }
    }

    private void check_if_edge_collision()
    {
        Point snake_head = snake.getFirst();
        if (snake_head.x >= game_scale || snake_head.x < 0 ||
                snake_head.y >= game_scale || snake_head.y < 0)
            game_running = false;
    }

    private boolean check_if_game_win()
    {
        return snake.size() >= game_scale * game_scale;
    }

    private void game_over(Graphics g)
    {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD, 75));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (screen_width - metrics1.stringWidth("GAME OVER")) / 2, screen_height / 2);

        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD, 40));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("APPLES EATEN: " + apples_eaten, (screen_width - metrics2.stringWidth("SCORE: "+ screen_height)) / 2, g.getFont().getSize());

        button.setVisible(true);
    }

    private void game_win(Graphics g)
    {
        g.setColor(Color.GREEN);
        g.setFont(new Font("Ink Free",Font.BOLD, 75));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("GAME WON", (screen_width - metrics1.stringWidth("GAME OVER")) / 2, screen_height / 2);

        g.setColor(Color.GREEN);
        g.setFont(new Font("Ink Free",Font.BOLD, 40));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("APPLES EATEN: " + apples_eaten, (screen_width - metrics2.stringWidth("SCORE: "+ screen_height)) / 2, g.getFont().getSize());

        button.setVisible(true);
    }

    private boolean is_snake(Point point)
    {
        for (var snake_point : snake)
            if (snake_point.equals(point))
                return true;

        return false;
    }

    private boolean is_apple(Point point)
    {
        for (var apple_point : apples)
            if (apple_point.equals(point))
                return true;

        return false;
    }

    private void generate_apple()
    {
        Point new_apple;
        while (true)
        {
            int x = (int) (Math.random() * game_scale);
            int y = (int) (Math.random() * game_scale);
            new_apple = new Point(x, y);

            if (!is_apple(new_apple) && !is_snake(new_apple))
                break;
        }
        apples.add(new_apple);
    }

    public class MyKeyAdapter extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_LEFT, KeyEvent.VK_A ->
                {
                    if (snake_direction != 'R') snake_direction = 'L';
                }
                case KeyEvent.VK_RIGHT, KeyEvent.VK_D ->
                {
                    if (snake_direction != 'L') snake_direction = 'R';
                }
                case KeyEvent.VK_UP, KeyEvent.VK_W ->
                {
                    if (snake_direction != 'D') snake_direction = 'U';
                }
                case KeyEvent.VK_DOWN, KeyEvent.VK_S ->
                {
                    if (snake_direction != 'U') snake_direction = 'D';
                }
            }
        }
    }

    static class Point
    {
        public int x;
        public int y;

        public Point(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

        public boolean equals(Point point)
        {
            return this.x == point.x && this.y == point.y;
        }
    }

}
