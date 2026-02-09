import java.util.*;

public class Controller {
    private List<Medicine> medicines;

    public Controller() {
        this.medicines = DatabaseUtil.getSampleMedicines();
    }

    public SelectionResult evaluate(Map<String, Integer> patientSymptoms, double budget) {
        List<Medicine> budgetOk = new ArrayList<>();
        List<Medicine> alternatives = new ArrayList<>();

        for (Medicine m : medicines) {
            if (m.curesAll(patientSymptoms)) {
                if (m.getPrice() <= budget) budgetOk.add(m);
                else alternatives.add(m);
            }
        }

        alternatives.sort(Comparator.comparingDouble(Medicine::getPrice));
        return new SelectionResult(budgetOk, alternatives);
    }
}
