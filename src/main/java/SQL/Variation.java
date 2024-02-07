package SQL;

public class Variation {
    private String interval;

    private String score;

    public Variation(String interval, String count) {
        this.interval = interval;
        this.score = count;
    }

    public String getInterval() {
        return interval;
    }

    public String getScore() {
        return score;
    }
}
