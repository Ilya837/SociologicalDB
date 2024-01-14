package SQL.model;

// Модель акции
public class Mednik extends BaseModel {
    private int RespondentID;
    private int IndexOfTest;
    private int QuestionId;
    private String Answer  ;
    private double Originality  ;


    public Mednik() {
    }

    public Mednik(long id, int respondentID, int indexOfTest, int questionId, String answer, double originality) {
        super(id);
        RespondentID = respondentID;
        IndexOfTest = indexOfTest;
        QuestionId = questionId;
        Answer = answer;
        Originality = originality;
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

    public int getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(int questionId) {
        QuestionId = questionId;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public double getOriginality() {
        return Originality;
    }

    public void setOriginality(double originality) {
        Originality = originality;
    }
}
