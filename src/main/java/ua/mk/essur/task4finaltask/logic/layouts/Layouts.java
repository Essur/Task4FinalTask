package ua.mk.essur.task4finaltask.logic.layouts;

import lombok.Data;
import ua.mk.essur.task4finaltask.logic.layouts.layout.Layout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Layouts implements Serializable {
    public static final long serialUID = 123456789L;
    private List<Layout> layoutList;
    private double totalCost;

    public Layouts() {
        this.layoutList = new ArrayList<>();
        totalCost = 0;
    }

    public void add(String name, double pricePerPiece, int countOfMade){
        Layout layout = new Layout(name, pricePerPiece, countOfMade);
        layoutList.add(layout);
        countTotalCost();
    }
    public void addAll(List<Layout> layoutList){
        this.layoutList.addAll(layoutList);
        countTotalCost();
    }
    private void countTotalCost(){
        totalCost = 0;
        layoutList.forEach(layout -> totalCost += layout.getTotalLayoutPrice());
    }
    public void remove(String name){
        layoutList.removeIf(layout -> name.equals(layout.getName()));
        countTotalCost();
    }

    public void edit(int id, String name, double pricePerPiece, int countOfMade) {
        layoutList.set(id,new Layout(name,pricePerPiece,countOfMade));
        countTotalCost();
    }

    public void clear() {
        layoutList.clear();
        totalCost = 0;
    }
}
