public class Effect {
    private int medicineId;
    private String symptomName;
    private int value;

    public Effect(int medicineId, String symptomName, int value) {
        this.medicineId = medicineId;
        this.symptomName = symptomName;
        this.value = value;
    }

    public int getMedicineId() { return medicineId; }
    public String getSymptomName() { return symptomName; }
    public int getValue() { return value; }
}
