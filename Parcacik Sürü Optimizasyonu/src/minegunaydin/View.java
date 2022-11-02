package minegunaydin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame implements UIEvents{


    private JPanel panel1;
    private JTextField txt_parcacik_sayisi;
    private JTextField txt_ıterasyon_sayisi;
    private JTextField txt_durdurma_cozum_degeri;
    private JButton başlatButton;
    private JLabel lbl_siralama;
    private JLabel lbl_kisa_mesafe;
    private JLabel lbl_enuzun_mesafe;
    private JPanel canvas;
    private JPanel panel;
    private JTextField txt_vmax;

    public View(){
        PSO pso = new PSO();
        setResizable(false);
        add(panel);
        setSize(App.CERCEVE_GENISLIK,App.CERCEVE_YUKSEKLIK);
        setLocationRelativeTo(null);
        canvas.add(pso);
        setVisible(true);
        setTitle("Mine Günaydın Karınca Kolonisi Algoritması");


        başlatButton.addActionListener(actionEvent -> {
            if(pso.isStarted()){
                pso.simulasyonDurdur();
                başlatButton.setText("BAŞLAT");
            }else{
                if(konfigurasyonuAyarla(pso)) {
                    pso.startSimulation();
                    başlatButton.setText("DURDUR");
                }
            }
        });
    }


    public static void main(String[] args) {
        new View();
    }

    @Override
    public void enKısaYoluCiz(Particle particle) {
        enIyıDegeriGoster(particle);
    }

    @Override
    public void enUzunMesafeCiz(Double distance) {
        lbl_enuzun_mesafe.setText(String.format("%.2f",distance));
    }

    @Override
    public void simulasyonTamamlandi(SIMULATION_RESULT result) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if(result.equals(SIMULATION_RESULT.MAX_ITERASYON)){
                    JOptionPane.showMessageDialog(null, "Max Iterasyon Sayısına Ulaşıldı.", "Tamamlandı", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(null, "Hedeflenen değere ulaşıldı.", "Tamamlandı", JOptionPane.INFORMATION_MESSAGE);
                }
                başlatButton.setText("BAŞLAT");
            }
        });

    }

    public void enIyıDegeriGoster(Particle particle){
        lbl_kisa_mesafe.setText(String.format("%.2f", particle.pBest()));
        String siralama = "";
        for(Sehir sehir : particle.getSehirler()){
            siralama += sehir.getSehirAdi() + " => \n";
        }

        lbl_siralama.setText(siralama);
    }

    boolean konfigurasyonuAyarla(PSO pso){
        int parcacikSayisi = getInt(txt_parcacik_sayisi, "Parçacık Sayısı ");
        if(parcacikSayisi == -1){
            return false;
        }

        int vMax = getInt(txt_vmax, "V Max ");
        if(vMax == -1){
            return false;
        }


        int maxIterasyonSayisi = getInt(txt_ıterasyon_sayisi, "Max Iterasyon Sayisi ");
        if(maxIterasyonSayisi == -1){
            return false;
        }

        double durdurma_degeri = getDouble(txt_durdurma_cozum_degeri, "Durdurma ");
        if(durdurma_degeri == -1){
            return false;
        }

        pso.setConfiguration(parcacikSayisi, maxIterasyonSayisi, durdurma_degeri, vMax,this);

        return true;
    }

    double getDouble(JTextField field, String alanAdi){
        double deger = -1;
        if(field.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null, "Lütfen " + alanAdi + " değerini boş bırakmayın", "Uyarı", JOptionPane.ERROR_MESSAGE);
            return deger;
        }

        try{
            deger =  Double.parseDouble(field.getText().trim());
            if(deger < 0){
                return  -1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return deger;
    }

    int getInt(JTextField field, String alanAdi){
        int deger = -1;
        if(field.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null, "Lütfen " + alanAdi + " değerini boş bırakmayın", "Uyarı", JOptionPane.ERROR_MESSAGE);
            return deger;
        }

        try{
            deger =  Integer.parseInt(field.getText().trim());
            if(deger <= 0){
                JOptionPane.showMessageDialog(null, "Lütfen " + alanAdi + " değerini 0 dan büyük girin.", "Uyarı", JOptionPane.ERROR_MESSAGE);
                return  -1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return deger;
    }


}
