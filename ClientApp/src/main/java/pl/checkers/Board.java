package pl.checkers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import pl.checkers.builders.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Klasa odpowiedzialna za logikę ruchów
 */
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

    boolean moved = false;
    boolean jump = false;

    int startIndex;
    int movingPlayer;
    int jumpIndex;
    int prev;

    /**
     *  Metoda tworząca tablicę dla danej ilości graczy
     *
     * @param size Rozmiar tablicy zależny od ilości graczy
     */
    public Board(int size) {
        this.size = size;
        /* Wzorzec budowniczego */
        switch(size) {
            case 2:
                concrete = new BoardTwo();
                break;
            case 3:
                concrete = new BoardThreeTest();
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

    /**Funkcja tworząca pionki w GUI*/
    private void makeCircles() {
        for(Field f : fields) {
            Circle c = new Circle(f.getX()*scale,f.getY()*scale,radius*scale);
            c.setFill(getPlayerColor(f.getPlayer()));
            c.setStroke(Color.BLACK);
            c.setOnMouseClicked((e -> {
                try {
                    handleMouseClick(c);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }));
            circles.add(c);
        }
    }

    /**Mouse click handler dla pionków*/
    private void handleMouseClick(Circle c) throws IOException {
            for(Field f: fields) {
                double fX = c.getCenterX()/scale;
                double fY = c.getCenterY()/scale;

                //Zaznaczenie pionka do poruszenia
                if( fX == f.getX() && fY == f.getY() && f.getPlayer() == currentPlayer && yourTurn && !jump) {
                    currentFieldNr = fields.indexOf(f);
                    showMoves();
                }
                //Wybranie pola docelowego
                if(currentFieldNr >= 0 && fX == f.getX() && fY == f.getY() && f.getPlayer() == 0 && yourTurn){
                    setMoving(fields.indexOf(f), currentPlayer, currentFieldNr);
                    game.requestMove();
                    game.unlock();
                }
            }

    }

    /**Funkcja ustawia parametry do wysłania do serwera*/
    public void setMoving(int index, int player, int field) {
        this.startIndex = field;
        this.jumpIndex = index;
        this.movingPlayer = player;
    }

    /**Funkcja przemieszczająca piony na planszy*/
    public void move( int player,int jumpTo, int currentField) {
        fields.get(jumpTo).setPlayer(player);
        fields.get(currentField).setPlayer(0);
        prev = currentField;
        currentFieldNr = -1;
        hideMoves();
        updateCircles();
        circles.get(prev).setFill(Color.rgb(120,0,0));
    }

    /**Funkcja pokazująca użytkownikowi dozwolone ruchy*/
    public void showMoves() {
        hideMoves();
        double moveDist = calculateDist(circles.get(1),circles.get(2));
        for (Circle c: circles) {

            if(calculateDist(c,circles.get(currentFieldNr)) <= moveDist && fields.get(circles.indexOf(c)).getPlayer() == 0 && !jump) {
                c.setStroke(Color.ORANGERED);
            }
            if(calculateDist(c,circles.get(currentFieldNr)) <= moveDist && fields.get(circles.indexOf(c)).getPlayer() != 0) {
                showJumps(circles.get(currentFieldNr),c);
            }
        }
    }

    /**Funkcja wyświetlająca dozwolone skoki*/
    private void showJumps(Circle main, Circle ch) {

        double xDis = ch.getCenterX() - main.getCenterX() ;
        double yDis = ch.getCenterY() - main.getCenterY() ;

        for(Circle c: circles) {
            if(c.getCenterX() - ch.getCenterX() == xDis && c.getCenterY() - ch.getCenterY() == yDis &&
                    fields.get(circles.indexOf(c)).getPlayer() == 0 && circles.indexOf(c) != prev) {
                c.setStroke(Color.DARKCYAN);
            }
        }
    }

    /**Funkcja do "chowania podpowiedzi" */
    private void hideMoves() {
        for (Circle c: circles) {
            c.setStroke(Color.BLACK);
        }
    }

    /**Funkcja pośrednicząca w wyświetalniu pozycji pionków w GUI*/
    public void updateCircles() {
        for(Circle c : circles) {
            c.setFill(getPlayerColor(fields.get(circles.indexOf(c)).getPlayer()));
        }
    }

    /**Funkcja zwracająca kolor gracza o danym numerze*/
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

    /**Funkcja licząca dystans między dwoma polami
     * Używana do pokazywania dozwolonych ruchów */
    public double calculateDist(Circle c1, Circle c2) {
        double xDis = c1.getCenterX()-c2.getCenterX();
        double yDis = c1.getCenterY()-c2.getCenterY();
        return Math.sqrt(xDis*xDis + yDis*yDis);
    }

    /**
     * Ustawia obecną grę
     * @param game
     */
    public void passGame(Game game) {
        this.game = game;
    }

    /**
     * Ustawia bieżące pole
     * @param currentFieldNr
     */
    public void setCurrentFieldNr(int currentFieldNr) {
        this.currentFieldNr = currentFieldNr;
    }
}
