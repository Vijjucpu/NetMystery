import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class NetMystery {
    private static JFrame frame;
    private static JPanel mainPanel;
    private static CardLayout cardLayout;

    private static int currentCase = 0;
    private static int score = 0;
    private static int correctAnswers = 0;
    private static boolean toolsOpenedFromCase = false;
    private static final int[] selectedAnswers = {-1, -1, -1, -1, -1};
    private static final boolean[] checked = {false, false, false, false, false};

    private static JLabel counterLabel, caseTitleLabel, descriptionLabel, scoreLabel;
    private static JLabel[] evidenceLabels = new JLabel[3];
    private static JRadioButton[] radios = new JRadioButton[3];
    private static ButtonGroup choiceGroup;
    private static JPanel feedbackPanel;
    private static JButton actionButton, previousButton, nextButton;
    private static JProgressBar progressBar;
    private static JComboBox<String> toolCommandChoice;
    private static JTextField toolTargetField;
    private static JTextArea toolOutputArea;
    private static JLabel toolRecommendationLabel;

    private static final Color BG = new Color(13, 22, 42);
    private static final Color PANEL = new Color(24, 38, 65);
    private static final Color ACCENT = new Color(64, 145, 255);
    private static final Color TEXT = new Color(235, 242, 255);
    private static final Color MUTED = new Color(165, 181, 207);
    private static final Color GREEN = new Color(60, 200, 145);
    private static final Color RED = new Color(255, 115, 115);

    private static final String[] titles = {
        "Website Not Opening",
        "Domain Name Not Resolving",
        "Network Connection Failure",
        "Slow Network Connection",
        "Incorrect Network Route"
    };

    private static final String[] descriptions = {
        "A website cannot be reached even though the computer is connected.",
        "A domain name cannot be translated into its IP address.",
        "The computer cannot communicate with its network gateway.",
        "The network responds, but communication is unusually slow.",
        "The destination cannot be reached through the expected route."
    };

    private static final String[][] evidence = {
        {"Computer connection: Working", "DNS lookup: Successful", "Website server: Not responding"},
        {"Computer connection: Working", "DNS lookup: Failed", "Website server: Not tested"},
        {"Computer connection: Failed", "Gateway response: Unavailable", "DNS lookup: Not tested"},
        {"Gateway: Responding", "Response time: High", "Packet loss: Detected"},
        {"Destination: Not reached", "Route trace: Stops at an intermediate hop", "Alternative route: Not tested"}
    };

    private static final String[][] options = {
        {"DNS configuration problem", "Website server may be unavailable", "Computer has no network connection"},
        {"Domain name resolution problem", "Website server is definitely down", "File transfer problem"},
        {"Mail server problem", "Computer network connection problem", "Website content problem"},
        {"Network delay or packet loss", "DNS always returns the wrong address", "The keyboard is disconnected"},
        {"A possible routing or path problem", "The website has no images", "The computer needs a new username"}
    };

    // Correct option index for each case (0 = first option).
    private static final int[] answers = {1, 0, 1, 0, 0};

    private static final String[] explanations = {
        "DNS and computer connectivity work, but the website server is not responding. The server may be unavailable, though other network problems are possible.",
        "DNS resolution failed. Check the domain name and DNS settings, then test whether the DNS server is reachable.",
        "The computer cannot reach its gateway. Check the network connection, IP configuration, and gateway availability.",
        "High response time and packet loss may indicate congestion or a weak link. Repeat the tests to confirm the problem.",
        "The route trace stops at an intermediate hop. Investigate routing and connectivity; some routers may simply block trace responses."
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("NetMystery | Network Intelligence Lab");
            frame.setSize(1000, 700);
            frame.setMinimumSize(new Dimension(850, 600));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            cardLayout = new CardLayout();
            mainPanel = new JPanel(cardLayout);
            mainPanel.setBackground(BG);

            mainPanel.add(createHome(), "home");
            mainPanel.add(createCaseScreen(), "case");
            mainPanel.add(createResultScreen(), "result");
            mainPanel.add(createToolsScreen(), "tools");

            frame.setContentPane(mainPanel);
            cardLayout.show(mainPanel, "home");
            frame.setVisible(true);
        });
    }

    private static JLabel label(String text, int size, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, size));
        label.setForeground(color);
        return label;
    }

    private static JButton button(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(ACCENT);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(11, 18, 11, 18));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private static JPanel createHome() {
        JPanel page = new JPanel(new BorderLayout(20, 20));
        page.setBackground(BG);
        page.setBorder(new EmptyBorder(30, 40, 30, 40));
        page.add(label("NETMYSTERY  /  NETWORK INTELLIGENCE LAB", 14, ACCENT), BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.add(Box.createVerticalGlue());

        JLabel icon = label("◈", 58, ACCENT);
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(icon);

        JLabel title = label("NetMystery", 42, TEXT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 42));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(title);

        JLabel subtitle = label("Intelligent Network Fault Diagnosis Simulator", 19, MUTED);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(Box.createVerticalStrut(12));
        center.add(subtitle);

        JLabel description = label("Investigate evidence. Identify faults. Learn networking.", 15, MUTED);
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(Box.createVerticalStrut(10));
        center.add(description);

        JPanel features = new JPanel(new GridLayout(1, 3, 14, 0));
        features.setOpaque(false);
        features.setMaximumSize(new Dimension(720, 95));
        features.setBorder(new EmptyBorder(30, 0, 20, 0));
        features.add(featureCard("05", "Investigation cases"));
        features.add(featureCard("10", "Points per correct answer"));
        features.add(featureCard("50", "Maximum score"));
        center.add(Box.createVerticalStrut(18));
        center.add(features);

        JButton start = button("START INVESTIGATION  →");
        start.setAlignmentX(Component.CENTER_ALIGNMENT);
        start.addActionListener(e -> {
            currentCase = 0;
            score = 0;
            correctAnswers = 0;
            for (int i = 0; i < selectedAnswers.length; i++) {
                selectedAnswers[i] = -1;
                checked[i] = false;
            }
            showCase();
        });
        center.add(Box.createVerticalStrut(18));
        center.add(start);

        JButton tools = button("OPEN NETWORKING TOOLS");
        tools.setAlignmentX(Component.CENTER_ALIGNMENT);
        tools.addActionListener(e -> {
            toolsOpenedFromCase = false;
            cardLayout.show(mainPanel, "tools");
        });
        center.add(Box.createVerticalStrut(12));
        center.add(tools);
        center.add(Box.createVerticalGlue());
        page.add(center, BorderLayout.CENTER);

        JLabel footer = label("CBP PROJECT  •  JAVA SWING  •  EDUCATIONAL SIMULATION", 12, MUTED);
        footer.setHorizontalAlignment(SwingConstants.CENTER);
        page.add(footer, BorderLayout.SOUTH);
        return page;
    }

    private static JPanel featureCard(String number, String description) {
        JPanel card = new JPanel(new GridLayout(2, 1, 5, 5));
        card.setBackground(PANEL);
        card.setBorder(new CompoundBorder(
            new LineBorder(new Color(48, 69, 105), 1, true),
            new EmptyBorder(12, 10, 12, 10)
        ));
        JLabel n = label(number, 23, ACCENT);
        n.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel d = label(description, 12, MUTED);
        d.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(n);
        card.add(d);
        return card;
    }

    private static JPanel createCaseScreen() {
        JPanel page = new JPanel(new BorderLayout(18, 18));
        page.setBackground(BG);
        page.setBorder(new EmptyBorder(24, 30, 24, 30));
        page.add(label("NETMYSTERY  /  INVESTIGATION", 14, ACCENT), BorderLayout.NORTH);

        JPanel body = new JPanel();
        body.setOpaque(false);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        JPanel progressPanel = new JPanel(new BorderLayout(10, 5));
        progressPanel.setOpaque(false);
        counterLabel = label("", 14, MUTED);
        progressBar = new JProgressBar(0, titles.length);
        progressBar.setStringPainted(false);
        progressBar.setForeground(ACCENT);
        progressBar.setBackground(PANEL);
        progressPanel.add(counterLabel, BorderLayout.WEST);
        progressPanel.add(progressBar, BorderLayout.CENTER);
        body.add(progressPanel);
        body.add(Box.createVerticalStrut(16));

        JPanel titleCard = new JPanel();
        titleCard.setBackground(PANEL);
        titleCard.setLayout(new BoxLayout(titleCard, BoxLayout.Y_AXIS));
        titleCard.setBorder(new CompoundBorder(
            new LineBorder(new Color(48, 69, 105), 1, true),
            new EmptyBorder(18, 20, 18, 20)
        ));
        caseTitleLabel = label("", 25, TEXT);
        caseTitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 25));
        descriptionLabel = label("", 15, MUTED);
        titleCard.add(caseTitleLabel);
        titleCard.add(Box.createVerticalStrut(8));
        titleCard.add(descriptionLabel);
        body.add(titleCard);
        body.add(Box.createVerticalStrut(15));

        JPanel evidenceCard = new JPanel();
        evidenceCard.setBackground(PANEL);
        evidenceCard.setLayout(new BoxLayout(evidenceCard, BoxLayout.Y_AXIS));
        evidenceCard.setBorder(new CompoundBorder(
            new LineBorder(new Color(48, 69, 105), 1, true),
            new EmptyBorder(16, 20, 16, 20)
        ));
        evidenceCard.add(label("DIAGNOSTIC EVIDENCE", 13, ACCENT));
        for (int i = 0; i < evidenceLabels.length; i++) {
            evidenceLabels[i] = label("", 14, TEXT);
            evidenceCard.add(Box.createVerticalStrut(7));
            evidenceCard.add(evidenceLabels[i]);
        }
        body.add(evidenceCard);
        body.add(Box.createVerticalStrut(15));
        body.add(label("SELECT THE MOST LIKELY CAUSE", 13, ACCENT));
        body.add(Box.createVerticalStrut(10));

        JPanel choices = new JPanel(new GridLayout(3, 1, 8, 8));
        choices.setOpaque(false);
        choiceGroup = new ButtonGroup();
        for (int i = 0; i < radios.length; i++) {
            radios[i] = new JRadioButton();
            radios[i].setFont(new Font("Segoe UI", Font.PLAIN, 14));
            radios[i].setForeground(TEXT);
            radios[i].setBackground(PANEL);
            radios[i].setBorder(new EmptyBorder(8, 12, 8, 12));
            radios[i].setFocusPainted(false);
            radios[i].setOpaque(true);
            choiceGroup.add(radios[i]);
            choices.add(radios[i]);
        }
        body.add(choices);

        feedbackPanel = new JPanel(new BorderLayout());
        feedbackPanel.setBackground(PANEL);
        feedbackPanel.setBorder(new EmptyBorder(10, 12, 10, 12));
        body.add(Box.createVerticalStrut(10));
        body.add(feedbackPanel);
        page.add(body, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout(10, 0));
        bottom.setOpaque(false);
        scoreLabel = label("SCORE: 0 / 50", 15, GREEN);
        bottom.add(scoreLabel, BorderLayout.WEST);

        JPanel navigation = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        navigation.setOpaque(false);
        previousButton = button("← PREVIOUS");
        previousButton.addActionListener(e -> {
            if (currentCase > 0) {
                currentCase--;
                showCase();
            }
        });

        actionButton = button("CHECK DIAGNOSIS");
        actionButton.addActionListener(e -> handleAction());
        nextButton = button("NEXT CASE  →");
        nextButton.addActionListener(e -> goToNextCase());
        JButton suggestedToolButton = button("SUGGESTED TOOL  ↗");
        suggestedToolButton.addActionListener(e -> openSuggestedTool());
        navigation.add(suggestedToolButton);
        navigation.add(previousButton);
        navigation.add(actionButton);
        navigation.add(nextButton);
        bottom.add(navigation, BorderLayout.EAST);
        page.add(bottom, BorderLayout.SOUTH);

        return page;
    }

    private static void handleAction() {
        int selected = -1;
        for (int i = 0; i < radios.length; i++) {
            if (radios[i].isSelected()) {
                selected = i;
                break;
            }
        }

        if (selected == -1) {
            JOptionPane.showMessageDialog(frame,
                "Explore the case and its suggested tool first. Select an answer when you are ready.");
            return;
        }

        selectedAnswers[currentCase] = selected;
        checked[currentCase] = true;
        updateScore();
        showFeedback();
        feedbackPanel.revalidate();
        feedbackPanel.repaint();
    }

    private static void goToNextCase() {
        if (currentCase < titles.length - 1) {
            currentCase++;
            showCase();
        } else {
            updateResults();
            cardLayout.show(mainPanel, "result");
        }
    }

    private static void showCase() {
        counterLabel.setText("CASE " + (currentCase + 1) + " OF " + titles.length);
        caseTitleLabel.setText(titles[currentCase]);
        descriptionLabel.setText(descriptions[currentCase]);
        progressBar.setValue(currentCase + 1);
        scoreLabel.setText("SCORE: " + score + " / 50");

        for (int i = 0; i < evidenceLabels.length; i++) {
            evidenceLabels[i].setText("•  " + evidence[currentCase][i]);
        }

        choiceGroup.clearSelection();
        for (int i = 0; i < radios.length; i++) {
            radios[i].setText(options[currentCase][i]);
            radios[i].setEnabled(true);
            if (selectedAnswers[currentCase] == i) {
                radios[i].setSelected(true);
            }
        }

        feedbackPanel.removeAll();
        if (checked[currentCase]) {
            showFeedback();
            actionButton.setText("CHECK DIAGNOSIS");
        } else {
            feedbackPanel.add(label("Select an answer, then check your diagnosis.", 13, MUTED),
                              BorderLayout.CENTER);
            actionButton.setText("CHECK DIAGNOSIS");
        }

        previousButton.setEnabled(currentCase > 0);
        nextButton.setText(currentCase == titles.length - 1
            ? "VIEW RESULTS  →" : "NEXT CASE  →");
        feedbackPanel.revalidate();
        feedbackPanel.repaint();
        cardLayout.show(mainPanel, "case");
    }

    private static void showFeedback() {
        boolean correct = selectedAnswers[currentCase] == answers[currentCase];
        feedbackPanel.removeAll();

        JTextArea explanation = new JTextArea(
            (correct ? "CORRECT! +10 POINTS\n" : "INCORRECT. REVIEW THE EVIDENCE\n")
            + explanations[currentCase]
        );
        explanation.setLineWrap(true);
        explanation.setWrapStyleWord(true);
        explanation.setEditable(false);
        explanation.setFocusable(false);
        explanation.setBackground(PANEL);
        explanation.setForeground(correct ? GREEN : RED);
        explanation.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        explanation.setRows(3);
        feedbackPanel.add(explanation, BorderLayout.CENTER);
        feedbackPanel.revalidate();
        feedbackPanel.repaint();
    }

    private static void updateScore() {
        score = 0;
        correctAnswers = 0;
        for (int i = 0; i < answers.length; i++) {
            if (checked[i] && selectedAnswers[i] == answers[i]) {
                score += 10;
                correctAnswers++;
            }
        }
        scoreLabel.setText("SCORE: " + score + " / 50");
    }


    private static JPanel createToolsScreen() {
        JPanel page = new JPanel(new BorderLayout(18, 18));
        page.setBackground(BG);
        page.setBorder(new EmptyBorder(25, 30, 25, 30));

        page.add(label("NETMYSTERY  /  NETWORKING TOOLS", 14, ACCENT), BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        JLabel heading = label("Networking Command Simulator", 27, TEXT);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 27));
        center.add(heading);
        center.add(Box.createVerticalStrut(8));
        center.add(label("Choose a command to view an educational sample output.", 14, MUTED));
        center.add(Box.createVerticalStrut(8));
        toolRecommendationLabel = label("Choose a tool or open this screen from an investigation case.", 13, GREEN);
        center.add(toolRecommendationLabel);
        center.add(Box.createVerticalStrut(18));

        JPanel commandPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        commandPanel.setBackground(PANEL);
        commandPanel.setBorder(new EmptyBorder(8, 8, 8, 8));

        String[] commands = {"ping", "ipconfig", "nslookup", "tracert"};
        toolCommandChoice = new JComboBox<>(commands);
        toolCommandChoice.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        toolCommandChoice.setPreferredSize(new Dimension(170, 36));

        toolTargetField = new JTextField("example.com", 18);
        toolTargetField.setFont(new Font("Consolas", Font.PLAIN, 14));
        JButton run = button("SHOW SAMPLE OUTPUT");
        commandPanel.add(label("Command:", 14, TEXT));
        commandPanel.add(toolCommandChoice);
        commandPanel.add(label("Target:", 14, TEXT));
        commandPanel.add(toolTargetField);
        commandPanel.add(run);
        center.add(commandPanel);
        center.add(Box.createVerticalStrut(14));

        toolOutputArea = new JTextArea();
        JTextArea output = toolOutputArea;
        output.setEditable(false);
        output.setLineWrap(true);
        output.setWrapStyleWord(true);
        output.setFont(new Font("Consolas", Font.PLAIN, 14));
        output.setBackground(new Color(8, 14, 28));
        output.setForeground(new Color(190, 225, 255));
        output.setBorder(new EmptyBorder(15, 15, 15, 15));
        output.setText("Select a command and click SHOW SAMPLE OUTPUT.\n\nNote: These results are simulated for learning; no real command is executed.");
        JScrollPane scroll = new JScrollPane(output);
        scroll.setBorder(new LineBorder(new Color(48, 69, 105), 1, true));
        center.add(scroll);

        run.addActionListener(e -> {
            String command = (String) toolCommandChoice.getSelectedItem();
            String host = toolTargetField.getText().trim();
            if (host.isEmpty()) host = "example.com";

            String result;
            switch (command) {
                case "ping":
                    result = "C:\\\\> ping " + host
                        + "\n\nPinging " + host + " with 32 bytes of data:"
                        + "\nReply from 192.0.2.10: bytes=32 time=24ms TTL=54"
                        + "\nReply from 192.0.2.10: bytes=32 time=26ms TTL=54"
                        + "\nReply from 192.0.2.10: bytes=32 time=23ms TTL=54"
                        + "\n\nPing statistics for 192.0.2.10:"
                        + "\n    Packets: Sent = 3, Received = 3, Lost = 0 (0% loss)"
                        + "\n\nLearning note: ping checks whether a host responds. This is sample data.";
                    break;
                case "ipconfig":
                    result = "C:\\\\> ipconfig"
                        + "\n\nEthernet adapter Ethernet:"
                        + "\n   IPv4 Address . . . . . . : 192.168.1.25"
                        + "\n   Subnet Mask . . . . . . : 255.255.255.0"
                        + "\n   Default Gateway . . . . : 192.168.1.1"
                        + "\n\nLearning note: ipconfig displays this computer's IP configuration. These values are examples.";
                    break;
                case "nslookup":
                    result = "C:\\\\> nslookup " + host
                        + "\n\nServer:  resolver.example"
                        + "\nAddress: 192.0.2.53"
                        + "\n\nNon-authoritative answer:"
                        + "\nName:    " + host
                        + "\nAddress: 192.0.2.10"
                        + "\n\nLearning note: nslookup asks DNS for name-resolution information. This is sample data.";
                    break;
                default:
                    result = "C:\\\\> tracert " + host
                        + "\n\nTracing route to " + host + " over a maximum of 5 hops:"
                        + "\n  1     2 ms     1 ms     2 ms  192.0.2.1"
                        + "\n  2    12 ms    11 ms    13 ms  198.51.100.1"
                        + "\n  3    24 ms    25 ms    23 ms  192.0.2.10"
                        + "\n\nTrace complete."
                        + "\n\nLearning note: tracert shows the route hops in a trace. This is simulated output.";
                    break;
            }
            // When opened from an investigation case, show evidence tailored to that case.
            // All outputs remain simulated educational examples; no real command is executed.
            if (toolsOpenedFromCase) {
                switch (currentCase) {
                    case 0:
                        result = "C:\\\\> ping " + host
                            + "\n\nPinging " + host + " with 32 bytes of data:"
                            + "\nReply from 192.0.2.10: bytes=32 time=25ms TTL=54"
                            + "\nReply from 192.0.2.10: bytes=32 time=24ms TTL=54"
                            + "\nReply from 192.0.2.10: bytes=32 time=26ms TTL=54"
                            + "\n\nPing statistics: Sent = 3, Received = 3, Lost = 0 (0% loss)"
                            + "\n\nCase evidence: The host responds, so basic connectivity appears to work."
                            + "\nThe case also reports that the website server is not responding."
                            + "\nDiagnosis hint: the website server may be unavailable."
                            + "\n\nSIMULATED SAMPLE — not a live network test.";
                        break;
                    case 1:
                        result = "C:\\\\> nslookup " + host
                            + "\n\nServer:  sample-dns-server"
                            + "\nAddress: 192.0.2.53"
                            + "\n\n*** sample-dns-server can't find " + host + ": Non-existent domain"
                            + "\n\nCase evidence: The sample DNS lookup failed to find an address for the domain."
                            + "\nDiagnosis hint: check the domain spelling and DNS configuration."
                            + "\n\nSIMULATED SAMPLE — not a live DNS lookup.";
                        break;
                    case 2:
                        result = "C:\\\\> ipconfig"
                            + "\n\nEthernet adapter Ethernet:"
                            + "\n   IPv4 Address . . . . . . : 169.254.10.20"
                            + "\n   Subnet Mask . . . . . . : 255.255.0.0"
                            + "\n   Default Gateway . . . . :"
                            + "\n\nCase evidence: No default gateway is shown in this sample configuration."
                            + "\nDiagnosis hint: check the network connection and IP configuration."
                            + "\n\nSIMULATED SAMPLE — example values only.";
                        break;
                    case 3:
                        result = "C:\\\\> ping " + host
                            + "\n\nPinging " + host + " with 32 bytes of data:"
                            + "\nReply from 192.0.2.10: bytes=32 time=185ms TTL=54"
                            + "\nRequest timed out."
                            + "\nReply from 192.0.2.10: bytes=32 time=210ms TTL=54"
                            + "\n\nPing statistics: Sent = 3, Received = 2, Lost = 1 (33% loss)"
                            + "\n\nCase evidence: High response times and packet loss appear in this sample."
                            + "\nDiagnosis hint: repeat the test; congestion or a weak link may be involved."
                            + "\n\nSIMULATED SAMPLE — not a live network test.";
                        break;
                    default:
                        result = "C:\\\\> tracert " + host
                            + "\n\nTracing route to " + host + " over a maximum of 5 hops:"
                            + "\n  1     2 ms     2 ms     1 ms  192.0.2.1"
                            + "\n  2    12 ms    11 ms    13 ms  198.51.100.1"
                            + "\n  3     *        *        *     Request timed out."
                            + "\n\nTrace stopped responding at an intermediate hop."
                            + "\nCase evidence: the destination was not reached in this sample."
                            + "\nDiagnosis hint: investigate the route, but remember some routers block trace responses."
                            + "\n\nSIMULATED SAMPLE — not a live route trace.";
                        break;
                }
            }
            toolOutputArea.setText(result);
            toolOutputArea.setCaretPosition(0);
        });

        center.add(Box.createVerticalStrut(10));
        JLabel note = label("Educational simulator only — sample outputs, not live network results.", 12, MUTED);
        center.add(note);
        page.add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.setOpaque(false);
        JButton back = button("← BACK TO CASE");
        back.addActionListener(e -> {
            if (toolsOpenedFromCase) {
                showCase(); // Restore the current case and its saved answer/feedback.
                cardLayout.show(mainPanel, "case");
            } else {
                cardLayout.show(mainPanel, "home");
            }
        });
        bottom.add(back);
        page.add(bottom, BorderLayout.SOUTH);
        return page;
    }

    private static JPanel createResultScreen() {
        JPanel page = new JPanel(new BorderLayout(20, 20));
        page.setBackground(BG);
        page.setBorder(new EmptyBorder(35, 45, 35, 45));
        page.add(label("NETMYSTERY  /  FINAL REPORT", 14, ACCENT), BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        JLabel heading = label("Investigation Complete", 34, TEXT);
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel resultScore = label("", 24, GREEN);
        resultScore.setName("resultScore");
        resultScore.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel correctText = label("", 17, MUTED);
        correctText.setName("resultCorrect");
        correctText.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel message = label("", 16, TEXT);
        message.setName("resultMessage");
        message.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea report = new JTextArea();
        report.setName("detailedReport");
        report.setEditable(false);
        report.setLineWrap(true);
        report.setWrapStyleWord(true);
        report.setFont(new Font("Consolas", Font.PLAIN, 13));
        report.setBackground(PANEL);
        report.setForeground(TEXT);
        report.setBorder(new EmptyBorder(12, 14, 12, 14));
        JScrollPane reportScroll = new JScrollPane(report);
        reportScroll.setBorder(BorderFactory.createLineBorder(ACCENT));
        reportScroll.setPreferredSize(new Dimension(760, 250));

        center.add(heading);
        center.add(Box.createVerticalStrut(12));
        center.add(resultScore);
        center.add(Box.createVerticalStrut(6));
        center.add(correctText);
        center.add(Box.createVerticalStrut(8));
        center.add(message);
        center.add(Box.createVerticalStrut(12));
        center.add(reportScroll);
        page.add(center, BorderLayout.CENTER);

        JButton restart = button("TRY AGAIN");
        restart.addActionListener(e -> {
            currentCase = 0;
            score = 0;
            correctAnswers = 0;
            for (int i = 0; i < selectedAnswers.length; i++) {
                selectedAnswers[i] = -1;
                checked[i] = false;
            }
            showCase();
        });
        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.add(restart);
        page.add(bottom, BorderLayout.SOUTH);
        return page;
    }

    private static void openSuggestedTool() {
        toolsOpenedFromCase = true;
        String command;
        String explanation;
        switch (currentCase) {
            case 0:
                command = "ping";
                explanation = "Suggested for Case 1: ping can test whether the website host responds. A failed response alone does not prove the server is down.";
                break;
            case 1:
                command = "nslookup";
                explanation = "Suggested for Case 2: nslookup checks whether DNS can find an IP address for the domain.";
                break;
            case 2:
                command = "ipconfig";
                explanation = "Suggested for Case 3: ipconfig helps inspect the computer's IP address and default gateway. You can also use ping to test a gateway.";
                break;
            case 3:
                command = "ping";
                explanation = "Suggested for Case 4: ping sample response times and packet loss can help explain a slow connection.";
                break;
            default:
                command = "tracert";
                explanation = "Suggested for Case 5: tracert illustrates the network hops toward a destination. Some routers may not reply to trace probes.";
                break;
        }
        toolCommandChoice.setSelectedItem(command);
        toolTargetField.setText("example.com");
        toolRecommendationLabel.setText(explanation);
        toolOutputArea.setText("Suggested tool: " + command + "\nClick SHOW SAMPLE OUTPUT to view the simulated result.\n\nEducational example only — no real network command is executed.");
        toolOutputArea.setCaretPosition(0);
        cardLayout.show(mainPanel, "tools");
    }

    private static void updateResults() {
        updateScore();
        JPanel page = (JPanel) mainPanel.getComponent(2);
        setText(page, "resultScore", "FINAL SCORE: " + score + " / 50");
        setText(page, "resultCorrect", "Correct answers: " + correctAnswers + " / " + titles.length);

        String message = correctAnswers == 5
            ? "Excellent! You solved every network mystery."
            : correctAnswers >= 3
                ? "Good effort! Review the explanations to improve."
                : "Keep practising! Review each fault and its evidence.";
        setText(page, "resultMessage", message);

        Component reportComponent = find(page, "detailedReport");
        if (reportComponent instanceof JTextArea) {
            StringBuilder report = new StringBuilder("CASE HISTORY / INVESTIGATION DETAILS\n\n");
            for (int i = 0; i < titles.length; i++) {
                report.append("CASE ").append(i + 1).append(": ").append(titles[i]).append("\n");
                if (!checked[i]) {
                    report.append("Status: Not answered\n");
                } else {
                    boolean correct = selectedAnswers[i] == answers[i];
                    report.append("Your answer: ").append(options[i][selectedAnswers[i]]).append("\n");
                    report.append("Result: ").append(correct ? "Correct (+10 points)" : "Incorrect (0 points)").append("\n");
                }
                report.append("Expected diagnosis: ").append(options[i][answers[i]]).append("\n");
                report.append("Explanation: ").append(explanations[i]).append("\n\n");
            }
            report.append("FINAL SUMMARY: ").append(correctAnswers).append(" correct out of ").append(titles.length)
                  .append("; total score ").append(score).append(" / 50.\n")
                  .append("Note: networking command output is simulated for educational use.");
            ((JTextArea) reportComponent).setText(report.toString());
            ((JTextArea) reportComponent).setCaretPosition(0);
        }
    }

    private static void setText(Container parent, String name, String text) {
        Component component = find(parent, name);
        if (component instanceof JLabel) {
            ((JLabel) component).setText(text);
        }
    }

    private static Component find(Container parent, String name) {
        for (Component component : parent.getComponents()) {
            if (name.equals(component.getName())) {
                return component;
            }
            if (component instanceof Container) {
                Component found = find((Container) component, name);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }
}
