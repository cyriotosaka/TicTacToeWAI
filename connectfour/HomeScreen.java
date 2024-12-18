package connectfour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeScreen extends JFrame {
    private static final long serialVersionUID = 1L;

    public HomeScreen() {
        setTitle("Game Home");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a panel for the title and description
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Welcome to Tic Tac Toe");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subDescriptionLabel = new JLabel("Play now!");
        subDescriptionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subDescriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        titlePanel.add(subDescriptionLabel);

        // Create buttons
        JButton ticTacToeButton = new JButton("Tic Tac Toe");
        JButton ticTacToeBotButton = new JButton("Tic Tac Toe Bot");
        JButton connectFourButton = new JButton("Connect Four");

        // Set button sizes and alignment
        Dimension buttonSize = new Dimension(200, 40);
        ticTacToeButton.setMaximumSize(buttonSize);
        ticTacToeBotButton.setMaximumSize(buttonSize);
        connectFourButton.setMaximumSize(buttonSize);

        ticTacToeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        ticTacToeBotButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        connectFourButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        buttonPanel.add(ticTacToeButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(ticTacToeBotButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(connectFourButton);

        // Add action listeners
        ticTacToeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Placeholder for Tic Tac Toe game
                JOptionPane.showMessageDialog(null, "Tic Tac Toe game will be here!");
            }
        });

        ticTacToeBotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Placeholder for Tic Tac Toe Bot game
                JOptionPane.showMessageDialog(null, "Tic Tac Toe Bot game will be here!");
            }
        });

        connectFourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Start Connect Four game
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new ConnectFour().play(); // Assuming ConnectFour is your Connect Four class
                    }
                });
                dispose(); // Close the home screen
            }
        });

        // Set layout and add panels
        setLayout(new BorderLayout());
        add(titlePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new HomeScreen().setVisible(true);
            }
        });
    }
}