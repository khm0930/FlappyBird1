import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;




public class FlappybirdGame extends JFrame {
   DrawPanel dp;

   static String player_name; //�÷��̾� �̸�

   static int PANEL_WIDTH = 1500; //�г� ����ũ��
   static int PANEL_HEIGHT = 500; //�г� ����ũ��

   static int FRAME_WIDTH = PANEL_WIDTH + 16; //������ ����ũ��
   static int FRAME_HEIGHT = PANEL_HEIGHT + 39; //������ ����ũ��

   static int BIRD_WIDTH = 20; //�� ����ũ��
   static int BIRD_HEIGHT = 35; //�� ����ũ��   

   //���ӻ���
   ImageIcon startImage = new ImageIcon(("���� ���� ȭ��.jpg")); 
   ImageIcon gameImage = new ImageIcon(("���ӹ��ȭ��.jpg"));
   ImageIcon explainImage = new ImageIcon(("���ӹ��3.png"));
   ImageIcon endImage = new ImageIcon(("���ӿ���3.png"));
   ImageIcon endImage2 = new ImageIcon(("����Ŭ����1.png"));
   
   ImageIcon b_right = new ImageIcon(("��3.png"));
   ImageIcon b_rightfall = new ImageIcon(("�������»�3.png"));
   ImageIcon b_rightJump = new ImageIcon(("������1.png"));

   
   

   Image heart = new ImageIcon(("���.png")).getImage();
   Image coin = new ImageIcon(("����.gif")).getImage();
   Image downpipe = new ImageIcon(("��������.png")).getImage();
   Image uppipe = new ImageIcon(("����������.png")).getImage();

   Image backImg = gameImage.getImage();
   Image img = b_right.getImage();

   ArrayList<Coin> listC = new ArrayList<Coin>();       //�׸� ����Ʈ
   ArrayList<players> listN = new ArrayList<players>();
   ArrayList<downpipe> listP = new ArrayList<downpipe>();
   ArrayList<uppipe> listP2 = new ArrayList<uppipe>();
   ArrayList<Heart> listH = new ArrayList<Heart>();


   ArrayList<Heart> HRemove = new ArrayList<>();      //�׸� ���� ����Ʈ
   ArrayList<Coin> CRemove = new ArrayList<>();
   ArrayList<downpipe> PRemove = new ArrayList<>();
   ArrayList<uppipe> PRemove2 = new ArrayList<>();





   int score = 0; //����
   int level = 1; 
   int INDEX = 1;   //����
   int count = 0; 
   

   JLabel start = new JLabel(); 
   JLabel game = new JLabel();
   JLabel nextgame = new JLabel();
   JLabel nameLabel;
   JLabel scoreLabel;
   JLabel levelLabel;
   JLabel lifeLabel;
   JLabel endLabel;
   JLabel explainLabel;
   JLabel endLabel2;

   Bird bird;
   Sound sound;

   Timer t;




   JButton howButton,startButton,nextButton;

   boolean up,right,left = false;
   boolean fall = true;
   boolean jump;
   boolean howButtonb = false;


   public class Sound{
      //�������
      
