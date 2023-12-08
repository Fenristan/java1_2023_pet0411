package lab;

import javafx.animation.AnimationTimer;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.collections.FXCollections;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

public class GameController {

	private boolean gameStarted = false;
	private Game game;
	@FXML
	private Canvas canvas;
	private AnimationTimer animationTimer;

	@FXML
	private TextField textbox1;


	@FXML
	private Text scoreL;

    @FXML
    private ListView playerList;

	@FXML
	private Text textLives;

    private ObservableList<Score> list;

	private Score score;



	public GameController() {
		list = FXCollections.observableList(new ArrayList<Score>());
		list.addListener(new ListChangeListener<Score>() {
			@Override
			public void onChanged(Change<? extends Score> c) {
				// TODO Auto-generated method stub
			}
		});
	}

	class GameListener2 implements GameListener
	{
		@Override
		public void stateChanged(Score score) {
			GameController.this.scoreL.setText(score.getScore(0)+"");
			GameController.this.score = score;
		}

		@Override
		public void stateChanged(int lives) {
			GameController.this.textLives.setText(""+lives);
		}

		@Override
		public void gameOver() {
			stopGame();
			score.setNameL(textbox1.getText());
            list.add(score);

            list.sort(new Comparator<Score>()
            {
                @Override
                public int compare(Score s1, Score s2) {
                    // TODO Auto-generated method stub
                    return s2.getScore(0)-s1.getScore(1);
                }

            });
			HighScore();

			textbox1.setFocusTraversable(true);
			textbox1.setMouseTransparent(false);
			textbox1.setEditable(true);

			GameController.this.scoreL.setText("0");

		}
        private void HighScore()
        {
            HashSet<Score> unique = new HashSet<Score>();
            for(Score s : list)
            {
                unique.add(s);
            }
            list.clear();
            list.addAll(unique);
			if(score.getScore(0)>=score.getScore(1))
			{
				list.sort((x,y)->y.getScore(0)-x.getScore(1));
			}
			else
			{
				list.sort((x,y)->y.getScore(1)-x.getScore(1));
			}
        }
	}

	@FXML
	void keyPressed(KeyEvent event) {
		if(gameStarted)
		{
			switch (event.getCode()) {
				case A:
					game.moveLeftBatLeft();
					break;
				case D:
					game.moveLeftBatRight();
					break;
				case SPACE:
					game.fire();
				default:
					break;
			}
		}
	}


	@FXML
	void keyReleased(KeyEvent event) {
		if(gameStarted) {
			switch (event.getCode()) {
				case A:
					game.stopMoveLeftBat();
					break;
				case D:
					game.stopMoveLeftBat();
					break;
				default:
					break;
			}
		}
	}

	
	public void startGame() {
		playerList.setItems(list);
		this.game = new Game(canvas.getWidth(), canvas.getHeight());
		//Draw scene on a separate thread to avoid blocking UI.
		animationTimer = new DrawingThread(canvas, game);
		game.setGameListener(new GameListener2());
		GameController.this.scoreL.setText("0");
		GameController.this.textLives.setText("3");
		animationTimer.start();
	}


	public void stopGame() {
		animationTimer.stop();
	}
	
	@FXML
	public void start() {
		gameStarted=true;
		startGame();

		textbox1.setFocusTraversable(false);
		textbox1.setMouseTransparent(true);
		textbox1.setEditable(false);

		game.setPlayerName(textbox1.getText());


		canvas.requestFocus();
	}

	@FXML
	private void saveScores()
	{
		try(FileWriter fw = new FileWriter("data.csv")) {
			for(Score score : list)
			{
				if(score.getScore(0)>=score.getScore(1))
				{
					fw.write(score.getNameL()+";"+score.getScore(0)+"\n");
				}

			}
		}
		catch(IOException err)
		{
			err.printStackTrace();
		}

	}

	@FXML
	private void loadScores()
	{
		list.clear();
		try(BufferedReader br = new BufferedReader(new FileReader("data.csv"))) {
			while(true)
			{
				String line = br.readLine();
				if(line==null) {
					break;
				}
				String[] parts = line.split(";");
				String name = parts[0];
				int score = Integer.parseInt(parts[1]);
				Score scoreRead = new Score(game, new Point2D(0,0));
				scoreRead.setNameL(name);
				scoreRead.setScoreL(score);
				list.add(scoreRead);
			}

		}
		catch(IOException err)
		{
			err.printStackTrace();
		}

	}

	
}
