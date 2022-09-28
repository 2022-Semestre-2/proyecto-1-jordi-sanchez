/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package programcontroller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;

/**
 *
 * @author Kevin
 */
public class FileLoader {
    
    private String EXTENSION = ".asm";
    private String content = "";
    
    FileLoader() {}
    
    public boolean isExtensionASM(String nameFile){
        String extensionFile = nameFile.substring(nameFile.length()-4);
        return extensionFile.equals(EXTENSION);
    }
    
    public void loadFile() {
        JFileChooser fileChooser = new JFileChooser();
        int selectionWindow = fileChooser.showOpenDialog(fileChooser);
        
        if (selectionWindow == JFileChooser.APPROVE_OPTION) {
            File filePath = fileChooser.getSelectedFile();
            
            if (isExtensionASM(filePath.getAbsolutePath())) {
                try (FileReader fileReader = new FileReader(filePath)) {
                    int value = fileReader.read();
                    while (value != -1) {
                        content = content + (char) value;
                        value = fileReader.read();
                    }
                } catch (IOException el) {
                    el.printStackTrace();
                }
            } 
        }
    }
    
    //metodo para convertir el contenido del archivo en un programa
}
