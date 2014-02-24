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


void setup() {
  size(600, 600);
  smooth();
  background(0);
  noStroke();
  //setting the values for the white rectangle
  rw = width - 30;
  rx = (width - rw)/2;
  rh = height/1.5;
  ry = height/2 - rh/2;
  //placing the rows and columns of balloons within the rectangle
  for (int i=0; i<r; i++) {
    for (int j=0; j<c; j++) {
      balloons[i][j] = new Balloon(
      j*rw/c+(2*rx), //x position
      i*rh/r+(ry+d), //y position
      d, //diameter
      random(-1.0, -0.5)); // random value of diameter change
    }
  }
}
void draw() {
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

void mousePressed() {
  //when the mouse is pressed call the popBalloon function 
  popBalloon();
}

void popBalloon() {

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

