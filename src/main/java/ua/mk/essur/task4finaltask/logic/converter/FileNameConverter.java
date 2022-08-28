package ua.mk.essur.task4finaltask.logic.converter;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public interface FileNameConverter {
    public static String convertFileName(String fileName){
        int i = fileName.indexOf('/');
        int j = fileName.lastIndexOf('_');
        return fileName.substring(i+1,j);
    }

    public static String makeFullName(String fileName){
        return fileName + '_' + LocalDate.now() + ".txt";
    }
    public static String fromTxtToDat(String fileName){
        return fileName.replace(".txt",".dat");
    }
}
