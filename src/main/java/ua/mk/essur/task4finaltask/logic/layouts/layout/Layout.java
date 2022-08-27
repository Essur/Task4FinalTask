package ua.mk.essur.task4finaltask.logic.layouts.layout;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
public class Layout implements Serializable {
    public static final long serialUID = 123456789L;
    private String name;
    private double pricePerPiece;
    private int countOfMade;
    private double totalLayoutPrice;

    public Layout(String name, double pricePerPiece, int countOfMade) {
        this.name = name;
        this.pricePerPiece = pricePerPiece;
        this.countOfMade = countOfMade;
        this.totalLayoutPrice = pricePerPiece * countOfMade;
    }

    public void setPricePerPiece(double pricePerPiece) {
        this.pricePerPiece = pricePerPiece;
        totalPrice();
    }

    public void setCountOfMade(int countOfMade) {
        this.countOfMade = countOfMade;
        totalPrice();
    }
    private void totalPrice(){
        this.totalLayoutPrice = pricePerPiece * countOfMade;
    }

}
