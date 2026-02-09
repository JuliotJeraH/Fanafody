public class Symptom {
    private String name;
    private int intensity;

    public Symptom(String name, int intensity) {
        this.name = name;
        this.intensity = intensity;
    }

    public String getName() { return name; }
    public int getIntensity() { return intensity; }
    public void setIntensity(int intensity) { this.intensity = intensity; }

    @Override
    public String toString() {
        return name + "=" + intensity;
    }
}
