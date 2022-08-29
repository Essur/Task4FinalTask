package ua.mk.essur.task4finaltask.logic.converter;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public interface FileNameConverter {
    static String convertFileName(String fileName){
        int i = fileName.indexOf('/');
        int j = fileName.lastIndexOf('_');
        return fileName.substring(i+1,j);
    }

    static String makeFullName(String fileName){
        return fileName + '_' + LocalDate.now() + ".txt";
    }
    static String fromTxtToDat(String fileName){
        return fileName.replace(".txt",".dat");
    }
}
