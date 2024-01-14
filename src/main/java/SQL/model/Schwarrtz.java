package SQL.model;

// Модель акции
public class Schwarrtz extends BaseModel {
    private String RespondentID;
    private int IndexOfTest;
    private double Safety;
    private double Comfort;
    private double Tradition;
    private double Independence;
    private double RiskNovelty;
    private double Hedonism;
    private double Achievement;
    private double Power;
    private double Benevolence;
    private double Universalism;
    private String Level;

    public Schwarrtz() {
    }

    public Schwarrtz(long id, String respondentID, int indexOfTest, double safety, double comfort, double tradition, double independence, double riskNovelty, double hedonism, double achievement, double power, double benevolence, double universalism, String level) {
        super(id);
        RespondentID = respondentID;
        IndexOfTest = indexOfTest;
        Safety = safety;
        Comfort = comfort;
        Tradition = tradition;
        Independence = independence;
        RiskNovelty = riskNovelty;
        Hedonism = hedonism;
        Achievement = achievement;
        Power = power;
        Benevolence = benevolence;
        Universalism = universalism;
        Level = level;
    }

    public String getRespondentID() {
        return RespondentID;
    }

    public void setRespondentID(String respondentID) {
        RespondentID = respondentID;
    }

    public int getIndexOfTest() {
        return IndexOfTest;
    }

    public void setIndexOfTest(int indexOfTest) {
        IndexOfTest = indexOfTest;
    }

    public double getSafety() {
        return Safety;
    }

    public void setSafety(double safety) {
        Safety = safety;
    }

    public double getComfort() {
        return Comfort;
    }

    public void setComfort(double comfort) {
        Comfort = comfort;
    }

    public double getTradition() {
        return Tradition;
    }

    public void setTradition(double tradition) {
        Tradition = tradition;
    }

    public double getIndependence() {
        return Independence;
    }

    public void setIndependence(double independence) {
        Independence = independence;
    }

    public double getRiskNovelty() {
        return RiskNovelty;
    }

    public void setRiskNovelty(double riskNovelty) {
        RiskNovelty = riskNovelty;
    }

    public double getHedonism() {
        return Hedonism;
    }

    public void setHedonism(double hedonism) {
        Hedonism = hedonism;
    }

    public double getAchievement() {
        return Achievement;
    }

    public void setAchievement(double achievement) {
        Achievement = achievement;
    }

    public double getPower() {
        return Power;
    }

    public void setPower(double power) {
        Power = power;
    }

    public double getBenevolence() {
        return Benevolence;
    }

    public void setBenevolence(double benevolence) {
        Benevolence = benevolence;
    }

    public double getUniversalism() {
        return Universalism;
    }

    public void setUniversalism(double universalism) {
        Universalism = universalism;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }
}
