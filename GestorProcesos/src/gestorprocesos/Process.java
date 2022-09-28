/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorprocesos;

import java.util.List;

/**
 *
 * @author jordi
 */
public class Process {
    private BCP bcp;
    private List<Instruction> listInstructions;
    private int size = 0;
    
    private String[] splitData(String data, String limit){
        String[] arrOfStr = data.split(limit);
        return arrOfStr;
    }

    public int getSize() {
        return size;
    }
    
    public void createLista(String data){
        String[] arrayIntruction = data.split("\n");
        for (String instruccion : arrayIntruction) {
            String[] arrayIntruction2 = instruccion.split(",");
            String[] arrayIntruction3 = arrayIntruction2[0].split(" ");
            Instruction instructionAdded = new Instruction(arrayIntruction3[0].trim());
            if (arrayIntruction2.length > 1) {
                if (isRegister(arrayIntruction2[1].trim())){
                    
                }else{
                    int i = Integer.parseInt(arrayIntruction2[1].trim());
                    instructionAdded.setNumber(i);
                }   
            } else {
                instructionAdded.setNumber(0);
            }
            instructionAdded.setRegister1(arrayIntruction3[1].trim());
            this.listInstructions.add(instructionAdded);
            this.size++;
        }
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

   
}