      public static void bgm() {
            
         try {
            File bgm = new File("���ӹ��1.wav");
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(bgm));
            clip.loop(10);
            clip.start();
            
         }catch(Exception e) {
            System.out.println("Sound Error bgm");
         }

      }
   }

   //����ȿ����
   public static void coin() {
      try {
         File bgm = new File( "���μҸ�.wav");
         Clip clip = AudioSystem.getClip();
         clip.open(AudioSystem.getAudioInputStream(bgm));
         clip.loop(0);
         clip.start();
      }catch(Exception e) {
         System.out.println("Sound Error bgm");
      }
   }

   //����ȿ����
   public static void jump() {
      try {
         File bgm = new File("����14.wav");
         Clip clip = AudioSystem.getClip();
         clip.open(AudioSystem.getAudioInputStream(bgm));
         clip.loop(0);
         clip.start();
      }catch(Exception e) {
         System.out.println("Sound Error bgm");
      }
   }


   FlappybirdGame(){
       sound = new Sound();

      nameInput(); //�̸��Է� �ޱ�

      dp = new DrawPanel();
      bird = new Bird(15,250);
      dp.add(bird);
      sound.bgm();

      t = new Timer(10, new Draw()); //0.1�ʸ��� �׷��ֱ�
      dp.addKeyListener(dp); 
      dp.setLayout(null); 

      //�̹��� ��ư�� ����ϱ� ���� ����
      startButton = new JButton(new ImageIcon("���ӽ��۹�ư.png"));
      startButton.setBorderPainted(false);
      startButton.setFocusPainted(false);
      startButton.setContentAreaFilled(false);
      
      
      nextButton = new JButton(new ImageIcon("�̾��ϱ�.PNG"));
      nextButton.setBorderPainted(false);
      nextButton.setFocusPainted(false);
      nextButton.setContentAreaFilled(false);
      nextButton.setVisible(false);
      
      
      howButton = new JButton(new ImageIcon("���Ӽ�����ư.png"));
      howButton.setBorderPainted(false);
      howButton.setFocusPainted(false);
      howButton.setContentAreaFilled(false);
      
      


      //��ư ��ġ�� ������
      startButton.setBounds(165,300,150,50);
      nextButton.setBounds(1100,200,150,50);
      howButton.setBounds(165,350,170,50);

      //��ư�� ������ �߰�
      ButtonListener bl = new ButtonListener();
      startButton.addActionListener(bl);
      nextButton.addActionListener(bl);
      howButton.addActionListener(bl);
   


      dp.add(startButton);
      dp.add(howButton);
      dp.add(nextButton);

      start.setIcon(startImage);   //��ŸƮ�󺧿� ��ŸƮ�̹��� �ֱ�
      
      dp.add(start);   //��ŸƮ �� ��ο� �гο� �ֱ�
      
      start.setBounds(0,0,PANEL_WIDTH,PANEL_HEIGHT);   //��ŸƮ�� �г� ũ��� ���߱�
      

      nameLabel = new JLabel("NAME: " + player_name);
      scoreLabel = new JLabel("SCORE: " + score);
      levelLabel = new JLabel("LEVEL: " + level);
      lifeLabel = new JLabel("LIFE: ");

      //�� �۾�ü
      nameLabel.setFont(new Font("FTLAB Hoony", Font.BOLD , 15));
      scoreLabel.setFont(new Font("FTLAB Hoony", Font.BOLD , 15));
      levelLabel.setFont(new Font("FTLAB Hoony", Font.BOLD , 15));
      lifeLabel.setFont(new Font("FTLAB Hoony", Font.BOLD , 15));

      //��ġ ����//setBounds(x,y,����,����) ������ġ
      nameLabel.setBounds(450, 0, 100, 30);
      scoreLabel.setBounds(600, 0, 100, 30); 
      levelLabel.setBounds(750, 0, 100, 30);
      lifeLabel.setBounds(900, 0, 100, 30);

      //��� ����
      nameLabel.setHorizontalAlignment(JLabel.CENTER);
      scoreLabel.setHorizontalAlignment(JLabel.CENTER);
      levelLabel.setHorizontalAlignment(JLabel.CENTER);
      lifeLabel.setHorizontalAlignment(JLabel.CENTER);

      //�� ����
      nameLabel.setForeground(Color.white);
      scoreLabel.setForeground(Color.white);
      levelLabel.setForeground(Color.white);
      lifeLabel.setForeground(Color.white);

      dp.add(nameLabel);
      dp.add(scoreLabel);
      dp.add(levelLabel);
      dp.add(lifeLabel);

      endLabel = new JLabel();
      endLabel2 = new JLabel();
      explainLabel = new JLabel("����� ESC");
      
      endLabel.setBounds(0,0,1500,500); //����󺧷� ������
      endLabel.setVisible(false); 
      endLabel.setIcon(endImage);
      dp.add(endLabel);

      endLabel2.setBounds(0,0,1500,500); //����󺧷� ������
      endLabel2.setVisible(false); 
      endLabel2.setIcon(endImage2);
      dp.add(endLabel2);
      
      explainLabel.setBounds(600,100,400,200); 
      explainLabel.setFont(new Font("FTLAB Hoony", Font.BOLD , 50));
      explainLabel.setVisible(false);
      dp.add(explainLabel);

      this.add(dp);   //�����ӿ� ��ο� �г� ����ֱ�
      this.setSize(500, FRAME_HEIGHT); 
      this.setVisible(true);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setTitle("FlappybirdGame");
      this.setResizable(false);
   }

   //��ư������
   class ButtonListener implements ActionListener{

      @Override
      public void actionPerformed(ActionEvent e) {
         if(e.getSource() == startButton) {
            start.setVisible(false);  //���� ��ư ������ ��ŸƮ �� ȭ�� ǥ�� �����
            game.setIcon(gameImage);  //���� ��ư ������ ���Ӷ󺧿� ���� �̹��� �ֱ�
            dp.add(game);             //��ο� �гο� ���� �̹��� �ֱ�

            bird.setIcon(b_right);      
            bird.setBounds(240,250, 20, 35);
            bird.setVisible(true);

            for(int i = 0; i<INDEX; i++) {     //���� 5��
               listH.add(new Heart(i)); // listh �� ���� ����Ʈ
            }


            startButton.setVisible(false);   //��ŸƮ ��ư �����
            howButton.setVisible(false);
            setSize(FRAME_WIDTH, FRAME_HEIGHT);  //������ Ű���
            t.start();   //Ÿ�̸� ���� 
         }
         if(e.getSource() == howButton) {
            howButtonb = true;  
            start.setIcon(explainImage);
            howButton.setVisible(false);
            startButton.setVisible(false);
            setSize(FRAME_WIDTH, FRAME_HEIGHT);
         }
         
         if(e.getSource() == nextButton) {
            
            INDEX = 1;
            nameLabel.setFont(new Font("FTLAB Hoony", Font.BOLD , 15));
            scoreLabel.setFont(new Font("FTLAB Hoony", Font.BOLD , 15));
            levelLabel.setFont(new Font("FTLAB Hoony", Font.BOLD , 15));
            lifeLabel.setFont(new Font("FTLAB Hoony", Font.BOLD , 15));
            
            game.setIcon(gameImage);  //���� ��ư ������ ���Ӷ󺧿� ���� �̹��� �ֱ�
            dp.add(game);             //��ο� �гο� ���� �̹��� �ֱ�
            bird.setIcon(b_right);      
            bird.setBounds(0,250, 20, 35);
            bird.setVisible(true);
            
            for(int i = 0; i<INDEX; i++) {     //���� 5��
               listH.add(new Heart(i)); // listh �� ���� ����Ʈ   
            }
            
            
            nameLabel.setBounds(450, 0, 100, 30);
            scoreLabel.setBounds(600, 0, 100, 30); 
            levelLabel.setBounds(750, 0, 100, 30);
            lifeLabel.setBounds(900, 0, 100, 30);

            howButton.setVisible(false);
            startButton.setVisible(false);
            nextButton.setVisible(false);   
            endLabel.setVisible(false);
            explainLabel.setVisible(false);
            t.start();   //Ÿ�̸� ���� 
            
         }
         
         
      }
   }

   //�̸� ����
   public void name() {
      listN.add(new players(player_name));
   }

   // ���� �� ����� �̸� �ޱ�
   public void nameInput() { //JOptionPane.showInputDialog�̰ɷ�  ���ڿ� �� �޾ƿ��� 
      while(true) {
         player_name = (String)JOptionPane.showInputDialog(this, "�÷��̾��� �̸��� �Է��ϼ���(5�� �̳�)", "BIRD", JOptionPane.PLAIN_MESSAGE);
         if(player_name == null)
            System.exit(0);
         else if(player_name.length() > 5)
            player_name = (String)JOptionPane.showInputDialog(this, "�÷��̾��� �̸��� �Է��ϼ���(5�� �̳�)", "BIRD", JOptionPane.PLAIN_MESSAGE);
         else if(player_name.length() <= 5) {
            break;
         }
      }
   }

   //�� �̵�
   class Draw implements ActionListener{

      @Override
      public void actionPerformed(ActionEvent e) {
         repaint();
         count++; //�гο�  ������,���� �߰��ϱ� ����   
         
         //����Ű�� ������ ����
         if(true) {
            bird.setIcon(b_rightfall);
            if(jump) {
               bird.setIcon(b_rightJump);  //jump�� true�϶� b_rightJump �̹����� �ٲٱ�
            }
            if(bird.x<=1480&&bird.y<=460)   //���� ������1480,460 ����� ���ϰ� �ϱ� ����
               bird.move();
             }

         //����
         if(count == 1) {
            listC.add(new Coin(100,250)); listC.add(new Coin(850,270)); 
            listC.add(new Coin(150,250)); listC.add(new Coin(900,270)); 
            listC.add(new Coin(200,250)); listC.add(new Coin(950,280)); 
            listC.add(new Coin(250,250)); listC.add(new Coin(1000,270)); 
            listC.add(new Coin(300,250)); listC.add(new Coin(1050,260)); 
            listC.add(new Coin(350,250)); listC.add(new Coin(1100,260)); 
            listC.add(new Coin(450,250)); listC.add(new Coin(1150,260)); 
            listC.add(new Coin(500,290)); listC.add(new Coin(1200,270)); 
            listC.add(new Coin(550,290)); listC.add(new Coin(1250,280)); 
            listC.add(new Coin(600,290)); listC.add(new Coin(1300,260)); 
            listC.add(new Coin(650,290)); listC.add(new Coin(1350,250)); 
            listC.add(new Coin(700,290)); listC.add(new Coin(1400,260)); 
            listC.add(new Coin(750,290)); listC.add(new Coin(1450,270)); 
            listC.add(new Coin(800,270)); listC.add(new Coin(1500,280));
         }



         //�Ʒ����� �� ������ ����
         if(count == 1) { 
            listP.add(new downpipe(50,400)); 
            listP.add(new downpipe(100,400)); //�Ʒ����� ���� ������ ����Ʈ�� ���� �ֱ�
            listP.add(new downpipe(200,350));
            listP.add(new downpipe(300,300));
            listP.add(new downpipe(400,350));
            listP.add(new downpipe(500,400));
            listP.add(new downpipe(600,500));
            listP.add(new downpipe(700,450));
            listP.add(new downpipe(800,300));
            listP.add(new downpipe(900,350));
            listP.add(new downpipe(1000,400));
            listP.add(new downpipe(1100,300));
            listP.add(new downpipe(1200,450));
            listP.add(new downpipe(1300,300));      
         }
         //������ �Ʒ� ������
         if(count == 1) {

            listP2.add(new uppipe(100,0));  //������ �Ʒ��� ������
            listP2.add(new uppipe(200,0));
            listP2.add(new uppipe(300,0));
            listP2.add(new uppipe(400,0));
            listP2.add(new uppipe(500,0));
            listP2.add(new uppipe(600,0));
            listP2.add(new uppipe(700,0));
            listP2.add(new uppipe(800,0));
            listP2.add(new uppipe(900,0));
            listP2.add(new uppipe(1000,0));
            listP2.add(new uppipe(1100,0));
            listP2.add(new uppipe(1200,0));
            listP2.add(new uppipe(1300,0));   
         }

      }
   }





   class DrawPanel extends JPanel implements KeyListener{
      public void paintComponent(Graphics g) {

         super.paintComponent(g);
         g.drawImage(backImg,0,0,this);

         //���� ����
         if(INDEX <= 0) {  // ������ 0�Ǹ� ���� ����
            
            //for(downpipe p : listP)
               //PRemove.add(p); 
            //for(uppipe p : listP2)
               //PRemove2.add(p);
            //for(Coin c : listC)
               //CRemove.add(c);
            bird.setBounds(bird.x, bird.y, 0, 0); //������ �������� ���� ������� �ϱ� ���ؼ�
            


            nameLabel.setFont(new Font("FTLAB Hoony", Font.BOLD , 30));
            scoreLabel.setFont(new Font("FTLAB Hoony", Font.BOLD , 30));
            levelLabel.setFont(new Font("FTLAB Hoony", Font.BOLD , 30));
            lifeLabel.setFont(new Font("FTLAB Hoony", Font.BOLD , 0));
            //����ġ �缳��
            nameLabel.setBounds(400,220,300,100);
            scoreLabel.setBounds(600,220, 300, 100); 
            levelLabel.setBounds(800,220, 300, 100);

            //dp.remove(game); //��ο��г� ���ӹ��ȭ�� ����
            //��������ȭ��
            endLabel.setVisible(true); //����󺧿� ���� ���� ���� ȭ�� ǥ��
            explainLabel.setVisible(true);
            nextButton.setVisible(true);
            t.stop();  //Ÿ�̸� ���߱�
            //count=0;
            
            
         }
         if(bird.x==1480) {   //������ ���� Ŭ���� �ϴ� �ڵ�
            for(Coin c : listC)
               CRemove.add(c);
            bird.setBounds(bird.x, bird.y, 0, 0); //������ �������� ���� ������� �ϱ� ���ؼ�
            
            nameLabel.setFont(new Font("FTLAB Hoony", Font.BOLD , 30));
            scoreLabel.setFont(new Font("FTLAB Hoony", Font.BOLD , 30));
            levelLabel.setFont(new Font("FTLAB Hoony", Font.BOLD , 30));
            lifeLabel.setFont(new Font("FTLAB Hoony", Font.BOLD , 0));

            //����ġ �缳��
            nameLabel.setBounds(400,220,300,100);
            scoreLabel.setBounds(600,220, 300, 100); 
            levelLabel.setBounds(800,220, 300, 100);
            endLabel2.setVisible(true); //����󺧿� ���� ���� ���� ȭ�� ǥ��
            explainLabel.setVisible(true);
            nextButton.setVisible(false);
            
            t.stop();  //Ÿ�̸� ���߱�
            
         }



         for(downpipe p : listP) {
            p.drawP(g);  //�Ʒ����������� �׸���

            if(p.distanceP(bird.x, bird.y)<=90) { //������ �����̶� ������ ��������Ʈ�� �ֱ�
               PRemove.add(p);   
               INDEX--; //ü�� ����
               score-=100; //���� ����
            }
         }
         for(uppipe p : listP2) {
            p.drawP(g); //������ ������ �׸���

            if(p.distanceP(bird.x, bird.y)<=90  ) {
               PRemove2.add(p);
               INDEX--;
               score-=100;
            }
         }


         //����(����ȹ��)
         for(Coin c: listC) {
            c.drawC(g);

            if(c.distanceC(bird.x, bird.y)<=30) {
               CRemove.add(c);
               score+=300;
               coin(); //bgm ���������� �Ҹ�����
            }
         }

         //������ ��Ÿ���� ��Ʈ
         for(Heart h : listH)
            h.drawH(g);


         //����
         for(Coin c : CRemove)
            listC.remove(c); // ���⼭ �΋Hġ�� ���� ����
         for(Heart h : HRemove)
            listH.remove(h); 
         for(downpipe p : PRemove) //�Ʒ������� ������ ����
            listP.remove(p);
         for(uppipe p : PRemove2)  //�� ������ ������ ����
            listP2.remove(p);


         //score�� level ���ΰ�ħ
         scoreLabel.setText("SCORE: " + score);
         levelLabel.setText("LEVEL: " +level);


         setFocusable(true);

      }


      @Override
      public void keyTyped(KeyEvent e) {}


      @Override
      public void keyPressed(KeyEvent e) {
         int keycode = e.getKeyCode();

         if(keycode == KeyEvent.VK_SPACE) {
            jump();
            bird.setIcon(b_right);
            new Thread(new Runnable() {  //������ �����

               //������ ����
               @Override 
               public void run() {
                  jump = true;   
                  fall = false;

                  int set = 20; 

                  while (set > 0&&bird.y>=10){ //���Ⱑ ���� �ϴ� �ڵ� 20��ŭ ����,�� y��ǥ�� 10���� ū������ ���� �г� ����� �ʰ� �ϱ� ����
                     set--;
                     bird.jump(set);  //y�࿡�� set��ŭ ����

                     try {
                        Thread.sleep(10);
                     }catch(InterruptedException e) {
                        e.printStackTrace();
                     }
                  }
                  bird.setIcon(b_right);
               }

            }).start(); //������ ���� ������ ����� ��
         }
         else if(keycode == KeyEvent.VK_ESCAPE) { //esc������ ���� ���α׷� ����
            System.exit(0);
         }
      }

      @Override
      public void keyReleased(KeyEvent e) { //��������
         int keycode = e.getKeyCode();

         if(keycode == KeyEvent.VK_SPACE) {
            bird.setIcon(b_right);
            jump = false;
            fall = true; //���������� �������Ƿ� fall true
         }
      
         
         //���� �������� �Ѿ�� ����
         if(keycode == KeyEvent.VK_ENTER&&howButtonb) { 

            game.setIcon(gameImage);
            dp.add(game);
            start.setVisible(false);

            bird.setIcon(b_rightfall);
            bird.setBounds(240, 405, 20, 35);
            bird.setVisible(true);

            for(int i = 0; i<INDEX; i++) {
               listH.add(new Heart(i));
            }
            
            t.start();
         }

      }
   }

   public static void main(String[] args) {
      new FlappybirdGame();
   }

   //����
   class Heart{
      int index;

      Heart(int i){
         index = i;
      }
      public void drawH(Graphics g) {
         g.drawImage(heart, 980+index*30, 5,27,21,null);
      }
   }

   //�ؿ� ������
   class downpipe{
      int x;
      int y;
      int w;
      int h;


      downpipe(int x, int y){
         this.x = x;
         this.y = y;
         w = 50;
         h = 200;

      }
      public void drawP(Graphics g) {
         g.drawImage(downpipe,this.x, this.y, w, h, null);
      }
      public int getX() {
         return this.x;
      }
      public int getY() {
         return this.y;
      }
      public double distanceP(int x, int y) {
         return Math.sqrt(Math.pow((this.x+w/2) - x, 2) + Math.pow((this.y+h/2) - y, 2));
      }
   }

   class uppipe{
      int x;
      int y;
      int w;
      int h;


      uppipe(int x, int y){
         this.x = x;
         this.y = y;
         w = 50;
         h = 170;

      }
      public void drawP(Graphics g) {
         g.drawImage(uppipe,this.x, this.y, w, h, null);
      }
      public int getX() {
         return this.x;
      }
      public int getY() {
         return this.y;
      }
      public double distanceP(int x, int y) {
         return Math.sqrt(Math.pow((this.x+w/2) - x, 2) + Math.pow((this.y+h/2) - y, 2));
      }
   }

   //����
   class Coin{
      int cX;
      int cY;
      int w = 11;
      int h = 16;

      Coin(int posX, int posY){
         cX = posX;
         cY = posY;
         w = 11;
         h = 16;
      }
      public void drawC(Graphics g) {
         g.drawImage(coin, cX,cY,w,h,null);
      }
      public double distanceC(int x, int y) {
         return Math.sqrt(Math.pow((cX+w/2) - x, 2) + Math.pow((cY+h/2) - y, 2));
      }
   }



   //��
   class Bird extends JLabel {
      int x;
      int y;
      int w;
      int h;
      int moveX = 3;
      int moveY = 3;

      Bird(int posX, int posY){
         x = posX;
         y = posY;
         w = BIRD_WIDTH;
         h = BIRD_HEIGHT;

         super.setIcon(b_right);
      }
      public void moveUP() {
         y-= moveY;
      }
      public void moveDOWN() {
         y += moveY;
      }
      public void moveRIGHT() {
         x += moveX;
      }
      public void moveLEFT() {
         x -= moveX;
      }
      public void move() {
         x += moveX/3;
         y += moveY*2;
      }
      public int getX() {
         return x;
      }
      public int getY() {
         return y;
      }
      public int setY(int i) {
         return y = i;
      }
      public void fall(int y) {
         this.y += y;
      }
      public void jump(int x) {
         this.y -= x;
      }
   }



   //�÷��̾� �̸�
   class players{
      String name;

      players(String player_name){
         this.name = player_name;
      }

      public String name() {
         return this.name;
      }
   }
}
