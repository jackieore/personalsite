import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 
import ddf.minim.analysis.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class PROJECT extends PApplet {

//adapted code from my HW3 assignment, SA8 assignment, 2/21/14 Sound and Music III notes and the 2/10/14 Particle Systems notes
//adapted code from http://www.openprocessing.org/sketch/135637
//Instructions:
//The objective of this game is to make all the balloon circles explode and clear the whole board before time runs out. 
//You can make a balloon explode by clicking the balloon when it turns red. 




//using the Minim, AudioPlayer and BeatDetect classes to play music and detect the tempo of the music
Minim minim;
AudioPlayer song;
BeatDetect beat;
FFT fft; // this class tracks the frequency of the music
int nFreqBand = 30;

int r=10; //amount of rows
int c=10;  // amount of columns

PFont font; //font for the text that is printed in the game

Balloon[][] balloons =  new Balloon[r][c]; // double array storing the amount of rows and columns for Balloon class
ParticleSystem[] ps =  new ParticleSystem[50]; // array for objects within the ParticleSystem class

int ptr = 0; // index pointer

float rw; // rectangle width
float rh; // rectangle height
float rx; // rectangle x position 
float ry; // rectangle y position

int d = 35; // initial diameter of Balloons
float dcl = -1.5f; //lower rate of diameter change
float dcu = -0.5f; //upper rate of diameter change

int time; //variable that will track the time for the game
int mouseclick; // variable hold the amount of times the mouse has been pressed
int nBalloonsLeft; //variable that will hold the amount of Balloons that have not been "popped"

int menu = 1;
int game = 2;
int lost = 3;
int win = 4;

int gameState; //allows the program to switch through different screen uses switch/case test

public void setup() {
  size(600, 600);
  smooth();
  background(0);
  noStroke();
  //setting the values for the white rectangle
  rw = width - 30;
  rx = (width - rw)/2;
  rh = height/1.5f;
  ry = height/2 - rh/2;
  //this loads the song file 
  minim = new Minim(this);
  song = minim.loadFile("song2.mp3", 2048);
  // a beat detection object song SOUND_ENERGY mode with a sensitivity of 10 milliseconds
  beat = new BeatDetect();
  gameInit();
}

public void gameInit()
{
  //game begins on menu screen
  gameState = menu;
  time = 0;
  mouseclick=0;
  song.play(); //starts the song
  nBalloonsLeft=r*c; //the amount of balloons not popped equals the row times the column
  //placing the rows and columns of balloons within the rectangle
  for (int i=0; i<r; i++) {
    for (int j=0; j<c; j++) {
      balloons[i][j] = new Balloon(
      j*rw/c+(3*rx), //x position
      i*rh/r+(ry+(d/1.5f)), //y position
      d, //diameter
      random(dcl, dcu)); // random value of diameter change
    }
  }
}

public void draw() {
  switch(gameState)
  {
  case 1:
    //menu screen
    background(0);
    //this tracks the frequency and creates color bands that change in size according to the range of the frequency as the song plays
    fft = new FFT(song.bufferSize(), song.sampleRate());
    colorMode(HSB);
    noStroke();
    fft.forward(song.mix);
    for (int i = 0; i < nFreqBand; i++)
    {
      fill(i*255/nFreqBand, 255, 255);
      rect(i*width/nFreqBand, height, width/nFreqBand, height - fft.getBand(i)*10);
    }
    //this will display all the text in the menu screen
    font = loadFont("SansSerif-48.vlw");
    textAlign(CENTER);
    textFont(font, 60);
    text("SoundXplosion", width/2, height/2);
    textFont(font, 15);
    text("Use your mouse to make the circles explode when red!", width/2, height/2 + 80);
    text("You only have 200 mouse clicks! Clear the board before time runs out!", width/2, height/2 + 120);
    textFont(font, 20);
    text("Click anywhere to begin", width/2, height/2 + 160);
    break;

  case 2:
    //game state
    colorMode(RGB);
    noStroke();
    background(0);
    fill(255);
    rect(rx, ry, rw, rh);
    //calling the draw and update functions within the Balloon class
    for (int i=0; i<r; i++) {
      for (int j=0; j<c; j++) {
        balloons[i][j].draw();
        balloons[i][j].update();
      }
    }
    for (int i=0; i<ps.length; i++) {
      //if the array for ParticleSystem is nothing 
      //then continue to call on the functions within Particle Systems
      if (ps[i] == null) {
        continue;
      }
      ps[i].draw();
      ps[i].update();
    }

    fill(255);
    //prints out the time variable
    font = loadFont("SansSerif-48.vlw");
    textFont(font, 20);
    textAlign(RIGHT);
    text("Time: " + time/30, (width - 20), 30);
    time ++;
    // prints out the mouse clicks variable
    fill(255);
    textFont(font, 20);
    textAlign(LEFT);
    text("Mouse Clicks: " + mouseclick, (20), 30);
    //if the amount of mouseclicks goes over 200 or the time variable exceeds 5000 the game is over
    if (mouseclick == 200 || time == 5000)
    {
      gameState = lost;
    }
    //if all the balloons are popped and the mouse clicks does not exceed 200 and the time has not run out you win the game
    if (time <= 5000 && mouseclick < 200 && nBalloonsLeft == 0) {
      gameState = win;
    }
    //if the time is close to running out then print "Time is running out!"
    if (time > 4000)
    {
      fill(255, 0, 0);
      textFont(font, 20);
      textAlign(RIGHT);
      text("Time is running out!", (width - 20), 50);
    }
    break;

  case 3:
    //game over screen
    background(0);
    font = loadFont("SansSerif-48.vlw");
    textFont(font, 50);
    textAlign(CENTER);
    fill(random(0, 255), random(0, 255), random(0, 255));
    text("GAME OVER", (width/2), (height/2));
    textFont(font, 10);
    text("Click anywhere to restart", width/2, height/2 + 80);
    break;

  case 4:
    //win screen
    background(0);
    font = loadFont("SansSerif-48.vlw");
    textFont(font, 50);
    textAlign(CENTER);
    fill(random(0, 255), random(0, 255), random(0, 255));
    text("YOU WIN", (width/2), (height/2));
    textFont(font, 10);
    text("Click anywhere to restart", width/2, height/2 + 80);
    break;
  }
}

public void mousePressed() {
  //when the mouse is pressed call the popBalloon function 
  popBalloon();
  //adds 1 to the mouse click variable every time the mouse is pressed
  mouseclick ++;
  //mouse pressed changes from the menu screen to game screen
  //from game over screen to menu screen
  //from win screen to menu screen
  if (gameState == menu)
  {
    gameState = game;
  }
  else if (gameState == lost)
  {
    gameInit();
  }
  else if (gameState == win) {
    gameInit();
  }
}

public void popBalloon() {
  for (int i=0; i<r; i++) {
    for (int j=0; j<c; j++) {
      //test if the balloon within a row and column is popped if not popped 
      //then call the checkPop function within the Balloon class
      if (!balloons[i][j].isPopped) {
        balloons[i][j].checkPop();
        balloons[i][j].tra = 255;
        if (balloons[i][j].isPopped) {
          //an array of the new objects belonging to the Particle System class
          //that are created when the balloon is popped
          ps[ptr] = new ParticleSystem(
          10, //number of particles
          balloons[i][j].x, //x position of new particle will match the x position of the balloon 
          balloons[i][j].y, //y position of new particle will match the y position of the balloon
          //color of particle and its particles will match balloon
          color(balloons[i][j].r, balloons[i][j].g, balloons[i][j].b), 
          //diameter of particle will match balloon
          balloons[i][j].d);
          //the index pointer grows
          ptr++;
          nBalloonsLeft--;
        }
        //if the index pointer gets to the end of the array if we go back to 0
        if (ptr == ps.length) {
          ptr = 0;
        }
      }
    }
  }
}
//takes a screenshot of the game if the space bar is pressed
public void keyPressed() {
  if (key == ' ') save("screenshot.png");
}

class Balloon { 
  float r = 0; //red color
  float g = 0; // green color
  float b = 255; // blue color
  float x, y; // x and y position
  float d; // diameter
  float dul; // diameter upper limit
  float dll = 15; // diameter lower limit
  float dc; // rate of diameter change
  boolean isPopped = false; // isPopped boolean starts as false
  float tra = 255; //transparency variable

  Balloon(float x0, float y0, float d0, float dc0) {
    //method definitions
    x = x0;
    y = y0;
    d = d0;
    dc = dc0;
    dul = d; // the diameter of the balloon starts as the diameter upper limit
  }

  public void draw() {
    fill(r, g, b, tra);
    ellipse(x, y, d, d);
  }

  public void update() {
    //if the balloon is not popped the diameter changes but is constrains between the diameter lower limit and upper limit
    if (!isPopped) {
      //this detects the tempo of the song and one of the circles responds to the music
      beat.detect(song.mix);
      float a = map(dul, dll, dul, dll, dul);
      if ( beat.isOnset() ) d = dul;
      //shrinks diameter
      d -= dc;
      //if gets to lower limit constrain then start change the rate of diameter change to the opposite of itself
      if (d != constrain(d, dll, dul)) {
        d = constrain(d, dll, dul);
        dc *= -1;
      }
      b = 255 * (d-dll)/(dul-dll);
      r = 255-b;
    }
    if (isPopped) {
      tra--; //once the balloon is popped it starts to disappear
    }
  }

  public void checkPop() {
    //if is popped already no need to check if its popped
    if (isPopped) {
      return;
    }
    //the the distance between the mouse position and the x and y of the balloon is less than the radius of the balloon
    // and the red color has a value higher than 150 the boolean isPopped becomes true
    if (dist(mouseX, mouseY, x, y) <= d/2 && r>=150) {
      isPopped = true;
    }
  }
}

class Particle {
  float x, y;    // current position
  float vx, vy;  // velocity
  float g = 0.05f; // gravity
  float r = 5;   // radius
  boolean on=false;

  Particle() {
  }
  // Put at original position, fully opaque, with random velocity
  public void initialize(float ox, float oy)
  {
    on = true;
    x = ox; 
    y = oy;
    // More vertical than horizontal
    vx = random(-1, 1); 
    vy = random(-5, -2);
  }

  public void draw(int c)
  {
    if (!on) return;
    fill(c);
    ellipse(x, y, 2*r, 2*r);
  }

  public void update(float ox, float oy)
  {
    // initialize if necessary
    if (!on) { 
      if (random(0, 1) < 0.5f) initialize(ox, oy); 
      return;
    }
    // Usual gravity stuff
    vy += g;
    x += vx;
    y += vy;
    // When exit screen or totally transparent, re-initialize
  }
}

class ParticleSystem {
  Particle[] particles;        // the individual bits exploding
  int c;                     // common color
  float x;                     // common origin
  float y;                     // common origin
  float r;                // source radius

  ParticleSystem(int numParticles, float x0, float y0, int c0, float r0)
  {
    c = c0;
    x = x0;
    y = y0;
    r = r0;
    // Create the particles for the firework
    particles = new Particle[numParticles];
    for (int i=0; i<particles.length; i++) particles[i] = new Particle();
  }

  public void draw()
  {
    noStroke();
    fill(c, 0); 
    ellipse(x, y, r, r);
    // Draw the individual particles with the shared color
    for (int i=0; i<particles.length; i++) particles[i].draw(c);
  }

  public void update()
  {
    // Update the individual particles
    for (int i=0; i<particles.length; i++) particles[i].update(x, y);
  }
}

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "PROJECT" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
