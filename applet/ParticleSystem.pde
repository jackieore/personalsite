class ParticleSystem {
  Particle[] particles;        // the individual bits exploding
  color c;                     // color
  float x, y;                   //position
  float r;                //radius
  float transparency, dt=3;    // transparency decays over time


  ParticleSystem(int numParticles, float x0, float y0, color c0, float r0)
  {
    c = c0;
    x = x0;
    y = y0;
    r = r0;
    // Creates the particles by creating new objects from the Particle class
    particles = new Particle[numParticles];
    for (int i=0; i<particles.length; i++) particles[i] = new Particle();
  }

  void draw()
  {
    fill(c); 
    stroke(c);
    ellipse(x, y, r, r);
    // Draw the individual particles with the shared color
    for (int i=0; i<particles.length; i++) particles[i].draw(c);
  }



  void update()
  {
    // Update the individual particles
    for (int i=0; i<particles.length; i++) particles[i].update(x, y);
  }
}

