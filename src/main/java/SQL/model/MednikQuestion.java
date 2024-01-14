package SQL.model;

// Модель акции
public class MednikQuestion extends BaseModel {
    private int QuestionId;
    private String Specification;

    public MednikQuestion() {
    }

    public MednikQuestion(long id, int questionId, String specification) {
        super(id);
        this.QuestionId = questionId;
        Specification = specification;
    }

    public int getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(int questionId) {
        this.QuestionId = questionId;
    }

    public String getSpecification() {
        return Specification;
    }

    public void setSpecification(String specification) {
        Specification = specification;
    }
}
