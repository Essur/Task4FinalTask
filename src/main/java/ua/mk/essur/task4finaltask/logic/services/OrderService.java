package ua.mk.essur.task4finaltask.logic.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import ua.mk.essur.task4finaltask.logic.converter.FileNameConverter;
import ua.mk.essur.task4finaltask.logic.layouts.Layouts;
import ua.mk.essur.task4finaltask.logic.saveAndLoad.SaveAndLoad;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Data
@AllArgsConstructor
@Service
public class OrderService {
    private static OrderService instance;
    private Layouts layouts;
    private SaveAndLoad saveAndLoad;

    public OrderService() {
        this.layouts = new Layouts();
        this.saveAndLoad = new SaveAndLoad();
    }

    public static OrderService getInstance() {
        if(instance == null){
           instance = new OrderService();
        } return instance;
    }

    public void addLayout(String name, double pricePerPiece, int countOfMade){
        if(isValidValue(pricePerPiece, countOfMade)) {
            layouts.add(name, pricePerPiece, countOfMade);
        } else {
            throw new RuntimeException("Invalid values!");
        }
    }

    public void removeLayout(String name){
        layouts.remove(name);
    }

    private boolean isValidValue(double pricePerPiece, int countOfMade){
        return !(pricePerPiece < 0) && countOfMade >= 0;
    }

    public void clearList() {
        layouts.clear();
    }

    public String saveOrder(String customerName, String fileName) {
        if (Files.exists(Path.of("src/main/resources/orders/" + customerName + "/" + fileName))) {
            return "File was exist";
        } else {
            saveAndLoad.setLayouts(layouts);
            return saveAndLoad.saveOrder(customerName, fileName);
        }
    }

    public Layouts loadOrder(String dirName,String fileName){
        String file = FileNameConverter.fromTxtToDat(fileName);
        saveAndLoad.readOrder(dirName,file);
        layouts.addAll(saveAndLoad.getLayouts().getLayoutList());
        return layouts;
    }
    public boolean removeFile(String fileName){
        Path dir = Path.of("src/main/resources/orders/" + fileName);
        if (Files.exists(dir)){
            try {
                Files.delete(dir);
                Files.delete(Path.of(dir.toString().replace(".txt",".dat")));
            } catch (IOException e) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
}
