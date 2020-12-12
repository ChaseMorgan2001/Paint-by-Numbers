import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MyImageObj extends JLabel {
    private BufferedImage bim = null;
    private boolean isPBN = false;
    private int threshVal = 8;
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

    public void setThreshVal(int val) { threshVal = val; }
    public void setPBN() { isPBN = true; }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D big = (Graphics2D) g;
        g.clearRect(0,0,getWidth(),getHeight());

        big.drawImage(bim,0,0,this);
    }
}
