import javax.swing.*;
import java.awt.*;
import java.util.*;

public class MainView extends JFrame {
    private CardLayout cards = new CardLayout();
    private JPanel mainPanel = new JPanel(cards);
    private JPanel page1 = new JPanel(new BorderLayout());
    private JPanel page2 = new JPanel(new BorderLayout());
    private JPanel page3 = new JPanel(new BorderLayout());

    private java.util.Map<String, JTextField> champsSymptomes = new LinkedHashMap<>();
    private JTextField budgetField = new JTextField("20000.0", 10);
    private Controller controller = new Controller();

    public MainView() {
        setTitle("Recommandation de médicaments");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        initPage1();
        initPage2();
        initPage3();

        mainPanel.add(page1, "page1");
        mainPanel.add(page2, "page2");
        mainPanel.add(page3, "page3");

        add(mainPanel);
        cards.show(mainPanel, "page1");
    }

    private void initPage1() {
        JPanel form = new JPanel();
        form.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4,4,4,4);
        c.gridx = 0; c.gridy = 0; c.anchor = GridBagConstraints.WEST;

        form.add(new JLabel("Symptômes :"), c);
        c.gridy++;

        String[] symptomes = DatabaseUtil.obtenirTousSymptomes();
        for (int i = 0; i < symptomes.length; i++) {
            String s = symptomes[i];
            JLabel l = new JLabel(s + ": ");
            JTextField tf = new JTextField("0", 5);
            champsSymptomes.put(s, tf);
            c.gridx = 0; form.add(l, c);
            c.gridx = 1; form.add(tf, c);
            c.gridy++;
        }

        c.gridx = 0; form.add(new JLabel("Budget (Ar): "), c);
        c.gridx = 1; form.add(budgetField, c);

        JButton submit = new JButton("Valider");
        submit.addActionListener(e -> onSubmit());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(submit);

        page1.add(form, BorderLayout.CENTER);
        page1.add(bottom, BorderLayout.SOUTH);
    }

    private void initPage2() {
        page2.add(new JLabel("Médicaments compatibles avec le budget"), BorderLayout.NORTH);
        page2.add(new JScrollPane(new JList<>()), BorderLayout.CENTER);
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton back = new JButton("Retour");
        back.addActionListener(e -> cards.show(mainPanel, "page1"));
        bottom.add(back);
        page2.add(bottom, BorderLayout.SOUTH);
    }

    private void initPage3() {
        page3.add(new JLabel("Alternatives (moin cher)"), BorderLayout.NORTH);
        page3.add(new JScrollPane(new JList<>()), BorderLayout.CENTER);
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton back = new JButton("Retour");
        back.addActionListener(e -> cards.show(mainPanel, "page1"));
        bottom.add(back);
        page3.add(bottom, BorderLayout.SOUTH);
    }

    private void onSubmit() {
        try {
            // Construire tableaux parallèles des symptômes saisis
            String[] noms = new String[champsSymptomes.size()];
            int[] valeurs = new int[champsSymptomes.size()];
            String[] keys = champsSymptomes.keySet().toArray(new String[0]);
            for (int i = 0; i < keys.length; i++) {
                String key = keys[i];
                JTextField champTexte = champsSymptomes.get(key);
                int valeur = Integer.parseInt(champTexte.getText().trim());
                if (valeur < 0) valeur = 0;
                noms[i] = key;
                valeurs[i] = valeur;
            }
            double budget = Double.parseDouble(budgetField.getText().trim());

            // Validation : au moins un symptôme doit avoir une valeur > 0
            boolean unSymptomePresent = false;
            for (int i = 0; i < valeurs.length; i++) {
                if (valeurs[i] > 0) {
                    unSymptomePresent = true;
                    break;
                }
            }
            if (!unSymptomePresent) {
                JOptionPane.showMessageDialog(this, "Au moins un symptôme doit avoir une valeur positive.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            SelectionResult resultat = controller.evaluer(noms, valeurs, budget);

            if (resultat.getBudgetOk().length > 0) {
                afficherListeMedicaments(page2, resultat.getBudgetOk());
                cards.show(mainPanel, "page2");
            } else {
                afficherListeMedicaments(page3, resultat.getAlternatives());
                cards.show(mainPanel, "page3");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer des nombres valides.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void afficherListeMedicaments(JPanel panel, Medicine[] liste) {
        String[] donnees = new String[liste.length];
        for (int i = 0; i < liste.length; i++) {
            donnees[i] = liste[i].toString();
        }
        JList<String> jlist = new JList<>(donnees);
        
       
        Component[] comps = panel.getComponents();
        for (int i = 0; i < comps.length; i++) {
            Object constraint = ((BorderLayout) panel.getLayout()).getConstraints(comps[i]);
            if (BorderLayout.CENTER.equals(constraint)) {
                panel.remove(comps[i]);
                break;
            }
        }
        
        panel.add(new JScrollPane(jlist), BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
    }
}
