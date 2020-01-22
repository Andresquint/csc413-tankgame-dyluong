/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;


import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 *
 */
public class TRE extends JPanel  {


    private BufferedImage world;
    private BufferedImage miniMap;
    private Graphics2D buffer;
    private SoundPlayer bgMusic;
    private static StartScreen startScreen = new StartScreen();
    private static EndScreen endScreen = new EndScreen();
    private static Boolean isOn =true;

    private static JFrame jf;
    private Tank t1;
    private Tank t2;
    private Weapon p1;
    private Weapon p2;

    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 800;
    public static final int WORLD_WIDTH = 2048;
    public static final int WORLD_HEIGHT = 2048;
    public static final int MINIMAP_WIDTH = 300;
    public static final int MINIMAP_HEIGHT= 250;

    public static HashMap<String,BufferedImage> imgList = new HashMap<>();
    public static HashMap<String,URL> soundURLs = new HashMap<>();

    private static ArrayList<GameObject> gameObjects = new ArrayList<>();
    private static ArrayList<Collidable> collidables = new ArrayList<>();

    //private static ArrayList<GameObject> initialObjects = new ArrayList<>();
    //private static ArrayList<Collidable> initialColliables = new ArrayList<>();

    public static void main(String[] args) {
        Thread x;
        TRE trex = new TRE();
        trex.init();
        startScreen.init();
        endScreen.init();
        try {

            while (true) {
                if(isOn){
                    trex.t1.update();
                    trex.t2.update();
                    trex.repaint();
                    trex.handleRemove();
                    trex.checkCollisions();
                    //System.out.println(trex.t1);
                    //System.out.println(trex.t2);
                }
                Thread.sleep(1000 / 144);

            }
        } catch (InterruptedException ignored) {

        }

    }


