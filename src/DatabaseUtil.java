import java.util.*;

public class DatabaseUtil {
    // Optional: configure your MySQL JDBC here and implement real DB fetching.
    // For the professor / demo, we provide an in-memory fallback dataset.

    public static List<String> getAllSymptoms() {
        return Arrays.asList("Temperature", "Nausea", "Diarrhea");
    }

    public static List<Medicine> getSampleMedicines() {
        List<Medicine> meds = new ArrayList<>();

        Map<String,Integer> ef1 = new HashMap<>();
        ef1.put("Temperature", -2);
        ef1.put("Nausea", -1);
        ef1.put("Diarrhea", -1);
        meds.add(new Medicine(1, "MedA", 10000.0, "Antipyretic + mild antiemetic", ef1));

        Map<String,Integer> ef2 = new HashMap<>();
        ef2.put("Temperature", 0);
        ef2.put("Nausea", -2);
        ef2.put("Diarrhea", -3);
        meds.add(new Medicine(2, "MedB", 25000.0, "Strong anti-diarrheal and antiemetic", ef2));

        Map<String,Integer> ef3 = new HashMap<>();
        ef3.put("Temperature", -1);
        ef3.put("Nausea", 0);
        ef3.put("Diarrhea", -1);
        meds.add(new Medicine(3, "MedC", 40000.0, "Broad spectrum", ef3));

        Map<String,Integer> ef4 = new HashMap<>();
        ef4.put("Temperature", -3);
        ef4.put("Nausea", -3);
        ef4.put("Diarrhea", -3);
        meds.add(new Medicine(4, "MedD", 75000.0, "Complete cure (expensive)", ef4));

        return meds;
    }
}
