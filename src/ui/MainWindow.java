package ui;

import models.WorkoutNode;
import structures.AVLTree;
import structures.UnbalancedBST;
import structures.SinglyLinkedList;
import utils.MockDataGenerator;
import utils.PerformanceBenchmark;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * MainWindow - Swing-based GUI for Fitness & Conditioning Data Tracker
 * Replaces CommandLineInterface with a window-based interface.
 */
public class MainWindow extends JFrame {

    private AVLTree avlTree;
    private UnbalancedBST bst;
    private SinglyLinkedList linkedList;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Shared output area
    private JTextArea outputArea;

    // Table model for workout list
    private DefaultTableModel tableModel;

    public MainWindow() {
        avlTree = new AVLTree();
        bst = new UnbalancedBST();
        linkedList = new SinglyLinkedList();

        setTitle("Fitness & Conditioning Data Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 650);
        setMinimumSize(new Dimension(800, 550));
        setLocationRelativeTo(null);

        buildUI();
    }

    private void buildUI() {
        // Main layout: left sidebar + right content
        JPanel root = new JPanel(new BorderLayout(8, 8));
        root.setBorder(new EmptyBorder(10, 10, 10, 10));
        root.setBackground(new Color(30, 30, 30));

        root.add(buildHeader(), BorderLayout.NORTH);
        root.add(buildSidebar(), BorderLayout.WEST);
        root.add(buildContent(), BorderLayout.CENTER);

        setContentPane(root);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(20, 20, 20));
        header.setBorder(new EmptyBorder(8, 12, 8, 12));

        JLabel title = new JLabel("Fitness & Conditioning Data Tracker");
        title.setForeground(new Color(100, 200, 100));
        title.setFont(new Font("SansSerif", Font.BOLD, 18));

        JLabel subtitle = new JLabel("AVL Tree  |  Unbalanced BST  |  Singly Linked List");
        subtitle.setForeground(new Color(150, 150, 150));
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));

        header.add(title, BorderLayout.WEST);
        header.add(subtitle, BorderLayout.EAST);
        return header;
    }

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(40, 40, 40));
        sidebar.setBorder(new EmptyBorder(10, 8, 10, 8));
        sidebar.setPreferredSize(new Dimension(180, 0));

        String[] labels = {
            "Add Workout", "Search by Date", "Range Query",
            "Load Mock Data", "Run Benchmark", "Statistics", "Clear All Data"
        };

        for (String label : labels) {
            sidebar.add(sidebarButton(label));
            sidebar.add(Box.createVerticalStrut(6));
        }

        sidebar.add(Box.createVerticalGlue());
        return sidebar;
    }

    private JButton sidebarButton(String text) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        btn.setBackground(new Color(60, 60, 60));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(80, 80, 80), 1, true),
            new EmptyBorder(6, 10, 6, 10)
        ));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(80, 130, 80)); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(new Color(60, 60, 60)); }
        });

        btn.addActionListener(e -> handleAction(text));
        return btn;
    }

    private JPanel buildContent() {
        JPanel content = new JPanel(new BorderLayout(0, 8));
        content.setBackground(new Color(30, 30, 30));

        // Workout table
        String[] cols = {"Date", "Lifting Volume (kg)", "Cardio (min)", "Body Weight (kg)"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(tableModel);
        styleTable(table);
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setPreferredSize(new Dimension(0, 260));
        tableScroll.getViewport().setBackground(new Color(45, 45, 45));
        tableScroll.setBorder(titledBorder("Workout Records"));

        // Output log
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setBackground(new Color(20, 20, 20));
        outputArea.setForeground(new Color(180, 230, 180));
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        outputArea.setMargin(new Insets(6, 8, 6, 8));
        JScrollPane logScroll = new JScrollPane(outputArea);
        logScroll.setBorder(titledBorder("Output Log"));

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tableScroll, logScroll);
        split.setDividerLocation(260);
        split.setBackground(new Color(30, 30, 30));
        split.setBorder(null);

        content.add(split, BorderLayout.CENTER);
        return content;
    }

    private void styleTable(JTable table) {
        table.setBackground(new Color(45, 45, 45));
        table.setForeground(Color.WHITE);
        table.setGridColor(new Color(70, 70, 70));
        table.setRowHeight(24);
        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        table.setSelectionBackground(new Color(70, 120, 70));
        table.getTableHeader().setBackground(new Color(35, 35, 35));
        table.getTableHeader().setForeground(new Color(100, 200, 100));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
    }

    private TitledBorder titledBorder(String title) {
        TitledBorder b = BorderFactory.createTitledBorder(
            new LineBorder(new Color(70, 70, 70), 1, true), title);
        b.setTitleColor(new Color(150, 150, 150));
        b.setTitleFont(new Font("SansSerif", Font.BOLD, 11));
        return b;
    }

    // ── Action dispatcher ────────────────────────────────────────────────────

    private void handleAction(String action) {
        switch (action) {
            case "Add Workout":      showAddWorkoutDialog(); break;
            case "Search by Date":   showSearchDialog(); break;
            case "Range Query":      showRangeQueryDialog(); break;
            case "Load Mock Data":   showLoadMockDataDialog(); break;
            case "Run Benchmark":    runBenchmark(); break;
            case "Statistics":       displayStatistics(); break;
            case "Clear All Data":   clearAllData(); break;
        }
    }

    // ── Dialogs ──────────────────────────────────────────────────────────────

    private void showAddWorkoutDialog() {
        JTextField dateField    = new JTextField(LocalDate.now().toString());
        JTextField liftingField = new JTextField("0.0");
        JTextField cardioField  = new JTextField("0");
        JTextField weightField  = new JTextField("70.0");

        Object[] fields = {
            "Date (yyyy-MM-dd):", dateField,
            "Lifting Volume (kg):", liftingField,
            "Cardio Duration (min):", cardioField,
            "Body Weight (kg):", weightField
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Add Workout",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result != JOptionPane.OK_OPTION) return;

        try {
            LocalDate date = LocalDate.parse(dateField.getText().trim(), dateFormatter);
            double lifting = Double.parseDouble(liftingField.getText().trim());
            int cardio     = Integer.parseInt(cardioField.getText().trim());
            double weight  = Double.parseDouble(weightField.getText().trim());

            WorkoutNode workout = new WorkoutNode(date, lifting, cardio, weight);
            avlTree.insert(workout);
            bst.insert(workout);
            linkedList.insert(workout);

            addTableRow(workout);
            log("✓ Workout added: " + workout);
        } catch (DateTimeParseException ex) {
            showError("Invalid date format. Use yyyy-MM-dd.");
        } catch (NumberFormatException ex) {
            showError("Invalid number entered.");
        }
    }

    private void showSearchDialog() {
        String input = JOptionPane.showInputDialog(this,
            "Enter date to search (yyyy-MM-dd):", "Search Workout", JOptionPane.PLAIN_MESSAGE);
        if (input == null) return;

        try {
            LocalDate date = LocalDate.parse(input.trim(), dateFormatter);
            WorkoutNode result = avlTree.search(date);
            if (result != null) {
                log("Search result: " + result);
            } else {
                log("No workout found for " + date);
            }
        } catch (DateTimeParseException ex) {
            showError("Invalid date format. Use yyyy-MM-dd.");
        }
    }

    private void showRangeQueryDialog() {
        JTextField startField = new JTextField(LocalDate.now().minusDays(30).toString());
        JTextField endField   = new JTextField(LocalDate.now().toString());

        Object[] fields = {
            "Start date (yyyy-MM-dd):", startField,
            "End date (yyyy-MM-dd):",   endField
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Range Query",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return;

        try {
            LocalDate start = LocalDate.parse(startField.getText().trim(), dateFormatter);
            LocalDate end   = LocalDate.parse(endField.getText().trim(), dateFormatter);

            List<WorkoutNode> results = avlTree.rangeQuery(start, end);

            if (results.isEmpty()) {
                log("No workouts found between " + start + " and " + end);
                return;
            }

            double totalLifting = 0;
            int totalCardio = 0;
            double totalWeight = 0;

            StringBuilder sb = new StringBuilder();
            sb.append("Range Query [").append(start).append(" → ").append(end)
              .append("] — ").append(results.size()).append(" result(s):\n");

            for (WorkoutNode w : results) {
                sb.append("  ").append(w).append("\n");
                totalLifting += w.getLiftingVolume();
                totalCardio  += w.getCardioDuration();
                totalWeight  += w.getBodyWeight();
            }

            sb.append(String.format("  Summary → Total lifting: %.2f kg | Total cardio: %d min | Avg weight: %.2f kg",
                totalLifting, totalCardio, totalWeight / results.size()));

            log(sb.toString());
        } catch (DateTimeParseException ex) {
            showError("Invalid date format. Use yyyy-MM-dd.");
        }
    }

    private void showLoadMockDataDialog() {
        String[] types = {"Chronological", "Progressive", "Sparse"};
        JComboBox<String> typeBox = new JComboBox<>(types);
        JTextField daysField = new JTextField("100");

        Object[] fields = {"Data type:", typeBox, "Number of days:", daysField};

        int result = JOptionPane.showConfirmDialog(this, fields, "Load Mock Data",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return;

        try {
            int numDays = Integer.parseInt(daysField.getText().trim());
            if (numDays <= 0) { showError("Number of days must be positive."); return; }

            MockDataGenerator gen = new MockDataGenerator();
            LocalDate startDate = LocalDate.now().minusDays(numDays);
            List<WorkoutNode> data;

            switch (typeBox.getSelectedIndex()) {
                case 1:  data = gen.generateProgressiveData(numDays, startDate); break;
                case 2:  data = gen.generateSparseData(numDays, startDate, 0.7); break;
                default: data = gen.generateChronologicalData(numDays, startDate);
            }

            clearAllData();
            tableModel.setRowCount(0);

            for (WorkoutNode w : data) {
                avlTree.insert(w);
                bst.insert(w);
                linkedList.insert(w);
                addTableRow(w);
            }

            double totalLifting = 0, totalWeight = 0;
            int totalCardio = 0;
            for (WorkoutNode w : data) {
                totalLifting += w.getLiftingVolume();
                totalCardio  += w.getCardioDuration();
                totalWeight  += w.getBodyWeight();
            }

            log(String.format(
                "✓ Loaded %d %s workout(s).\n  Date range: %s → %s\n  Total lifting: %.2f kg | Total cardio: %d min | Avg weight: %.2f kg",
                data.size(), types[typeBox.getSelectedIndex()],
                data.get(0).getDate(), data.get(data.size() - 1).getDate(),
                totalLifting, totalCardio, totalWeight / data.size()
            ));
        } catch (NumberFormatException ex) {
            showError("Invalid number of days.");
        }
    }

    private void runBenchmark() {
        if (avlTree.getSize() == 0) {
            log("No data loaded. Please load mock data first.");
            return;
        }

        LocalDate end   = LocalDate.now();
        LocalDate start = end.minusDays(30);
        LocalDate mid   = end.minusDays(15);

        List<PerformanceBenchmark.BenchmarkResult> results = new ArrayList<>();
        results.add(PerformanceBenchmark.benchmarkRangeQuery(avlTree,    start, end, "AVL Tree"));
        results.add(PerformanceBenchmark.benchmarkRangeQuery(bst,        start, end, "BST"));
        results.add(PerformanceBenchmark.benchmarkRangeQuery(linkedList, start, end, "Linked List"));
        results.add(PerformanceBenchmark.benchmarkSearch(avlTree,    mid, "AVL Tree"));
        results.add(PerformanceBenchmark.benchmarkSearch(bst,        mid, "BST"));
        results.add(PerformanceBenchmark.benchmarkSearch(linkedList, mid, "Linked List"));

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-15s %-15s %-20s%n", "Structure", "Operation", "Time (ns)"));
        sb.append("-".repeat(52)).append("\n");
        for (PerformanceBenchmark.BenchmarkResult r : results) {
            sb.append(String.format("%-15s %-15s %-20d%n", r.structureName, r.operation, r.durationNanos));
        }

        log("── Benchmark Results ──\n" + sb);
    }

    private void displayStatistics() {
        log(String.format(
            "── Statistics ──\n  AVL Tree size:   %d\n  BST size:        %d\n  Linked List size:%d",
            avlTree.getSize(), bst.getSize(), linkedList.getSize()
        ));
    }

    private void clearAllData() {
        avlTree.clear();
        bst.clear();
        linkedList.clear();
        tableModel.setRowCount(0);
        log("✓ All data cleared.");
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private void addTableRow(WorkoutNode w) {
        tableModel.addRow(new Object[]{
            w.getDate().toString(),
            String.format("%.2f", w.getLiftingVolume()),
            w.getCardioDuration(),
            String.format("%.2f", w.getBodyWeight())
        });
    }

    private void log(String message) {
        outputArea.append(message + "\n\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
