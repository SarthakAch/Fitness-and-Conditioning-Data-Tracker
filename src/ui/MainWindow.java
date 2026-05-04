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
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.GradientPaint;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * MainWindow - Clean Figma-style fitness UI, red accent palette
 */
public class MainWindow extends JFrame {

    // ── Palette (Pump House inspired — coral/hot-pink fitness) ───────────────
    private static final Color BG_PAGE     = new Color(248, 248, 250);   // near-white page
    private static final Color BG_CARD     = Color.WHITE;
    private static final Color BG_SIDEBAR  = Color.WHITE;
    private static final Color PINK        = new Color(255,  77, 109);   // coral-pink primary
    private static final Color PINK_LIGHT  = new Color(255, 228, 234);   // soft pink tint
    private static final Color PINK_MID    = new Color(255, 120, 145);   // medium pink
    private static final Color PINK_DARK   = new Color(200,  30,  70);   // deep rose
    private static final Color GRAD_TOP    = new Color(255,  77, 109);   // gradient start
    private static final Color GRAD_BOT    = new Color(180,  30,  80);   // gradient end
    private static final Color TEXT_DARK   = new Color(17,   24,  39);   // near-black
    private static final Color TEXT_MUTED  = new Color(107, 114, 128);   // grey
    private static final Color BORDER_CLR  = new Color(229, 231, 235);   // light border
    private static final Color ROW_ALT     = new Color(255, 245, 247);   // pink-tinted alt row

    // Alias so existing references still compile
    private static final Color RED_PRIMARY = PINK;
    private static final Color RED_LIGHT   = PINK_LIGHT;
    private static final Color RED_MID     = PINK_MID;
    private static final Color RED_DARK    = PINK_DARK;

    private AVLTree avlTree;
    private UnbalancedBST bst;
    private SinglyLinkedList linkedList;
    private final DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private JTextArea outputArea;
    private DefaultTableModel tableModel;

    // Stat card value labels — kept as fields so they update live
    private JLabel statAvl;
    private JLabel statBst;
    private JLabel statList;

