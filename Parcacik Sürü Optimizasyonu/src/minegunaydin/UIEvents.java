package minegunaydin;


public interface UIEvents {

    void enKısaYoluCiz(Particle particle);
    void enUzunMesafeCiz(Double distance);

    void simulasyonTamamlandi(SIMULATION_RESULT result);

   public  enum SIMULATION_RESULT{
        MAX_ITERASYON,
        BEST_VALUE
    }
}
