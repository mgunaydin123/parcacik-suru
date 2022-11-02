package minegunaydin;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SehirSaglayici {

    List<Sehir> sehirler = new ArrayList<>();

    static SehirSaglayici instance;

    public static SehirSaglayici getInstance(){
         if(instance == null){
             synchronized (SehirSaglayici.class){
                 instance = new SehirSaglayici();
             }
         }
         return instance;
    }

    public int getSize(){
        return sehirler.size();
    }


    private SehirSaglayici(){

        Sehir sehir = new Sehir("Istanbul", 61 ,78);
        Sehir sehir2 = new Sehir("Giresun", 483  ,88);
        Sehir sehir3 = new Sehir("Sivas", 422 ,157);
        Sehir sehir4 = new Sehir("Sinop", 350 ,25);
        Sehir sehir5 = new Sehir("Kütahya", 100 ,174);
        Sehir sehir6 = new Sehir("Samsun", 386 ,68);
        Sehir sehir7 = new Sehir("Isparta", 122 ,272);
        Sehir sehir8 = new Sehir("Afyon", 125 ,215);
        Sehir sehir9 = new Sehir("Erzurum", 613, 136);
        Sehir sehir10 = new Sehir("Kars", 610, 72);




            sehirler.add(sehir);
            sehirler.add(sehir2);
            sehirler.add(sehir3);
            sehirler.add(sehir4);
            sehirler.add(sehir5);
            sehirler.add(sehir6);
            sehirler.add(sehir7);
            sehirler.add(sehir8);
            sehirler.add(sehir9);
            sehirler.add(sehir10);


        normalizasyonYap();
    }

    public void  yuvarlakSehir(){
        sehirler.clear();
      int radius = 120;
      double angle = Math.PI * 2 / 12;

        for(int i = 0; i < 12; i++){
            double xVal = radius * Math.cos(angle * i + 1) + App.CANVAS_GENISLIK /2;
            double yVal = radius * Math.sin(angle * i + 1) +App.CANVAS_YUKSEKLIK/4;
            Sehir sehir = new Sehir(i + "", xVal, yVal);
            sehirler.add(sehir);
        }
    }

    public List<Sehir> rastgeleSiralamaListesiGetir(){
        List<Sehir> rastgeleSehirler = new ArrayList<>(sehirler);
        for(int i = 0; i < 25; i++)
            listeyiKaristir(rastgeleSehirler);
        return rastgeleSehirler;
    }

    void listeyiKaristir(List<Sehir> sehirListesi){
        int i = (int) Math.floor(Math.random() * sehirler.size());
        int j = (int) Math.floor(Math.random() * sehirler.size());
        Collections.swap(sehirListesi, i ,j);
    }

    public double mesafeHesapla(List<Sehir> sehirListesi){
        double sum = 0;
        for (var i = 0; i < sehirListesi.size() - 1; i++) {
            Sehir sehir = sehirListesi.get(i);
            Sehir sehir2 = sehirListesi.get(i + 1);
            double d = sehirlerArasiMesafeHesapla(sehir, sehir2);
            sum += d;
        }
        return sum;
    }


    public double sehirlerArasiMesafeHesapla(Sehir sehir1, Sehir sehir2){
        return Math.sqrt((sehir2.getY() - sehir1.getY()) * (sehir2.getY() - sehir1.getY()) + (sehir2.getX() - sehir1.getX()) * (sehir2.getX() - sehir1.getX()));
    }



    public void normalizasyonYap(){
        double maxX = -1;
        double maxY = -1;
        double minX = Integer.MAX_VALUE;
        double minY = Integer.MAX_VALUE;
        for(Sehir sehir : sehirler){

            if(sehir.getX() < minX){
                minX = sehir.getX();
            }
            if(sehir.getX() > maxX){
                maxX= sehir.getX();
            }
            if(sehir.getY() < minY){
                minY = sehir.getY();
            }
            if(sehir.getY() > maxY){
                maxY= sehir.getY();
            }

        }

        int offsetX = 60;
        int offsetY = 100;

        var normalizedLow = minX + offsetX ;
        var normalizedHigh = App.CANVAS_GENISLIK / 2 - offsetX;

        var normalizedLowY = minY + offsetY;
        var normalizedHighY = App.CANVAS_YUKSEKLIK - offsetY;


        for(Sehir sehir : sehirler){

            sehir.setX(((sehir.getX() - minX) / (maxX - minX))* (normalizedHigh - normalizedLow) + normalizedLow);
            sehir.setY(((sehir.getY() - minY) / (maxY - minY))* (normalizedHighY - normalizedLowY) + normalizedLowY);

        }
    }

    public List<Sehir> getSehirler(){
        return sehirler;
    }







}
