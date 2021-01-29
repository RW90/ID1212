package lecture2;

import lecture2.SessionHandler.GameMsg;

public class GameStateDTO {
	private int noOfGuesses;
	private GameMsg msg;
	
	public GameStateDTO(int noOfGuesses, GameMsg msg) {
		this.noOfGuesses = noOfGuesses;
		this.msg = msg;
	}
	
	public int getNoOfGuesses() {
		return noOfGuesses;
	}
	
	public GameMsg getMsg() {
		return msg;
	}
}
