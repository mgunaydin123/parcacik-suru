package minegunaydin;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PSO extends JPanel implements Runnable {

	private SIMULATION_STATUS simulation_status = SIMULATION_STATUS.STOPPED;

	private int particle_count = 10;
	private int vMax = 4;
	private double worst = -1;
	private UIEvents uiEvents;
	private int max_iteration = 10000;
	private static ArrayList<Particle> particles = new ArrayList<Particle>();
	
	private List<Sehir> sehirler = SehirSaglayici.getInstance().getSehirler();
	private double target;
	Particle aParticle = null;
	int epoch = 0;

	public PSO(){
		new Thread(this).start();
	}

	public void setConfiguration(int particle_count, int max_iteration, double target, int vMax, UIEvents uiEvents){
		this.particle_count = particle_count;
		this.max_iteration = max_iteration;
		this.target = target;
		this.uiEvents = uiEvents;
		this.vMax = vMax;
		initialize();
	}

	public void startSimulation(){
		simulation_status = SIMULATION_STATUS.RUNNING;

	}
	

	
	private void initialize()
	{
		particles = new ArrayList<>();
		for(int i = 0; i < particle_count; i++)
	    {
	        Particle newParticle = new Particle();
	        for(int j = 0; j < sehirler.size(); j++)
	        {
	            newParticle.addSehir(j, sehirler.get(j));
	        }
	        particles.add(newParticle);
	        for(int j = 0; j < 10; j++)
	        {
	        	randomlyArrange(particles.indexOf(newParticle));
	        }
	        getTotalDistance(particles.indexOf(newParticle));
	    }
		epoch = 0;
		worst = -1;
	}
	
	private void randomlyArrange(final int index)
	{
		int cityA = new Random().nextInt(sehirler.size());
		int cityB = 0;
		boolean done = false;
		while(!done)
		{
			cityB = new Random().nextInt(sehirler.size());
			if(cityB != cityA){
				done = true;
			}
		}
		
		Sehir temp = particles.get(index).getSehir(cityA);
		particles.get(index).addSehir(cityA, particles.get(index).getSehir(cityB));
		particles.get(index).addSehir(cityB, temp);
	}
	
	private void getVelocity()
	{
		double worstResults = 0;
		double vValue = 0.0;
		
		// after sorting, worst will be last in list.
	    worstResults = particles.get(particle_count - 1).pBest();

	    for(int i = 0; i < particle_count; i++)
	    {
	        vValue = (vMax * particles.get(i).pBest()) / worstResults;

	        if(vValue > vMax){
	        	particles.get(i).velocity(vMax);
	        }else if(vValue < 0.0){
	        	particles.get(i).velocity(0.0);
	        }else{
	        	particles.get(i).velocity(vValue);
	        }
	    }
	    return;
	}
	
	private void updateparticles()
	{

	    for(int i = 1; i < particle_count; i++)
	    {

	    	int changes = (int)Math.floor(Math.abs(particles.get(i).velocity()));

        	for(int j = 0; j < changes; j++){
        		if(new Random().nextBoolean()){
        			randomlyArrange(i);
        		}

        		copyFromParticle(i - 1, i);
        	}
	        getTotalDistance(i);
	    }
	}

	
	private void copyFromParticle(final int source, final int destination)
	{
		Particle best = particles.get(source);
		Sehir targetA = sehirler.get(new Random().nextInt(sehirler.size()));
		Sehir targetB = sehirler.get(0); //TODO
		int indexA = 0;
		int indexB = 0;
		int tempIndex = 0;

		int i = 0;
		for(; i < sehirler.size(); i++)
		{
			if(best.getSehir(i) == targetA){
				if(i == sehirler.size() - 1){
					targetB = best.getSehir(0);
				}else{
					targetB = best.getSehir(i + 1);
				}
				break;
			}
		}

		for(int j = 0; j < sehirler.size(); j++)
		{
			if(particles.get(destination).getSehir(j).getSehirAdi().equals(targetA.getSehirAdi())){
				indexA = j;
			}
			if(particles.get(destination).getSehir(j).getSehirAdi().equals(targetB.getSehirAdi())){
				indexB = j;
			}
		}
		if(indexA == sehirler.size() - 1){
			tempIndex = 0;
		}else{
			tempIndex = indexA + 1;
		}
		Sehir temp = particles.get(destination).getSehir(tempIndex);
		particles.get(destination).addSehir(tempIndex, particles.get(destination).getSehir(indexB));
		particles.get(destination).addSehir(indexB, temp);
	}
	
	private void getTotalDistance(final int index)
	{
		Particle thisParticle = null;
	    thisParticle = particles.get(index);
	    thisParticle.pBest(0.0);
	    
	    for(int i = 0; i < sehirler.size(); i++)
	    {
	        if(i == sehirler.size() - 1){
	        	thisParticle.pBest(thisParticle.pBest() + SehirSaglayici.getInstance().sehirlerArasiMesafeHesapla(thisParticle.getSehir(sehirler.size() - 1), thisParticle.getSehir(0))); // Complete trip.
	        }else{
	        	thisParticle.pBest(thisParticle.pBest() + SehirSaglayici.getInstance().sehirlerArasiMesafeHesapla(thisParticle.getSehir(i), thisParticle.getSehir(i + 1)));
	        }
	    }
	}

	
	private void bubbleSort() {
		boolean done = false;
		while(!done)
		{
			int changes = 0;
			int listSize = particles.size();
			for(int i = 0; i < listSize - 1; i++)
			{
				if(particles.get(i).compareTo(particles.get(i + 1)) == 1){
					Particle temp = particles.get(i);
					particles.set(i, particles.get(i + 1));
					particles.set(i + 1, temp);
					changes++;
				}
			}
			if(changes == 0){
				done = true;
			}
		}
		return;
	}

	@Override
	public void paintComponent(Graphics gfx) {
		super.paintComponent(gfx);
		if(!refresh || simulation_status != SIMULATION_STATUS.RUNNING) return;
		refresh = false;
		pso(gfx);
	}

	void pso(Graphics gfx){

		if(epoch < max_iteration) {


			for (int i = 0; i < particle_count; i++) {
				aParticle = particles.get(i);
				getTotalDistance(i);
				if (aParticle.pBest() <= target) {
					simulation_status = SIMULATION_STATUS.FINISHED;
					uiEvents.simulasyonTamamlandi(UIEvents.SIMULATION_RESULT.BEST_VALUE);
				}

				if(aParticle.pBest() > worst){
					worst = aParticle.pBest();
				}
			}

			bubbleSort();

			getVelocity();

			updateparticles();



			epoch++;
		}else{

			simulation_status = SIMULATION_STATUS.FINISHED;
			uiEvents.simulasyonTamamlandi(UIEvents.SIMULATION_RESULT.MAX_ITERASYON);
		}

		sehirleriCiz(gfx);


		uiEvents.enKısaYoluCiz(particles.get(0));
		uiEvents.enUzunMesafeCiz(worst);
	}

	public void sehirleriCiz(Graphics gfx){
		gfx.setColor(new Color(0, 50, 80));
		gfx.fillRect(0, 0, App.CANVAS_GENISLIK, App.CANVAS_YUKSEKLIK);


		drawParticule(gfx, 0);

		gfx.translate(App.CERCEVE_GENISLIK / 2, 0);

		drawParticule(gfx, App.rastgeleSayiOlustur(particles.size() - 1));

		gfx.translate(-App.CERCEVE_GENISLIK / 2,  0);





	}

	public void drawParticule(Graphics gfx, int index){
		for(int i = 0; i < particles.get(0).getSehirler().length; i++){
			Sehir sehir = particles.get(0).getSehir(i);
			sehir.goster(gfx);
		}

		gfx.setColor(Color.WHITE);
		for(int i = 0; i < particles.get(index).getSehirler().length - 1; i++)
		{
			Sehir sehir = particles.get(index).getSehirler()[i];
			Sehir sonrakiSehir = particles.get(index).getSehirler()[i +1];
			gfx.drawLine((int) (sehir.getX() + sehir.getDaireBoyutu() / 2), (int)(sehir.getY() + sehir.getDaireBoyutu() / 2),
					(int) (sonrakiSehir.getX() + sehir.getDaireBoyutu() /2), (int) (sonrakiSehir.getY() + sehir.getDaireBoyutu() / 2));
		}

		gfx.drawLine((int) (particles.get(index).getSehirler()[particles.get(index).getSehirler().length -1].getX() + SehirSaglayici.getInstance().getSehirler().get(0).getDaireBoyutu() / 2), (int)(particles.get(index).getSehirler()[particles.get(index).getSehirler().length -1].getY() + SehirSaglayici.getInstance().getSehirler().get(0).getDaireBoyutu() / 2),
				(int) (particles.get(index).getSehirler()[0].getX()  + SehirSaglayici.getInstance().getSehirler().get(0).getDaireBoyutu() / 2), (int) (particles.get(index).getSehirler()[0].getY()+ SehirSaglayici.getInstance().getSehirler().get(0).getDaireBoyutu() / 2));
	}

	public void simulasyonDurdur(){
		simulation_status = SIMULATION_STATUS.STOPPED;
	}

	public boolean isStarted(){
		return simulation_status == SIMULATION_STATUS.RUNNING;
	}


	public enum SIMULATION_STATUS{
		STOPPED, RUNNING, FINISHED
	}

	boolean refresh = false;
	@Override
	public void run() {
		while (true) {
			if(simulation_status == SIMULATION_STATUS.RUNNING){
				if(!refresh) refresh = true;
				repaint();
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}
