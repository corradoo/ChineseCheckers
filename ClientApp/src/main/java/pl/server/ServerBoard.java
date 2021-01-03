package pl.server;

import pl.checkers.Field;
import pl.checkers.builders.*;
import java.util.ArrayList;

public class ServerBoard {
    ConcreteBoard concrete;
    ArrayList<Field> fields;
    double jumpDist = 30;

    ServerBoard(int mode) {
        switch (mode) {
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
    }

    public boolean validateMove(String msg) {
        String[] arr= msg.split(" ");
        int start = Integer.parseInt(arr[1]);
        int end = Integer.parseInt(arr[0]);
        //Czy wybrano pionek
        //Czy miejsce jest w zasięgu normalnego ruchu
        //Czy end jest puste
        if(fields.get(start).getPlayer() != 0 && calculateDist(start,end) <= jumpDist && fields.get(end).getPlayer() == 0) {
            move(start,end);
            return true;
        }
        return false;
        //Czy wykonano skok
    }
    private void move(int start,int end) {
       fields.get(end).setPlayer(fields.get(start).getPlayer());
       fields.get(start).setPlayer(0);
    }

    public double calculateDist(int f1, int f2) {
        double xDis = fields.get(f1).getX() - fields.get(f2).getX();
        double yDis = fields.get(f1).getY() - fields.get(f2).getY();
        return Math.sqrt(xDis*xDis + yDis*yDis);
    }
}
