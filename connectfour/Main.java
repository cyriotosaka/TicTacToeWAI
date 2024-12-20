//package connectfour;
//
//public class Main {
//    public static void main(String[] args) {
//        System.out.println("Connect Four Game");
//        ConnectFour game = new ConnectFour();
//        game.play();
//    }
//}

package connectfour;

public class Main {
    public static void main(String[] args) {
        // Launch the HomeScreen
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new HomeScreen(true).setVisible(true);
            }
        });
    }
}