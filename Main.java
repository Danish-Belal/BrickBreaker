import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        JFrame obj = new JFrame();
        obj.setSize(700,600);
        obj.setTitle("Brick Breaker");
        obj.setResizable(false);
        obj.setVisible(true);
        GamePlay gameplay = new GamePlay();
        obj.add(gameplay);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

