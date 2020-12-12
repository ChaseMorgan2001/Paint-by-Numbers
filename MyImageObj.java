import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MyImageObj extends JLabel {
    private BufferedImage bim = null;

    MyImageObj(){

    }

    public void setImage(BufferedImage img){
        if(img == null){
            return;
        }
        bim = img;

        this.repaint();
    }

    public BufferedImage getImage(){
        return bim;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D big = (Graphics2D) g;
        g.clearRect(0,0,getWidth(),getHeight());

        big.drawImage(bim,0,0,this);
    }
}
