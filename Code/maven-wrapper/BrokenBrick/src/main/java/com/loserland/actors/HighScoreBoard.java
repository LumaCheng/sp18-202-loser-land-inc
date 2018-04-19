package com.loserland.actors;
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.*;
import java.net.*;

public class HighScoreBoard extends Actor {

    private String HIGHSCORE_FILE;
    private List<HighScore> scoreList;
    FileWriter fw = null;
    InputStream inputStream;
    OutputStream outputStream;
    String[] rank = new String[]{"1ST","2ND","3RD","4TH","5TH","6TH","7TH","8TH","9TH","10TH"};
    Color[] color = new Color[]{Color.WHITE,Color.WHITE,Color.RED,Color.RED, Color.ORANGE,
            Color.ORANGE, Color.GREEN, Color.GREEN, Color.BLUE, Color.BLUE};


    public HighScoreBoard()
    {
        scoreList = new ArrayList<>();
        ReadScoreFromFile();
        //ShowScore();
    }


    public void act() {

    }

    public void SaveScore(int s){
        addChild(new HighScore(s));
        SortScore();
        WriteScoreToFile();
    }

    public void ShowScore(){
        SortScore();
        GreenfootImage image = new GreenfootImage(700, 520);
        image.setColor(Color.BLACK);
        image.fillRect(1, 1, 700, 520);
        Font myFont = new Font("Tahoma", true, false, 20);
        image.setFont(myFont);

        image.setColor(Color.CYAN);
        image.drawString("HIGH SCORES", 270 , 50);

        image.setColor(Color.YELLOW);
        image.drawString("RANK", 80 , 130);
        image.drawString("SCORE", 280 , 130);
        image.drawString("TIME", 510 , 130);


        int count = 0;
        int offset  = 30;
        int y = 180;
        for (HighScore obj  : scoreList)
        {
            if(count == 10){
                break;
            }
            image.setColor(color[count]);
            image.drawString(""+ rank[count], 90 , y );
            image.drawString(""+ obj.getScore(),  290 , y );
            image.drawString(""+ obj.gettime(),  440, y );
            count++;
            y += offset;
        }
        setImage(image);
    }

    public void SettingFile(String file){
        HIGHSCORE_FILE = file;
    }

    public void SortScore(){
        Collections.sort(scoreList, new SortbyScore());
    }

    class SortbyScore implements Comparator<HighScore>
    {
        // Used for sorting in decending order of
        public int compare(HighScore a, HighScore b)
        {
            return b.getScore() - a.getScore();
        }
    }


    public void addChild(HighScore s) {
        scoreList.add(s);
    }

    public void removeChild(HighScore s) {
        scoreList.remove(s);
    }

    private void ReadScoreFromFile(){
        try {
            //inputStream = new FileInputStream(HIGHSCORE_FILE);
            inputStream = getClass().getClassLoader().getResourceAsStream("configs/highscore.dat");
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
            URL resourceUrl = getClass().getClassLoader().getResource("configs/highscore.dat");
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
