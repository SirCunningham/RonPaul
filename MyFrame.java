package labb4;
import javax.swing.*;

public class MyFrame extends JFrame {
    
    public static void main(String[] args) {
        Model model = new Model(8);
        new View(model);
    }
}
