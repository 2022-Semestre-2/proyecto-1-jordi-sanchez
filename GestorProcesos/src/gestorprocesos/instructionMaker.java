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
public class instructionMaker {

    public instructionMaker() {
    }
    
    // crea una lista de instrucciones creado a partir de un texto proveniente de un archivo
    // primero se splitean las lineas luego se parcean estas
    public List<Instruction> createListInstruction(String text) {
        String[] arrayIntruction = text.split("\n");
        List<Instruction> instructionList = new ArrayList<>();
        for (String line : arrayIntruction) {
            instructionList.add(parseStringtoInstruction(line));
        }
        return instructionList;
    }
    
    //parcea un texto para crear una linea, según sea válido el texto por lo que puede identificar 
    //errores de estructuracion del texto
    private Instruction parseStringtoInstruction(String text) {
        if (text.length() >= 3){ // verifica que el texto tenga almenos 3 letras
            switch (text.substring(0, 3)) {
                case "LOA": return loadpushInstruction(text, "LOAD", 2);
                case "STO": return storeInstruction(text, 2);
                case "MOV": return movInstruction(text, 1);
                case "ADD": return generalInstruction(text, "ADD", 3);
                case "SUB": return generalInstruction(text, "SUB", 3);
                case "INC": return incdecInstruction(text, "INC", 1);
                case "DEC": return incdecInstruction(text, "DEC", 1);
                case "SWA": return swapInstruction(text, 1);
                case "INT": return intInstruction(text);
                case "JMP": return jmpjejneInstruction(text, "JMP", 2);
                case "CMP": return cmpInstruction(text, 2);
                case "JE ": return jmpjejneInstruction(text, "JE", 2);
                case "JNE": return jmpjejneInstruction(text, "JNE", 2);
//                case "PAR": AAAAAA;
                case "PUS": return loadpushInstruction(text, "PUSH", 1);
                case "POP": return generalInstruction(text, "POP", 1);

                default: return instructionErrorDefault();
            }
        } else {
            return instructionErrorDefault();
        }
    }
    
    //#########################  MAKING INSTRUCTION ####################################
    
    //Crea una instruccion load o push con sus validaciones necesarias, si la funcon tira una instruccion error es
    //por una mala estructura de la instruccion load o push
    private Instruction loadpushInstruction(String text, String type, int wei) {
        Instruction loadpush = new Instruction();
        if (text.length() < 4) { // verifica que el texto tenga almenos 4 letras
            return instructionErrorDefault();
        } else {
            if (text.substring(0, 4).equals(type)) {
                loadpush.setType(type);
                String registro = registerInstruction(text.substring(5, 7));
                if (registro.equals("ERROR")) {
                    return instructionErrorDefault();
                } else {
                    loadpush.setRegister1(registro);
                }
            } else {
                return instructionErrorDefault();
            }
            loadpush.setWeight(wei);
            return loadpush;
        }
    }
    
    //Crea una instruccion mov con sus validaciones necesarias, si la funcon tira una instruccion error es
    //por una mala estructura de la instruccion mov, verifica que el primer registro exista, que el segundo
    //parametro sea un registro valido o un número valido
    private Instruction movInstruction(String text, int wei) {
        Instruction mov = new Instruction();
        mov.setType("MOV");
        if (text.length() < 6) { // verifica que el texto tenga almenos 4 letras
            return instructionErrorDefault();
        } else {
            String register1 = registerInstruction(text.substring(4, 6));
            if (register1.equals("ERROR")) { // verifica que el registro exista, si no es un error
                return instructionErrorDefault();
            } else { // si el registro existe sigue
                mov.setRegister1(register1); 
                if (text.length() == 10) { // verifica que sea posible la existencia un registro y no un numero de 1 digito
                    String register2 = registerInstruction(text.substring(8, 10));
                    if (register2.equals("ERROR")) { // si es error, no es un registro es un numero o un error
                        if (isNumeric(text.substring(8).trim())) { // si es un numero lo registra
                            int num = Integer.parseInt(text.substring(8).trim());
                            mov.setNumber(num);
                        } else { // sino es un numero es un error
                            return instructionErrorDefault();
                        }
                    } else { // es un registro 
                        mov.setRegister2(register2);
                    }
                } else { // es posiblemente un numero de 1 dígito
                    if (isNumeric(text.substring(8).trim())) { // si es un numero lo registra
                        int num = Integer.parseInt(text.substring(8).trim());
                        mov.setNumber(num);
                    } else { // sino es un numero es un error
                        return instructionErrorDefault();
                    }
                }
            }
        mov.setWeight(wei);
        return mov;
        }
    }
    
    //Crea una instruccion store con sus validaciones necesarias, si la funcon tira una instruccion error es
    //por una mala estructura de la instruccion store
    private Instruction storeInstruction(String text, int wei) {
        Instruction store = new Instruction();
        if (text.length() < 5) { // verifica que el texto tenga almenos 4 letras
            return instructionErrorDefault();
        } else {
            if (text.substring(0, 5).equals("STORE")) {
                store.setType("STORE");
                String registro = registerInstruction(text.substring(6, 8));
                if (registro.equals("ERROR")) {
                    return instructionErrorDefault();
                } else {
                    store.setRegister1(registro);
                }
            } else {
                return instructionErrorDefault();
            }
            store.setWeight(wei);
            return store;
        }
    }
    
