package pl.checkers.builders;

import pl.checkers.Field;

import java.util.ArrayList;

public abstract class ConcreteBoard implements BoardBuilder {

    private ArrayList<Field> fields = new ArrayList<>();
    int gapX = 20,gapY = 17;
    int oddRowX = 10;

    /**
     * Funkcja tworząca promienie na planszy
     */
    public void drawBase( int beginX,int beginY,int h, boolean invert, int player) {
        int endX = beginX;
        int endY;

        if(!invert) {
            endY = beginY + h;
            for(int i = beginY; i < endY;i++) {
                for(int j = beginX; j<=endX;j++) {
                    Field f;

                    if (i % 2 == 0) {
                        f = new Field(j * gapX, i * gapY, player);
                    } else {
                        f = new Field(j * gapX + oddRowX, i * gapY,player);
                    }

                    fields.add(f);
                }
                if (i % 2 == 0) beginX--;
                else endX++;
            }
        } else {
            endY = beginY - h;
            for (int i = beginY; i > endY; i--) {
                for(int j = beginX; j<=endX;j++) {
                    Field f;

                    if (i % 2 == 0) {
                        f = new Field(j * gapX, i * gapY,player);
                    } else {
                        f = new Field(j * gapX + oddRowX, i * gapY,player);
                    }
                    fields.add(f);
                }
                if (i % 2 == 0) beginX--;
                else endX++;

            }
        }
    }

    /**
     * Tworzenie planszy bez baz
     */
    public void drawBoard() {

        int beginX = 17 / 2 - 2, endX = beginX + 5;
        int beginY = 5, endY = 14, middleY = 9;
        for (int i = beginY; i < endY; i++) {
            for (int j = beginX; j < endX; j++) {
                Field f;
                if (i % 2 == 0) {
                    f = new Field(j * gapX, i * gapY,0);
                } else {
                    f = new Field(j * gapX + oddRowX, i * gapY,0);
                }
                fields.add(f);
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

    public ArrayList<Field> getFields() {
        return fields;
    }
}
