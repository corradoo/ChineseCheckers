package pl.checkers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Board {


    public ArrayList<Circle> circles = new ArrayList<>();
    public ArrayList<Circle> chequers = new ArrayList<>();


    public Circle currChequer = null;
    public int yGap = 45;
    Socket socket;
    public PrintWriter out;









    Board(Socket socket, PrintWriter out) throws Exception {

        this.socket=socket;
        this.out=out;
        drawBoard();
        drawBase(8,1,4,false);
        drawBase(4,10,4,false);
        drawBase(4+9,10,4,false);

        drawBase(4+9,8,4,true);
        drawBase(4,8,4,true);
        drawBase(8,17,4,true);
    }
    /**
     * Funkcja tworząca promienie na planszy
     */
    public void drawBase( int beginX,int beginY,int h, boolean invert) {
        int endX = beginX;
        int endY;

        if(!invert) {
            endY = beginY + h;
            for(int i = beginY; i < endY;i++) {
                for(int j = beginX; j<=endX;j++) {
                    Circle c;

                    if (i % 2 == 0) {
                        c = new Circle(j * 50, i * yGap, 24);
                    } else {
                        c = new Circle(j * 50 + 25, i * yGap, 24);
                    }
                    c.setOnMouseClicked((event -> {
                        if(currChequer != null) {
                            try {
                                moveChequer(c);
                            } catch (Exception e) {
                            }
                            //currChequer = null;
                        }
                    }));
                    circles.add(c);

                }
                if (i % 2 == 0) beginX--;
                else endX++;
            }
        } else {
            endY = beginY - h;
            for (int i = beginY; i > endY; i--) {
                for (int j = beginX; j <= endX; j++) {
                    Circle c;
                    if (i % 2 == 0) {
                        c = new Circle(j * 50, i * yGap, 24);
                    } else {
                        c = new Circle(j * 50 + 25, i * yGap, 24);
                    }
                    c.setOnMouseClicked((event -> {
                        if(currChequer != null) {
                            try {
                                moveChequer(c);
                            } catch (Exception e) {
                            }
                            currChequer = null;

                        }
                    }));
                    circles.add(c);

                }
                if (i % 2 == 0) beginX--;
                else endX++;

            }
        }
    }

    public void putChequers( int beginX,int beginY,int h, boolean invert, Color color) {
        int endX = beginX;
        int endY;

        if(!invert) {
            endY = beginY + h;
            for(int i = beginY; i < endY;i++) {
                for(int j = beginX; j<=endX;j++) {
                    Circle c;

                    if (i % 2 == 0) {
                        c = new Circle(j * 50, i * yGap, 24);
                    } else {
                        c = new Circle(j * 50 + 25, i * yGap, 24);
                    }
                    c.setFill(color);
                    c.setOnMouseClicked((event -> {
                        if(currChequer == null) {
                            setCurrentChequer(c);
                            showMoves(currChequer);
                        }
                    }));
                    c.setOnMouseMoved((e -> c.setOpacity(0.8)));
                    c.setOnMouseExited((e -> c.setOpacity(1)));
                    chequers.add(c);

                }
                if (i % 2 == 0) beginX--;
                else endX++;
            }
        } else {
            endY = beginY - h;
            for (int i = beginY; i > endY; i--) {
                for (int j = beginX; j <= endX; j++) {
                    Circle c;
                    if (i % 2 == 0) {
                        c = new Circle(j * 50, i * yGap, 24);
                    } else {
                        c = new Circle(j * 50 + 25, i * yGap, 24);
                    }
                    c.setFill(color);
                    c.setOnMouseClicked((event -> {
                        if(currChequer == null) {
                            setCurrentChequer(c);
                            showMoves(currChequer);
                        }
                    }));
                    c.setOnMouseMoved((e -> c.setOpacity(0.8)));
                    c.setOnMouseExited((e -> c.setOpacity(1)));
                    chequers.add(c);

                }
                if (i % 2 == 0) beginX--;
                else endX++;

            }
        }
    }

    private void setCurrentChequer(Circle c) {
        for (Circle ch: chequers) {
            if(ch.getCenterX() == c.getCenterX() && ch.getCenterY() == c.getCenterY())
                currChequer = ch;

        }
    }

    private void showMoves(Circle chequer) {
        paintNeighbours(chequer);
    }

    public void drawBoard() {

        /*Tworzenie planszy bez baz*/
        int beginX = 17 / 2 - 2, endX = beginX + 5;
        int beginY = 5, endY = 14, middleY = 9;
        for (int i = beginY; i < endY; i++) {
            for (int j = beginX; j < endX; j++) {
                Circle c;
                if (i % 2 == 0) {
                    c = new Circle(j * 50, i * yGap, 24);
                } else {
                    c = new Circle(j * 50 + 25, i * yGap, 24);
                }
                c.setOnMouseClicked((event -> {
                    if(currChequer != null) {
                        try {
                            moveChequer(c);
                        } catch (Exception e) {

                        }
                        currChequer = null;
                    }
                }));
                circles.add(c);
            }
            if (i >= middleY) {
                if (i % 2 == 1) beginX++;
                else endX--;
            } else {
                if (i % 2 == 0) beginX--;
                else endX++;
            }
        }
    }

    private void moveChequer( Circle c) throws Exception {
        for (Circle ch:chequers) {
            if(ch.getCenterX() == currChequer.getCenterX() && ch.getCenterY() == currChequer.getCenterY()) {
                System.out.println("request");
                requestMove(c, ch);

                /*ch.setCenterX(c.getCenterX());
                ch.setCenterY(c.getCenterY());

                 */
                for (Circle cir: circles)cir.setStroke(Color.BLACK);
            }
            if (ch.getCenterX() == currChequer.getCenterX() && ch.getCenterY() == currChequer.getCenterY() && c.getStroke() == Color.LIGHTCYAN) {

                ch.setCenterX(c.getCenterX());
                ch.setCenterY(c.getCenterY());

                for (Circle cir: circles)cir.setStroke(Color.BLACK);

            }
        }
    }

    private void requestMove(Circle c, Circle ch){
        /*ArrayList<Circle> moving= new ArrayList<>();
        moving.add(0,c);
        moving.add(1,ch);

        OutputStream outputStream=socket.getOutputStream();
        ObjectOutputStream objectOutputStream= new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(moving);

         */
        System.out.println("w request");
        out.println("MOVECH "+c.getCenterX()+" "+c.getCenterY()+" "+ch.getCenterX()+" "+ch.getCenterY());
    }

    public void moveResponse(double cX, double cY, double chX, double chY){
        for (Circle ch:chequers) {
            if(ch.getCenterX() == chX && ch.getCenterY() == chY) {
                ch.setCenterX(cX);
                ch.setCenterY(cY);
            }
        }
    }

    public void paintNeighbours(Circle main) {
        double dist;
        double xDis;
        double yDis;
        for (Circle c: circles) {
            xDis = c.getCenterX()-main.getCenterX();
            yDis = c.getCenterY()-main.getCenterY();
            dist = Math.sqrt(xDis*xDis + yDis*yDis);
            if(dist < 60 && dist !=0) c.setStroke(Color.RED);
        }
        for (Circle ch: chequers) {
            xDis = ch.getCenterX()-main.getCenterX();
            yDis = ch.getCenterY()-main.getCenterY();
            dist = Math.sqrt(xDis*xDis + yDis*yDis);
            if(dist < 60 && dist !=0) {
                showJumps(main,ch);
            }
        }
    }

    private void showJumps(Circle main ,Circle ch) {

        double xDis = ch.getCenterX() - main.getCenterX();
        double yDis = ch.getCenterY() - main.getCenterY();

        for (Circle c : circles) {
            if (c.getCenterX() - ch.getCenterX() == xDis && c.getCenterY() - ch.getCenterY() == yDis) {
                c.setStroke(Color.LIGHTCYAN);
            }
        }

    }

    public double calculateDist(Circle c1, Circle c2) {
        double xDis = c1.getCenterX()-c2.getCenterX();
        double yDis = c1.getCenterY()-c2.getCenterY();
        return Math.sqrt(xDis*xDis + yDis*yDis);
    }
}
