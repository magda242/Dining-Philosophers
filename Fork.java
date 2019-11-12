import java.util.concurrent.Semaphore;

public class Fork {
	public Semaphore available=new Semaphore(1);

	public Fork(Semaphore available) {
		this.available=available;
	}
	
	
	
	void take() throws InterruptedException {
		available.acquire();
	}
	
		void putdown() {
			available.release();
		}
	

}
