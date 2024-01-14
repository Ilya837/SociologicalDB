package SQL.model;

// Модель акции
public class Respondents extends BaseModel {
    private int ID;


    public Respondents() {
    }

    public Respondents(long id, int ID) {
        super(id);
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
