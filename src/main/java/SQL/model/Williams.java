package SQL.model;

// Модель акции
public class Williams extends SQL.model.BaseModel {
    private int RespondentID;
    private int IndexOfTest;
    private int Inquisitiveness;
    private int Imagination  ;
    private int Complexity  ;
    private int RiskAppetite;
    private int Sum ;
    private String Level;

    public Williams() {
    }

    public Williams(long id, int respondentID, int indexOfTest, int inquisitiveness, int imagination, int complexity, int riskAppetite, int sum, String level) {
        super(id);
        RespondentID = respondentID;
        IndexOfTest = indexOfTest;
        Inquisitiveness = inquisitiveness;
        Imagination = imagination;
        Complexity = complexity;
        RiskAppetite = riskAppetite;
        Sum = sum;
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

    public int getInquisitiveness() {
        return Inquisitiveness;
    }

    public void setInquisitiveness(int inquisitiveness) {
        Inquisitiveness = inquisitiveness;
    }

    public int getImagination() {
        return Imagination;
    }

    public void setImagination(int imagination) {
        Imagination = imagination;
    }

    public int getComplexity() {
        return Complexity;
    }

    public void setComplexity(int complexity) {
        Complexity = complexity;
    }

    public int getRiskAppetite() {
        return RiskAppetite;
    }

    public void setRiskAppetite(int riskAppetite) {
        RiskAppetite = riskAppetite;
    }

    public int getSum() {
        return Sum;
    }

    public void setSum(int sum) {
        Sum = sum;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }
}
