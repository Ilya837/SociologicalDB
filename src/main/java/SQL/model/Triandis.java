package SQL.model;

// Модель акции
public class Triandis extends BaseModel {
    private String RespondentID;
    private int IndexOfTest;
    private int Figure;
    private String Level;

    public Triandis() {
    }

    public Triandis(long id, String respondentID, int indexOfTest, int figure, String level) {
        super(id);
        RespondentID = respondentID;
        IndexOfTest = indexOfTest;
        Figure = figure;
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

    public int getFigure() {
        return Figure;
    }

    public void setFigure(int figure) {
        Figure = figure;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }
}
