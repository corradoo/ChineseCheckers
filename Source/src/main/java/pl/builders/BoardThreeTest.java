package pl.builders;

public class BoardThreeTest extends ConcreteBoard{
    public BoardThreeTest() {
        drawBoard();

        drawBase(8,1,4,false,1,1);
        drawBase(4,10,4,false,3,3);
        drawBase(4+9,10,4,false,2,2);

        drawBase(4+9,8,4,true,0,3);
        drawBase(4,8,4,true,0,2);
        drawBase(8,17,4,true,0,1);
        setTestPos();
    }

    void setTestPos(){
        fields.get(0).setPlayer(1);
        fields.get(62).setPlayer(0);

        fields.get(60).setPlayer(2);
        fields.get(72).setPlayer(0);

        fields.get(35).setPlayer(3);
        fields.get(86).setPlayer(0);
    }
}
