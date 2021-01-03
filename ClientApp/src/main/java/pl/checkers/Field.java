package pl.checkers;

public class Field {

    int x;
    int y;
    private int player;
    int base;
    boolean empty = true;

    public Field(int x, int y, int player,int base) {
        this.x = x;
        this.y = y;
        this.player = player;
        this.base = base;
    }

    public void setPlayer(int player) {
        this.player = player;
        empty = false;
    }

    public boolean isEmpty() {
        return empty;
    }

    public int getBase(){
        return base;
    }

    public int getPlayer() {
        return player;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
