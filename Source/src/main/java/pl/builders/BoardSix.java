package pl.builders;
/**
 * Klasa tworząca pola dla standarowego rozmiaru planszy
 * w trybie dla sześciu graczy
 * */
public class BoardSix extends ConcreteBoard {

    public BoardSix() {
        drawBoard();

        drawBase(8,1,4,false,1,4);
        drawBase(4,10,4,false,5,2);
        drawBase(4+9,10,4,false,3,6);

        drawBase(4+9,8,4,true,2,5);
        drawBase(4,8,4,true,6,3);
        drawBase(8,17,4,true,4,1);
    }
}
