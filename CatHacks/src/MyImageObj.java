
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MyImageObj extends JLabel {
    private BufferedImage bim = null, og = null;

    private Color[] colors = {
            Color.black,
            Color.white,
            Color.gray,
            Color.lightGray,
            Color.RED,
            Color.ORANGE,
            Color.YELLOW,
            Color.GREEN,
            Color.BLUE,
            Color.cyan,
            Color.DARK_GRAY,
            Color.magenta,
            Color.pink
    };

    MyImageObj(){

    }

    public void setImage(BufferedImage img){
        if(img == null){
            return;
        }
        bim = img;

        og = new BufferedImage(bim.getWidth(),bim.getHeight(),bim.getType());
        Graphics2D g2d = (Graphics2D) og.getGraphics();
        g2d.drawImage(bim,0,0,bim.getWidth(), bim.getHeight(), null);
        g2d.dispose();

        this.repaint();
    }

    public BufferedImage getImage(){
        return bim;
    }

    public void thresholdImage(int i){

        int colorIndex = 0;
        for(int y = 0; y < bim.getHeight(); y++){
            for(int x = 0; x < bim.getWidth(); x++){
                double min = 99999;
                int pixel = bim.getRGB(x,y);
                Color clr = new Color(pixel,true);
                int red = clr.getRed(), blue = clr.getBlue(), green = clr.getGreen();
                for( int n = 0; n < Math.min(i, colors.length);n++){
                    int j = (colors[n].getRed() - red), k = (colors[n].getBlue() - blue), m = (colors[n].getGreen() - green);
                    double distance = Math.sqrt((j*j)+(k*k)+(m*m));
                    if(distance < min){
                        min = distance;
                        colorIndex = n;
                    }
                }
                bim.setRGB(x,y,colors[colorIndex].getRGB());
                //repaint();
            }
        }
        repaint();
    }

    public void reset(){
        setImage(og);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D big = (Graphics2D) g;
        g.clearRect(0,0,getWidth(),getHeight());

        big.drawImage(bim,0,0,this);
    }
}