    private void init() {
        this.jf = new JFrame("Tank Game");
        this.world = new BufferedImage(TRE.WORLD_WIDTH, TRE.WORLD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        this.miniMap = new BufferedImage(TRE.MINIMAP_WIDTH, TRE.MINIMAP_HEIGHT,BufferedImage.TYPE_INT_RGB);
        BufferedImage t1img = null, t2img = null, b1img = null, unbkWallimg = null, bkWallimg= null, rocket = null, background = null ,powerUpimg = null, heart = null, bomb = null, gameTitle =null;
        MapLoader map =null;
        AudioInputStream bgMusicStream = null;
        InputStream mapFile = null;
        try {
            System.out.println(System.getProperty("user.dir"));
            /*
             * note class loaders read files from the out folder (build folder in netbeans) and not the
             * current working directory.
             */
           // t1img = read(new File("resources/Tank1.gif"));
           // t2img = read(new File("resources/Tank2.gif"));
            t1img = ImageIO.read(TRE.class.getClassLoader().getResource("Tank1.gif"));
            t2img = ImageIO.read(TRE.class.getClassLoader().getResource("Tank2.gif"));
            b1img = ImageIO.read(TRE.class.getClassLoader().getResource("Shell.gif"));
            background = ImageIO.read(TRE.class.getClassLoader().getResource("Background.bmp"));
            bkWallimg= ImageIO.read(TRE.class.getClassLoader().getResource("Wall1.gif"));
            unbkWallimg = ImageIO.read(TRE.class.getClassLoader().getResource("Wall2.gif"));
            rocket = ImageIO.read(TRE.class.getClassLoader().getResource("Rocket.gif"));
            powerUpimg = ImageIO.read(TRE.class.getClassLoader().getResource("Pickup.gif"));
            heart = ImageIO.read(TRE.class.getClassLoader().getResource("Heart.png"));
            bomb = ImageIO.read(TRE.class.getClassLoader().getResource("Bomb.png"));
            gameTitle = ImageIO.read(TRE.class.getClassLoader().getResource("Title.bmp"));
            mapFile = TRE.class.getClassLoader().getResourceAsStream("Map_1.txt");
            URL bgMusicURL = TRE.class.getClassLoader().getResource("Music.wav");
            URL shootSoundURL = TRE.class.getClassLoader().getResource("Explosion_small.wav");
            bgMusicStream = AudioSystem.getAudioInputStream(bgMusicURL);

            imgList.put("Tank1",t1img);
            imgList.put("Tank2",t2img);
            imgList.put("Bullet", b1img);
            imgList.put("Rocket", rocket);
            imgList.put("PowerUp", powerUpimg);
            imgList.put("UnbreakableWall", unbkWallimg);
            imgList.put("BreakableWall", bkWallimg);
            imgList.put("Background",background);
            imgList.put("Heart", heart);
            imgList.put("Bomb", bomb);
            imgList.put("Title", gameTitle);

            soundURLs.put("Background", bgMusicURL);
            soundURLs.put("Shoot", shootSoundURL);
        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        }

        initBG(background);
        map = new MapLoader(mapFile);
        map.init();

        p1 = new Bullet(100,100,0,0,0,b1img);
        p2 = new Bullet(1800, 1800,0,0,0,b1img);
        t1 = new Tank(100, 100, 0, 0, 0, t1img,p1,1);
        t2 = new Tank(1800, 1800,0,0,180,t2img,p2,2);


        gameObjects.add(t1);
        gameObjects.add(t2);
        gameObjects.add(p1);
        gameObjects.add(p2);

        collidables.add(p1);
        collidables.add(p2);
        collidables.add(t1);
        collidables.add(t2);

        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);

        this.jf.setLayout(new BorderLayout());
        this.jf.add(this);

        this.jf.addKeyListener(tc1);
        this.jf.addKeyListener(tc2);
        this.jf.setSize(TRE.SCREEN_WIDTH, TRE.SCREEN_HEIGHT + 30);
        this.jf.setResizable(false);
        jf.setLocationRelativeTo(null);

        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jf.setVisible(false);

        this.bgMusic = new SoundPlayer(bgMusicStream);
        this.bgMusic.playLoop();

       // save(gameObjects,collidables);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        buffer = world.createGraphics();
        super.paintComponent(g2);
        for(int i = 0 ; i<gameObjects.size();i++){
            gameObjects.get(i).drawImage(buffer);
        }
        g2.drawImage(world.getSubimage(t1.getCenterX(), t1.getCenterY(), TRE.SCREEN_WIDTH / 2, TRE.SCREEN_HEIGHT), 0, 0, null);
        g2.drawImage(world.getSubimage(t2.getCenterX(), t2.getCenterY(),TRE.SCREEN_WIDTH/2, TRE.SCREEN_HEIGHT),TRE.SCREEN_WIDTH/2,0,null);
        g2.drawImage(world.getScaledInstance(TRE.MINIMAP_WIDTH,TRE.MINIMAP_HEIGHT,Image.SCALE_DEFAULT),TRE.SCREEN_WIDTH/2-TRE.MINIMAP_WIDTH/2,TRE.SCREEN_HEIGHT-TRE.MINIMAP_HEIGHT-25,null);
    }

    private void initBG(BufferedImage bg){
        try {
            int tileWidth = bg.getWidth();
            int tileHeight = bg.getHeight();
            int x = world.getWidth() / tileWidth;
            int y = world.getHeight() / tileHeight;
            int currX = 0;
            int currY = 0;
            Background background;
            for (int i = 0; i <= y; i++) {
                for (int j = 0; j <= x; j++) {
                    background = new Background(currX,currY,0,0,0,bg);
                    gameObjects.add(background);
                    currX += tileWidth;
                }
                currX = 0;
                currY += tileHeight;
            }
        }catch(Exception ex){System.out.println("Failed to init background.");}
    }

    public static void addGameObject(GameObject gameObject){
        gameObjects.add(gameObject);
    }

    public static void addColliable(Collidable collidable){
        collidables.add(collidable);
    }

