package src;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class GameObject {

    protected int x;
    protected int y;
    protected int vx;
    protected int vy;
    protected int angle;

    protected BufferedImage img;


    private Boolean removeThis = false;

    public GameObject(){}

    GameObject(int x, int y, int vx, int vy, int angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
        System.out.println(this.img);
    }

    public void init(int x, int y, int vx, int vy, int angle, BufferedImage img){
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
        System.out.println(this.img);
    }

    void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
    }

    public Boolean getRemoveThis() {
        return removeThis;
    }

    public void setRemoveThis(Boolean removeThis) {
        this.removeThis = removeThis;
    }

    public void nullEverything(){
        this.x=TRE.WORLD_WIDTH+9000;
        this.y=TRE.WORLD_HEIGHT+9000;
    }
}
