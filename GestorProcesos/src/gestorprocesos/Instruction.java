/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorprocesos;

/**
 *
 * @author jordi
 */
public class Instruction {
    private String type;
    private String register1;
    private String register2;
    private int number;
    private int weight;
    private int line;
    
    private String error;

    public Instruction() {
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Instruction(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegister1() {
        return register1;
    }

    public void setRegister1(String register) {
        this.register1 = register;
    }
    
    public String getRegister2() {
        return register2;
    }

    public void setRegister2(String register) {
        this.register2 = register;
    }
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

   public int verifyRegister1(){
       switch (this.register1) {
            case "AX" -> {
                return 1;
            }
            case "BX" -> {
                return 2;
            }
               case "CX" -> {
                   return 3;
            }
               case "DX" -> {
                   return 4;
            }
               /*case null -> {
                   return 5;
               }*/
           default -> throw new AssertionError();
       }
   }
    
}