    public void checkCollisions(){
        try{
        collidables.forEach(collidable ->{
                collidable.handleCollision(t1);
                collidable.handleCollision(t2);
                t1.getAmmoList().forEach(ammo->{
                    ammo.handleCollision(collidable);
                });
                t2.getAmmoList().forEach(ammo->{
                    ammo.handleCollision(collidable);
                });
        });
        }catch (Exception ex){}
    }


    public void handleRemove(){
        GameObject object;
        for (int i=0;i<gameObjects.size();i++){
            if(gameObjects.get(i).getRemoveThis()) {
                object = gameObjects.get(i);
                collidables.remove(gameObjects.get(i));
                gameObjects.remove(gameObjects.get(i));
                object.nullEverything();
            }
        }
    }

    public static JFrame getJf() {
        return jf;
    }

    public static EndScreen getEndScreen() {
        return endScreen;
    }

    public static void setIsOn(Boolean isOn) {
        TRE.isOn = isOn;
    }

    /*    public static void save(ArrayList<GameObject> gameObjects, ArrayList<Collidable> collidables){
        gameObjects.forEach(gameObject -> initialObjects.add(gameObject));
        collidables.forEach(collidable -> initialColliables.add(collidable));
    }

    public static void reset(){
        gameObjects.clear();
        collidables.clear();
        initialObjects.forEach(gameObject -> gameObjects.add(gameObject));
        initialColliables.forEach(collidable -> collidables.add(collidable));
    }*/
    /*
    public static void setIsOn(Boolean isOn) {
        TRE.isOn = isOn;
    }
*/
    /*
    private void initWalls(BufferedImage wall1, BufferedImage wall2){
        int wallWidth = wall1.getWidth();
        int wallHeight = wall1.getHeight();
        int x = world.getWidth() / wallWidth;
        int y = world.getHeight() / wallHeight;
        ArrayList<GameObject> walls= new ArrayList<>();
        Wall unbkWall, bkWall;
        int currX = 0;
        int currY = 0;
        for (int i = 0; i < y; i++) {              //makes the unbreakable walls parameter
            for (int j = 0; j < x; j++) {
                if(i == 0  || i == y-1){
                    unbkWall = new Wall(currX, currY, 0, 0, 0, wall2,false);
                    gameObjects.add(unbkWall);
                }
                else if(j == 0 || j == x-1) {
                    unbkWall = new Wall(currX, currY, 0, 0, 0, wall2,false);
                    gameObjects.add(unbkWall);
                }
                currX += wallWidth;
            }
            currX = 0;
            currY += wallHeight;
        }
        currX=0;
        currY=0;
        for (int i = 0; i < y; i++) {              //makes the unbreakable walls parameter
            for (int j = 0; j < x; j++) {
                if((20<i && i<40)&&((20<j && j<40))) {
                    bkWall = new Wall(currX, currY, 0, 0, 0, wall1, true);
                    gameObjects.add(bkWall);
                }
                currX += wallWidth;
            }
            currX = 0;
            currY += wallHeight;
        }
    }
*/



/*    private void checkAmmo(){
        if(t1.isNewAmmo()) {
            gameObjects.add(t1.getPowerUp());
            bullets.add(t1.getPowerUp());
            t1.setNewAmmo(false);
        }
        if(t2.isNewAmmo()) {
            gameObjects.add(t2.getPowerUp());
            bullets.add(t2.getPowerUp());
            t2.setNewAmmo(false);
        }
    }*/

    //private void updateBullets(){
    // bullets.forEach(powerUp -> powerUp.update());
    //}

/*    private void drawBG(BufferedImage background){
        try {
            int tileWidth = background.getWidth();
            int tileHeight = background.getHeight();
            int x = world.getWidth() / tileWidth;
            int y = world.getHeight() / tileHeight;
            int currX = 0;
            int currY = 0;
            for (int i = 0; i <= y; i++) {
                for (int j = 0; j <= x; j++) {
                    buffer.drawImage(background, currX, currY, null);
                    currX += tileWidth;
                }
                currX = 0;
                currY += tileHeight;
            }
        }catch(Exception ex){System.out.println("Failed to draw background.");}
    }*/

}
