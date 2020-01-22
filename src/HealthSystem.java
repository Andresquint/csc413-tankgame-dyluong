package src;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HealthSystem{

    private int x;
    private int y;
    private int angle;
    private BufferedImage img;
    private int playerID;
    private int winnerID=-1;


    private int hp=100;
    private int hearts=3;
    private Rectangle healthBar = new Rectangle();

    public void init(int x, int y, int angle, BufferedImage img, int hp, int hearts){
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.img = img;
        this.hp = hp;
        this.hearts = hearts;

        healthBar.setSize(hp/2, 5);
        healthBar.setLocation(this.x,this.y-5);
    }

    void update(int x, int y){
        this.x = x;
        this.y = y;
        healthBar.setLocation(this.x,this.y-5);

        if(hp<=0 && hearts>0){
            hp = 100;
            hearts--;
        } else if(hp <= 0 && hearts <= 0){
            System.out.println("Player "+playerID+" is DEAD");
            if(playerID == 1) {
                winnerID = 2;
            }
            else {
                winnerID = 1;
            }
        }
    }


    void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        healthBar.setSize(hp/2, 5);

        g2d.setColor(Color.green);
        g2d.fill(healthBar);

        try {
            for (int i = 0; i < hearts; i++) {
                g2d.drawImage(img.getScaledInstance(img.getWidth() / 3, img.getHeight() / 3, 0), this.x + i * (img.getWidth() / 3 + 2), (int) (this.y - healthBar.getHeight()) - 20, null);
            }
        }catch (Exception ex){}

    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getWinnerID() {
        return winnerID;
    }
}
