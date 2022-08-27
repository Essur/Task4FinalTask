package ua.mk.essur.task4finaltask.logic.saveAndLoad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.mk.essur.task4finaltask.logic.layouts.layout.Layout;
import ua.mk.essur.task4finaltask.logic.layouts.Layouts;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveAndLoad {
    Layouts layouts;

    public String saveOrder(String name, String fileName) {
        Path dir = null;
        try {
            dir = Files.createDirectories(Paths.get("src/main/resources/orders/" + name + '/'));
        } catch (IOException e) {
            return "I/O Exception";
        }
        try(FileWriter writer = new FileWriter(dir.toString() + '/' + fileName + ".txt")){
            writer.write("Name - " + "Price per piece - " + "Count of made - " + "Total price for layout " + "\n");
            for (Layout layout : layouts.getLayoutList()) {
               writer.write(layout.getName() + " - " + layout.getPricePerPiece() + " - " + layout.getCountOfMade() + " - " + layout.getTotalLayoutPrice() + "\n");
            }
            writer.write("Total price of order: " + layouts.getTotalCost());
            saveOrderDat(name,fileName);
            return "Saved!";
        } catch (IOException e) {
            return "I/O Exception";
        }
    }

    private void saveOrderDat(String name, String fileName) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/main/resources/orders/" + name + '/' + fileName + ".dat"))){
            oos.writeObject(layouts);
        } catch (IOException e)  {
            throw new RuntimeException(e);
        }
    }

    public void readOrder(String fileName){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/main/resources/orders/" + fileName))) {
            layouts = (Layouts) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
