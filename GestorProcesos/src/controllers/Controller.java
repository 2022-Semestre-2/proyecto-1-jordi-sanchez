/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import gestorprocesos.Instruction;
import gestorprocesos.instructionMaker;
import gestorprocesos.Process;
import gestorprocesos.Program;
import gui.GUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import programcontroller.FileLoader;

/**
 *
 * @author jordi
 */
public class Controller {
    
    public GUI v;
    public Program p;
    
    public Controller() {
        v = new GUI();
        p = new Program(128,512,64);
    }
    
    private boolean verifyMemorys(Process process){
        if (p.getMemory1().addProcess(process)) {
            return true;
        } else {
            if (p.getMemory2().addProcess(process)) {
                return true;
            } else {
                if (p.getMemory3().addProcess(process)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }
    
    private void setProcessLoad(Process process) {
        DefaultTableModel model = (DefaultTableModel) v.getTableProcesses().getModel();
        model.addRow(new Object[]{"Process "+ p.getProcesCount(), process.getState()});
    }
    
    private void setProcessTable(Process process) {
        int memoryProcess = process.getListInstructions().size();
        if (p.getMemory1().getcurrentAmountMemoryUsed() + memoryProcess <= p.getMemory1().getSize()) {
            setProcessesTableMemory(process);
        } else if (p.getMemory2().getcurrentAmountMemoryUsed() + memoryProcess <= p.getMemory2().getSize()) {
            setProcessesTableDisc(process);
        } else {
            System.out.println("Se ingreso en la memoria virtual");
        }
        
    }
    
    private void setProcessesTableMemory(Process process) {
        DefaultTableModel model = (DefaultTableModel) v.getTableMemory().getModel();
         for (Instruction temp: process.getListInstructions()) {
             String instruction = temp.toString();
             model.addRow(new Object[]{p.getMemory1().getcurrentAmountMemoryUsed()+1, instruction});
             p.getMemory1().setcurrentAmountMemoryUsed(p.getMemory1().getcurrentAmountMemoryUsed()+1);
         }
    }
    
    private void setProcessesTableDisc(Process process) {
        DefaultTableModel model = (DefaultTableModel) v.getTableDisc().getModel();
         for (Instruction temp: process.getListInstructions()) {
             String instruction = temp.toString();
             model.addRow(new Object[]{p.getMemory2().getcurrentAmountMemoryUsed()+1, instruction});
             p.getMemory2().setcurrentAmountMemoryUsed(p.getMemory2().getcurrentAmountMemoryUsed()+1);
         }
    }
    
    public void init() {
        
        v.setVisible(true);
        v.setLocationRelativeTo(null);

        v.getBtnLoadFile().addActionListener((ActionEvent e) -> {
            actionLoadFileBtn();
        });
        
        v.getBtnClean().addActionListener((ActionEvent e) -> {
            actionCleanBtn();
        });
    }
    
    private void actionLoadFileBtn() {
        FileLoader fileLoader = new FileLoader();
        fileLoader.loadFile();
        String fileContent = fileLoader.getContent();
        System.out.println("=======================");
        System.out.println(fileContent);
        if (!fileContent.equals("")){
            instructionMaker instructMaker = new instructionMaker();
            Process process = new Process() {};
            process.setListInstructions(instructMaker.createListInstruction(fileContent));
            if (!process.searchError()) {
                if (verifyMemorys(process)) {
                    setProcessLoad(process);
                    setProcessTable(process);
                } else {
                    JFrame f = new JFrame("frame");
                    JOptionPane.showMessageDialog(f ,
                        "No hay suficiente espacio" ,
                        "Error de archivo cargado" ,
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JFrame f = new JFrame("frame");
                JOptionPane.showMessageDialog(f ,
                    "El archivo a cargar debe ser de extensión .ASM." ,
                    "Error de archivo cargado" ,
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void actionCleanBtn() {
            p = new Program(128,512,64);
            
            //limpia la tabla de procesos
            DefaultTableModel dtmProcess = new DefaultTableModel(0, 0);
            String headerProcess[] = new String[]{"Procesos", "Estados"};
            dtmProcess.setColumnIdentifiers(headerProcess);
            v.getTableProcesses().setModel(dtmProcess);
            
            //limpia la tabla de memoria
            DefaultTableModel dtmMemory = new DefaultTableModel(0, 0);
            String headerMemory[] = new String[]{"Pos", "Valor en memoria"};
            dtmMemory.setColumnIdentifiers(headerMemory);
            v.getTableMemory().setModel(dtmMemory);
            
            //limpia la tabla de disco
            DefaultTableModel dtmDisc = new DefaultTableModel(0, 0);
            String headerDisc[] = new String[]{"Pos", "Valor en disco"};
            dtmDisc.setColumnIdentifiers(headerDisc);
            v.getTableDisc().setModel(dtmDisc);
    }
}
