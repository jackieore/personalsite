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

public class hw1 extends PApplet {

float tree_diameter, apple_diameter, blue, sun_x, sun_y, bird_x; 

public void setup() {
  size(800, 600);
  smooth();
  frameRate(8);
  sun_x = 800; // initial x position of the ellipse that represents the sun
  sun_y = 0; // initial y position of the ellipse that represents the sun
  blue = 255; // initial value of blue which represnts the brightest blue color
}

public void draw() {
  blue = 255*mouseX/width;
  // changes background different shades of blue according to where the mouse position is
  background(12, 17, blue, 150);


  noStroke();

  // grass
  fill(8, 85, 43); // green
  rect(0, 550, 800, 50);

  // tree stump
  fill(62, 22, 7); // brown
  rect(150, 300, 70, 250);

  // tree bush
  tree_diameter = 90;
  fill(34, 88, 6); // green
  ellipse(145, 300, tree_diameter, tree_diameter);
  ellipse(220, 300, tree_diameter, tree_diameter);
  ellipse(110, 250, tree_diameter, tree_diameter);
  ellipse(175, 250, tree_diameter, tree_diameter);
  ellipse(250, 250, tree_diameter, tree_diameter);
  ellipse(145, 200, tree_diameter, tree_diameter);
  ellipse(220, 200, tree_diameter, tree_diameter);

  // apples
  apple_diameter = 20;
  // if mouse is pressed the red apples turn to green apples
  if (mousePressed) { 
    fill(44, 227, 87); // green apples
  } 
  else {
    fill(206, 10, 17); // red apples
  } 


  //apple shapes
  ellipse(140, 300, apple_diameter, apple_diameter);
  ellipse(130, 260, apple_diameter, apple_diameter);
  ellipse(170, 250, apple_diameter, apple_diameter);
  ellipse(210, 260, apple_diameter, apple_diameter);
  ellipse(230, 300, apple_diameter, apple_diameter);
  ellipse(250, 260, apple_diameter, apple_diameter);
  ellipse(130, 220, apple_diameter, apple_diameter);
  ellipse(180, 210, apple_diameter, apple_diameter);
  ellipse(235, 215, apple_diameter, apple_diameter);
  ellipse(185, 295, apple_diameter, apple_diameter);


  // birds
  fill(255); // white birds

  beginShape();
  vertex(325, 85);
  vertex(290, 80);
  vertex(315, 50);
  vertex(300, 80);
  endShape(CLOSE);

  beginShape();
  vertex(400, 85);
  vertex(365, 80);
  vertex(390, 50);
  vertex(375, 80);
  endShape(CLOSE);

  beginShape();
  vertex(475, 85);
  vertex(440, 80);
  vertex(465, 50);
  vertex(450, 80);
  endShape(CLOSE);

  // if the background gets dark the sun starts to set

  if (blue <= 100) {
    sun_x -= 10;
  }
  // if the background is light the sun goes back to its original position
  else if (blue >= 100) {
    sun_x += 10;
    sun_x = constrain(sun_x, 0, width);
  }

  // sun
  fill(247, 241, 32); // yellow
  ellipse(sun_x, sun_y, 250, 250);


  // when the sun is in its original position the sun beams are drawn
  if (sun_x == 800) {
    noFill();
    stroke(247, 241, 32); // yellow curve
    strokeWeight(4); // thicker curve



    // sun beams
    beginShape(); // first beam
    curveVertex(535, 115);
    curveVertex(585, 40);
    curveVertex(615, 25);
    curveVertex(645, 40);
    curveVertex(675, 25);
    curveVertex(785, 115);
    endShape();

    beginShape(); // second beam
    curveVertex(535, 155);
    curveVertex(585, 90);
    curveVertex(615, 65);
    curveVertex(645, 80);
    curveVertex(700, 65);
    curveVertex(785, 155);
    endShape();

    beginShape(); // third beam
    curveVertex(535, 195);
    curveVertex(640, 140);
    curveVertex(670, 105);
    curveVertex(700, 120);
    curveVertex(735, 105);
    curveVertex(800, 200);
    endShape();

    beginShape(); // fourth beam
    curveVertex(535, 245);
    curveVertex(710, 190);
    curveVertex(750, 145);
    curveVertex(770, 160);
    curveVertex(790, 105);
    curveVertex(800, 245);
    endShape();
  }

  // picnic basket
  stroke(0);
  strokeWeight(1);
  fill(183, 95, 0); // light brown
  // if mouse is positioned within the basket, the basket becomes transparent
  if (mouseX >= 300 && mouseX <= 400 && mouseY >= 485 && mouseY <= 550)  
  { 
    fill(183, 95, 0, 50); // light brown with a high transparency
  } 
  beginShape(); // basket shape
  vertex(300, 485);
  vertex(400, 485);
  vertex(380, 550);
  vertex(320, 550);
  endShape(CLOSE);



  // picnic basket handle
  noFill();
  beginShape();
  curveVertex(260, 660);
  curveVertex(310, 485);
  curveVertex(390, 485);
  curveVertex(500, 660);
  endShape(CLOSE);

  // apples in picnic basket appear when the mouse is positioned within the basket
  if (mouseX >= 300 && mouseX <= 400 && mouseY >= 485 && mouseY <= 550)
  { 
    fill(240, 0, 0); // red apples

    ellipse(320, 510, 15, 15);
    ellipse(330, 510, 15, 15);
    ellipse(340, 510, 15, 15);
    ellipse(350, 510, 15, 15);
    ellipse(360, 510, 15, 15);
    ellipse(370, 510, 15, 15);
    ellipse(380, 510, 15, 15);
    ellipse(320, 520, 15, 15);
    ellipse(330, 520, 15, 15);
    ellipse(340, 520, 15, 15);
    ellipse(350, 520, 15, 15);
    ellipse(360, 520, 15, 15);
    ellipse(370, 520, 15, 15);
    ellipse(380, 520, 15, 15);
    ellipse(330, 535, 15, 15);
    ellipse(340, 535, 15, 15);
    ellipse(350, 535, 15, 15);
    ellipse(360, 535, 15, 15);
    ellipse(370, 535, 15, 15);
    ellipse(340, 540, 15, 15);
    ellipse(350, 540, 15, 15);
    ellipse(360, 540, 15, 15);
    ellipse(370, 540, 15, 15);
    ellipse(330, 545, 15, 15);
    ellipse(350, 543, 15, 15);
    ellipse(360, 543, 15, 15);
    ellipse(370, 543, 15, 15);
  }

  // red and white checkered picnic blanket
  fill(255); // white checker
  rect(450, 543, 50, 6);

  // if the mouse is pressed within the first red checker on the blanket the color changes from red to a random color
  if (mousePressed && mouseX >= 500 && mouseX < 550 && mouseY >= 540 && mouseY <= 550) {
    fill(random(0, 255), random(0, 255), random(0, 255));
  }
  // if mouse isnt being pressed it fills the color back to red
  else { 
    fill(255, 0, 0); // red
  } 
  rect(500, 543, 50, 6); // checker shape

  fill(255); // white checker
  rect(550, 543, 50, 6);

  fill(255, 0, 0); // red checker
  rect(600, 543, 50, 6);

  fill(255); // white checker
  rect(650, 543, 50, 6);

  fill(255, 0, 0); // red checker
  rect(700, 543, 50, 6);
}

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "hw1" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
