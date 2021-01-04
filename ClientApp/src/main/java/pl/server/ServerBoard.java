package pl.server;

import pl.checkers.Field;
import pl.checkers.builders.*;
import java.util.ArrayList;

public class ServerBoard {
    ConcreteBoard concrete;
    ArrayList<Field> fields;
    double jumpDist = 30;
    ArrayList<Integer> players = new ArrayList<>();

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
        for(int i=1; i<=6; i++){
            players.add(i);
        }
        fields.get(0).setPlayer(1);
        fields.get(62).setPlayer(0);

        fields.get(60).setPlayer(2);
        fields.get(72).setPlayer(0);

        fields.get(52).setPlayer(3);
        fields.get(86).setPlayer(0);
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

    public int getWinner() {
        int player=0;
        int c1 = 0,c2=0,c3=0,c4=0,c5=0,c6=0;
        for(Field field: fields){
            if(field.getBase()!=0 && field.getPlayer()!=0){
                if(field.getBase()==1&&players.contains(1)){
                    if(field.getPlayer()==1){
                        c1++;
                        if(c1==10) {
                            player=1;
                            players.set(0,0);
                        }
                    }
                }
                else if(field.getBase()==2&&players.contains(2)){
                    if(field.getPlayer()==2){
                        c2++;
                        if(c2==10) {
                            player=2;
                            players.set(1,0);
                        }
                    }
                }
                else if(field.getBase()==3&&players.contains(3)){
                    if(field.getPlayer()==3){
                        c3++;
                        if(c3==10) {
                            player=3;
                            players.set(2,0);
                        }
                    }
                }
                else if(field.getBase()==4&&players.contains(4)){
                    if(field.getPlayer()==4){
                        c4++;
                        if(c4==10) {
                            player=4;
                            players.set(3,0);
                        }
                    }
                }
                else if(field.getBase()==5&&players.contains(5)){
                    if(field.getPlayer()==5){
                        c5++;
                        if(c5==10) {
                            player=5;
                            players.set(4,0);
                        }
                    }
                }
                else if(field.getBase()==6&&players.contains(6)){
                    if(field.getPlayer()==6){
                        c6++;
                        if(c6==10) {
                            player=6;
                            players.set(5,0);
                        }
                    }
                }


            }
        }

        return player;
    }


    public double calculateDist(int f1, int f2) {
        double xDis = fields.get(f1).getX() - fields.get(f2).getX();
        double yDis = fields.get(f1).getY() - fields.get(f2).getY();
        return Math.sqrt(xDis*xDis + yDis*yDis);
    }
}
