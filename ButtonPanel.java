import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ButtonPanel extends JPanel {
    public ButtonPanel(GasAnalyzerPanel analyzerPanel) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        setBackground(new Color(255, 200, 221));

        JButton loadButton = new JButton("Load dept.txt");
        loadButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JButton calcButton = new JButton("Calculate");
        calcButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JTextField fluidContactField = new JTextField(String.valueOf(analyzerPanel.getFluidContact()), 8);
        fluidContactField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel volumeLabel = new JLabel("Total Volume");
        volumeLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        volumeLabel.setForeground(new Color(25, 25, 112));

  
        loadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int res = fileChooser.showOpenDialog(this);
            if (res == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                analyzerPanel.loadFile(selectedFile);
                volumeLabel.setText("Total Volume: " + analyzerPanel.getFormattedTotalVolume() + " m³");
            }
        });

  
        calcButton.addActionListener(e -> {
            try {
                double contact = Double.parseDouble(fluidContactField.getText());
                analyzerPanel.setFluidContact(contact);
                analyzerPanel.updateGrid();
                volumeLabel.setText("Total Volume: " + analyzerPanel.getFormattedTotalVolume() + " m³");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Fluid Contact value");
            }
        });

        add(loadButton);
        add(new JLabel("Fluid Contact"));
        add(fluidContactField);
        add(calcButton);
        add(volumeLabel);
    }
}
