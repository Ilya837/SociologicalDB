package SQL.model;

// Модель акции
public class Sociology extends BaseModel {
    private int RespondentID;
    private String QuestionId;
    private int Answer;


    public Sociology() {
    }

    public Sociology(long id, int respondentID, String questionId, int answer) {
        super(id);
        RespondentID = respondentID;
        QuestionId = questionId;
        Answer = answer;
    }

    public int getRespondentID() {
        return RespondentID;
    }

    public void setRespondentID(int respondentID) {
        RespondentID = respondentID;
    }

    public String getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(String questionId) {
        QuestionId = questionId;
    }

    public int getAnswer() {
        return Answer;
    }

    public void setAnswer(int answer) {
        Answer = answer;
    }


}
