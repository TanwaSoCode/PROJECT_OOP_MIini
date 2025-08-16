import javax.swing.*;

public class MyMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gas Volume Analyzer (2D Grid)");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 900);
            frame.setLocationRelativeTo(null);

            GasAnalyzerPanel analyzerPanel = new GasAnalyzerPanel();
            ButtonPanel buttonPanel = new ButtonPanel(analyzerPanel);

            frame.setLayout(new java.awt.BorderLayout(15, 15));
            frame.getContentPane().setBackground(new java.awt.Color(255, 200, 221));
            frame.add(buttonPanel, java.awt.BorderLayout.NORTH);
            frame.add(new JScrollPane(analyzerPanel), java.awt.BorderLayout.CENTER);
            frame.setVisible(true);

        });
    }

    public void setVisible(boolean b) {
        throw new UnsupportedOperationException("Unimplemented method 'setVisible'");
    }
}
