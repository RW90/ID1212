package labb4;

import java.util.HashMap;

public class SessionHandler {
	
	private HashMap<Integer, GuessGame> sessions; 
	
	public SessionHandler() {
		sessions = new HashMap<Integer, GuessGame>();
	}
	
	public synchronized void newSession(int id) {
		sessions.put(Integer.valueOf(id), new GuessGame());
	}
	
	public GameStateDTO getSessionGameState(int id) {
		return sessions.get(Integer.valueOf(id)).getState();
	}
	
	public void sessionGuess(int id, int guess) {
		sessions.get(Integer.valueOf(id)).newGuess(guess);
	}
	
	public synchronized void removeSession(int id) {
		sessions.remove(Integer.valueOf(id));
	}
	
	public enum GameMsg {
		LOWER, HIGHER, NEW, WIN
	}
	
	private class GuessGame {
		private int nrOfGuesses;
		private int secretNumber;
		private GameMsg msg;
		
		public GuessGame() {
			resetGame();
		}
		
		public void resetGame() {
			nrOfGuesses = 0;
			secretNumber = (int) (Math.random() * 99 + 1);
			msg = GameMsg.NEW;
		}
		
		public void newGuess(int guess) {
			if(msg == GameMsg.WIN) {
				resetGame();
			}
			nrOfGuesses++;
			if(guess > secretNumber) {
				msg = GameMsg.LOWER;
			} else if(guess < secretNumber) {
				msg = GameMsg.HIGHER;
			} else {
				msg = GameMsg.WIN;
			}
		}

		public GameStateDTO getState() {
			return new GameStateDTO(nrOfGuesses, msg);
		}
	}
}
