package src;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UnbreakableWall extends GameObject implements Collidable{
    private boolean breakable = false;
    private Rectangle hitBox = new Rectangle();
    public UnbreakableWall(){}
    private int hp=10000;

    UnbreakableWall(int x, int y, int vx, int vy, int angle, BufferedImage img) {
        super(x, y, vx, vy, angle, img);
        hitBox.setSize(img.getWidth(), img.getHeight());
        hitBox.setLocation(x, y);
    }

    @Override
    public void init(int x, int y, int vx, int vy, int angle, BufferedImage img) {
        super.init(x, y, vx, vy, angle, img);
        hitBox.setSize(img.getWidth(), img.getHeight());
        hitBox.setLocation(x, y);
    }

    @Override
    public void handleCollision(Collidable co) {

        if(co instanceof Tank){
            if(this.hitBox.intersects(((Tank) co).getHitBox())) {
                ((Tank) co).setDisableMove(true);
                ((Tank) co).setX(((Tank) co).getOldPosX());
                ((Tank) co).setY(((Tank) co).getOldPosY());
            }
        }
/*        if(co instanceof Weapon){
            if(this.hitBox.intersects(((Weapon) co).getHitBox())) {
                ((Weapon) co).setRemoveThis(true);
            }
        }*/
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

/*    @Override
    void drawImage(Graphics g) {
        super.drawImage(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.red);
        g2d.draw(hitBox);
    }*/

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
