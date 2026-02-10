import java.util.List;

public class SelectionResult {
    private Medicine[] dansBudget;
    private Medicine[] alternatives;

    public SelectionResult(Medicine[] dansBudget, Medicine[] alternatives) {
        this.dansBudget = dansBudget;
        this.alternatives = alternatives;
    }

    public Medicine[] getBudgetOk() { return dansBudget; }
    public Medicine[] getAlternatives() { return alternatives; }
}
