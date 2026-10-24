// MainApp.java
package lab2tech;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutionException;

public class MainApp extends JFrame {
    private JTextField intervalsField;
    private JTextField threadsField;
    private JLabel resultLabel;
    private JLabel timeLabel;

    public MainApp() {
        setTitle("Лабораторна 2: ExecutorService");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("Кількість інтервалів (n):"));
        intervalsField = new JTextField("100000000");
        add(intervalsField);

        add(new JLabel("Кількість потоків:"));
        threadsField = new JTextField("4");
        add(threadsField);

        JButton calcButton = new JButton("Обчислити");
        add(calcButton);
        add(new JLabel(""));

        add(new JLabel("Результат:"));
        resultLabel = new JLabel("0.0");
        add(resultLabel);

        add(new JLabel("Час виконання (мс):"));
        timeLabel = new JLabel("0");
        add(timeLabel);

        calcButton.addActionListener(e -> calculateIntegral());
    }

    private void calculateIntegral() {
        try {
            int n = Integer.parseInt(intervalsField.getText());
            int threads = Integer.parseInt(threadsField.getText());
            Function f = new Function();
            IntegralCalculator calculator = new IntegralCalculator();

            long startTime = System.currentTimeMillis();
            double result = calculator.calculate(1, 9, n, f, threads);
            long endTime = System.currentTimeMillis();

            resultLabel.setText(String.format("%.6f", result));
            timeLabel.setText(String.valueOf(endTime - startTime));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Некоректний формат числа!", "Помилка", JOptionPane.ERROR_MESSAGE);
        } catch (InterruptedException | ExecutionException ex) {
            JOptionPane.showMessageDialog(this, "Помилка під час обчислень у потоках!", "Помилка", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainApp().setVisible(true));
    }
}