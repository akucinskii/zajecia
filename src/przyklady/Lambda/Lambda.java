package przyklady.Lambda;
public class Lambda {

	public static void main(String[] args) {
		// Tradycyjne podejście
		Runnable runnable = new Runnable() {
		    @Override
		    public void run() {
		        System.out.println("Hello, World!");
		    }
		};

		// Wyrażenie Lambda
		Runnable runnableLambda = () -> System.out.println("Hello, Lambda!");
		
		// Uruchomienie Runnable z wyrażeniem Lambda
		runnableLambda.run();
		// Uruchomienie Runnable 
		runnable.run();
	}

}
