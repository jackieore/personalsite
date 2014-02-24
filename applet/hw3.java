import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class hw3 extends PApplet {

//adapted code from my SA8 assignment and the 2/10/14 Particle Systems notes
//Instructions:
//The objective of this minigame is to make all the balloon circles explode and turn the whole board from blue balloons to red balloons. 
//You can make a balloon explode by clicking the balloon when it turns red. 

int r = 10; // amount of rows
int c = 15; // amount of columns
Balloon[][] balloons =  new Balloon[r][c]; // double array storing the amount of rows and columns for Balloon class
ParticleSystem[] ps =  new ParticleSystem[50]; // array for objects within the ParticleSystem class
int ptr = 0; // index pointer
float rw; // rectangle width
float rh; // rectangle height
float rx; // rectangle x position 
float ry; // rectangle y position
int d = 20; // initial diameter of Balloons


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
  //placing the rows and columns of balloons within the rectangle
  for (int i=0; i<r; i++) {
    for (int j=0; j<c; j++) {
      balloons[i][j] = new Balloon(
      j*rw/c+(2*rx), //x position
      i*rh/r+(ry+d), //y position
      d, //diameter
      random(-1.0f, -0.5f)); // random value of diameter change
    }
  }
}
public void draw() {
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
}

public void mousePressed() {
  //when the mouse is pressed call the popBalloon function 
  popBalloon();
}

public void popBalloon() {

  for (int i=0; i<r; i++) {
    for (int j=0; j<c; j++) {
      //test if the balloon within a row and column is popped if not popped 
      //then call the checkPop function within the Balloon class
      if (!balloons[i][j].isPopped) {
        balloons[i][j].checkPop();
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
        }
        //if the index pointer gets to the end of the array if we go back to 0
        if (ptr == ps.length) {
          ptr = 0;
        }
      }
    }
  }
}

class Balloon { 
  float r = 0; //red color
  float g = 0; // green color
  float b = 255; // blue color
  float x, y; // x and y position
  float d; // diameter
  float dul; // diameter upper limit
  float dll = 5; // diameter lower limit
  float dc; // rate of diameter change
  boolean isPopped = false; // isPopped boolean starts as false

    Balloon(float x0, float y0, float d0, float dc0) {
    //method definitions
    x = x0;
    y = y0;
    d = d0;
    dc = dc0;
    dul = d; // the diameter of the balloon starts as the diameter upper limit
  }

  public void draw() {
    fill(r, g, b);
    ellipse(x, y, d, d);
  }

  public void update() {
    //if the balloon is not popped the diameter changes but is constrains between the diameter lower limit and upper limit
    if (!isPopped) {
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
  float x, y;    // x and y position
  float vx, vy;  // velocity
  float timer;   // time left before estinguishing
  float dt = 3;  // estinguishing speed
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
    vy = random(-2, -1);
    timer = 255; // timer is directly related to the transparency  
    dt = random(0.1f, 5);
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

    timer -= dt;    // decay the transparency
    // gravity affects the vertical velocity
    vy += g;
    x += vx;
    y += vy;
  }
}

class ParticleSystem {
  Particle[] particles;        // the individual bits exploding
  int c;                     // color
  float x, y;                   //position
  float r;                //radius
  float transparency, dt=3;    // transparency decays over time


  ParticleSystem(int numParticles, float x0, float y0, int c0, float r0)
  {
    c = c0;
    x = x0;
    y = y0;
    r = r0;
    // Creates the particles by creating new objects from the Particle class
    particles = new Particle[numParticles];
    for (int i=0; i<particles.length; i++) particles[i] = new Particle();
  }

  public void draw()
  {
    fill(c); 
    stroke(c);
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
    String[] appletArgs = new String[] { "hw3" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
