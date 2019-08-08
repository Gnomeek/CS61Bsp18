import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {
    private Picture picture;
    private int width;
    private int height;
    private double[][] energy;


    public SeamCarver(Picture pic) {
        picture = new Picture(pic);
        width = pic.width();
        height = pic.height();
        energy = new double[width][height];
        energyBuilder(picture);

    }


    public Picture picture() {
        return new Picture(picture);
    }


    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public double energy(int x, int y) {
        return energy[x][y];
    }

    private void energyBuilder(Picture pic) {
        for (int i = 0; i < pic.height(); i += 1) {
            for (int j = 0; j < pic.width(); j += 1) {
                if (j == 0) {
                    Color x1 = pic.get(pic.width() - 1, i);
                    Color x2 = pic.get(j + 1, i);
                    double dr;
                    double db;
                    double dg;
                    double dEnergy = 0.0;
                    dr = x1.getRed() - x2.getRed();
                    db = x1.getBlue() - x2.getBlue();
                    dg = x1.getGreen() - x2.getGreen();
                    dEnergy += dr * dr + db * db + dg * dg;

                    if (i == 0) {
                        Color y1 = pic.get(j, pic.height() - 1);
                        Color y2 = pic.get(j, i + 1);
                        dr = y1.getRed() - y2.getRed();
                        db = y1.getBlue() - y2.getBlue();
                        dg = y1.getGreen() - y2.getGreen();
                        dEnergy += dr * dr + db * db + dg * dg;;
                        energy[j][i] = dEnergy;
                    } else if (i == pic.height() - 1) {
                        Color y1 = pic.get(j, 0);
                        Color y2 = pic.get(j, i - 1);
                        dr = y1.getRed() - y2.getRed();
                        db = y1.getBlue() - y2.getBlue();
                        dg = y1.getGreen() - y2.getGreen();
                        dEnergy += dr * dr + db * db + dg * dg;;
                        energy[j][i] = dEnergy;
                    } else {
                        Color y1 = pic.get(j, i - 1);
                        Color y2 = pic.get(j, i + 1);
                        dr = y1.getRed() - y2.getRed();
                        db = y1.getBlue() - y2.getBlue();
                        dg = y1.getGreen() - y2.getGreen();
                        dEnergy += dr * dr + db * db + dg * dg;;
                        energy[j][i] = dEnergy;
                    }
                } else if (j == pic.width() - 1) {
                    Color x1 = pic.get(0, i);
                    Color x2 = pic.get(j - 1, i);
                    double dr;
                    double db;
                    double dg;
                    double dEnergy = 0.0;
                    dr = x1.getRed() - x2.getRed();
                    db = x1.getBlue() - x2.getBlue();
                    dg = x1.getGreen() - x2.getGreen();
                    dEnergy += dr * dr + db * db + dg * dg;

                    if (i == 0) {
                        Color y1 = pic.get(j, pic.height() - 1);
                        Color y2 = pic.get(j, i + 1);
                        dr = y1.getRed() - y2.getRed();
                        db = y1.getBlue() - y2.getBlue();
                        dg = y1.getGreen() - y2.getGreen();
                        dEnergy += dr * dr + db * db + dg * dg;;
                        energy[j][i] = dEnergy;
                    } else if (i == pic.height() - 1) {
                        Color y1 = pic.get(j, 0);
                        Color y2 = pic.get(j, i - 1);
                        dr = y1.getRed() - y2.getRed();
                        db = y1.getBlue() - y2.getBlue();
                        dg = y1.getGreen() - y2.getGreen();
                        dEnergy += dr * dr + db * db + dg * dg;;
                        energy[j][i] = dEnergy;
                    } else {
                        Color y1 = pic.get(j, i - 1);
                        Color y2 = pic.get(j, i + 1);
                        dr = y1.getRed() - y2.getRed();
                        db = y1.getBlue() - y2.getBlue();
                        dg = y1.getGreen() - y2.getGreen();
                        dEnergy += dr * dr + db * db + dg * dg;;
                        energy[j][i] = dEnergy;
                    }
                } else {
                    Color x1 = pic.get(j + 1, i);
                    Color x2 = pic.get(j - 1, i);
                    double dr;
                    double db;
                    double dg;
                    double dEnergy = 0.0;
                    dr = x1.getRed() - x2.getRed();
                    db = x1.getBlue() - x2.getBlue();
                    dg = x1.getGreen() - x2.getGreen();
                    dEnergy += dr * dr + db * db + dg * dg;

                    if (i == 0) {
                        Color y1 = pic.get(j, pic.height() - 1);
                        Color y2 = pic.get(j, i + 1);
                        dr = y1.getRed() - y2.getRed();
                        db = y1.getBlue() - y2.getBlue();
                        dg = y1.getGreen() - y2.getGreen();
                        dEnergy += dr * dr + db * db + dg * dg;;
                        energy[j][i] = dEnergy;
                    } else if (i == pic.height() - 1) {
                        Color y1 = pic.get(j, 0);
                        Color y2 = pic.get(j, i - 1);
                        dr = y1.getRed() - y2.getRed();
                        db = y1.getBlue() - y2.getBlue();
                        dg = y1.getGreen() - y2.getGreen();
                        dEnergy += dr * dr + db * db + dg * dg;;
                        energy[j][i] = dEnergy;
                    } else {
                        Color y1 = pic.get(j, i - 1);
                        Color y2 = pic.get(j, i + 1);
                        dr = y1.getRed() - y2.getRed();
                        db = y1.getBlue() - y2.getBlue();
                        dg = y1.getGreen() - y2.getGreen();
                        dEnergy += dr * dr + db * db + dg * dg;;
                        energy[j][i] = dEnergy;
                    }
                }
            }
        }
    }
    public int[] findHorizontalSeam() {
        int[] ans = new int[picture.height()];

        for (int i = 0; i < picture.height(); i += 1) {
            double min = energy[0][i];
            int minIndex = 0;
            for (int j = 0; j < picture.width(); j += 1) {
                if (energy[j][i] < min) {
                    min = energy[j][i];
                    minIndex = j;
                }
            }
            ans[i] = minIndex;
        }
        return ans;
    }

    public int[] findVerticalSeam() {
        int[] ans = new int[picture.height()];

        for (int j = 0; j < picture.width(); j += 1) {
            double min = energy[j][0];
            int minIndex = 0;
            for (int i = 0; i < picture.width(); i += 1) {
                if (energy[j][i] < min) {
                    min = energy[j][i];
                    minIndex = i;
                }
            }
            ans[j] = minIndex;
        }
        return ans;
    }

    public void removeHorizontalSeam(int[] seam) {
        SeamRemover.removeHorizontalSeam(picture, seam);
    }

    public void removeVerticalSeam(int[] seam) {
        SeamRemover.removeVerticalSeam(picture, seam);
    }
}
