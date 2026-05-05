public class LeaderboardEntry
{
    private int score;
    private String username;
    private String game_size;

    public LeaderboardEntry(int score, String username, String game_size)
    {
        this.score = score;
        this.username = username;
        this.game_size = game_size;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getGame_size()
    {
        return game_size;
    }

    public void setGame_size(String game_size)
    {
        this.game_size = game_size;
    }

    @Override
    public String toString()
    {
        return score + "," + username + "," + game_size;
    }
}
