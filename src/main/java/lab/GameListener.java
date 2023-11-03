package lab;

public interface GameListener {
	
	void stateChanged(Score score);
	void stateChanged(int lives);
	
	void gameOver();
}
