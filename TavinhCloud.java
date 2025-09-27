package com.tavinhcloud;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TavinhCloud extends JFrame {
    private JTextField vmNameField;
    private JComboBox<String> osComboBox;
    private JSpinner ramSpinner;
    private JSpinner cpuSpinner;
    private JTextField diskSizeField;
    
    public TavinhCloud() {
        setTitle("Tavinh Cloud - Criador de Máquinas Virtuais");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        
        initComponents();
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        
        // Nome da VM
        mainPanel.add(new JLabel("Nome da Máquina Virtual:"));
        vmNameField = new JTextField("MinhaVM");
        mainPanel.add(vmNameField);
        
        // Sistema Operacional
        mainPanel.add(new JLabel("Sistema Operacional:"));
        String[] osOptions = {"Ubuntu 22.04", "Ubuntu 20.04", "Windows 10", "Debian", "Customizado"};
        osComboBox = new JComboBox<>(osOptions);
        mainPanel.add(osComboBox);
        
        // RAM
        mainPanel.add(new JLabel("Memória RAM (GB):"));
        SpinnerNumberModel ramModel = new SpinnerNumberModel(4, 1, 32, 1);
        ramSpinner = new JSpinner(ramModel);
        mainPanel.add(ramSpinner);
        
        // CPU
        mainPanel.add(new JLabel("Núcleos de CPU:"));
        SpinnerNumberModel cpuModel = new SpinnerNumberModel(2, 1, 16, 1);
        cpuSpinner = new JSpinner(cpuModel);
        mainPanel.add(cpuSpinner);
        
        // Disco
        mainPanel.add(new JLabel("Tamanho do Disco (GB):"));
        diskSizeField = new JTextField("20");
        mainPanel.add(diskSizeField);
        
        // Botões
        JButton createButton = new JButton("Criar Máquina Virtual");
        JButton cancelButton = new JButton("Cancelar");
        
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createVirtualMachine();
            }
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createButton);
        buttonPanel.add(cancelButton);
        
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void createVirtualMachine() {
        try {
            String vmName = vmNameField.getText();
            String os = (String) osComboBox.getSelectedItem();
            int ram = (int) ramSpinner.getValue();
            int cpu = (int) cpuSpinner.getValue();
            int diskSize = Integer.parseInt(diskSizeField.getText());
            
            // Aqui você integraria com QEMU/KVM ou VirtualBox
            String command = generateVMCommand(vmName, os, ram, cpu, diskSize);
            
            JOptionPane.showMessageDialog(this, 
                "Máquina Virtual criada!\n" +
                "Nome: " + vmName + "\n" +
                "OS: " + os + "\n" +
                "RAM: " + ram + "GB\n" +
                "CPU: " + cpu + " núcleos\n" +
                "Disco: " + diskSize + "GB");
                
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String generateVMCommand(String vmName, String os, int ram, int cpu, int diskSize) {
        // Exemplo de comando QEMU
        return String.format(
            "qemu-system-x86_64 -name %s -m %dG -smp %d -hda %s.img",
            vmName, ram, cpu, vmName
        );
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TavinhCloud().setVisible(true);
            }
        });
    }
}
