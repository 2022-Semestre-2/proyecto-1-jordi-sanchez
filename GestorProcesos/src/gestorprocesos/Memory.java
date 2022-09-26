/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorprocesos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jordi
 */
public class Memory {
    private int Size;
    private int currentMemory; // for position of next process
    private int availableMemory;
    private Process cuerrentProcess;
    private Process nextProcess;
    private List<Process> listProcess = new ArrayList<>();
    
    public boolean addProcess(Process process){
        if (this.availableMemory < process.getSize()){
            return false;
        } else {
          listProcess.add(process);
          return true;
        }
    }  

    public int getSize() {
        return Size;
    }

    public void setSize(int Size) {
        this.Size = Size;
    }

    public int getCurrentMemory() {
        return currentMemory;
    }

    public void setCurrentMemory(int currentMemory) {
        this.currentMemory = currentMemory;
    }

    public int getAvailableMemory() {
        return availableMemory;
    }

    public void setAvailableMemory(int availableMemory) {
        this.availableMemory = availableMemory;
    }

    public Process getCuerrentProcess() {
        return cuerrentProcess;
    }

    public void setCuerrentProcess(Process cuerrentProcess) {
        this.cuerrentProcess = cuerrentProcess;
    }

    public Process getNextProcess() {
        return nextProcess;
    }

    public void setNextProcess(Process nextProcess) {
        this.nextProcess = nextProcess;
    }

    public List<Process> getListProcess() {
        return listProcess;
    }

    public void setListProcess(List<Process> listProcess) {
        this.listProcess = listProcess;
    }
    
    
    
}
