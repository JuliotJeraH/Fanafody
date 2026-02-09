import java.util.Map;

public class Medicine {
    private int id;
    private String name;
    private double price;
    private String description;
    private Map<String, Integer> effects; // symptomName -> effect value

    public Medicine(int id, String name, double price, String description, Map<String, Integer> effects) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.effects = effects;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public Map<String, Integer> getEffects() { return effects; }

    public int getEffectOn(String symptomName) {
        return effects.getOrDefault(symptomName, 0);
    }

    public boolean curesAll(Map<String, Integer> patientSymptoms) {
        for (Map.Entry<String, Integer> e : patientSymptoms.entrySet()) {
            int finalVal = e.getValue() + getEffectOn(e.getKey());
            if (finalVal > 0) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name + " (" + price + " Ar) - " + description;
    }
}
