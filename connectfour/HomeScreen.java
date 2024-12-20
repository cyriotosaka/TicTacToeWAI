/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #4
 * 1 - 5026221177 - Muhammad Ariq Alwin
 * 2 - 5026231065 - Beh Siu Li
 * 3 - 5026231168 - Okky Priscila Putri
 */

package connectfour;

import playingwithbot.TicTacToe;
import TicTacToe.TTTGraphics;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class HomeScreen extends JFrame {
    private static final long serialVersionUID = 1L;
    private MusicPlayer musicPlayer;
    private JButton musicControlButton;
    private boolean isMusicPlaying;

    public HomeScreen(boolean startMusic) {
        setTitle("Game Home");
        setSize(800, 600); // Increase the size of the home screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        musicPlayer = new MusicPlayer();
        isMusicPlaying = true;

        // Load the background image
        Image backgroundImage = new ImageIcon(getClass().getResource("pics/background.png")).getImage();
        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage);
        backgroundPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for centering

        // Create a panel for the title and description
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        titlePanel.setOpaque(false); // Make the panel transparent

//        JLabel titleLabel = new JLabel("Welcome to Tic Tac Toe");
        JLabel titleLabel = new JLabel("");

        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(new Color(255, 20, 147)); // Neon pink color

//        JLabel subDescriptionLabel = new JLabel("Play now!");
        JLabel subDescriptionLabel = new JLabel(" ");

        subDescriptionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subDescriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subDescriptionLabel.setForeground(Color.WHITE); // Set text color to white for better visibility

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 100)));
        titlePanel.add(subDescriptionLabel);

        // Create buttons
        JButton ticTacToeButton = new JButton("Tic Tac Toe");
        JButton ticTacToeBotButton = new JButton("Tic Tac Toe Bot");
        JButton connectFourButton = new JButton("Connect Four");

        // Apply styles to buttons
        styleButton(ticTacToeButton);
        styleButton(ticTacToeBotButton);
        styleButton(connectFourButton);

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
        buttonPanel.setOpaque(false); // Make the panel transparent

        buttonPanel.add(ticTacToeButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(ticTacToeBotButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(connectFourButton);

//        // Add action listeners
//        ticTacToeButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                SoundEffects.EAT_FOOD.play(); // Play sound effect
//                // Placeholder for Tic Tac Toe game
//                JOptionPane.showMessageDialog(null, "Tic Tac Toe game will be here!");
//            }
//        });

        ticTacToeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundEffects.EAT_FOOD.play();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new TTTGraphics();
                    }
                });
                dispose();
            }
        });

//        ticTacToeBotButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                SoundEffects.EAT_FOOD.play(); // Play sound effect
//                // Placeholder for Tic Tac Toe Bot game
//                JOptionPane.showMessageDialog(null, "Tic Tac Toe Bot game will be here!");
//            }
//        });

        ticTacToeBotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundEffects.EAT_FOOD.play();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new playingwithbot.TicTacToe(12, 5).show();
                    }
                });
                dispose();
            }
        });

        connectFourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundEffects.EAT_FOOD.play(); // Play sound effect
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new ConnectFour().play(); // Assuming ConnectFour is your Connect Four class
                    }
                });
                dispose(); // Close the home screen
            }
        });

        // Add music control button
        musicControlButton = new JButton("\uD83D\uDD07");
        musicControlButton.setPreferredSize(new Dimension(150, 100));
        musicControlButton.setContentAreaFilled(false);
        musicControlButton.setBorder(BorderFactory.createEmptyBorder());
        musicControlButton.setFocusPainted(false);
        musicControlButton.setFont(new Font("Arial", Font.PLAIN, 60));
        musicControlButton.setForeground(Color.WHITE);

        musicControlButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isMusicPlaying) {
                    musicPlayer.stop();
                    musicControlButton.setText("\uD83D\uDD07");
                } else {
                    musicPlayer.play("audio/background_music.wav");
                    musicControlButton.setText("\uD83D\uDD07");
                }
                isMusicPlaying = !isMusicPlaying;
            }
        });

        JPanel musicControlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        musicControlPanel.setOpaque(false);
        musicControlPanel.add(musicControlButton);

        // Add components to the background panel using GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        backgroundPanel.add(titlePanel, gbc);

        gbc.gridy = 1;
        backgroundPanel.add(buttonPanel, gbc);

        gbc.gridy = 2;
        backgroundPanel.add(musicControlPanel, gbc);

        // Set the content pane to the background panel
        setContentPane(backgroundPanel);

        // Play the music when the screen starts, if specified
        if (startMusic) {
            musicPlayer.play("audio/background_music.wav");
        }
    }

    // Define the styleButton method
    private void styleButton(JButton button) {
        button.setForeground(Color.WHITE); // Set text color to white
        button.setBackground(Color.BLACK); // Set background color to black
        button.setBorder(BorderFactory.createEmptyBorder()); // Remove border
        button.setFocusPainted(false); // Remove focus border
        button.setOpaque(true); // Make the background color visible
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new HomeScreen(true).setVisible(true); // Start music on initial load
            }
        });
    }
}