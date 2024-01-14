package SQL.model;

// Модель акции
public class Respondents extends BaseModel {
    private String RespondentID;


    public Respondents() {
    }

    public Respondents(long id, String ID) {
        super(id);
        this.RespondentID = ID;
    }

    public String getRespondentID() {
        return RespondentID;
    }

    public void setRespondentID(String respondentID) {
        this.RespondentID = respondentID;
    }
}
