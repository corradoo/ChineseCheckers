package pl.server;

public class Move {
    private int from;
    private int to;
    private int moveNr;

    public Move(int from, int to, int moveNr) {
        this.from = from;
        this.to = to;
        this.moveNr = moveNr;
    }
    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getMoveNr() {
        return moveNr;
    }

    public void setMoveNr(int moveNr) {
        this.moveNr = moveNr;
    }
}
