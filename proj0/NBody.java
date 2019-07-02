import java.util.ArrayList;

public class NBody {

    /** read the universe radius from the file */
    public static double readRadius(String filePath){
        In in = new In(filePath);

        int planetsNumbers = in.readInt();
        double universeRadius = in.readDouble();

        return universeRadius;
    }

    /**  read the planets in the universe into an array from the file */
    public static Planet[] readPlanets(String filePath){
        In in = new In(filePath);

        int planetsNumbers = in.readInt();
        double universeRadius = in.readDouble();
        ArrayList<Planet> planets = new ArrayList<Planet>();

        while(!in.isEmpty()){
            String firstword = in.readString();
            try{
                double xP = Double.parseDouble(firstword);
            }catch (java.lang.NumberFormatException e){
                break;
            }
            double xP = Double.parseDouble(firstword);
            double yP = in.readDouble();
            double xV = in.readDouble();
            double yV = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();
            //String imgPath = "images/"+ img; move to Planet.draw() to pass the Autograder.

            planets.add(new Planet(xP, yP, xV, yV, m, img));
        }
        // Convert ArrayList to normal static Array to pass the AutoGrader
        Planet planet[] = new Planet[planets.size()];
        planets.toArray(planet);

        return planet;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);

        String filename = args[2];
        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);

        StdAudio.play("audio/2001.mid");
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-radius, radius);
        StdDraw.clear();
        //StdDraw.picture(0,0,"images/starfield.jpg");


        for(int time = 0; time <= T; time += dt){

            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];
            StdDraw.picture(0,0,"images/starfield.jpg");

            for(int i = 0; i < planets.length; i += 1){
                xForces[i] = planets[i].calcNetForceExertedByY(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);

                planets[i].update(dt, xForces[i], yForces[i]);
                planets[i].draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
        }

        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }


    }
}
