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
    private int currentMemory = 1; // for position of next process
    private int availableMemory;
    private Process cuerrentProcess;
    private List<Process> listProcess = new ArrayList<>();
    private int currentAmountMemoryUsed;

    public Memory(int Size) {
        this.Size = Size;
        this.availableMemory = Size;
    }
    
    public boolean addProcess(Process process){
        if (this.availableMemory < process.getListInstructions().size()){
            return false;
        } else {
          listProcess.add(process);
          setMemoriInstruction(process);
          return true;
        }
    }  
    
    private void setMemoriInstruction(Process proc) {
        for (Instruction inst : proc.getListInstructions()) {
            inst.setLine(currentMemory);
            currentMemory++;
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

    public List<Process> getListProcess() {
        return listProcess;
    }

    public void setListProcess(List<Process> listProcess) {
        this.listProcess = listProcess;
    }
    
    public int getcurrentAmountMemoryUsed() {
        return currentAmountMemoryUsed;
    }

    public void setcurrentAmountMemoryUsed(int currentMemoryUsed) {
        this.currentAmountMemoryUsed = currentMemoryUsed;
    }
    
}