    public MainWindow() {
        try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); }
        catch (Exception ignored) {}

        avlTree    = new AVLTree();
        bst        = new UnbalancedBST();
        linkedList = new SinglyLinkedList();

        setTitle("FitTrack — Conditioning Data Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1060, 700);
        setMinimumSize(new Dimension(860, 580));
        setLocationRelativeTo(null);
        setBackground(BG_PAGE);
        buildUI();
    }

    // ── Layout ───────────────────────────────────────────────────────────────

    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(BG_PAGE);
        root.add(buildHeader(),  BorderLayout.NORTH);
        root.add(buildSidebar(), BorderLayout.WEST);
        root.add(buildContent(), BorderLayout.CENTER);
        setContentPane(root);
    }

    // ── Header (gradient banner) ─────────────────────────────────────────────

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, GRAD_TOP, getWidth(), 0, GRAD_BOT);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(16, 24, 16, 24));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setOpaque(false);

        // White dumbbell badge
        JLabel badge = new JLabel("🏋") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 50));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        badge.setFont(new Font("SansSerif", Font.PLAIN, 20));
        badge.setPreferredSize(new Dimension(38, 34));
        badge.setHorizontalAlignment(SwingConstants.CENTER);
        badge.setOpaque(false);

        JPanel titles = new JPanel();
        titles.setLayout(new BoxLayout(titles, BoxLayout.Y_AXIS));
        titles.setOpaque(false);

        JLabel title = new JLabel("FitTrack");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(Color.WHITE);

        JLabel sub = new JLabel("Conditioning Data Tracker");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        sub.setForeground(new Color(255, 255, 255, 180));

        titles.add(title);
        titles.add(sub);

        left.add(badge);
        left.add(titles);

        // Right side pill tag
        JLabel tag = new JLabel("AVL  •  BST  •  Linked List") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 40));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        tag.setForeground(Color.WHITE);
        tag.setFont(new Font("SansSerif", Font.PLAIN, 11));
        tag.setBorder(new EmptyBorder(5, 14, 5, 14));
        tag.setOpaque(false);

        header.add(left, BorderLayout.WEST);
        header.add(tag,  BorderLayout.EAST);
        return header;
    }

    // ── Sidebar ──────────────────────────────────────────────────────────────

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(BG_SIDEBAR);
        sidebar.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0, 0, 0, 1, BORDER_CLR),
            new EmptyBorder(24, 14, 24, 14)
        ));
        sidebar.setPreferredSize(new Dimension(200, 0));

        JLabel nav = new JLabel("NAVIGATION");
        nav.setForeground(TEXT_MUTED);
        nav.setFont(new Font("SansSerif", Font.BOLD, 10));
        nav.setAlignmentX(Component.LEFT_ALIGNMENT);
        nav.setBorder(new EmptyBorder(0, 6, 12, 0));
        sidebar.add(nav);

        String[][] items = {
            {"", "Add Workout"},
            {"", "Search by Date"},
            {"", "Range Query"},
            {"", "Load Mock Data"},
            {"", "Run Benchmark"},
            {"", "Statistics"},
            {"", "Clear All Data"}
        };

        for (String[] item : items) {
            sidebar.add(navButton(item[0], item[1]));
            sidebar.add(Box.createVerticalStrut(4));
        }

        sidebar.add(Box.createVerticalGlue());
        return sidebar;
    }

    private JButton navButton(String icon, String label) {
        JButton btn = new JButton(label) {
            private boolean hovered = false;
            {
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hovered = true;  repaint(); }
                    public void mouseExited(MouseEvent e)  { hovered = false; repaint(); }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hovered ? PINK_MID : PINK);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setFont(new Font("Dialog", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new EmptyBorder(8, 14, 8, 14));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> handleAction(label));
        return btn;
    }

    // ── Main content ─────────────────────────────────────────────────────────

    private JPanel buildContent() {
        JPanel content = new JPanel(new BorderLayout(0, 16));
        content.setBackground(BG_PAGE);
        content.setBorder(new EmptyBorder(20, 20, 20, 20));

        content.add(buildStatCards(), BorderLayout.NORTH);

        // Table card
        String[] cols = {"Date", "Lifting Volume (kg)", "Cardio (min)", "Body Weight (kg)"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = buildTable();
        JScrollPane tableScroll = cleanScroll(table);

        // Log card
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setBackground(BG_CARD);
        outputArea.setForeground(TEXT_DARK);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        outputArea.setMargin(new Insets(12, 14, 12, 14));
        JScrollPane logScroll = cleanScroll(outputArea);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
            wrapCard("Workout Records", tableScroll),
            wrapCard("Output Log", logScroll));
        split.setDividerLocation(270);
        split.setBackground(BG_PAGE);
        split.setBorder(null);
        split.setDividerSize(8);

        content.add(split, BorderLayout.CENTER);
        return content;
    }

    private JPanel buildStatCards() {
        JPanel row = new JPanel(new GridLayout(1, 3, 14, 0));
        row.setOpaque(false);
        row.add(statCard("AVL Tree",    "0 records", PINK,      PINK_LIGHT));
        row.add(statCard("BST",         "0 records", PINK_MID,  new Color(255, 220, 228)));
        row.add(statCard("Linked List", "0 records", PINK_DARK, new Color(255, 200, 215)));
        return row;
    }

    private JPanel statCard(String title, String value, Color accent, Color tint) {
        JPanel card = new JPanel(new BorderLayout(0, 4)) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(18, 20, 18, 20));

        // Colored left accent bar
        JPanel bar = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, accent, 0, getHeight(), tint);
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 4, 4);
                g2.dispose();
            }
        };
        bar.setOpaque(false);
        bar.setPreferredSize(new Dimension(5, 0));

        JLabel lTitle = new JLabel(title.toUpperCase());
        lTitle.setFont(new Font("SansSerif", Font.BOLD, 10));
        lTitle.setForeground(TEXT_MUTED);

        JLabel lValue = new JLabel(value);
        lValue.setFont(new Font("SansSerif", Font.BOLD, 28));
        lValue.setForeground(accent);

        JPanel text = new JPanel(new BorderLayout(0, 4));
        text.setOpaque(false);
        text.setBorder(new EmptyBorder(0, 14, 0, 0));
        text.add(lTitle,  BorderLayout.NORTH);
        text.add(lValue,  BorderLayout.CENTER);

        card.add(bar,  BorderLayout.WEST);
        card.add(text, BorderLayout.CENTER);

        // Shadow wrapper
        JPanel wrapper = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 77, 109, 20));
                g2.fillRoundRect(3, 5, getWidth() - 4, getHeight() - 4, 16, 16);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        wrapper.setOpaque(false);
        wrapper.add(card);
        return wrapper;
    }

    private JTable buildTable() {
        JTable table = new JTable(tableModel);
        table.setBackground(BG_CARD);
        table.setForeground(TEXT_DARK);
        table.setGridColor(BORDER_CLR);
        table.setRowHeight(32);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.setSelectionBackground(RED_LIGHT);
        table.setSelectionForeground(RED_DARK);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(
                    JTable t, Object val, boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                setBackground(sel ? RED_LIGHT : (row % 2 == 0 ? BG_CARD : ROW_ALT));
                setForeground(sel ? RED_DARK : TEXT_DARK);
                setBorder(new EmptyBorder(0, 14, 0, 14));
                setFont(new Font("SansSerif", Font.PLAIN, 13));
                return this;
            }
        });

        JTableHeader header = table.getTableHeader();
        header.setBackground(ROW_ALT);
        header.setForeground(TEXT_MUTED);
        header.setFont(new Font("SansSerif", Font.BOLD, 11));
        header.setBorder(new MatteBorder(0, 0, 1, 0, BORDER_CLR));
        header.setReorderingAllowed(false);
        ((DefaultTableCellRenderer) header.getDefaultRenderer())
            .setBorder(new EmptyBorder(0, 14, 0, 14));

        return table;
    }

    private JScrollPane cleanScroll(Component c) {
        JScrollPane sp = new JScrollPane(c);
        sp.setBorder(null);
        sp.getViewport().setBackground(BG_CARD);
        sp.setBackground(BG_CARD);
        styleScrollBar(sp.getVerticalScrollBar());
        styleScrollBar(sp.getHorizontalScrollBar());
        return sp;
    }

    private void styleScrollBar(JScrollBar bar) {
        bar.setBackground(BG_CARD);
        bar.setUI(new BasicScrollBarUI() {
            @Override protected void configureScrollBarColors() {
                thumbColor = new Color(220, 38, 38, 80);
                trackColor = BG_CARD;
            }
            @Override protected JButton createDecreaseButton(int o) { return zeroBtn(); }
            @Override protected JButton createIncreaseButton(int o) { return zeroBtn(); }
            private JButton zeroBtn() {
                JButton b = new JButton();
                b.setPreferredSize(new Dimension(0, 0));
                return b;
            }
        });
    }

    private JPanel wrapCard(String title, JComponent inner) {
        JPanel card = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 14));
                g2.fillRoundRect(3, 4, getWidth() - 4, getHeight() - 4, 14, 14);
                g2.setColor(BG_CARD);
                g2.fillRoundRect(0, 0, getWidth() - 2, getHeight() - 2, 14, 14);
                g2.dispose();
            }
        };
        card.setOpaque(false);

        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("Dialog", Font.BOLD, 13));
        lbl.setForeground(TEXT_DARK);
        lbl.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0, 0, 1, 0, BORDER_CLR),
            new EmptyBorder(12, 16, 12, 16)
        ));
        lbl.setOpaque(false);

        card.add(lbl,   BorderLayout.NORTH);
        card.add(inner, BorderLayout.CENTER);
        return card;
    }

    // ── Actions ──────────────────────────────────────────────────────────────

    private void handleAction(String action) {
        switch (action) {
            case "Add Workout":    showAddWorkoutDialog();   break;
            case "Search by Date": showSearchDialog();       break;
            case "Range Query":    showRangeQueryDialog();   break;
            case "Load Mock Data": showLoadMockDataDialog(); break;
            case "Run Benchmark":  runBenchmark();           break;
            case "Statistics":     displayStatistics();      break;
            case "Clear All Data": clearAllData();           break;
        }
    }

    // ── Dialogs ──────────────────────────────────────────────────────────────

    private void showAddWorkoutDialog() {
        JTextField dateF    = field(LocalDate.now().toString());
        JTextField liftingF = field("0.0");
        JTextField cardioF  = field("0");
        JTextField weightF  = field("70.0");

        JPanel form = form(
            new String[]{"Date (yyyy-MM-dd)", "Lifting Volume (kg)", "Cardio Duration (min)", "Body Weight (kg)"},
            new JTextField[]{dateF, liftingF, cardioF, weightF}
        );

        if (dialog(form, "Add Workout") != JOptionPane.OK_OPTION) return;
        try {
            LocalDate date = LocalDate.parse(dateF.getText().trim(), dateFmt);
            double lifting = Double.parseDouble(liftingF.getText().trim());
            int cardio     = Integer.parseInt(cardioF.getText().trim());
            double weight  = Double.parseDouble(weightF.getText().trim());

            WorkoutNode w = new WorkoutNode(date, lifting, cardio, weight);
            avlTree.insert(w); bst.insert(w); linkedList.insert(w);
            addRow(w);
            log("✓ Workout added: " + w);
        } catch (DateTimeParseException ex) { err("Invalid date. Use yyyy-MM-dd."); }
          catch (NumberFormatException ex)   { err("Invalid number entered."); }
    }

    private void showSearchDialog() {
        JTextField f = field(LocalDate.now().toString());
        if (dialog(form(new String[]{"Date (yyyy-MM-dd)"}, new JTextField[]{f}), "Search Workout") != JOptionPane.OK_OPTION) return;
        try {
            LocalDate date = LocalDate.parse(f.getText().trim(), dateFmt);
            WorkoutNode r = avlTree.search(date);
            log(r != null ? "Found: " + r : "No workout found for " + date);
        } catch (DateTimeParseException ex) { err("Invalid date. Use yyyy-MM-dd."); }
    }

    private void showRangeQueryDialog() {
        JTextField startF = field(LocalDate.now().minusDays(30).toString());
        JTextField endF   = field(LocalDate.now().toString());
        if (dialog(form(new String[]{"Start date (yyyy-MM-dd)", "End date (yyyy-MM-dd)"}, new JTextField[]{startF, endF}), "Range Query") != JOptionPane.OK_OPTION) return;
        try {
            LocalDate start = LocalDate.parse(startF.getText().trim(), dateFmt);
            LocalDate end   = LocalDate.parse(endF.getText().trim(), dateFmt);
            List<WorkoutNode> results = avlTree.rangeQuery(start, end);
            if (results.isEmpty()) { log("No workouts found between " + start + " and " + end); return; }
            double tl = 0, tw = 0; int tc = 0;
            StringBuilder sb = new StringBuilder();
            sb.append("Range [").append(start).append(" → ").append(end).append("] — ").append(results.size()).append(" result(s):\n");
            for (WorkoutNode w : results) {
                sb.append("  ").append(w).append("\n");
                tl += w.getLiftingVolume(); tc += w.getCardioDuration(); tw += w.getBodyWeight();
            }
            sb.append(String.format("  Total lifting: %.2f kg | Total cardio: %d min | Avg weight: %.2f kg", tl, tc, tw / results.size()));
            log(sb.toString());
        } catch (DateTimeParseException ex) { err("Invalid date. Use yyyy-MM-dd."); }
    }

    private void showLoadMockDataDialog() {
        String[] types = {"Chronological", "Progressive", "Sparse"};
        JComboBox<String> typeBox = new JComboBox<>(types);
        typeBox.setBackground(Color.WHITE); typeBox.setForeground(TEXT_DARK);
        typeBox.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JTextField daysF = field("100");

        JPanel form = new JPanel(new GridLayout(0, 2, 12, 10));
        form.setBackground(Color.WHITE);
        form.setBorder(new EmptyBorder(10, 10, 10, 10));
        form.add(label("Data type:")); form.add(typeBox);
        form.add(label("Number of days:")); form.add(daysF);

        if (dialog(form, "Load Mock Data") != JOptionPane.OK_OPTION) return;
        try {
            int numDays = Integer.parseInt(daysF.getText().trim());
            if (numDays <= 0) { err("Days must be positive."); return; }
            MockDataGenerator gen = new MockDataGenerator();
            LocalDate startDate = LocalDate.now().minusDays(numDays);
            List<WorkoutNode> data;
            switch (typeBox.getSelectedIndex()) {
                case 1:  data = gen.generateProgressiveData(numDays, startDate); break;
                case 2:  data = gen.generateSparseData(numDays, startDate, 0.7); break;
                default: data = gen.generateChronologicalData(numDays, startDate);
            }
            clearAllData();
            for (WorkoutNode w : data) { avlTree.insert(w); bst.insert(w); linkedList.insert(w); addRow(w); }
            double tl = 0, tw = 0; int tc = 0;
            for (WorkoutNode w : data) { tl += w.getLiftingVolume(); tc += w.getCardioDuration(); tw += w.getBodyWeight(); }
            log(String.format("✓ Loaded %d %s workout(s).\n  Range: %s → %s\n  Lifting: %.2f kg | Cardio: %d min | Avg weight: %.2f kg",
                data.size(), types[typeBox.getSelectedIndex()], data.get(0).getDate(), data.get(data.size()-1).getDate(), tl, tc, tw / data.size()));
        } catch (NumberFormatException ex) { err("Invalid number of days."); }
    }

    private void runBenchmark() {
        if (avlTree.getSize() == 0) { log("No data loaded. Load mock data first."); return; }
        LocalDate end = LocalDate.now(), start = end.minusDays(30), mid = end.minusDays(15);
        List<PerformanceBenchmark.BenchmarkResult> res = new ArrayList<>();
        res.add(PerformanceBenchmark.benchmarkRangeQuery(avlTree,    start, end, "AVL Tree"));
        res.add(PerformanceBenchmark.benchmarkRangeQuery(bst,        start, end, "BST"));
        res.add(PerformanceBenchmark.benchmarkRangeQuery(linkedList, start, end, "Linked List"));
        res.add(PerformanceBenchmark.benchmarkSearch(avlTree,    mid, "AVL Tree"));
        res.add(PerformanceBenchmark.benchmarkSearch(bst,        mid, "BST"));
        res.add(PerformanceBenchmark.benchmarkSearch(linkedList, mid, "Linked List"));
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-14s %-14s %s%n", "Structure", "Operation", "Time (ns)"));
        sb.append("─".repeat(46)).append("\n");
        for (PerformanceBenchmark.BenchmarkResult r : res)
            sb.append(String.format("%-14s %-14s %d%n", r.structureName, r.operation, r.durationNanos));
        log("⚡ Benchmark Results\n" + sb);
    }

    private void displayStatistics() {
        log(String.format("📊 Statistics\n  AVL Tree:    %d records\n  BST:         %d records\n  Linked List: %d records",
            avlTree.getSize(), bst.getSize(), linkedList.getSize()));
    }

    private void clearAllData() {
        avlTree.clear(); bst.clear(); linkedList.clear();
        tableModel.setRowCount(0);
        log("All data cleared.");
    }

    // ── Form / dialog helpers ─────────────────────────────────────────────────

    private JPanel form(String[] labels, JTextField[] fields) {
        JPanel p = new JPanel(new GridLayout(0, 2, 12, 10));
        p.setBackground(Color.WHITE);
        p.setBorder(new EmptyBorder(10, 10, 10, 10));
        for (int i = 0; i < labels.length; i++) { p.add(label(labels[i])); p.add(fields[i]); }
        return p;
    }

    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.PLAIN, 13));
        l.setForeground(TEXT_MUTED);
        return l;
    }

    private JTextField field(String val) {
        JTextField f = new JTextField(val);
        f.setFont(new Font("SansSerif", Font.PLAIN, 13));
        f.setForeground(TEXT_DARK);
        f.setBackground(Color.WHITE);
        f.setCaretColor(RED_PRIMARY);
        f.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_CLR, 1, true),
            new EmptyBorder(5, 10, 5, 10)
        ));
        return f;
    }

    private int dialog(JPanel content, String title) {
        UIManager.put("OptionPane.background",        Color.WHITE);
        UIManager.put("Panel.background",             Color.WHITE);
        UIManager.put("OptionPane.messageForeground", TEXT_DARK);
        UIManager.put("Button.background",            RED_PRIMARY);
        UIManager.put("Button.foreground",            Color.WHITE);
        UIManager.put("Button.font",                  new Font("Dialog", Font.BOLD, 13));
        UIManager.put("Button.border",                new EmptyBorder(7, 18, 7, 18));
        return JOptionPane.showConfirmDialog(this, content, title,
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    }

    private void err(String msg) {
        UIManager.put("OptionPane.background",        Color.WHITE);
        UIManager.put("Panel.background",             Color.WHITE);
        UIManager.put("OptionPane.messageForeground", RED_PRIMARY);
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void addRow(WorkoutNode w) {
        tableModel.addRow(new Object[]{
            w.getDate().toString(),
            String.format("%.2f", w.getLiftingVolume()),
            w.getCardioDuration(),
            String.format("%.2f", w.getBodyWeight())
        });
    }

    private void log(String msg) {
        outputArea.append(msg + "\n\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }
}
