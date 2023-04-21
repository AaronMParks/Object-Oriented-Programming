package ChattyPackage;
import java.util.ArrayList;
import java.util.List;

import ChattyPackage.ChatObserver;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;

class ChatRoom {
    private final List<ChatObserver> observers = new ArrayList<>();
    private final List<Color> userColors = new ArrayList<>();

    public int addObserver(ChatObserver observer) {
        observers.add(observer);
        Color newUserColor = createRandomColor();
        userColors.add(newUserColor);
        return observers.size() - 1;
    }

    public void sendMessage(String message, int userIndex) {
        Color userColor = userColors.get(userIndex);
        for (ChatObserver observer : observers) {
            observer.update(message, userColor);
        }
    }

    public void broadcastTypingIndicator(String userName, int userIndex) {
        for (int i = 0; i < observers.size(); i++) {
            if (i != userIndex) {
                observers.get(i).showTypingIndicator(userName);
            }
        }
    }

    private Color createRandomColor() {
        Random random = new Random();
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }
}


