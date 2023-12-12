package privat;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizApp extends JFrame {
    private JLabel questionLabel;
    private JCheckBox[] answerCheckboxes;
    private JButton nextButton;


    private final String[] questions = {
            "01. Welche Protokolle kennst du?",
            "02. Was sind Netzwerkgeräte?",
            "03. Mit welchem Windows CMD Befehl kann ein DNS Server abgefragt werden?",
            "04. Was bewirkt der Linux-Terminalbefehl 'traceroute'?",
            "05. Nenne die vierte OSI-Layer-Schicht!",
            "06. Welche Topologien kennst du?",
            "07. Welche Aussage trifft auf das TCP Transmission Control Protocol zu?",
            "08. Eine IP + Port nennt man:",
            "09. Wofür steht der Begriff 'MTU'?",
            "10. Einfach ausgedrückt: Was macht eine SYN-Flag?",
            "11. Welche Aufgabe übernimmt das CSMA/CA Protokoll?",
            "12. Was ergibt 2x2-2:2+(2x2)x2-(2:2+2:2):2?",
            "13. Was ist TTL?"

    };

    private final String[][] answers = {
            {"www", "TCP  ", "UDP  ", "DNA"},
            {"HUB  ", "Switch  ", "Twitch", "SUB"},
            {"netsh", "getdns", "nslookup  ", "tracert"},
            {"Anzeigen und Konfiguration von Netzwerkgeräten", "Routenverfolgung und Verbindungsanalyse  ",
                    "Werkzeug für WLAN-Schnittstellen", "Der Befehl bewirkt nichts"},
            {"Transport Layer  ", "Network Layer", "Session Layer", "Data Link Layer"},
            {"BUS  ", "BAHN", "RING  ", "RAD"},
            {"Ist ein verbindungsloses Protokoll", "Ist sehr unzuverlässig, dafür aber sehr schnell",
                    "Wird generell für gaming genutzt", "Versendet Datenpakete  "},
            {"Rocket", "Pocket", "Socket  ", "Hat keine Bezeichnung"},
            {"Master Transport Unit", "Most Transmission Upload", "Missed Transport Unit", "Maximum Transmission Unit  "},
            {"Eine Anfrage zur Kommunikation  ", "Eine Ablehnung zum Kommunikationsaufbau",
                    "Ein SYN-Flag, so einen Shit gibts nicht", "Die Antwort einer Kommunikations-Anfrage"},
            {"WTF, den bullshit gibts doch gar ned",
                    "Ein Backup vom RAM-Speicher, wenn das Gerät plötzlich ausfällt",
                    "Kollisionsvermeidung auf einem Übertragungskanal  ",
                    "Ist für die grafische Oberfläche eines Browsers verantwortlich"},
            {"4", "10  ", "8", "10,25"},
            {"Time To Lose: Legt fest, dass du deine Verbindung verlierst.",
                    "Transport To Limit: Legt die Übertragungsgeschwindigkeit fest.",
                    "Transmission Timer Legacy: Ist die größe der Einheit, die übermittelt wird.",
                    "Time To Live: Legt fest, wie viele \"Hops\" das Paket überleben darf  "}

    };

    private int currentQuestionIndex = 0;
    private int correctAnswers = 0;
    private int incorrectAnswers = 0;

    public QuizApp() {
        setTitle("Infrastruktur Quiz");
        setSize(600, 400);
        Font customFont = new Font("Arial", Font.PLAIN, 16);
        setFont(customFont);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createGUI();

        setVisible(true);
    }

    private void createGUI() {
        setLayout(new BorderLayout());

        questionLabel = new JLabel(questions[currentQuestionIndex]);
        questionLabel.setHorizontalAlignment(JLabel.CENTER);
        add(questionLabel, BorderLayout.NORTH);

        answerCheckboxes = new JCheckBox[4];
        JPanel answersPanel = new JPanel();
        answersPanel.setLayout(new BoxLayout(answersPanel, BoxLayout.Y_AXIS));

        for (int i = 0; i < 4; i++) {
            answerCheckboxes[i] = new JCheckBox(answers[currentQuestionIndex][i]);
            answersPanel.add(answerCheckboxes[i]);
        }

        add(answersPanel, BorderLayout.CENTER);

        nextButton = new JButton("Nächste Frage");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
            }
        });
        add(nextButton, BorderLayout.SOUTH);
    }

    private void checkAnswer() {
        boolean anySelected = false;
        boolean[] selectedAnswers = new boolean[4];
        for (int i = 0; i < 4; i++) {
            selectedAnswers[i] = answerCheckboxes[i].isSelected();
            anySelected = anySelected || selectedAnswers[i];
        }

        if (!anySelected) {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie eine Antwort aus.");
            return;
        }

        boolean[] correctAnswers = getCorrectAnswers(currentQuestionIndex);

        boolean isCorrect = checkArraysEqual(selectedAnswers, correctAnswers);

        if (isCorrect) {
            this.correctAnswers++;
        } else {
            this.incorrectAnswers++;
        }

        if (currentQuestionIndex < questions.length - 1) {
            // Load the next question
            currentQuestionIndex++;
            questionLabel.setText(questions[currentQuestionIndex]);
            for (int i = 0; i < 4; i++) {
                answerCheckboxes[i].setText(answers[currentQuestionIndex][i]);
                answerCheckboxes[i].setSelected(false);
            }
        } else {
            // Display results
            displayResults();
        }
    }

    private boolean[] getCorrectAnswers(int index) {
        boolean[] correctAnswers = new boolean[4];
        for (int i = 0; i < 4; i++) {
            correctAnswers[i] = answers[index][i].endsWith("  ");
        }
        return correctAnswers;
    }


    private boolean checkArraysEqual(boolean[] arr1, boolean[] arr2) {
        if (arr1.length != arr2.length) {
            return false;
        }

        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }

        return true;
    }

    private void displayResults() {
        // Implement logic to display results, e.g., in a new JFrame or JOptionPane.
        // You can use correctAnswers and incorrectAnswers variables for this.
        JOptionPane.showMessageDialog(this, "Quiz beendet. Richtig: " + correctAnswers + ", Falsch: " + incorrectAnswers);
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new QuizApp();
            }
        });
    }
}
