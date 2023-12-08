package lab;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;

public class Score extends GameEntity implements DrawableSimulable {

    private Point2D position;
    private Game game;
    private int scoreL=0;
    private String nameL;

    public Score(Game game, Point2D position)
    {
        super(game,position);
        this.game=game;
        this.position=position;
    }

    public void increaseScore()
    {
        scoreL++;
    }

    public int getScore(int goalId)
    {
        return scoreL;
    }

    @Override
    public void simulate(double timeDelta) {

    }

    @Override
    protected void drawInternal(GraphicsContext gc) {
        /*gc.setFont(new Font(30));
        gc.fillText(""+scoreL, position.getX(),position.getY());
        gc.fillText(""+scoreR, position.getX()+100-18,position.getY());*/
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof Score s)
        {
            return this.scoreL == s.getScore(0);
        }
        return false;

    }
    public void setNameL(String name)
    {
        this.nameL=name;
    }
    public String getNameL()
    {
        return this.nameL;
    }
    public void setScoreL(int score)
    {
        this.scoreL=score;
    }


    @Override
    public String toString()
    {
        return nameL + ": "+scoreL;
    }
    @Override
    public int hashCode()
    {
        return nameL.hashCode();
    }
}
