import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class GasAnalyzerPanel extends JPanel {
    private static final int CELL_SIZE = 150;
    private static final int TOP_HORIZON_OFFSET = 200;

    private int rows = 0, cols = 0;
    private double fluidContact = 2500;
    private double totalVolume = 0;
    private double[][] baseHorizon;

    public GasAnalyzerPanel() {
        setBackground(new Color(255, 200, 221));
    }

    public void loadFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            ArrayList<Double> values = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                for (String s : line.trim().split("\\s+")) {
                    if (!s.isEmpty()) values.add(Double.parseDouble(s));
                }
            }

            int totalCells = values.size();
            cols = (int) Math.ceil(Math.sqrt(totalCells));
            rows = (int) Math.ceil((double) totalCells / cols);

            baseHorizon = new double[rows][cols];
            for (int i = 0; i < totalCells; i++) {
                baseHorizon[i / cols][i % cols] = values.get(i);
            }

            updateGrid();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error reading dept.txt: " + e.getMessage());
        }
    }

    public void updateGrid() {
        if (baseHorizon == null) return;

        removeAll();
        setLayout(new GridLayout(rows, cols, 4, 4));
        totalVolume = 0;

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                double base = baseHorizon[y][x];
                double top = base - TOP_HORIZON_OFFSET;
                double thickness = base - top;

                if (thickness <= 0) continue;

                double gasHeight = Math.max(0, Math.min(fluidContact - top, thickness));
                double gasPercent = (thickness > 0) ? (gasHeight / thickness) * 100 : 0;
                double cellVolume = CELL_SIZE * CELL_SIZE * gasHeight;
                totalVolume += cellVolume;

                JPanel cell = new JPanel();
                cell.setPreferredSize(new Dimension(40, 40));
                cell.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

                if (gasPercent == 0) {
                    cell.setBackground(new Color(255, 99, 71)); // Red
                } else if (gasPercent < 50) {
                    cell.setBackground(new Color(255, 255, 102)); // Yellow
                } else {
                    cell.setBackground(new Color(144, 238, 144)); // Green
                }

                cell.setToolTipText(String.format("Depth: %.1f m, Gas: %.1f%%", base, gasPercent));
                add(cell);
            }
        }

        revalidate();
        repaint();
    }

    public void setFluidContact(double value) {
        this.fluidContact = value;
    }

    public double getFluidContact() {
        return fluidContact;
    }

    public double getTotalVolume() {
        return totalVolume;
    }

    public String getFormattedTotalVolume() {
        return String.format("%,.0f", totalVolume);
    }
}
