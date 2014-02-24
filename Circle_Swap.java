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

public class Circle_Swap extends PApplet {

float x, y; //position of the ellipse
float diameter=10, dc=1; //diameter of the ellipse and the diameter change
int c= color(0, 0, 255); //color of ellipse starts as blue

public void setup() {
  //600 by 600 screen with black background
  size(600, 600);
  background(0);
  smooth();
  x=width/2; //ellispe positioned at the center of the screen
  y=height/2;
}

public void draw() {
  noStroke();
  //drawing an ellipse and filling it with the variable c which will allow the color to change
  fill(c);
  ellipse(x, y, diameter, diameter);
  //if the diameter equals 50 the diameter change will be minus 1
  if (diameter==50) {
    dc=-1;
  }
  //if the diameter equals 1 the diameter change will be plus 1
  else if (diameter==1) {
    dc=1;
  }
  //adding the diameter change variable to the diameter 
  diameter+=dc;
  //fades out the ellipse as it changes in size
  fill(0, 100);
  rect(0, 0, width, height);
}

public void mousePressed() {
  //checks if the mouse was pressed within the ellipse
  if ((dist(x, y, mouseX, mouseY) < diameter/2)) {
    // fills the ellipse with random color
    c=color(random(0, 255), random(0, 255), random(0, 255));
    // moves ellipse to new random position on the screen
    x=random(0, width);
    y=random(0, height);
  }
  // draws the new ellipse
  ellipse(x, y, diameter, diameter);
}



  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Circle_Swap" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
