import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class canvas extends JFrame {
    public canvas(){

    }
    public static void main(String[] args){
        canvas c = new canvas();
        c.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { System.exit(0); }
        });
    }
}
