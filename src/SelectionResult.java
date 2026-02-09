import java.util.List;

public class SelectionResult {
    private List<Medicine> budgetOk;
    private List<Medicine> alternatives;

    public SelectionResult(List<Medicine> budgetOk, List<Medicine> alternatives) {
        this.budgetOk = budgetOk;
        this.alternatives = alternatives;
    }

    public List<Medicine> getBudgetOk() { return budgetOk; }
    public List<Medicine> getAlternatives() { return alternatives; }
}
