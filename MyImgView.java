package model;

import javafx.scene.image.ImageView;

//klasa do wyswietlania rysunkow. tutaj bedziemy przychowac pozycje pola gry
public class MyImgView extends ImageView {
    private int positionX; //  х
    private int positionY; //  у
    private int value = 0; // przechowywa Х lub О

    public MyImgView() {
    }

    public MyImgView(String url) {
        super(url);
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}