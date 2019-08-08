package lab14;

import lab14lib.Generator;
import lab14lib.GeneratorAudioVisualizer;

public class AcceleratingSawToothGenerator implements Generator{
    private long period;
    private double factor;
    private int state;
    private Boolean needReset = false;

    public AcceleratingSawToothGenerator(long period, double factor) {
        this.period = period;
        this.factor = factor;
    }

    public double next() {
        if (needReset) {
            period =  Math.round(period * factor);
            needReset = false;
        }
        state += 1;
        double slope = 2.0 / period;
        if (state == period) {
            needReset = true;
            state = 0;
        }
        return slope * state - 1;
    }

    public static void main(String[] args) {
        Generator generator = new AcceleratingSawToothGenerator(200, 1.1);
        GeneratorAudioVisualizer gav = new GeneratorAudioVisualizer(generator);
        gav.drawAndPlay(4096, 1000000);
    }
}
