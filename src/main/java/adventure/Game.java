/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventure;

/**
 * @author Davide Porcelluzzi - Domenico Signorile
 */

import adventure.games.EscapeRooms;
import adventure.games.Interpreter;
import adventure.type.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.Clip;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;

public class Game {
    private static final String NO_DOOR = "Non hai ancora l'abilità di passare oltre le porte...\n";
    private static final String EMPTY_INV = "Non hai la chiave con te.\nTrova la chiave!";
    private static final String NO_CONT_OBJ = "Non c'è più niente da raccogliere in questo baule!";

    GameDescription gamed;
    GameObject go = new GameObject();
    Interpreter ip = new Interpreter();

    short lr_N_id = 0;
    short lr_E_id = 0;
    short lr_S_id = 0;
    short lr_W_id = 0;
    short lr_NW_id = 0;
    short lr_NE_id = 0;
    int o_id = 0;
    int inner_o;

    JFrame window;
    Container con;
    JPanel titleNamePanel, startButtonPanel, mainTextPanel, choiceButtonPanel, playerPanel, imagePanel;
    JLabel titleNameLabel, hpLabel, hpLabelNumber, timeLabel, timeLabelNumber, inventoryLabel, inventoryLabelName, imageLabel;
    Font titleFont = new Font("Times New Roman", Font.CENTER_BASELINE, 90);
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 25);
    JButton startButton, choice1, choice2, choice3, choice4, choice5, choice6;
    JTextArea mainTextArea;
    int playerHP, time;
    String inventory, position;

    TitleScreenHandler tsHandler = new TitleScreenHandler();
    ChoiceHandler choiceHandler = new ChoiceHandler();


    public Game(GameDescription game) {
        this.gamed = game;
        try {
            game.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        window = new JFrame();

        window.setSize(1366, 750);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if(gamed.getCurrentRoom().getId() == 1){
            window.getContentPane().setBackground(Color.black);
        }else if(gamed.getCurrentRoom().getId() == 2){
            window.getContentPane().setBackground(Color.gray);
        }else if(gamed.getCurrentRoom().getId() == 3){
            window.getContentPane().setBackground(Color.green);
        }

        window.setLayout(null);


        con = window.getContentPane();

        titleNamePanel = new JPanel();
        titleNamePanel.setBounds(325, 200, 700, 150);

        titleNamePanel.setBackground(Color.black);
        titleNameLabel = new JLabel("ESCAPE ROOM");
        titleNameLabel.setForeground(Color.white);
        titleNameLabel.setFont(titleFont);

        startButtonPanel = new JPanel();
        startButtonPanel.setBounds(580, 400, 200, 100);
        startButtonPanel.setBackground(Color.black);

        startButton = new JButton("START");
        startButton.setBackground(Color.black);
        startButton.setForeground(Color.white);
        startButton.setFont(normalFont);
        startButton.addActionListener(tsHandler);
        startButton.setFocusPainted(false);

        imagePanel = new JPanel();
        imagePanel.setBounds(650,450,240,180);
        imagePanel.setBackground(Color.black);
        imageLabel = new JLabel("");
        imageLabel.setBounds(650,450,240,180);
        imageLabel.setForeground(Color.black);
        imageLabel.setFont(titleFont);
        imageLabel.setHorizontalTextPosition(JLabel.CENTER);

        titleNamePanel.add(titleNameLabel);
        startButtonPanel.add(startButton);

        con.add(titleNamePanel);
        con.add(startButtonPanel);
        window.setVisible(true);
        playMusic("music\\Zack Hemsey - The Way.wav");
    }

    public static void main(String[] args) {

        Game game = new Game(new EscapeRooms());
        game.run();
    }

    public void run() {
        gamed.setGame(gamed);
    }

    public void createGameScreen() {

        titleNamePanel.setVisible(false);
        startButtonPanel.setVisible(false);

        mainTextPanel = new JPanel();
        mainTextPanel.setBounds(150, 100, 910, 350);

        if(gamed.getCurrentRoom().getId() == 1){
            mainTextPanel.setBackground(Color.black);
        }else if(gamed.getCurrentRoom().getId() == 2){
            mainTextPanel.setBackground(Color.gray);
        }else if(gamed.getCurrentRoom().getId() == 3){
            mainTextPanel.setBackground(Color.green);
        }

        con.add(mainTextPanel);

        mainTextArea = new JTextArea(gamed.getDescriptions().get(1));
        mainTextArea.setBounds(150, 100, 910, 350);
        if(gamed.getCurrentRoom().getId() == 1){
            mainTextArea.setBackground(Color.black);
            mainTextArea.setForeground(Color.white);
        }else if(gamed.getCurrentRoom().getId() == 2){
            mainTextArea.setBackground(Color.gray);
            mainTextArea.setForeground(Color.white);
        }else if(gamed.getCurrentRoom().getId() == 3){
            mainTextArea.setBackground(Color.green);
            mainTextArea.setForeground(Color.black);
        }


        mainTextArea.setFont(normalFont);
        mainTextArea.setLineWrap(true);
        mainTextPanel.add(mainTextArea);

        imagePanel.add(imageLabel);
        con.add(imagePanel);

        choiceButtonPanel = new JPanel();
        choiceButtonPanel.setBounds(250, 450, 400, 180);
        choiceButtonPanel.setBackground(Color.black);
        choiceButtonPanel.setLayout(new GridLayout(3, 1));
        con.add(choiceButtonPanel);

        choice1 = new JButton("Choice 1");
        choice1.setBackground(Color.black);
        choice1.setForeground(Color.white);
        choice1.setFont(normalFont);
        choice1.setFocusPainted(false);
        choice1.addActionListener(choiceHandler);
        choice1.setActionCommand("c1");
        choiceButtonPanel.add(choice1);
        choice2 = new JButton("Choice 2");
        choice2.setBackground(Color.black);
        choice2.setForeground(Color.white);
        choice2.setFont(normalFont);
        choice2.setFocusPainted(false);
        choice2.addActionListener(choiceHandler);
        choice2.setActionCommand("c2");
        choiceButtonPanel.add(choice2);
        choice3 = new JButton("Choice 3");
        choice3.setBackground(Color.black);
        choice3.setForeground(Color.white);
        choice3.setFont(normalFont);
        choice3.setFocusPainted(false);
        choice3.addActionListener(choiceHandler);
        choice3.setActionCommand("c3");
        choiceButtonPanel.add(choice3);
        choice4 = new JButton("Choice 4");
        choice4.setBackground(Color.black);
        choice4.setForeground(Color.white);
        choice4.setFont(normalFont);
        choice4.setFocusPainted(false);
        choice4.addActionListener(choiceHandler);
        choice4.setActionCommand("c4");
        choiceButtonPanel.add(choice4);
        choice5 = new JButton("Choice 5");
        choice5.setBackground(Color.black);
        choice5.setForeground(Color.white);
        choice5.setFont(normalFont);
        choice5.setFocusPainted(false);
        choice5.addActionListener(choiceHandler);
        choice5.setActionCommand("c5");
        choiceButtonPanel.add(choice5);
        choice6 = new JButton("Choice 6");
        choice6.setBackground(Color.black);
        choice6.setForeground(Color.white);
        choice6.setFont(normalFont);
        choice6.setFocusPainted(false);
        choice6.addActionListener(choiceHandler);
        choice6.setActionCommand("c6");
        choiceButtonPanel.add(choice6);

        playerPanel = new JPanel();
        playerPanel.setBounds(100, 15, 1200, 50);
        playerPanel.setBackground(Color.black);
        playerPanel.setLayout(new GridLayout(1, 3));
        con.add(playerPanel);

        hpLabel = new JLabel("HP:");
        hpLabel.setFont(normalFont);
        hpLabel.setForeground(Color.white);
        playerPanel.add(hpLabel);
        hpLabelNumber = new JLabel();
        hpLabelNumber.setFont(normalFont);
        hpLabelNumber.setForeground(Color.white);
        playerPanel.add(hpLabelNumber);

        timeLabel = new JLabel("Time:");
        timeLabel.setFont(normalFont);
        timeLabel.setForeground(Color.white);
        playerPanel.add(timeLabel);
        timeLabelNumber = new JLabel();
        timeLabelNumber.setFont(normalFont);
        timeLabelNumber.setForeground(Color.white);
        playerPanel.add(timeLabelNumber);

        inventoryLabel = new JLabel("Inventory: ");
        inventoryLabel.setFont(normalFont);
        inventoryLabel.setForeground(Color.white);
        playerPanel.add(inventoryLabel);
        inventoryLabelName = new JLabel();
        inventoryLabelName.setFont(normalFont);
        inventoryLabelName.setForeground(Color.white);
        playerPanel.add(inventoryLabelName);

        playerSetup();
    }

    private void scaleImage(String s){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(s));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(imagePanel.getWidth(), imagePanel.getHeight(),Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(dimg);
        imageLabel.setIcon(scaledIcon);
    }

    public void playerSetup() {
        playerHP = 100;
        time = 80;
        inventory = " ";
        String ap = " ";
        inventoryLabelName.setText(inventory);
        hpLabelNumber.setText(ap + playerHP);
        hpLabelNumber.setForeground(Color.green);
        timeLabelNumber.setText(ap + time);
        startRoom();
    }

    public int getO_id() {
        int i = 0;
        boolean found = false;
        o_id = gamed.getCurrentLogicRoom().getFirstObject();
        int f_id = gamed.getCurrentLogicRoom().getFirstObject();
        while (i < gamed.getCurrentLogicRoom().getObjects().size() && found == false) {
            if (gamed.getCurrentLogicRoom().getObjects().get(i).getID() == f_id) {
                go = gamed.getGame_obj().get(o_id);
                if (go.isIs_container()) {
                    inner_o = go.getInner_Object();
                    found = true;
                    return inner_o;
                }
                found = true;
            } else {
                i++;
            }
        }
        return o_id;
    }

    public void startRoom() {
        position = "startRoom";
        Logic_Room start_id;
        short start_lr_id = 0;
        start_id = gamed.getLogicRoom().get(start_lr_id);
        gamed.setCurrentLogicRoom(start_id);

        String desc = gamed.getDescriptions().get(1);
        mainTextArea.setText(desc);
        choice1.setText("Next");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");
        choice2.setVisible(false);
        choice3.setVisible(false);
        choice4.setVisible(false);
        choice5.setVisible(false);
        choice6.setVisible(false);
    }

    public void descRoom() {
        position = "descRoom";
        scaleImage("image project\\closed prison.png\\");
        String desc = gamed.getDescriptions().get(2);
        mainTextArea.setText(desc);
        choice1.setText("Read");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");
    }

    public void startPaperRoom() {
        position = "paperRoom";
        Logic_Room paper_room_id;
        short paper_lr_id = 1;
        paper_room_id = gamed.getLogicRoom().get(paper_lr_id);
        gamed.setCurrentLogicRoom(paper_room_id);
        int ob_id = gamed.getCurrentLogicRoom().getFirstObject();
        GameObject p_o = gamed.getGame_obj().get(ob_id);
        String desc = p_o.getObjDescription();
        scaleImage("image project\\pergamena.png\\");
        mainTextArea.setText(desc);
        choice1.setText("Next");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");
    }

    public Room crossRoad() {
        position = "crossRoad";
        choice2.setVisible(true);
        choice3.setVisible(true);
        choice4.setVisible(true);
        choice5.setVisible(true);
        choice6.setVisible(true);

        scaleImage("image project\\crossorad3.png\\");

        if(gamed.getCurrentRoom().getId() == 1){
            window.getContentPane().setBackground(Color.black);
            mainTextArea.setBackground(Color.black);
            mainTextArea.setForeground(Color.white);
            mainTextPanel.setBackground(Color.black);
        }else if(gamed.getCurrentRoom().getId() == 2){
            window.getContentPane().setBackground(Color.gray);
            mainTextArea.setBackground(Color.gray);
            mainTextArea.setForeground(Color.black);
            mainTextPanel.setBackground(Color.gray);
        }else if(gamed.getCurrentRoom().getId() == 3){
            window.getContentPane().setBackground(Color.green);
            mainTextArea.setBackground(Color.green);
            mainTextArea.setForeground(Color.black);
            mainTextPanel.setBackground(Color.green);
        }


        if (gamed.getCurrentRoom().getId() == 1) {

            mainTextArea.setText(gamed.getDescriptions().get(5));
            choice1.setText("Go North");
            choice2.setText("Go East");
            choice3.setText("Go South");
            choice4.setText("Go West");
            choice5.setText("");
            choice6.setText("");
            choice5.setVisible(false);
            choice6.setVisible(false);

        } else {
            if (gamed.getCurrentRoom().getId() == 2) {
                mainTextArea.setText(gamed.getDescriptions().get(21));
                choice1.setText("Go North");
                choice2.setText("Go East");
                choice3.setText("Go South");
                choice4.setText("Go West");
                choice5.setText("Go North West");
                choice6.setText("Go North East");
                choice5.setVisible(true);
                choice6.setVisible(true);

            } else if (gamed.getCurrentRoom().getId() == 3) {
                mainTextArea.setText(gamed.getDescriptions().get(33));
                choice1.setText("Go North");
                choice2.setText("Go East");
                choice3.setText("Go South");
                choice4.setText("Go West");
                choice5.setText("Go North West");
                choice6.setText("Go North East");
                choice5.setVisible(true);
                choice6.setVisible(true);
            }
        }
        return gamed.getCurrentRoom();
    }

    public void north() {

        position = "north";
        Logic_Room NORTH = ip.north(gamed, lr_N_id);

        mainTextArea.setText(NORTH.getDescription());

        time = time - 5;
        timeLabelNumber.setText("" + time);
        if (gamed.getCurrentRoom().getId() == 1) {
            scaleImage("image project\\closed prison.png\\");
            mainTextArea.setText(NORTH.getDescription() + "\n\n" + gamed.getDescriptions().get(15));
            choice1.setText("Insert");
            choice2.setText("Push");
            choice3.setText("Back");
            choice4.setText("");
            choice5.setText("");
            choice6.setText("");
        } else if (gamed.getCurrentRoom().getId() == 2 || gamed.getCurrentRoom().getId() == 3) {
            scaleImage("image project\\closed door.png\\");
            choice1.setText("Insert");
            choice2.setText("Back");
            choice3.setText("");
            choice4.setText("");
            choice5.setText("");
            choice6.setText("");
        }

    }

    public void east() {

        position = "east";
        Logic_Room EAST = ip.east(gamed, lr_E_id);

        time = time - 5;
        timeLabelNumber.setText("" + time);
        scaleImage("image project\\closedsettle2.png\\");
        mainTextArea.setText(EAST.getDescription());

        choice1.setText("Open");
        choice2.setText("Back");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");

    }

    public void west() {
        position = "west";
        Logic_Room WEST = ip.west(gamed, lr_W_id);
        time = time - 5;
        timeLabelNumber.setText("" + time);
        scaleImage("image project\\closedsettle2.png\\");
        mainTextArea.setText(WEST.getDescription());
        choice1.setText("Open");
        choice2.setText("Back");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");
    }

    public void south() {
        position = "south";
        Logic_Room SOUTH = ip.south(gamed, lr_S_id);

        if (gamed.getCurrentRoom().getId() == 1) {
            time = time - 5;
            timeLabelNumber.setText("" + time);
            scaleImage("image project\\closedsettle2.png\\");
            mainTextArea.setText(SOUTH.getDescription());
            choice1.setText("Open");
            choice2.setText("Back");
            choice3.setText("");
            choice4.setText("");
            choice5.setText("");
            choice6.setText("");
        } else {
            choice1.setText("Back");
            choice2.setText("");
            choice3.setText("");
            choice4.setText("");
            choice5.setText("");
            choice6.setText("");
        }
    }

    public void north_east() {
        if (gamed.getCurrentRoom().getId() != 1) {
            position = "north_east";
            Logic_Room NORTH_EAST = ip.north_east(gamed, lr_NE_id);
            time = time - 5;
            timeLabelNumber.setText("" + time);
            scaleImage("image project\\closedsettle2.png\\");
            mainTextArea.setText(NORTH_EAST.getDescription());

            choice1.setText("Open");
            choice2.setText("Back");
            choice3.setText("");
            choice4.setText("");
            choice5.setText("");
            choice6.setText("");
        }
    }

    public void north_west() {
        if (gamed.getCurrentRoom().getId() != 1) {
            position = "north_west";
            Logic_Room NORTH_WEST = ip.north_west(gamed, lr_NW_id);
            time = time - 5;
            timeLabelNumber.setText("" + time);
            scaleImage("image project\\closedsettle2.png\\");
            mainTextArea.setText(NORTH_WEST.getDescription());

            choice1.setText("Open");
            choice2.setText("Back");
            choice3.setText("");
            choice4.setText("");
            choice5.setText("");
            choice6.setText("");
        }
    }

    public void insert() {
        position = "insert";

        if (!gamed.getInventory().isEmpty()) {
            Room new_r = ip.insert(gamed);
            if(gamed.getCurrentLogicRoom().getId() != 12){
                String desc_s = new_r.getName();
                mainTextArea.setText("Sei entrato nella " + desc_s);
            }
            if(gamed.getCurrentLogicRoom().getId() == 4){
                scaleImage("image project\\open prison.png\\");
            }else {
                scaleImage("image project\\open door.png\\");
            }

            choice1.setText("Next Room");
            choice2.setText("");
            choice3.setText("");
            choice4.setText("");
            choice5.setText("");
            choice6.setText("");
        }else if (gamed.getInventory().isEmpty()) {

            mainTextArea.setText(NO_DOOR + EMPTY_INV);
            choice1.setText("Back");
            choice2.setText("");
            choice3.setText("");
            choice4.setText("");
            choice5.setText("");
            choice6.setText("");
        }
    }

    public void push() {
        position = "push";
        Room new_r = ip.push(gamed);
        playerHP = playerHP - 15;
        hpLabelNumber.setText("" + playerHP);
        inventoryLabelName.setText("");
        String desc_s = new_r.getName();
        mainTextArea.setText(gamed.getDescriptions().get(16)+ "\n" + gamed.getDescriptions().get(17) + "\n\n" +"Sei entrato nella " + desc_s);
        choice1.setText("Next Room");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");
    }

    public void open() {
        position = "open";
        String desc_s = ip.open(gamed, o_id);

        scaleImage("image project\\chest_open.png\\");
        mainTextArea.setText(desc_s);

        GameObject obj = gamed.getCurrentLogicRoom().getObjects().get(0);

        if (obj instanceof gameObjectContainer) {
            gameObjectContainer container = (gameObjectContainer) obj;
            try {

                GameObject containedObject = container.getContainerList().get(0);
                if (containedObject.isReadable()) {
                    containedObject.setVisible(true);
                    choice1.setText("Read");
                    choice2.setText("Back");
                    choice3.setText("");
                    choice4.setText("");
                    choice5.setText("");
                    choice6.setText("");
                } else if (containedObject.isUsable()) {
                    containedObject.setVisible(true);
                    choice1.setText("Use");
                    choice2.setText("Back");
                    choice3.setText("");
                    choice4.setText("");
                    choice5.setText("");
                    choice6.setText("");
                } else if (containedObject.isPickable()) {
                    containedObject.setVisible(true);
                    choice1.setText("Pick Up");
                    choice2.setText("Back");
                    choice3.setText("");
                    choice4.setText("");
                    choice5.setText("");
                    choice6.setText("");
                }
            }catch (Exception e){
                mainTextArea.setText(desc_s + "\n" + NO_CONT_OBJ);
            }
        } else {
            if(obj.isOpen()){
                if(playerHP < 15){
                    playerHP = 0;
                    hpLabelNumber.setText("" + playerHP);
                    lose();
                }else {

                    playerHP = playerHP - 15;
                    if(playerHP < 20){
                        hpLabelNumber.setForeground(Color.red);
                    }
                    hpLabelNumber.setText("" + playerHP);
                }
            }
            choice1.setText("");
            choice2.setText("Back");
            choice3.setText("");
            choice4.setText("");
            choice5.setText("");
            choice6.setText("");
        }
    }

    public void read() {
        position = "read";
        String desc_o = ip.read(gamed);
        scaleImage("image project\\pergamena.png\\");
        mainTextArea.setText(desc_o);
        choice1.setText("Back");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");
    }

    public void use() {
        position = "use";

        gameObjectContainer container = (gameObjectContainer) gamed.getCurrentLogicRoom().getObjects().get(0);
        GameObject potion = container.getContainerList().get(0);
        String desc_o = ip.use(gamed);
        scaleImage("image project\\potion.png\\");
        mainTextArea.setText(desc_o);

        if (gamed.getGame_obj().get(18).equals(potion) || gamed.getGame_obj().get(17).equals(potion)) {
            if(playerHP < 15){
                playerHP = 0;
                hpLabelNumber.setText("" + playerHP);
                lose();
            }else {
                playerHP = playerHP - 15;
                if(playerHP < 20){
                    hpLabelNumber.setForeground(Color.red);
                }
                hpLabelNumber.setText("" + playerHP);
            }

        } else if (gamed.getGame_obj().get(19).equals(potion)){
            timeLabelNumber.setText(" ");
            timeLabelNumber.setVisible(false);
            time = time + 10000;
        }

        choice1.setText("Back");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");
    }

    public void pick_up() {
        position = "pick_up";
        String desc_o = ip.pick_up(gamed);
        scaleImage("image project\\old key.png\\");
        mainTextArea.setText(desc_o);
        inventoryLabelName.setForeground(Color.yellow);
        inventoryLabelName.setText("Key " + gamed.getCurrentRoom().getId());
        choice1.setText("Back");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");
    }

    public void win() {

        scaleImage("image project\\youwin.png\\");
        mainTextArea.setText(gamed.getDescriptions().get(47));

        choice1.setText("");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");
        choice1.setVisible(false);
        choice2.setVisible(false);
        choice3.setVisible(false);
        choice4.setVisible(false);
        choice5.setVisible(false);
        choice6.setVisible(false);
    }

    public void lose() {
        position = "lose";
        String desc = "";
        if (time < 1) {
            desc = gamed.getDescriptions().get(50);
        } else if (playerHP < 1) {
            desc = gamed.getDescriptions().get(49);
        }
        scaleImage("image project\\game over.png\\");
        mainTextArea.setText(desc);

        choice1.setText("Exit");
        choice2.setText("");
        choice3.setText("");
        choice4.setText("");
        choice5.setText("");
        choice6.setText("");

        choice2.setVisible(false);
        choice3.setVisible(false);
        choice4.setVisible(false);
        choice5.setVisible(false);
        choice6.setVisible(false);
    }

    private GameObject getObjCurrentLogicRoom(){
        gameObjectContainer container = (gameObjectContainer) gamed.getCurrentLogicRoom().getObjects().get(0);
        return container.getContainerList().get(0);

    }

   private void playMusic(String musicLocation) {
        try
        {
            File musicPath = new File(musicLocation);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
        }
        catch(Exception e)
        {
            System.out.println("Can't find the file");
        }

    }


    public class TitleScreenHandler implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            createGameScreen();
        }
    }

    public class ChoiceHandler implements ActionListener {

        public void actionPerformed(ActionEvent event) {

            String yourChoice = event.getActionCommand();
			int o = getO_id();

            switch (position) {
                case "startRoom":
                    switch (yourChoice) {
                        case "c1":
                            descRoom();
                            break;
                    }
                    break;

                case "descRoom":
                    switch (yourChoice) {
                        case "c1":
                            startPaperRoom();
                            break;
                    }
                    break;

                case "paperRoom":
                    switch (yourChoice) {
                        case "c1":
                            crossRoad();
                            break;
                    }
                    break;

                case "crossRoad":
                    if (gamed.getCurrentRoom().getId() == 1) {
                        switch (yourChoice) {
                            case "c1":
                                if (playerHP < 1 || time < 1) {
                                    lose();
                                } else {
                                    north();
                                }
                                break;
                            case "c2":
                                if (playerHP < 1 || time < 1) {
                                    lose();
                                } else {
                                    east();
                                }
                                break;
                            case "c3":
                                if (playerHP < 1 || time < 1) {
                                    lose();
                                } else {
                                    south();
                                }
                                break;
                            case "c4":
                                if (playerHP < 1 || time < 1) {
                                    lose();
                                } else {
                                    west();
                                }
                                break;
                        }
                    } else if (gamed.getCurrentRoom().getId() == 2 || gamed.getCurrentRoom().getId() == 3) {
                        switch (yourChoice) {
                            case "c1":
                                if (playerHP < 1 || time < 1) {
                                    lose();
                                } else {
                                    north();
                                }
                                break;
                            case "c2":
                                if (playerHP < 1 || time < 1) {
                                    lose();
                                } else {
                                    east();
                                }
                                break;
                            case "c3":
                                if (playerHP < 1 || time < 1) {
                                    lose();
                                } else {
                                    south();
                                }
                                break;
                            case "c4":
                                if (playerHP < 1 || time < 1) {
                                    lose();
                                } else {
                                    west();
                                }
                                break;
                            case "c5":
                                if (playerHP < 1 || time < 1) {
                                    lose();
                                } else {
                                    north_west();
                                }
                                break;
                            case "c6":
                                if (playerHP < 1 || time < 1) {
                                    lose();
                                } else {
                                    north_east();
                                }
                                break;
                        }
                    }

                    break;
                case "north":
                    if (gamed.getCurrentRoom().getId() == 1) {
                        switch (yourChoice) {
                            case "c1":
                                insert();
                                inventoryLabelName.setText(" ");
                                break;
                            case "c2":
                                push();
                                break;
                            case "c3":
                                crossRoad();
                                break;
                        }
                    } else if (gamed.getCurrentRoom().getId() == 2 || gamed.getCurrentRoom().getId() == 3) {
                        switch (yourChoice) {
                            case "c1":
                                if (playerHP < 1 || time < 1) {
                                    lose();
                                } else {
                                    insert();
                                    inventoryLabelName.setText(" ");
                                }
                                break;
                            case "c2":
                                if (playerHP < 1 || time < 1) {
                                    lose();
                                } else {
                                    crossRoad();
                                }
                                break;
                        }
                    }
                    break;

                case "east":
                    switch (yourChoice) {
                        case "c1":
                            if (playerHP < 1 || time < 1) {
                                lose();
                            } else {
                                open();
                            }
                            break;
                        case "c2":
                            if (playerHP < 1 || time < 1) {
                                lose();
                            } else {
                                crossRoad();
                            }
                            break;
                    }
                    break;

                case "south":
                    if (gamed.getCurrentRoom().getId() == 1) {
                        switch (yourChoice) {
                            case "c1":
                                if (playerHP < 1 || time < 1) {
                                    lose();
                                } else {
                                    open();
                                }
                                break;

                            case "c2":
                                if (playerHP < 1 || time < 1) {
                                    lose();
                                } else {
                                    crossRoad();
                                }
                                break;
                        }
                    } else if (gamed.getCurrentRoom().getId() == 2 || gamed.getCurrentRoom().getId() == 3) {
                        switch (yourChoice) {
                            case "c1":
                                if (playerHP < 1 || time < 1) {
                                    lose();
                                } else {
                                    crossRoad();
                                }
                                break;
                        }
                    }
                    break;

                case "west":
                    switch (yourChoice) {
                        case "c1":
                            if (playerHP < 1 || time < 1) {
                                lose();
                            } else {
                                open();
                            }
                            break;

                        case "c2":
                            if (playerHP < 1 || time < 1) {
                                lose();
                            } else {
                                crossRoad();
                            }
                            break;
                    }
                    break;

                case "north_east":
                    switch (yourChoice) {
                        case "c1":
                            if (playerHP < 1 || time < 1) {
                                lose();
                            } else {
                                open();
                            }
                            break;

                        case "c2":
                            if (playerHP < 1 || time < 1) {
                                lose();
                            } else {
                                crossRoad();
                            }
                            break;
                    }
                    break;

                case "north_west":
                    switch (yourChoice) {
                        case "c1":
                            if (playerHP < 1 || time < 1) {
                                lose();
                            } else {
                                open();
                            }
                            break;

                        case "c2":
                            if (playerHP < 1 || time < 1) {
                                lose();
                            } else {
                                crossRoad();
                            }
                            break;
                    }
                    break;

                case "open":

                        switch (yourChoice) {
                            case "c1":

                                if (getObjCurrentLogicRoom().isVisible() && getObjCurrentLogicRoom().isReadable()) {
                                    read();
                                } else if (getObjCurrentLogicRoom().isVisible() && getObjCurrentLogicRoom().isUsable()) {
                                    use();
                                } else if (getObjCurrentLogicRoom().isVisible() && getObjCurrentLogicRoom().isPickable()) {
                                    pick_up();
                                }
                                break;
                            case "c2":
                                crossRoad();
                                break;
                        }

                    break;

                case "read":
                    switch (yourChoice) {
                        case "c1":
                            crossRoad();
                            break;
                    }
                    break;

                case "use":
                    switch (yourChoice) {
                        case "c1":
                            crossRoad();
                            break;
                    }
                    break;

                case "pick_up":
                    switch (yourChoice) {
                        case "c1":
                            crossRoad();
                            break;
                    }
                    break;

                case "insert":
                    switch (yourChoice) {
                        case "c1":
                            if (gamed.getCurrentLogicRoom().getId() == 12 && !gamed.getCurrentLogicRoom().getDoors().get(0).isLocked()) {
                                win();
                            } else if (gamed.getInventory().isEmpty()) {
                                //mainTextArea.setText(NO_DOOR + EMPTY_INV);
                                crossRoad();
                            } else if (!gamed.getInventory().isEmpty()) {
                                crossRoad();
                            }
                            break;

                    }
                    break;

                case "push":
                    switch (yourChoice) {
                        case "c1":
                            crossRoad();
                            break;
                    }
                    break;

                case "lose":
                    switch (yourChoice) {
                        case "c1":
                            System.exit(0);
                            break;
                    }
                    break;


            }
        }
    }
}
