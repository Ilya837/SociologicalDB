package SQL.model;

// Модель акции
public class MednikQuestion extends BaseModel {
    private int ID;
    private String Specification;

    public MednikQuestion() {
    }

    public MednikQuestion(long id, int ID, String text) {
        super(id);
        this.ID = ID;
        Specification = text;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSpecification() {
        return Specification;
    }

    public void setSpecification(String specification) {
        Specification = specification;
    }
}
