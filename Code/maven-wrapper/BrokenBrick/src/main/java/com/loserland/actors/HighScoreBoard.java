package com.loserland.actors;
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.*;
import java.net.*;
import com.loserland.configs.*;
import com.loserland.context.GameContext;

public class HighScoreBoard extends Actor implements IObserver {

    private static HighScoreBoard instance = new HighScoreBoard();
    private Config config = ConfigFactory.getInstance().getConfig(GameContext.GAME_DEFAULT_CONFIG_FILENAME);
    private List<HighScore> scoreList;
    int score;
    FileWriter fw = null;
    InputStream inputStream;
    OutputStream outputStream;
    String[] rank = new String[]{"1ST","2ND","3RD","4TH","5TH","6TH","7TH","8TH","9TH","10TH"};
    Color[] color = new Color[]{Color.WHITE,Color.WHITE,Color.RED,Color.RED, Color.ORANGE,
            Color.ORANGE, Color.GREEN, Color.GREEN, Color.BLUE, Color.BLUE};
    int WIDTH = config.get(Integer.class,GameContext.WORLD_WIDTH);
    int HEIGHT = config.get(Integer.class,GameContext.WORLD_HEIGHT);
    int fontsize = config.get(Integer.class,GameContext.HIGHSCORE_FONT_SIZE);


    public HighScoreBoard()
    {
        scoreList = new ArrayList<>();
        ReadScoreFromFile();
        ShowScore();
    }

    public static HighScoreBoard getInstance(){return instance;}


    @Override
    public void update(int s) {
        score = s ;
    }


    public void SaveScore(){
        addChild(new HighScore(score));
        SortScore();
        WriteScoreToFile();
    }

    public void ShowScore(){
        SortScore();
        GreenfootImage image = new GreenfootImage(config.get(GameContext.SCORE_BG_IMG));
//        image.fillRect(1, 1, WIDTH, HEIGHT);
        Font myFont = new Font("Tahoma", true, false, fontsize);
        image.setFont(myFont);

        image.setColor(Color.CYAN);
        image.drawString("HIGH SCORES", (int) Math.round(WIDTH/2.6) , (int) Math.round(HEIGHT/10) );

        image.setColor(Color.YELLOW);
        image.drawString("RANK", (int) Math.round(WIDTH*0.12) , (int) Math.round(HEIGHT*0.25));
        image.drawString("SCORE", (int) Math.round(WIDTH*0.4) , (int) Math.round(HEIGHT*0.25));
        image.drawString("TIME", (int) Math.round(WIDTH*0.7) , (int) Math.round(HEIGHT*0.25));


        int count = 0;
        int offset  = (int) Math.round(HEIGHT*0.06);
        int y = (int) Math.round(HEIGHT*0.35);
        for (HighScore obj  : scoreList)
        {
            if(count == 10){
                break;
            }
            image.setColor(color[count]);
            image.drawString(""+ rank[count], (int) Math.round(WIDTH*0.12)+fontsize/2, y );
            image.drawString(""+ obj.getScore(),  (int) Math.round(WIDTH*0.4)+fontsize/2 , y );
            image.drawString(""+ obj.gettime(),  (int) Math.round(WIDTH*0.7)-(int)(fontsize*3.5), y );
            count++;
            y += offset;
        }
        setImage(image);
    }


    public void SortScore(){
        Collections.sort(scoreList, new Comparator<HighScore>(){
        // Used for sorting in decending order of
            @Override
            public int compare(HighScore a, HighScore b)
            {
                return b.getScore() - a.getScore();
            }
         });
    }




    public void addChild(HighScore s) {
        scoreList.add(s);
    }

    public void removeChild(HighScore s) {
        scoreList.remove(s);
    }

    private void ReadScoreFromFile(){
        try {
            inputStream = getClass().getClassLoader().getResourceAsStream(config.get(String.class,GameContext.GAME_MENU_HIGHSCORE));
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String str;
            while ((str = reader.readLine())!=null) {
                String[] parts = str.split(",");
                addChild(new HighScore(Integer.parseInt(parts[0]),parts[1]));
            }

            inputStream.close();
        } catch(Exception e) {
            // if any I/O error occurs
            e.printStackTrace();
        }
    }

    private void WriteScoreToFile(){
        int count = 0;
        try {
            URL resourceUrl = getClass().getClassLoader().getResource(config.get(String.class,GameContext.GAME_MENU_HIGHSCORE));
            File file = new File(resourceUrl.toURI());
            outputStream = new FileOutputStream(file);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            for (HighScore obj : scoreList) {
                if(count == 10){
                    break;
                }
                writer.write(obj.getScore()+","+obj.gettime());
                writer.newLine();
                writer.flush();
                count++;
            }
            writer.close();
            //fw.close();
        }catch(Exception e) {
            // if any I/O error occurs
            e.printStackTrace();
        }
    }
}
