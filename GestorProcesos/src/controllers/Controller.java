/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

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
    
    private void setProcessLoad(Process process){
        DefaultTableModel model = (DefaultTableModel) v.getTableProcesses().getModel();
        model.addRow(new Object[]{"Process "+ p.getProcesCount(), process.getState()});
    }
    
    public void init() {

        v.setVisible(true);
        v.setLocationRelativeTo(null);

        v.getBtnLoadFile().addActionListener((ActionEvent e) -> {
            FileLoader fileLoader = new FileLoader();
            fileLoader.loadFile();
            String fileContent = fileLoader.getContent();
            System.out.println(fileContent);
            if (!fileContent.equals("")){
                instructionMaker instructMaker = new instructionMaker();
                Process process = new Process() {};
                process.setListInstructions(instructMaker.createListInstruction(fileContent));
                if (!process.searchError()) {
                    if (verifyMemorys(process)) {
                        setProcessLoad(process);
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
        });

    }
}
