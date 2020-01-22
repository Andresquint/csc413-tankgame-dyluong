package src;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class PowerUp extends GameObject implements Collidable{
    private Rectangle hitBox;
    private Random RNG = new Random();
    public PowerUp(){}

    PowerUp(int x, int y, int vx, int vy, int angle, BufferedImage img) {
        super(x, y, vx, vy, angle, img);
        hitBox = new Rectangle (this.img.getWidth(), this.img.getHeight());
        hitBox.setLocation(this.x, this.y);
    }

    @Override
    public void init(int x, int y, int vx, int vy, int angle, BufferedImage img) {
        super.init(x, y, vx, vy, angle, img);
        hitBox = new Rectangle (this.img.getWidth(), this.img.getHeight());
        hitBox.setLocation(this.x, this.y);

    }

    @Override
    public void handleCollision(Collidable co) {
        int choice = RNG.nextInt(3);
        if(co instanceof Tank){
            if(this.hitBox.intersects(((Tank) co).getHitBox())) {
                switch(choice) {
                    case 0:
                        ((Tank) co).setR(((Tank) co).getR()+1);
                        System.out.println("Player "+((Tank) co).getPlayerID()+" : Movement Speed increased by 1.");
                        break;
                    case 1:
                        if(((Tank) co).getWeapon() instanceof Rocket) {
                            int buff = 5;
                            ((Tank) co).getWeapon().setDmg(((Tank) co).getWeapon().getDmg()+buff);
                            System.out.println("Player "+((Tank) co).getPlayerID()+" : Rocket damage increased by "+buff+".");
                            break;
                        }
                        ((Tank) co).setWeapon(new Rocket(((Tank) co).getX(), ((Tank) co).getY(), 0, 0, 0, TRE.imgList.get("Rocket")));
                        System.out.println("Player "+((Tank) co).getPlayerID()+" : Weapon Changed to Rocket.");
                        break;
                    case 2:
                        if(((Tank) co).getWeapon() instanceof Bomb) {
                            int buff = 4;
                            ((Tank) co).getWeapon().setDmg(((Tank) co).getWeapon().getDmg()+buff);
                            System.out.println("Player "+((Tank) co).getPlayerID()+" : Bomb damage increased by "+buff+".");
                            break;
                        }
                        ((Tank) co).setWeapon(new Bomb(((Tank) co).getX(), ((Tank) co).getY(), 0, 0, 0, TRE.imgList.get("Bomb")));
                        System.out.println("Player "+((Tank) co).getPlayerID()+" : Weapon Changed to Bomb.");
                        break;
                    default:
                        System.out.println("ERRRORRR in PowerUp handleCollision");
                        break;
                }
                setRemoveThis(true);
            }
        }
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

 /*   @Override
    void drawImage(Graphics g) {
        super.drawImage(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.red);
        g2d.draw(hitBox);
    }*/

}
