package ChattyPackage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

class ChatWindow extends JFrame implements ChatObserver {
    private JTextPane chatPane;
    private JTextField inputField;
    private JButton sendButton;
    private JLabel typingIndicator;
    private ChatRoom chatRoom;
    private String userName;
    private int userIndex;

    ChatWindow(ChatRoom chatRoom, String userName) {
        this.chatRoom = chatRoom;
        this.userName = userName;
        this.userIndex = chatRoom.addObserver(this);

        setLayout(new BorderLayout());
        setTitle("Chat - " + userName);

        chatPane = new JTextPane();
        chatPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatPane);
        add(scrollPane, BorderLayout.CENTER);

        inputField = new JTextField();
        sendButton = new JButton("Send");
        sendButton.addActionListener(new SendButtonListener());
        inputField.addActionListener(new SendButtonListener());
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() != KeyEvent.VK_ENTER) {
                    chatRoom.broadcastTypingIndicator(userName, userIndex);
                }
            }
        });
        typingIndicator = new JLabel("");

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        inputPanel.add(typingIndicator, BorderLayout.SOUTH);

        add(inputPanel, BorderLayout.SOUTH);

        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void update(String message, Color color) {
        StyledDocument doc = chatPane.getStyledDocument();
        SimpleAttributeSet messageAttributes = new SimpleAttributeSet();
        StyleConstants.setForeground(messageAttributes, color);
        try {
            doc.insertString(doc.getLength(), message + "\n", messageAttributes);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showTypingIndicator(String userName) {
        typingIndicator.setText(userName + " is typing...");
        new Timer(2000, e -> typingIndicator.setText("")).start();
    }

    private class SendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String message = userName + ": " + inputField.getText();
            if (!inputField.getText().trim().isEmpty()) {
                chatRoom.sendMessage(message, userIndex);
                inputField.setText("");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChatRoom chatRoom = new ChatRoom();
            for (int i = 1; i <= 4; i++) {
                String userName = JOptionPane.showInputDialog("Enter your name:");
                ChatWindow chatWindow = new ChatWindow(chatRoom, userName != null ? userName : "User" + i);
            }
        });
    }
}
