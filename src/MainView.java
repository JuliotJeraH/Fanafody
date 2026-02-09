import javax.swing.*;
import java.awt.*;
import java.util.*;

public class MainView extends JFrame {
    private CardLayout cards = new CardLayout();
    private JPanel mainPanel = new JPanel(cards);
    private JPanel page1 = new JPanel(new BorderLayout());
    private JPanel page2 = new JPanel(new BorderLayout());
    private JPanel page3 = new JPanel(new BorderLayout());

    private Map<String, JTextField> symptomFields = new LinkedHashMap<>();
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

        form.add(new JLabel("Symptômes (entier >=0) :"), c);
        c.gridy++;

        for (String s : DatabaseUtil.getAllSymptoms()) {
            JLabel l = new JLabel(s + ": ");
            JTextField tf = new JTextField("0", 5);
            symptomFields.put(s, tf);
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
        page3.add(new JLabel("Alternatives (peuvent dépasser le budget)"), BorderLayout.NORTH);
        page3.add(new JScrollPane(new JList<>()), BorderLayout.CENTER);
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton back = new JButton("Retour");
        back.addActionListener(e -> cards.show(mainPanel, "page1"));
        bottom.add(back);
        page3.add(bottom, BorderLayout.SOUTH);
    }

    private void onSubmit() {
        Map<String, Integer> patient = new LinkedHashMap<>();
        try {
            for (Map.Entry<String, JTextField> en : symptomFields.entrySet()) {
                int val = Integer.parseInt(en.getValue().getText().trim());
                if (val < 0) val = 0;
                patient.put(en.getKey(), val);
            }
            double budget = Double.parseDouble(budgetField.getText().trim());

            SelectionResult res = controller.evaluate(patient, budget);

            if (!res.getBudgetOk().isEmpty()) {
                showMedicineList(page2, res.getBudgetOk());
                cards.show(mainPanel, "page2");
            } else {
                showMedicineList(page3, res.getAlternatives());
                cards.show(mainPanel, "page3");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer des nombres valides.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showMedicineList(JPanel panel, java.util.List<Medicine> list) {
        JList<String> jlist = new JList<>(list.stream().map(Medicine::toString).toArray(String[]::new));
        panel.remove(1);
        panel.add(new JScrollPane(jlist), BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
    }
}
