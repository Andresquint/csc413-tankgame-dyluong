package src;

import java.awt.image.BufferedImage;

public class Bullet extends Weapon {

    private int dmg=5;
    private int R = 1;

    Bullet(int x, int y, int vx, int vy, int angle, BufferedImage img) {
        super(x, y, vx, vy, angle, img);
    }

    @Override
    public Weapon respawn() {
        return new Bullet(this.x,this.y, this.vx,this.vy, this.angle,this.img);
    }

    @Override
    public void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
    }

    @Override
    public void handleCollision(Collidable co) {

        if (co instanceof Tank) {
            if (this.getHitBox().intersects(((Tank) co).getHitBox())) {
                if (!((Tank) co).getAmmoList().contains(this)) {
                    ((Tank) co).setHp(((Tank) co).getHp() - this.dmg);
                    //System.out.println("HIT");
                    this.dmg = 0;
                    setRemoveThis(true);
                }
            }
        }

        if (co instanceof BreakableWall) {
            if (this.getHitBox().intersects(((BreakableWall) co).getHitBox())) {
                ((BreakableWall) co).setHp(((BreakableWall) co).getHp()-dmg);
                this.dmg = 0;
                this.setRemoveThis(true);
                if(((BreakableWall) co).getHp()<=0) ((BreakableWall) co).setRemoveThis(true);
            }
        }

       if (co instanceof UnbreakableWall) {
            if (this.getHitBox().intersects(((UnbreakableWall) co).getHitBox())) {
             this.setRemoveThis(true);
            }
        }
    }
}
