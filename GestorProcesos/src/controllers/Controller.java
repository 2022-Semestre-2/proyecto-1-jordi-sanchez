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
    static String state = "";
    public int inst = 0;
    private int processesCount = 1;
    abstract class threadProcess implements Runnable {

    }
    
    public Controller() {
        v = new GUI();
        p = new Program(128,512,64);
    }
    
    private boolean verifyMemorys(Process process){
        process.setID("Proceso " + processesCount);
        processesCount++;
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
        
        v.getStart_Btn().addActionListener((ActionEvent e) -> {
            actionStartBtn();
        });
         v.getBtnNextStep().addActionListener((ActionEvent e) -> {
            actionBtnNextStep();
        });
        v.getBtnClean().addActionListener((ActionEvent e) -> {
            actionCleanBtn();
        });
        
        v.getBtnExecute().addActionListener((ActionEvent e) -> {
            autoExecute();
        });
        
        v.getBtnStats().addActionListener((ActionEvent e) -> {
            actionShowStats();
        });
    }
    
    private int calculateSecondsDif(Process current) {
        int SECONDS = 60;
        int SECONDS_PER_MINUTE = 60;
        int SECONDS_PER_HOUR = SECONDS_PER_MINUTE*SECONDS;
        
        int startHour = current.getStarted().getHour() * SECONDS_PER_HOUR;
        int startMinute = current.getStarted().getMinute() * SECONDS_PER_MINUTE;
        int startSecond = current.getStarted().getSecond();
        int amountSecondsStart = startHour + startMinute + startSecond;
        System.out.println(amountSecondsStart);
        
        int finishHour = current.getFinished().getHour() * SECONDS_PER_HOUR;
        int finishMinute = current.getFinished().getMinute() * SECONDS_PER_MINUTE;
        int finishSecond = current.getFinished().getSecond();
        int amountSecondsFinish = finishHour + finishMinute + finishSecond;
        System.out.println(amountSecondsFinish);
        
        return amountSecondsFinish - amountSecondsStart;
    }
    
    private void actionShowStats() {
        String allProcesses = "";
        for (Process temp : p.getCpu1().getFinishedProcesses()) {
            String name = temp.getID();
            String start = temp.getStarted().getHour()+":"+temp.getStarted().getMinute()+":"+temp.getStarted().getSecond();
            String finish = temp.getFinished().getHour()+":"+temp.getFinished().getMinute()+":"+temp.getFinished().getSecond();
            
            int secondsDif = calculateSecondsDif(temp);
            
            allProcesses += name + ", inicio: " + start + ", finalización: " + finish + ", Duración en segundos: "+ secondsDif +"\n";
            System.out.println("==============\n"+allProcesses);
        }
        for (Process temp : p.getCpu2().getFinishedProcesses()) {
            String name = temp.getID();
            String start = temp.getStarted().getHour()+":"+temp.getStarted().getMinute()+":"+temp.getStarted().getSecond();
            String finish = temp.getFinished().getHour()+":"+temp.getFinished().getMinute()+":"+temp.getFinished().getSecond();
            
            int secondsDif = calculateSecondsDif(temp);
            
            allProcesses += name + ", inicio: " + start + ", finalización: " + finish + ", Duración en segundos: "+ secondsDif + "\n";
            System.out.println("==============\n"+allProcesses);
        }
        
        JFrame f = new JFrame("frame");
                    JOptionPane.showMessageDialog(f ,
                        allProcesses ,
                        "Estadisticas de procesos" ,
                        JOptionPane.WARNING_MESSAGE);
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
                    v.getStart_Btn().setEnabled(true);
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
    
    private void startCPUS() {
        selectCPU();
        if (!p.getMemory1().getListProcess().isEmpty()) {
                p.getMemory1().setCuerrentProcess(p.getMemory1().getListProcess().get(0));
                if (p.getCPU_Use() == 1) {
                    p.getCpu1().setCurrentProcess(p.getMemory1().getCuerrentProcess());
                } else {
                    p.getCpu2().setCurrentProcess(p.getMemory1().getCuerrentProcess()); 
                }
                for (Process process : p.getMemory1().getListProcess()) {
                    process.setState("Preparado");
                }
                for (Process process : p.getMemory2().getListProcess()) {
                    process.setState("Preparado");
                }
                for (Process process : p.getMemory3().getListProcess()) {
                    process.setState("Preparado");
                }
        }
        
    }
    
    
    
    private void actionStartBtn() {
        v.getBtnLoadFile().setEnabled(false);
        v.getBtnNextStep().setEnabled(true);
        v.getBtnExecute().setEnabled(true);
        v.getStart_Btn().setEnabled(false);
        startCPUS();
    }
    
    private void CleanBtn_Program() {
        v.getBtnLoadFile().setEnabled(true);
        v.getBtnNextStep().setEnabled(false);
        v.getBtnExecute().setEnabled(false);
        v.getStart_Btn().setEnabled(false);
        v.getTextInput().setEditable(false);
        v.getBtnStats().setEnabled(false);
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
            
            //Limpia los botones
            CleanBtn_Program();
            
            //limpia los textFields de los BPCs
            cln_BCP();
            
            //limpia todo lo relacionado a la pantalla
            v.getTextInput().setText("");
            v.getjTextArea1().setText("");
            
            state = "";
            inst = 0;
            processesCount = 1;
    }
    
    
    
    private void actionBtnNextStep() {
        if (p.getCPU_Use() == 1) {
            if (!p.getCpu1().ejecuteProcessInstruction()){
                if (p.getMemory1().getListProcess().size() > 1) {
                    setMemoryFinishProcess();
                    startCPUS();
                    cln_BCP();
                    actionBtnNextStep();
                } else {
                    this.state = "END";
                    JFrame f = new JFrame("frame");
                    JOptionPane.showMessageDialog(f ,
                    "El Programa a terminado" ,
                    "Fin de ejecución" ,
                    JOptionPane.ERROR_MESSAGE);
                    
                    v.getBtnStats().setEnabled(true);
                }
            }
        } else {
            if (!p.getCpu2().ejecuteProcessInstruction()){
                if (p.getMemory1().getListProcess().size() > 1) {
                    setMemoryFinishProcess();
                    startCPUS();
                    cln_BCP();
                    actionBtnNextStep();
                } else {
                    this.state = "END";
                    JFrame f = new JFrame("frame");
                    JOptionPane.showMessageDialog(f ,
                    "El Programa a terminado" ,
                    "Fin de ejecución" ,
                    JOptionPane.ERROR_MESSAGE);
                    
                    v.getBtnStats().setEnabled(true);
                }
            }
        }
        inst++;
        refreshBCP();
    }
    
    private void autoExecute() {
        Thread done;
        done = new Thread( new  threadProcess() {
            public void run() {
                while (state != "END") {
                    try {
                        Thread.sleep((1000));
                    } catch (InterruptedException e) {
                        System.err.println("se cayó");
                    }
                    actionBtnNextStep();
                }
            }
        });
        done.start();
    }
    
    private void cln_BCP(){
            v.getTxtAC_CPU1().setText("");
            v.getTxtAX_CPU1().setText("");
            v.getTxtBX_CPU1().setText("");
            v.getTxtCX_CPU1().setText("");
            v.getTxtDX_CPU1().setText("");
            v.getTxtPC_CPU1().setText("");
            v.getTxtIR_CPU1().setText("");
            
            v.getTxtAC_CPU2().setText("");
            v.getTxtAX_CPU2().setText("");
            v.getTxtBX_CPU2().setText("");
            v.getTxtCX_CPU2().setText("");
            v.getTxtDX_CPU2().setText("");
            v.getTxtPC_CPU2().setText("");
            v.getTxtIR_CPU2().setText("");
    }
    private void refreshBCP(){
        if (p.getCPU_Use() == 1) {
//            v.getTxtIR_CPU1();
//            v.getTxtPC_CPU1();
            v.getTxtPC_CPU1().setText(p.getCpu1().getCurrentProcess().getBcp().getPC());
            v.getTxtIR_CPU1().setText(p.getCpu1().getCurrentProcess().getBcp().getIR());
            v.getTxtAC_CPU1().setText(p.getCpu1().getCurrentProcess().getBcp().getAC());
            v.getTxtAX_CPU1().setText(p.getCpu1().getCurrentProcess().getBcp().getAX());
            v.getTxtBX_CPU1().setText(p.getCpu1().getCurrentProcess().getBcp().getBX());
            v.getTxtCX_CPU1().setText(p.getCpu1().getCurrentProcess().getBcp().getCX());
            v.getTxtDX_CPU1().setText(p.getCpu1().getCurrentProcess().getBcp().getDX());
          
        } else {
            v.getTxtPC_CPU2().setText(p.getCpu2().getCurrentProcess().getBcp().getPC());
            v.getTxtIR_CPU2().setText(p.getCpu2().getCurrentProcess().getBcp().getIR());
            v.getTxtAC_CPU2().setText(p.getCpu2().getCurrentProcess().getBcp().getAC());
            v.getTxtAX_CPU2().setText(p.getCpu2().getCurrentProcess().getBcp().getAX());
            v.getTxtBX_CPU2().setText(p.getCpu2().getCurrentProcess().getBcp().getBX());
            v.getTxtCX_CPU2().setText(p.getCpu2().getCurrentProcess().getBcp().getCX());
            v.getTxtDX_CPU2().setText(p.getCpu2().getCurrentProcess().getBcp().getDX());
            
        }
        
    }
    
    private void setMemoryFinishProcess() {
        p.getMemory1().setAvailableMemory(p.getMemory1().getAvailableMemory()-p.getMemory1().getCuerrentProcess().getListInstructions().size());
        p.getMemory1().getListProcess().remove(p.getMemory1().getCuerrentProcess());
        while (!p.getMemory2().getListProcess().isEmpty()&&  p.getMemory1().getAvailableMemory() < p.getMemory2().getListProcess().get(0).getListInstructions().size())  {
            p.getMemory1().addProcess(p.getMemory2().getListProcess().get(0));
            p.getMemory2().setAvailableMemory(p.getMemory2().getAvailableMemory()-p.getMemory2().getListProcess().get(0).getListInstructions().size());
            p.getMemory2().getListProcess().remove(p.getMemory2().getListProcess().remove(0));
                
            while (!p.getMemory3().getListProcess().isEmpty() && p.getMemory2().getAvailableMemory() < p.getMemory3().getListProcess().get(0).getListInstructions().size()) {
                p.getMemory2().addProcess(p.getMemory3().getListProcess().get(0));
                p.getMemory3().setAvailableMemory(p.getMemory3().getAvailableMemory()-p.getMemory3().getListProcess().get(0).getListInstructions().size());
                p.getMemory3().getListProcess().remove(p.getMemory3().getListProcess().remove(0));
            }
        }
        
    }
    
    private void selectCPU(){
        int tmp = (int) ( Math.random() * 2 + 1);
        System.out.println(tmp);
        p.setCPU_Use(tmp);
    }
  
}
