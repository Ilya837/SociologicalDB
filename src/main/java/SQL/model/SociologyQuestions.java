package SQL.model;

// Модель акции
public class SociologyQuestions extends BaseModel {

    private String QuestionId;
    private String Specification;

    public SociologyQuestions() {
    }

    public SociologyQuestions(long id, String questionId, String specification) {
        super(id);
        this.QuestionId = questionId;
        Specification = specification;
    }

    public String getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(String questionId) {
        this.QuestionId = questionId;
    }

    public String getSpecification() {
        return Specification;
    }

    public void setSpecification(String specification) {
        Specification = specification;
    }
}
