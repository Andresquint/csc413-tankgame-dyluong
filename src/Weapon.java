package src;


import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class Weapon extends GameObject implements Collidable{

    private int R=1;
    protected boolean show = false;
    private boolean hitWall = false;
    private int dmg;
    private boolean ShootPressed = false;
    private Rectangle hitBox;
    private Collidable c;

    Weapon(int x, int y, int vx, int vy, int angle, BufferedImage img) {
        super(x, y, vx, vy, angle, img);
        hitBox = new Rectangle (img.getWidth(), img.getHeight());
        hitBox.setLocation(x, y);
    }

    public void update(){
        if(!show)
            return;
        moveForwards();
        hitBox.setLocation(x,y);
    }

    public void update(int x, int y, int angle) {
        if(this.ShootPressed){
            this.shoot(x,y,angle);
        }
        hitBox.setLocation(this.x, this.y);
    }

    void toggleShootPressed(){
        this.ShootPressed = true;
    }

    void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= TRE.WORLD_WIDTH - 88) {
            x = TRE.WORLD_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= TRE.WORLD_HEIGHT - 80) {
            y = TRE.WORLD_HEIGHT - 80;
        }
    }

    public void shoot(){
        this.moveForwards();
    }

    public void shoot(int x, int y, int angle){
        if(!show) {
            show = true;
            this.x = x+25;
            this.y = y+12;
            this.angle = angle;
        }
        this.moveForwards();
    }


    public void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
    }

    public boolean isShow() {
        return show;
    }

    public abstract Weapon respawn();

    @Override
    void drawImage(Graphics g) {
        if(show) {
            AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
            rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(this.img, rotation, null);

/*            g2d.setColor(Color.red);
            g2d.draw(hitBox);*/
        }
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public int getDmg() {
        return dmg;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }
}
