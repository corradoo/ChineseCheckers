package pl.checkers.builders;
/**
 * Klasa tworząca pola dla standarowego rozmiaru planszy
 * w trybie dla trzech graczy
 * */
public class BoardThree extends ConcreteBoard{

    public BoardThree() {
        drawBoard();

        drawBase(8,1,4,false,1,0);
        drawBase(4,10,4,false,3,0);
        drawBase(4+9,10,4,false,2,0);

        drawBase(4+9,8,4,true,0,3);
        drawBase(4,8,4,true,0,2);
        drawBase(8,17,4,true,0,1);
    }
}
