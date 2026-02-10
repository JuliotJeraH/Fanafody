
public class Medicine {
    private int id;
    private String nom;
    private double price;
    private String description;
    // effets par tableaux parallèles
    private String[] nomsEffets;
    private int[] valeursEffets;

    public Medicine(int id, String nom, double price, String description, String[] nomsEffets, int[] valeursEffets) {
        this.id = id;
        this.nom = nom;
        this.price = price;
        this.description = description;
        this.nomsEffets = nomsEffets;
        this.valeursEffets = valeursEffets;
    }

    public int getId() { return id; }
    public String getName() { return nom; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }

    // Cherche l'effet sur un symptôme en parcourant le tableau
    public int obtenirEffet(String nomSymptome) {
        if (nomsEffets == null || valeursEffets == null) return 0;
        for (int i = 0; i < nomsEffets.length; i++) {
            if (nomsEffets[i] != null && nomsEffets[i].equals(nomSymptome)) {
                return valeursEffets[i];
            }
        }
        return 0;
    }

    // Vérifie si pour chaque symptôme donné, l'effet le ramène à <= 0
    public boolean gueritTous(String[] nomsSymptomes, int[] valeursSymptomes) {
        for (int i = 0; i < nomsSymptomes.length; i++) {
            String nom = nomsSymptomes[i];
            int val = valeursSymptomes[i];
            int valFinale = val + obtenirEffet(nom);
            if (valFinale > 0) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nom + " (" + price + " Ar) - " + description;
    }
}
