package src;

import java.io.*;
import java.util.HashMap;

public class MapLoader {

    private File mapFile;
    private BufferedReader mapReader;
    private int imageSizeX;
    private int imageSizeY;
    private static HashMap<Integer, String> objectTable;
    static{
        objectTable = new HashMap<>();
        objectTable.put(3,"PowerUp");
        objectTable.put(2,"BreakableWall");
        objectTable.put(1,"UnbreakableWall");
        objectTable.put(0,"");
    }

    MapLoader(InputStream in){
        mapReader = new BufferedReader(new InputStreamReader(in));
        imageSizeX = TRE.imgList.get("UnbreakableWall").getWidth();
        imageSizeY= TRE.imgList.get("UnbreakableWall").getHeight();
    }


    public void init(){
        try {
            String line = mapReader.readLine();
            System.out.println(line);
            Class<?> c;
            String className;
            GameObject gameObject;

            int currY = 0;
            int counter =0;

            while(line!=null){
                for(int i = 0; i < TRE.WORLD_WIDTH ; i+=imageSizeX) {
                    try {
                        className = objectTable.get(Integer.parseInt(String.valueOf(line.charAt(counter))));
                    }catch (Exception ex){
                        className = null;
                    }
                    if (className == null || className.equals("")) {
                        counter++;
                        continue;
                    }
                    c = Class.forName("src."+className);
                    gameObject = (GameObject) c.getConstructor().newInstance();
                    gameObject.init(i,currY,0,0,0,TRE.imgList.get(className));
                    TRE.addGameObject(gameObject);
                    TRE.addColliable((Collidable) gameObject);
                    counter++;
                }
                currY+=imageSizeY;
                counter = 0;
                line = mapReader.readLine();
            }
        }catch (Exception ex){
            System.out.println(ex);
        }
    }
}
