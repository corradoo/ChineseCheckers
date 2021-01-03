package pl.checkers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import pl.checkers.builders.*;

import java.util.ArrayList;

public class Board {
    ConcreteBoard concrete;
    Game game;

    public ArrayList<Field> fields;
    public ArrayList<Circle> circles = new ArrayList<>();
    int radius = 9;
    double scale = 3;
    int currentFieldNr = -1;
    int currentPlayer;
    boolean yourTurn = false;
    int size;

    boolean moved=false;

    int startIndex;
    int movingPlayer;
    int jumpIndex;

    public Object locker;


    public Board(int size,Game game) {
        this.game = game;
        this.size = size;
        /* Wzorzec budowniczego */
        switch(size) {
            case 2:
                concrete = new BoardTwo();
                break;
            case 3:
                concrete = new BoardThree();
                break;
            case 4:
                concrete = new BoardFour();
                break;
            case 6:
                concrete = new BoardSix();
                break;
        }
        fields = concrete.getFields();
        makeCircles();

    }

    private void makeCircles() {
        for(Field f : fields) {
            Circle c = new Circle(f.x*scale,f.y*scale,radius*scale);
            c.setFill(getPlayerColor(f.getPlayer()));
            c.setStroke(Color.BLACK);
            c.setOnMouseClicked((e -> handleMouseClick(c)));
            circles.add(c);
        }
    }

    private void handleMouseClick(Circle c) {
            for(Field f: fields) {
                double fX = c.getCenterX()/scale;
                double fY = c.getCenterY()/scale;

                if( fX == f.x && fY == f.y && f.getPlayer() == currentPlayer && yourTurn) {
                    currentFieldNr = fields.indexOf(f);
                    showMoves();
                }
                if(currentFieldNr >= 0 && fX == f.x && fY == f.y && f.getPlayer() == 0 && yourTurn){

                    setMoving(fields.indexOf(f), currentPlayer, currentFieldNr);
                   // move(currentPlayer,fields.indexOf(f), currentFieldNr);
                    game.unlock();
                }
            }

    }

    public void setMoving(int index, int player, int field) {
        this.startIndex = field;
        this.jumpIndex = index;
        this.movingPlayer = player;
    }

    public void move( int player,int jumpTo, int currentField) {
        fields.get(jumpTo).setPlayer(player);
        fields.get(currentField).setPlayer(0);
        currentFieldNr = -1;
        hideMoves();
        updateCircles();

    }


    private void showMoves() {
        for (Circle c: circles) {
            double moveDist = calculateDist(circles.get(1),circles.get(2));
            if(calculateDist(c,circles.get(currentFieldNr)) <= moveDist && fields.get(circles.indexOf(c)).getPlayer() == 0)
                c.setStroke(Color.ORANGERED);
        }
    }

    private void hideMoves() {
        for (Circle c: circles) {
            c.setStroke(Color.BLACK);
        }
    }

    public void updateCircles() {
        for(Circle c : circles) {
            c.setFill(getPlayerColor(fields.get(circles.indexOf(c)).getPlayer()));
        }
    }

    private Color getPlayerColor(int player) {
        switch(player) {
            case 1:
                return Color.BLUEVIOLET;
            case 2:
                return Color.ORANGE;
            case 3:
                return Color.GREEN;
            case 4:
                return Color.INDIANRED;
            case 5:
                return Color.YELLOW;
            case 6:
                return Color.CADETBLUE;
            default:
                return Color.BLACK;
        }
    }

    public double calculateDist(Circle c1, Circle c2) {
        double xDis = c1.getCenterX()-c2.getCenterX();
        double yDis = c1.getCenterY()-c2.getCenterY();
        return Math.sqrt(xDis*xDis + yDis*yDis);
    }


}
