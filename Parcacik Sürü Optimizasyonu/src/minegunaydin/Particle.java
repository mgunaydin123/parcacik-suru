package minegunaydin;


public class Particle implements Comparable<Particle>
{
    private Sehir[] sehirler = new Sehir[SehirSaglayici.getInstance().getSehirler().size()];
    private double mpBest = 0;
    private double mVelocity = 0.0;

    public Particle()
    {
        this.mpBest = 0;
        this.mVelocity = 0.0;
    }

    public int compareTo(Particle that)
    {
        if(this.pBest() < that.pBest()){
            return -1;
        }else if(this.pBest() > that.pBest()){
            return 1;
        }else{
            return 0;
        }
    }

    public Sehir getSehir(int index)
    {
       return sehirler[index];
    }

    public void addSehir(int index, Sehir value)
    {
        sehirler[index] = value;
    }

    public double pBest()
    {
        return this.mpBest;
    }

    public void pBest(final double value)
    {
        this.mpBest = value;
        return;
    }

    public double velocity()
    {
        return this.mVelocity;
    }

    public void velocity(final double velocityScore)
    {
        this.mVelocity = velocityScore;
        return;
    }

    public Sehir[] getSehirler() {
        return sehirler;
    }


}
