import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Philosopher implements Runnable {

	public static ArrayList<Fork> forks;
	private int id;
	public Semaphore waiter=new Semaphore(4, true);

	public Philosopher(int id) {
		this.id = id;
		forks=new ArrayList<Fork>();
	}

	public void think() {

		Random r = new Random();
		int time = r.nextInt(4000) + 2000;
		try {
			System.out.println("Philosopher " + this.id+" thinking...");
			Thread.sleep(time);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return;
		}

	}

	public void eat() {

		Random r = new Random();
		int time = r.nextInt(5000) + 2000;
		try {
			System.out.println("Philosopher " + this.id+" eating...");
			Thread.sleep(time);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return;
		}

	}

	@Override
	public void run() {
		try {
			while (true) {
				// Initially thinking
				think();

				// Try to acquire forks, get ready to eat
				
				System.out.println("Philosopher " + this.id+" ready to eat...");
				
				waiter.acquire();

				forks.get(this.id).take();
				forks.get((this.id + 1) % forks.size()).take();

				eat();

				forks.get(this.id).putdown();
				forks.get((this.id + 1) % forks.size()).putdown();
				waiter.release();
				System.out.println("Philosopher " + this.id+" back to thinking...");

				

			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return;
		}
		// Not hungry anymore. Back to thinking.

	}

	public static void main(String[] args) {

		Philosopher p1 = new Philosopher(0);
		Philosopher p2 = new Philosopher(1);
		Philosopher p3 = new Philosopher(2);
		Philosopher p4 = new Philosopher(3);
		Philosopher p5 = new Philosopher(4);

		Thread t1 = new Thread(p1);
		Thread t2 = new Thread(p2);
		Thread t3 = new Thread(p3);
		Thread t4 = new Thread(p4);
		Thread t5 = new Thread(p5);

		Fork f1 = new Fork(new Semaphore(1));
		Fork f2 = new Fork(new Semaphore(1));
		Fork f3 = new Fork(new Semaphore(1));
		Fork f4 = new Fork(new Semaphore(1));
		Fork f5 = new Fork(new Semaphore(1));

		forks.add(f1);
		forks.add(f2);
		forks.add(f3);
		forks.add(f4);
		forks.add(f5);
		
		
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();


	}

}
