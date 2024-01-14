package SQL.model;

// Модель акции
public class SociologyQuestions extends BaseModel {

    private String ID;
    private String Specification;

    public SociologyQuestions() {
    }

    public SociologyQuestions(long id, String ID, String text) {
        super(id);
        this.ID = ID;
        Specification = text;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSpecification() {
        return Specification;
    }

    public void setSpecification(String specification) {
        Specification = specification;
    }
}
