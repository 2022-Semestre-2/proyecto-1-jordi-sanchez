/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorprocesos;

/**
 *
 * @author jordi
 */
public class CPU {
    private Process currentProcess;
    private int currentLine = 0;

    public boolean isInstructionIrregular() {
        String type = "";
        if (currentProcess.getListInstructions().size() > currentLine) {
            type = currentProcess.getListInstructions().get(currentLine).getType();
        }
        return type == "CMP" || type == "INT" || type == "JMP" || type == "JNE" || type == "JE";
    }
    
    public boolean isJMP() {
        return currentProcess.getListInstructions().get(currentLine).getType() == "JMP";
    }
    
    public Instruction getCurrentInstruction() {
        return currentProcess.getListInstructions().get(currentLine);
    }
    public Process getCurrentProcess() {
        return currentProcess;
    }

    public void setCurrentProcess(Process currentProcess) {
        this.currentProcess = currentProcess;
    }

    public int getCurrentLine() {
        return currentLine;
    }

    public void setCurrentLine(int currentLine) {
        this.currentLine = currentLine;
    }
    
    public boolean ejecuteProcessInstruction(){
        if (currentProcess.getListInstructions().size() > currentLine) {
            int peso = currentProcess.getListInstructions().get(currentLine).getCurrentWeight();
            currentProcess.getListInstructions().get(currentLine).setCurrentWeight(peso - 1);
            currentProcess.getBcp().setPC(currentProcess.getListInstructions().get(currentLine).getLine()+"");
            setValueRegister("IR", currentProcess.getListInstructions().get(currentLine).getInst());
            if ( peso - 1 == 0) {
                ejecuteInstruction();
                currentProcess.getListInstructions().get(currentLine).setCurrentWeight(currentProcess.getListInstructions().get(currentLine).getWeight());
                this.currentLine += 1;
            }
            return true;
        } else {
            currentProcess.setState("Finalizado");
            return false;
        }
    }
    
    private void ejecuteInstruction(){
        switch (currentProcess.getListInstructions().get(currentLine).getType()) {
            case "LOAD" -> ejecuteLoad();
            case "STORE" -> ejecuteStore();
            case "MOV" -> ejecuteMov();
            case "ADD" -> ejecuteAdd();
            case "SUB" -> ejecuteSub();
            case "INC" -> ejecuteInc();
            case "DEC" -> ejecuteDec();
            case "SWAP" -> ejecuteSwap();
            case "CMP" -> ejecuteCmp();
            default -> System.out.println("no entá disponible");
        }
    }
    
    // ############################# Ejecucion de instrucciones ###################################
    
    private void ejecuteCmp(){
        // registra el valor obtenido del registro a buscar en el bcp
        
    }
    
    private void ejecuteLoad(){
        // registra el valor obtenido del registro a buscar en el bcp
        String valueRegister = getValueRegister(currentProcess.getListInstructions().get(currentLine).getRegister1()); 
        currentProcess.getBcp().setAC(valueRegister);
    }
    
    private void ejecuteStore(){
        // registra el valor obtenido del registro ac
        String valueRegister = getValueRegister("AC"); 
        setValueRegister(currentProcess.getListInstructions().get(currentLine).getRegister1(), valueRegister);
    }
    
    private void ejecuteMov(){
        if (currentProcess.getListInstructions().get(currentLine).getRegister2().isEmpty()) { 
            // si el registro 2 es nulo es porque es un mov con numero
            // si entra cambia de una vez el primer registro por el numero
            setValueRegister(currentProcess.getListInstructions().get(currentLine).getRegister1(), currentProcess.getListInstructions().get(currentLine).getNumber()+"");
        } else {
            // sino busca el valor que hay en el bcp del registro del que queremos mover los valores
            String valueRegister = getValueRegister(currentProcess.getListInstructions().get(currentLine).getRegister2()); 
            setValueRegister(currentProcess.getListInstructions().get(currentLine).getRegister1(), valueRegister);
        }
    }
    
    private void ejecuteAdd(){
        // registra el valor obtenido del registro a buscar en el bcp
        String valueRegister = getValueRegister(currentProcess.getListInstructions().get(currentLine).getRegister1()); 
        String valueRegisterAC = getValueRegister("AC"); 
        String val = ""+(Integer.parseInt(valueRegister) + Integer.parseInt(valueRegisterAC));
        currentProcess.getBcp().setAC(val);
    }
    
    private void ejecuteSub(){
        // registra el valor obtenido del registro a buscar en el bcp
        String valueRegister = getValueRegister(currentProcess.getListInstructions().get(currentLine).getRegister1()); 
        String valueRegisterAC = getValueRegister("AC"); 
        String val = ""+(Integer.parseInt(valueRegister) - Integer.parseInt(valueRegisterAC));
        currentProcess.getBcp().setAC(val);
    }
    
    private void ejecuteInc(){
        String valueRegisterAC = getValueRegister("AC"); 
        if (currentProcess.getListInstructions().get(currentLine).getRegister1().isBlank()) { 
            currentProcess.getBcp().setAC((Integer.parseInt(valueRegisterAC)+1)+"");
        } else {
            String valueRegister = getValueRegister(currentProcess.getListInstructions().get(currentLine).getRegister1()); 
            currentProcess.getBcp().setAC((Integer.parseInt(valueRegisterAC)+Integer.parseInt(valueRegister))+"");
        }
    }
    
    private void ejecuteDec(){
        String valueRegisterAC = getValueRegister("AC"); 
        if (currentProcess.getListInstructions().get(currentLine).getRegister1().isBlank()) { 
            currentProcess.getBcp().setAC((Integer.parseInt(valueRegisterAC)-1)+"");
        } else {
            String valueRegister = getValueRegister(currentProcess.getListInstructions().get(currentLine).getRegister1()); 
            currentProcess.getBcp().setAC((Integer.parseInt(valueRegisterAC)-Integer.parseInt(valueRegister))+"");
        }
    }
    
    private void ejecuteSwap(){
        String valueRegister1 = getValueRegister(currentProcess.getListInstructions().get(currentLine).getRegister1()); 
        String valueRegister2 = getValueRegister(currentProcess.getListInstructions().get(currentLine).getRegister2()); 
        setValueRegister(currentProcess.getListInstructions().get(currentLine).getRegister1(), valueRegister2);
        setValueRegister(currentProcess.getListInstructions().get(currentLine).getRegister2(), valueRegister1);
    }
    
    // #############################################################################################
    
    private String getValueRegister(String register) {
        switch (register) {
            case "AX" -> {
                return currentProcess.getBcp().getAX();
            }
            case "BX" -> {
                return currentProcess.getBcp().getBX();
            }
            case "CX" -> {
                return currentProcess.getBcp().getCX();
            }
            case "DX" -> {
                return currentProcess.getBcp().getDX();
            }
            case "AC" -> {
                return currentProcess.getBcp().getAC();
            }
            default -> throw new AssertionError();
        }
    }
    
    private void setValueRegister(String register, String value) {
        switch (register) {
            case "AX" -> {
                currentProcess.getBcp().setAX(value);
                break;
            }
            case "BX" -> {
                currentProcess.getBcp().setBX(value);
                break;
            }
            case "CX" -> {
                currentProcess.getBcp().setCX(value);
                break;
            }
            case "DX" -> {
                currentProcess.getBcp().setDX(value);
                break;
            }
            case "IR" -> {
                currentProcess.getBcp().setIR(value);
                break;
            }
            default -> throw new AssertionError();
        }
    }
    
}
