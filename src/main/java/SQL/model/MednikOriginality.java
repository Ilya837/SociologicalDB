package SQL.model;

// Модель акции
public class MednikOriginality extends BaseModel {
    private int RespondentID;
    private int IndexOfTest;
    private double Originality;

    private String Level;

    public MednikOriginality() {
    }

    public MednikOriginality(long id, int respondentID, int indexOfTest, double originality, String level) {
        super(id);
        RespondentID = respondentID;
        IndexOfTest = indexOfTest;
        Originality = originality;
        Level = level;
    }

    public int getRespondentID() {
        return RespondentID;
    }

    public void setRespondentID(int respondentID) {
        RespondentID = respondentID;
    }

    public int getIndexOfTest() {
        return IndexOfTest;
    }

    public void setIndexOfTest(int indexOfTest) {
        IndexOfTest = indexOfTest;
    }

    public double getOriginality() {
        return Originality;
    }

    public void setOriginality(double originality) {
        Originality = originality;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }
}
