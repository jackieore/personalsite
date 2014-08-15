class ParticleSystem {
  Particle[] particles;        // the individual bits exploding
  color c;                     // common color
  float x;                     // common origin
  float y;                     // common origin
  float r;                // source radius

  ParticleSystem(int numParticles, float x0, float y0, color c0, float r0)
  {
    c = c0;
    x = x0;
    y = y0;
    r = r0;
    // Create the particles for the firework
    particles = new Particle[numParticles];
    for (int i=0; i<particles.length; i++) particles[i] = new Particle();
  }

  void draw()
  {
    noStroke();
    fill(c, 0); 
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

