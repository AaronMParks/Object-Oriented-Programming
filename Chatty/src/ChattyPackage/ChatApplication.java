package ChattyPackage;

import javax.swing.*;
import java.awt.*;
public class ChatApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChatRoom chatRoom = new ChatRoom();
            for (int i = 1; i <= 2; i++) { // change 4 to any other number to create more users
                String userName = JOptionPane.showInputDialog("Enter your name:");
                ChatWindow chatWindow = new ChatWindow(chatRoom, userName != null ? userName : "User" + i);
            }
        });
    }
}
interface ChatObserver {
    void update(String message, Color color);
    void showTypingIndicator(String userName);
}