    //Crea una instruccion según el tipo con sus validaciones necesarias, si la funcon tira una instruccion error es
    //por una mala estructura de la instruccion, utilizada en instrucciones de 3 letras más un registro
    // ejemplo: "SUB BX", "ADD CX", "POP AX","DEC BX", "INC DX"
    private Instruction generalInstruction(String text, String type, int wei) {
        Instruction gen = new Instruction();
        gen.setType(type);
        if (text.length() < 6) { // verifica que el texto tenga almenos 6 letras
            return instructionErrorDefault();
        } else {
            String registro = registerInstruction(text.substring(4, 6));
            if (registro.equals("ERROR")) {
                return instructionErrorDefault();
            } else {
                gen.setRegister1(registro);
            }
            gen.setWeight(wei);
            return gen;
        }
    }
    
    //Crea una instruccion ind o dec con sus validaciones necesarias, si la funcon tira una instruccion error es
    //por una mala estructura de la instruccion dec o inc, además verifica que sea un inc o dec con o sin registro
    private Instruction incdecInstruction(String text, String type, int wei) {
        if (text.length() == 3) {
            Instruction incdec = new Instruction();
            incdec.setType(type);
            incdec.setWeight(wei);
            return incdec;
        } else {
            return generalInstruction(text, type, wei);
        }
    }
    
    //Crea una instruccion swap con sus validaciones necesarias, si la funcon tira una instruccion error es
    //por una mala estructura de la instruccion swap
    private Instruction swapInstruction(String text, int wei) {
        Instruction swap = new Instruction();
        if (text.length() < 11) { // verifica que el texto tenga almenos 4 letras
            return instructionErrorDefault();
        } else {
            if (text.substring(0, 4).equals("SWAP")) {
                swap.setType("SWAP");
                String register1 = registerInstruction(text.substring(5, 7));
                String register2 = registerInstruction(text.substring(9, 11));
                if (register1.equals("ERROR") || register2.equals("ERROR")) { // verifica que el registro exista, si no es un error
                    return instructionErrorDefault();
                } else { // si el registro existe sigue
                    swap.setRegister1(register1);
                    swap.setRegister2(register2);
                }
            } else {
                return instructionErrorDefault();
            }
            swap.setWeight(wei);
            return swap;
        }
    }
    
    //Crea una instruccion jmp, je o jne con sus validaciones necesarias, si la funcon tira una instruccion error es
    //por una mala estructura de la instruccion jmp, je o jne
    private Instruction jmpjejneInstruction(String text, String type, int wei) {
        Instruction jmpjejne = new Instruction();
        if (type.equals("JE")) {
            if (isNumeric(text.substring(3))) {
                jmpjejne.setNumber(Integer.parseInt(text.substring(3)));
            } else {
                return instructionErrorDefault();
            }
        } else {
            if (isNumeric(text.substring(4))) {
                jmpjejne.setNumber(Integer.parseInt(text.substring(4)));
            } else {
                return instructionErrorDefault();
            }
        }
        jmpjejne.setType(type);
        jmpjejne.setWeight(wei);
        return jmpjejne;
    }
    
    //Crea una instruccion INT con sus validaciones necesarias, si la funcon tira una instruccion error es
    //por una mala estructura de la instruccion INT
    private Instruction intInstruction(String text) {
        Instruction iNt = new Instruction();
        if (text.length() < 7) {
            return instructionErrorDefault();
        } else {
            if (text.substring(4, 7).equals("20H") || text.substring(4, 7).equals("09H") || text.substring(4, 7).equals("10H")) {
                iNt.setRegister1(text.substring(4, 7));
            } else {
                return instructionErrorDefault();
            }
            iNt.setType("INT");
            return iNt;
        }
    }
    
    //Crea una instruccion cmp con sus validaciones necesarias, si la funcon tira una instruccion error es
    //por una mala estructura de la instruccion cmp
    private Instruction cmpInstruction(String text, int wei) {
        Instruction cmp = new Instruction();
        if (text.length() < 10) { // verifica que el texto tenga almenos 4 letras
            return instructionErrorDefault();
        } else {    
            String register1 = registerInstruction(text.substring(4, 6));
            String register2 = registerInstruction(text.substring(8, 10));
            if (register1.equals("ERROR") || register2.equals("ERROR")) { // verifica que el registro exista, si no es un error
                return instructionErrorDefault();
            } else { // si el registro existe sigue
                cmp.setRegister1(register1);
                cmp.setRegister2(register2);
            }
            cmp.setType("CMP");
            cmp.setWeight(wei);
            return cmp;
        }
    }
    
    //Crea una instruccion de typo error por defaul
    private Instruction instructionErrorDefault() {
        Instruction error = new Instruction();
        error.setError("Error de sintaxis de instruccion");
        return error;
    }
    
    //######################################################################
    
    // verifica si el string es un registro, si no es retorna un error
    private String registerInstruction(String register) {
        return switch (register) {
            case "AX" -> "AX";
            case "BX" -> "BX";
            case "CX" -> "CX";
            case "DX" -> "DX";
            default -> "ERROR";
        };  
    }
    
    //verifica que un string contenga solo numeros
    private static boolean isNumeric(String strNum) {
    if (strNum == null) {
        return false;
    }
    try {
        Integer.parseInt(strNum);
    } catch (NumberFormatException nfe) {
        return false;
    }
    return true;
    }
    
    public static void main(String[] args) {
        System.out.println("INT 10H".substring(4,7));
    }
}
