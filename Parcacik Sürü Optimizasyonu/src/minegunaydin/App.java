package minegunaydin;

import java.util.Collection;
import java.util.Random;

public class App {


    public static final int CERCEVE_YUKSEKLIK = 700;
    public static final int CERCEVE_GENISLIK = 1200;

    public static final int CANVAS_GENISLIK = CERCEVE_GENISLIK;
    public static final int CANVAS_YUKSEKLIK = 500;
    private static Random random = new Random();

    public static int rastgeleSayiOlustur(int limit){
        return random.nextInt(limit);
    }


    public static double normalize(double sayi, double min, double max, double normalizedLow, double normalizedHigh){
      return ((sayi- min) / (max - min))* (normalizedHigh - normalizedLow) + normalizedLow;
    }
}
