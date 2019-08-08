package lab14;
import edu.princeton.cs.algs4.StdAudio;
import lab14lib.*;

public class SawToothGenerator implements Generator{
    private int state;
    private int period;

    public SawToothGenerator(int period) {
        state = 0;
        this.period = period;
    }

    public double next() {
        state = (state + 1);
        return normalize(state);
    }

    private double normalize(int state) {
        return ((2.0 / period) * (state % period) - 1);
    }


    public static void main(String[] args) {
        Generator generator = new SawToothGenerator(512);
        GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
        gav.drawAndPlay(4096, 1000000);
    }
}
