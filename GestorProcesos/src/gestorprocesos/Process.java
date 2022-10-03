/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorprocesos;

import java.time.LocalDateTime;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author jordi y Kevin
 */
public class Process {
    private final BCP bcp;
    private List<Instruction> listInstructions;
    private String state = "nuevo";
    private LocalDateTime started;
    private LocalDateTime finished;
    private String ID;
    
    public Process() {
        this.bcp = new BCP();
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public LocalDateTime getStarted() {
        return started;
    }

    public void setStarted(LocalDateTime started) {
        this.started = started;
    }

    public LocalDateTime getFinished() {
        return finished;
    }

    public void setFinished(LocalDateTime finished) {
        this.finished = finished;
    }
    

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
        return this.bcp;
    }

    public List<Instruction> getListInstructions() {
        return this.listInstructions;
    }

    public void setListInstructions(List<Instruction> listValues) {
        this.listInstructions = listValues;
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
