public class Planet {
    public double xxPos;//x position
    public double yyPos;//y position
    public double xxVel;//current velocity in the x direction
    public double yyVel;//current velocity in the y direction
    public double mass;
    public String imgFileName;
    private static double G = 6.67e-11;

    /** Constructor */
    public Planet(double xP, double yP, double xV, double yV, double m, String img){
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;

    }
    /** do not use this.xxPos etc. */
    public Planet(Planet p){
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    /** calculate the distance between two planets */
    public double calcDistance(Planet b){
        double xDistance = this.xxPos - b.xxPos;
        double yDistance = this.yyPos - b.yyPos;
        return Math.sqrt(xDistance * xDistance + yDistance *yDistance);
    }

    /** calculate the force exerted from one planet to another */
    public double calcForceExertedBy(Planet b){
        return G * this.mass * b.mass / (this.calcDistance(b) * this.calcDistance(b));
    }

    /** calculate the force exerted from one planet to another in the x,y direction*/
    public double calcForceExertedByX(Planet b){
        double xDistance = this.xxPos - b.xxPos;
        return this.calcForceExertedBy(b) * xDistance / this.calcDistance(b);
    }

    public double calcForceExertedByY(Planet b){
        double yDistance = this.yyPos - b.yyPos;
        return this.calcForceExertedBy(b) * yDistance / this.calcDistance(b);
    }

    /** calculate the net X and net Y force exerted by planets in array upon the current Planet. */
    public double calcNetForceExertedByX(Planet[] planets){
        double NetForceX = 0;
        for (int i = 0; i < planets.length; i += 1){
            if(!this.equals(planets[i])){
                NetForceX += this.calcForceExertedByX(planets[i]);
            }
        }
        return NetForceX;
    }

    public double calcNetForceExertedByY(Planet[] planets){
        double NetForceY = 0;
        for (int i = 0; i < planets.length; i += 1){
            if(!this.equals(planets[i])){
                NetForceY += this.calcForceExertedByY(planets[i]);
            }
        }
        return NetForceY;
    }

    /** calculate the accelerate caused by the forces exerted on the planet */
    public void update(double dt, double fX, double fY){
        double accelerateX = fX / this.mass;
        double accelerateY = fY / this.mass;
        this.xxVel = this.xxVel + dt * accelerateX;
        this.yyVel = this.yyVel + dt * accelerateY;
        this.xxPos = this.xxPos + dt * this.xxVel;
        this.yyPos = this.yyPos + dt * this.yyVel;
    }

    /** draw planet itself in the starfield */
    public void draw(){
        String imgpath = "images/" + this.imgFileName;
        StdDraw.picture(this.xxPos, this.yyPos, imgpath);
    }
}
