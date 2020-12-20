package pl.checkers;

public class Field {
    int x;
    int y;
    int player;

    boolean empty = true;

    Field(int x, int y, int player) {
        this.x = x;
        this.y = y;
        this.player = player;
    }

    public void setPlayer(int player) {
        this.player = player;
        empty = false;
    }

    public boolean isEmpty() {
        return empty;
    }

}
