

public class Controller {
    private Medicine[] medicaments;

    public Controller() {
        this.medicaments = DatabaseUtil.obtenirMedicamentsEchantillon();
    }

    public SelectionResult evaluer(String[] nomsSymptomes, int[] valeursSymptomes, double budget) {

        int countAbordables = 0;
        int countAlternatives = 0;

        for (int i = 0; i < medicaments.length; i++) {
            Medicine med = medicaments[i];
            if (med == null) continue;
            if (med.gueritTous(nomsSymptomes, valeursSymptomes)) {
                if (med.getPrice() <= budget) countAbordables++;
                else countAlternatives++;
            }
        }

        Medicine[] abordables = new Medicine[countAbordables];
        Medicine[] alternatives = new Medicine[countAlternatives];

        int ai = 0, bi = 0;
        for (int i = 0; i < medicaments.length; i++) {
            Medicine med = medicaments[i];
            if (med == null) continue;
            if (med.gueritTous(nomsSymptomes, valeursSymptomes)) {
                if (med.getPrice() <= budget) {
                    abordables[ai++] = med;
                } else {
                    alternatives[bi++] = med;
                }
            }
        }


        triParSelectionParPrix(alternatives);

        return new SelectionResult(abordables, alternatives);
    }


    private void triParSelectionParPrix(Medicine[] liste) {
        int n = liste.length;
        for (int i = 0; i < n - 1; i++) {
            int indexMin = i;
            for (int j = i + 1; j < n; j++) {
                if (liste[j].getPrice() < liste[indexMin].getPrice()) {
                    indexMin = j;
                }
            }
            if (indexMin != i) {
                Medicine temp = liste[i];
                liste[i] = liste[indexMin];
                liste[indexMin] = temp;
            }
        }
    }
}
