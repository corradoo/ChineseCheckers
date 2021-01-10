package pl.server;

import pl.checkers.Field;
import pl.checkers.builders.*;
import java.util.ArrayList;

public class ServerBoard {
    ConcreteBoard concrete;
    ArrayList<Field> fields;
    double jumpDist = 30;
    ArrayList<Integer> players = new ArrayList<>();

    boolean jumped = false;
    public int prevIndex;
    public int movingIndex;

    /**
     * Ustawia tryb gry w zależnosci od ilości graczy
     * @param mode Ilość graczy
     */
    public ServerBoard(int mode) {
        switch (mode) {
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
        for(int i=1; i<=6; i++){
            players.add(i);
        }
    }

    /**
     * Przetwarza wiadomość od klienta i sprawdza czy wykonany ruch jest poprawny
     * @param msg Wiadomość o ruchu od klienta
     * @return Zwraca czy ruch jest poprawny czy nie
     */
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
        if(jumpable(start,end)) {
            move(start,end);
            movingIndex = end;
            prevIndex = start;
            jumped = true;
            return true;
        }
        return false;
        //Czy wykonano skok
    }

    /**
     * Wykonuje ruch na tablicy serwerowej
     * @param start Lokacja startowa
     * @param end Lokacja końcowa
     */
    private void move(int start,int end) {
       fields.get(end).setPlayer(fields.get(start).getPlayer());
       fields.get(start).setPlayer(0);
    }

    /**
     * Sprawdza czy któryś zawodnik już skończył grę
     * @return Zwraca ID zwycięzcy lub 0 jeśli nikt jeszcze nie wygrał
     */
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

    /**
     * Oblicza dystans między dwoma polami
     * @param f1 Pole startowe
     * @param f2 Pole końcowe
     * @return Zwraca dystans między polami
     */
    public double calculateDist(int f1, int f2) {
        double xDis = fields.get(f1).getX() - fields.get(f2).getX();
        double yDis = fields.get(f1).getY() - fields.get(f2).getY();
        return Math.sqrt(xDis*xDis + yDis*yDis);
    }

    /** Sprawdza czy rządany ruch może zostać wykonany jako skok*/
    public boolean jumpable(int start, int end) {
        //Jeżeli przekroczono zasięg skoku zwróć fałsz
        if(calculateDist(start,end) > jumpDist*2.0) return false;

        //Sprawdź czy pomiędzy polem docelowym i poczatkowym znajduje się zajęte pole w połowie drogi
        double x = (fields.get(start).getX() + fields.get(end).getX())/2.0;
        double y = (fields.get(start).getY() + fields.get(end).getY())/2.0;
        for(Field f : fields) {
            if(f.getX() == x && f.getY() == y && fields.get(end).getPlayer() == 0
                    && fields.indexOf(f) != prevIndex && f.getPlayer() != 0 ) {
                jumped = true;
                return true;
            }
        }
        return false;
    }
    /** Sprawdza poprawność ruchu po wykonaniu skoku*/
    public boolean validateJump(String msg) {
        String[] arr= msg.split(" ");
        int start = Integer.parseInt(arr[1]);
        int end = Integer.parseInt(arr[0]);

        if(jumpable(start,end) && end != prevIndex) {
            move(start,end);
            movingIndex = end;
            prevIndex = start;
            return true;
        }
        return false;
    }

    /** Sprawdza czy wykonanie dłigoego skoku jest możliwe*/
    public boolean nextJumpPossible() {
        double x;
        double y;
        for(Field f : fields) {
            //Jeżeli ma sąsiada który jest pionkiem
            if(calculateDist(fields.indexOf(f), movingIndex) <= jumpDist && f.getPlayer() != 0) {
                x = f.getX() + f.getX() - fields.get(movingIndex).getX();
                y = f.getY() * 2.0 - fields.get(movingIndex).getY();
                //Jeżeli ma, szukamy czy można przez niego skoczyć
                for(Field f2 : fields) {
                    if(x == f2.getX() && y == f2.getY() && f2.getPlayer() == 0 && fields.indexOf(f2) != prevIndex) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
