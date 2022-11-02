package minegunaydin;

import java.awt.*;

public class Sehir {

    public String sehirAdi;

    public double x, y;

    private double daireBoyutu = 15;

    public Sehir(String sehirAdi, double x, double y) {
        this.sehirAdi = sehirAdi;
        this.x = x;
        this.y = y;
    }

    public void goster(Graphics gfx){
        gfx.setColor(Color.WHITE);
        gfx.fillOval((int)x,  (int)y, (int)daireBoyutu, (int)daireBoyutu);
        gfx.setColor(Color.RED);
        gfx.drawString(sehirAdi, (int)x, (int)y -10);
    }

    public String getSehirAdi() {
        return sehirAdi;
    }

    public void setSehirAdi(String sehirAdi) {
        this.sehirAdi = sehirAdi;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
       return sehirAdi;
    }

    public double getDaireBoyutu() {
        return daireBoyutu;
    }
}
