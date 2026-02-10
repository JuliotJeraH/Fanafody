import java.util.*;
import java.sql.*;

public class DatabaseUtil {
    private static final String HOST = "localhost";
    private static final String DATABASE = "medicament_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final int PORT = 3306;

    private static Connection connection = null;

    // Établir la connexion MySQL
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?useSSL=false&serverTimezone=UTC";
                connection = DriverManager.getConnection(url, USER, PASSWORD);
                System.out.println("Connexion MySQL réussie !");
            } catch (ClassNotFoundException e) {
                System.err.println("Driver MySQL non trouvé. Veuillez ajouter mysql-connector-java au classpath.");
                throw e;
            } catch (SQLException e) {
                System.err.println("Erreur de connexion : " + e.getMessage());
                throw e;
            }
        }
        return connection;
    }

    // Fermer la connexion
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connexion fermée");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la fermeture : " + e.getMessage());
        }
    }


    public static String[] obtenirTousSymptomes() {
        String[] symptomes = new String[0];

        try {
            java.sql.Connection conn = getConnection();
            // compter les symptômes
            Statement stmtCount = conn.createStatement();
            ResultSet rsCount = stmtCount.executeQuery("SELECT COUNT(*) AS c FROM symptome");
            int count = 0;
            if (rsCount.next()) count = rsCount.getInt("c");
            rsCount.close();
            stmtCount.close();

            symptomes = new String[count];

            String sql = "SELECT nom_symptome FROM symptome ORDER BY id_symptome";
            Statement instruction = conn.createStatement();
            ResultSet resultat = instruction.executeQuery(sql);

            int idx = 0;
            while (resultat.next()) {
                symptomes[idx++] = resultat.getString("nom_symptome");
            }

            resultat.close();
            instruction.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Erreur lors de la récupération des symptômes : " + e.getMessage());
            e.printStackTrace();
        }

        return symptomes;
    }

    public static Medicine[] obtenirMedicamentsEchantillon() {
        Medicine[] medicaments = new Medicine[0];

        try {
            java.sql.Connection conn = getConnection();

            // compter les médicaments
            Statement stmtCount = conn.createStatement();
            ResultSet rsCount = stmtCount.executeQuery("SELECT COUNT(*) AS c FROM medicament");
            int count = 0;
            if (rsCount.next()) count = rsCount.getInt("c");
            rsCount.close();
            stmtCount.close();

            medicaments = new Medicine[count];

            // Récupérer tous les médicaments
            String sqlMedicaments = "SELECT id_medicament, nom_medicament, prix, description FROM medicament ORDER BY id_medicament";
            Statement instructionMedicaments = conn.createStatement();
            ResultSet resultatMedicaments = instructionMedicaments.executeQuery(sqlMedicaments);

            int idxMed = 0;
            // Pour chaque médicament, récupérer ses effets (liaisons avec symptômes)
            while (resultatMedicaments.next()) {
                int idMed = resultatMedicaments.getInt("id_medicament");
                String nomMed = resultatMedicaments.getString("nom_medicament");
                double prixMed = resultatMedicaments.getDouble("prix");
                String descMed = resultatMedicaments.getString("description");

                // compter les effets pour ce médicament
                Statement stmtEffCount = conn.createStatement();
                ResultSet rsEffCount = stmtEffCount.executeQuery("SELECT COUNT(*) AS c FROM effet WHERE id_medicament = " + idMed);
                int effCount = 0;
                if (rsEffCount.next()) effCount = rsEffCount.getInt("c");
                rsEffCount.close();
                stmtEffCount.close();

                String[] nomsEffets = new String[effCount];
                int[] valeursEffets = new int[effCount];

                if (effCount > 0) {
                    String sqlEffets = "SELECT s.nom_symptome, e.valeur_effet FROM effet e " +
                                        "JOIN symptome s ON e.id_symptome = s.id_symptome " +
                                        "WHERE e.id_medicament = " + idMed + " ORDER BY e.id_symptome";
                    Statement instructionEffets = conn.createStatement();
                    ResultSet resultatEffets = instructionEffets.executeQuery(sqlEffets);
                    int ei = 0;
                    while (resultatEffets.next()) {
                        nomsEffets[ei] = resultatEffets.getString("nom_symptome");
                        valeursEffets[ei] = resultatEffets.getInt("valeur_effet");
                        ei++;
                    }
                    resultatEffets.close();
                    instructionEffets.close();
                }

                medicaments[idxMed++] = new Medicine(idMed, nomMed, prixMed, descMed, nomsEffets, valeursEffets);
            }

            resultatMedicaments.close();
            instructionMedicaments.close();

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Erreur lors de la récupération des médicaments : " + e.getMessage());
            e.printStackTrace();
        }

        return medicaments;
    }
}
