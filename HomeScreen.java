//import connectfour.BackgroundPanel;
//import connectfour.ConnectFour;
//import playingwithbot.MinimaxPlayer;
//import playingwithbot.TicTacToe;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class HomeScreen extends JFrame {
//    private static final long serialVersionUID = 1L;
//
//    public HomeScreen() {
//        setTitle("Game Home");
//        setSize(400, 300);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//
//        // Load the background image
//        Image backgroundImage = new ImageIcon(getClass().getResource("pics/background.png")).getImage();
//        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage);
//        backgroundPanel.setLayout(new BorderLayout());
//
//        // Create a panel for the title and description
//        JPanel titlePanel = new JPanel();
//        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
//        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//        titlePanel.setOpaque(false); // Make the panel transparent
//
//        JLabel titleLabel = new JLabel("Welcome to Tic Tac Toe");
//        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
//        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//        titleLabel.setForeground(Color.WHITE); // Set text color to white for better visibility
//
//        JLabel subDescriptionLabel = new JLabel("Play now!");
//        subDescriptionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
//        subDescriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//        subDescriptionLabel.setForeground(Color.WHITE); // Set text color to white for better visibility
//
//        titlePanel.add(titleLabel);
//        titlePanel.add(Box.createRigidArea(new Dimension(0, 10)));
//        titlePanel.add(subDescriptionLabel);
//
//        // Create buttons
//        JButton ticTacToeButton = new JButton("Tic Tac Toe");
//        JButton ticTacToeBotButton = new JButton("Tic Tac Toe Bot");
//        JButton connectFourButton = new JButton("Connect Four");
//
//        // Set button sizes and alignment
//        Dimension buttonSize = new Dimension(200, 40);
//        ticTacToeButton.setMaximumSize(buttonSize);
//        ticTacToeBotButton.setMaximumSize(buttonSize);
//        connectFourButton.setMaximumSize(buttonSize);
//
//        ticTacToeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//        ticTacToeBotButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//        connectFourButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//        // Create a panel for the buttons
//        JPanel buttonPanel = new JPanel();
//        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
//        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//        buttonPanel.setOpaque(false); // Make the panel transparent
//
//        buttonPanel.add(ticTacToeButton);
//        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
//        buttonPanel.add(ticTacToeBotButton);
//        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
//        buttonPanel.add(connectFourButton);
//
//        // Add action listeners
//        ticTacToeButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // Placeholder for Tic Tac Toe game
//                JOptionPane.showMessageDialog(null, "Tic Tac Toe game will be here!");
//            }
//        });
//
//        ticTacToeBotButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // Placeholder for Tic Tac Toe game
//                JOptionPane.showMessageDialog(null, "Tic Tac Toe BOT game will be here!");
//
////                // Start the Tic Tac Toe BOT game
////                SwingUtilities.invokeLater(new Runnable() {
////                    @Override
////                    public void run() {
////                        TicTacToe botGame = new TicTacToe();
////                        botGame.play(new String[]{"12", "5"}); // Pass arguments if required
////                    }
////                });
////                dispose(); // Close the home screen
//            }
//        });
//
//
//        connectFourButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // Start Connect Four game
//                SwingUtilities.invokeLater(new Runnable() {
//                    public void run() {
//                        new ConnectFour().play(); // Assuming ConnectFour is your Connect Four class
//                    }
//                });
//                dispose(); // Close the home screen
//            }
//        });
//
//        // Add panels to the background panel
//        backgroundPanel.add(titlePanel, BorderLayout.NORTH);
//        backgroundPanel.add(buttonPanel, BorderLayout.CENTER);
//
//        // Set the content pane to the background panel
//        setContentPane(backgroundPanel);
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                new HomeScreen().setVisible(true);
//            }
//        });
//    }
//}