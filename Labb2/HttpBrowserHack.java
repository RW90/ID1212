package lecture2;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpClient;

public class HttpBrowserHack {
	
	private int nrOfGuesses;
	private int nrOfGames;
	private String baseUrl;
	private HttpClient client;
	private HttpRequest req;
	private HttpResponse<String> res;
	private int sessionId;
	
	public HttpBrowserHack() {
		nrOfGuesses = 0;
		nrOfGames = 0;
		baseUrl = "http://localhost:1234/";
	}
	
	private void sendGuess(int guess) {
		createHttpClient();
		createReq(baseUrl + "?guess=" + guess);
		sendReq();
	}
	
	private void startNewGame() {
		createHttpClient();
		createReq(baseUrl + "new");
		sendReq();
	}
	
	private void getCookie() {
		createHttpClient();
		req = HttpRequest.newBuilder().uri(URI.create(baseUrl)).build();
		sendReq();
		sessionId = Integer.valueOf(res.headers().firstValue("Set-cookie").get().split("=")[1]);
	}
	
	private void createHttpClient() {
		client = HttpClient.newHttpClient();
	}
	
	private void createReq(String url) {
		req = HttpRequest.newBuilder().uri(URI.create(url)).header("Cookie", "sessionId=" + sessionId).build();
	}
	
	private void sendReq() {
		try {
			res = client.send(req, BodyHandlers.ofString());
		} catch(Exception e) {
			System.out.println("Failed sending request.");
		}
	}
	
	private void playGuessGame() {
		int currentGameGuesses;
		int low;
		int high;
		int mid;
		while(nrOfGames < 100) {
			currentGameGuesses = 1;
			low = 1;
			high = 100;
			mid = (low + high)/2;
			startNewGame();
			sendGuess(mid);
			//System.out.println("Guessed " + mid);
			while(!res.body().contains("win")) {
				if(res.body().contains("lower")) {
					high = mid - 1;
				} else {
					low = mid + 1;
				}
				mid = (high + low) / 2;
				sendGuess(mid);
				//System.out.println("Guessed " + mid);
				currentGameGuesses++;
			}
			nrOfGames++;
			nrOfGuesses += currentGameGuesses;
		}
		System.out.println("100 games finished! Total nr of guesses:" + nrOfGuesses );
		System.out.println("Average number of guesses per game is:" + nrOfGuesses / 100.0);
	}
	
	public void start() {
		getCookie();
		playGuessGame();
	}

	public static void main(String[] args) {
		HttpBrowserHack httpClient = new HttpBrowserHack();
		httpClient.start();
	}
}
