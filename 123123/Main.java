import java.awt.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Pogi ko");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        JLabel label = new JLabel("Nigga", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(label);
        JButton button = new JButton("pindutin mo ako");
        JButton button1 = new JButton("dont pindot me");
        JButton button2 = new JButton("go");
        JButton button3 = new JButton("stop");

        button.addActionListener(e -> {
            frame.getContentPane().setBackground(Color.BLUE);
        });

        button1.addActionListener(e -> {
            frame.getContentPane().setBackground(Color.RED);
            label.setText("Ayaw mo pindutin");
        });

        button2.addActionListener(e -> {
            frame.getContentPane().setBackground(Color.GREEN);
            label.setText("Go go go");
        });

        button3.addActionListener(e -> {
            frame.getContentPane().setBackground(Color.YELLOW);
            label.setText("Stop!");
        });

        frame.setLayout(new BorderLayout());
        frame.add(label, BorderLayout.CENTER);
        frame.add(button1, BorderLayout.PAGE_END);
        frame.add(button2, BorderLayout.PAGE_START);
        frame.add(button3, BorderLayout.EAST);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }
}
