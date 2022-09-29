/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorprocesos;

import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author jordi y Kevin
 */
public class Process {
    private BCP bcp;
    private List<Instruction> listInstructions;
    private int size = 0;
    
    public Process() {}
    
    private String[] splitData(String data, String limit){
        String[] arrOfStr = data.split(limit);
        return arrOfStr;
    }

    public int getSize() {
        return size;
    }
    
    private boolean isRegister(String register){
        switch (register) {
            case "AX" -> {
                return true;
            }
            case "BX" -> {
                return true;
            }
               case "CX" -> {
                   return true;
            }
               case "DX" -> {
                   return true;
            }
               /*case null -> { 
                   return true;
               }*/
           default -> {
               return  false;
            }
        }
    }
        
    public BCP getBcp() {
        return bcp;
    }

    public List<Instruction> getListInstructions() {
        return listInstructions;
    }

    public void setListInstructions(List<Instruction> listValues) {
        listInstructions = listValues;
    }
    
    public boolean searchError() {
        for (Instruction temp : listInstructions) {
            if(!temp.getError().equals("")) {
                JFrame f = new JFrame("frame");
                JOptionPane.showMessageDialog(f ,
                "Se ha encontrado un error en el codigo cargado en la linea "+Integer.toString(temp.getLine())+".",
                "Error de archivo cargado" ,
                JOptionPane.ERROR_MESSAGE);
                return true;
            }
        }
        return false;
    }
}
