package src;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 *
 */
public class Tank extends GameObject implements Collidable{

    private int R = 2;
    private final int ROTATIONSPEED = 4;

    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;

    private Weapon weapon;
    private ArrayList<Weapon> ammoList = new ArrayList<>();

    private int oldPosX;
    private int oldPosY;
    private int oldPosX_Camera = TRE.WORLD_WIDTH-TRE.SCREEN_WIDTH/2;
    private int oldPosY_Camera = TRE.WORLD_HEIGHT-TRE.SCREEN_HEIGHT;

    private Rectangle hitBox;
    private HealthSystem healthSystem = new HealthSystem();
    private Collidable c;
    private Boolean disableMove =false;
    private SoundPlayer shootSound;

    private final int playerID;

    Tank(int x, int y, int vx, int vy, int angle, BufferedImage img, Weapon weapon , int playerID) {
        super(x, y, vx, vy, angle, img);
        this.weapon = weapon;
        ammoList.add(this.weapon);
        hitBox = new Rectangle(this.img.getWidth(), this.img.getHeight());
        oldPosX = x;
        oldPosY = y;
        this.playerID = playerID;
        healthSystem.init(this.x, this.y, 0, TRE.imgList.get("Heart"),100, 3);
        healthSystem.setPlayerID(this.playerID);
        try {
            AudioInputStream shootSound = AudioSystem.getAudioInputStream(TRE.soundURLs.get("Shoot"));
            this.shootSound = new SoundPlayer(shootSound);
        }catch (Exception ex){}
    }

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void toggleShootPressed(){
        this.ShootPressed = true;
        this.shootSound.play();
        reloadAmmo();
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() { this.DownPressed = false; }

    void unToggleRightPressed() { this.RightPressed = false; }

    void unToggleLeftPressed() { this.LeftPressed = false; }

    void unToggleShootPressed(){
        this.ShootPressed = false;
        //reloadAmmo();
    }

    public void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if(this.ShootPressed){
            this.shoot();
        }
        updateAmmo();
        healthSystem.update(this.x, this.y);
        hitBox.setLocation(this.x, this.y);
        checkWinner();
        if(disableMove) {disableMove =false; return;}

        oldPosX = this.x;
        oldPosY = this.y;
    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
       // checkBorder();
    }

    private void moveForwards() {

        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        //checkBorder();
    }

     private void shoot(){
        if(!weapon.isShow()) {
            weapon.update(this.x, this.y, this.angle);
            weapon.toggleShootPressed();
        }
    }

/*    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= TRE.WORLD_WIDTH - 88) {
            x = TRE.WORLD_WIDTH- 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= TRE.WORLD_HEIGHT - 80) {
            y = TRE.WORLD_HEIGHT - 80;
        }
    }*/


    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }


    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }


    public Weapon getWeapon() {
        return weapon;
    }

    private void reloadAmmo(){
        this.weapon = weapon.respawn();
        ammoList.add(this.weapon);
        TRE.addGameObject(this.weapon);
        TRE.addColliable(this.weapon);
    }

    public int getCenterX(){
        int deltaX =250;        //deltaX is for positioning the tank to be center in the X axis
        if(this.x - deltaX <= 0) return 0;      //for the case where the tank is at the edge of the map
        if(this.x - deltaX + TRE.SCREEN_WIDTH/2 >= TRE.WORLD_WIDTH) return oldPosX_Camera;
        oldPosX_Camera = this.x-250;
        return this.x - deltaX;
    }

    public int getCenterY(){
        int deltaY =250;        //deltaY is for positioning the tank to be center in the Y axis
        if(this.y - deltaY <= 0) return 0;      //for the case where the tank is at the edge of the map
        if(this.y - deltaY + TRE.SCREEN_HEIGHT >= TRE.WORLD_HEIGHT) return oldPosY_Camera;
        oldPosY_Camera = this.y-250;
        return this.y - deltaY;
    }

    private void updateAmmo(){
       try {
            ammoList.forEach(ammo -> ammo.update());
        }catch (Exception ex){}
    }

    @Override
    void drawImage(Graphics g) {
        super.drawImage(g);
        Graphics2D g2d = (Graphics2D) g;

        healthSystem.drawImage(g);
    }

    @Override
    public void handleCollision(Collidable co) {}

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setR(int r) {
        R = r;
    }

    public ArrayList<Weapon> getAmmoList() {
        return ammoList;
    }

    public int getX(){
        return this.x;
    }

    public void setX(int x){
        this.x = x;
    }

    public int getY(){
        return this.y;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getOldPosX() {
        return oldPosX;
    }

    public int getOldPosY() {
        return oldPosY;
    }

    public int getR() {
        return R;
    }

    public void setDisableMove(Boolean disableMove) {
        this.disableMove = disableMove;
    }

    public int getHp() {
        return healthSystem.getHp();
    }

    public void setHp(int hp) {
        healthSystem.setHp(hp);
    }

    public int getPlayerID() {
        return playerID;
    }

    public HealthSystem getHealthSystem() {
        return healthSystem;
    }

    private void checkWinner(){
        if(healthSystem.getWinnerID() != -1) {
            TRE.getEndScreen().execute(healthSystem.getWinnerID());
            TRE.setIsOn(false);
        }
    }
    public void nullBullet(){

    }
}
