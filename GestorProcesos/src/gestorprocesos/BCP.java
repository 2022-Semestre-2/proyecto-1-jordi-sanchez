/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorprocesos;

/**
 *
 * @author jordi
 */
public class BCP {
    private int location;
    private String AX = "0";
    private String BX = "0";
    private String CX = "0";
    private String DX = "0";
    private String CPU;
    private String initTime;
    private String currentTime;
    private String State;

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public String getAX() {
        return AX;
    }

    public void setAX(String AX) {
        this.AX = AX;
    }

    public String getBX() {
        return BX;
    }

    public void setBX(String BX) {
        this.BX = BX;
    }

    public String getCX() {
        return CX;
    }

    public void setCX(String CX) {
        this.CX = CX;
    }

    public String getDX() {
        return DX;
    }

    public void setDX(String DX) {
        this.DX = DX;
    }

    public String getCPU() {
        return CPU;
    }

    public void setCPU(String CPU) {
        this.CPU = CPU;
    }

    public String getInitTime() {
        return initTime;
    }

    public void setInitTime(String initTime) {
        this.initTime = initTime;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }
    
    
    
}