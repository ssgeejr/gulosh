/*
Gulosh: A very simple file encryption tool, that contains a simple text editor,
written in Java and uses no external, third party tools. JDK 1.5 or better required.
Copyright (C) 2010  Steve S Gee Jr
http://gulosh.sourceforge.net/
ioexcept@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/gpl-3.0.html>.
*/

package com.semaphoreit.editor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.io.File;


public class ConvertFileDialog extends JDialog{
    private JFileChooser fileChooser = null;
    private final GuloshUI parentUI;
    private Dimension DIALOG_DIMENSION = new Dimension(500, 115);
    JPanel panel1 = new JPanel();
    JPanel insetsPanel1 = new JPanel();
    JButton jbConvert = new JButton();
    ImageIcon image1 = new ImageIcon();
    BorderLayout borderLayout1 = new BorderLayout();
    FlowLayout flowLayout1 = new FlowLayout();
    JButton jButton1 = new JButton();
    JPanel jPanel1 = new JPanel();
    JPanel jpSouth = new JPanel();
    JPanel jpNorth = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    BorderLayout borderLayout3 = new BorderLayout();
    JLabel jLabel1 = new JLabel();
    JLabel jlTargetFile = new JLabel();
    JButton jbTargetFile = new JButton();
    JButton jbSourceFile = new JButton();
    JLabel jlSourceFile = new JLabel();
    JLabel jLabel3 = new JLabel();
    BorderLayout borderLayout4 = new BorderLayout();
    public ConvertFileDialog(GuloshUI parent) {
        super(parent);
        parentUI = parent;
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    /**
     * Component initialization.
     *
     * @throws java.lang.Exception
     */
    private void jbInit() throws Exception {
        image1 = new ImageIcon(com.semaphoreit.editor.GuloshUI.class.getResource("gulosh.jpg"));
        setTitle("Convert File");
        panel1.setLayout(borderLayout1);
        jbConvert.setEnabled(false);
        jbConvert.setText("Convert File");
        jbConvert.addActionListener(new ConvertFileDialog_jbConvert_actionAdapter(this));
        flowLayout1.setAlignment(FlowLayout.RIGHT);
        flowLayout1.setHgap(2);
        flowLayout1.setVgap(2);
        insetsPanel1.setLayout(flowLayout1);
        jButton1.setText("Close");
        jButton1.addActionListener(new ConvertFileDialog_jButton1_actionAdapter(this));
        jpNorth.setLayout(borderLayout4);
        jpSouth.setLayout(borderLayout3);
        jPanel1.setLayout(borderLayout2);
        jLabel1.setText(" Target File: ");
        jbTargetFile.setEnabled(false);
        jbTargetFile.setText("...");
        jbTargetFile.addActionListener(new
                ConvertFileDialog_jbTargetFile_actionAdapter(this));
        jlTargetFile.setText("<unassigned>");
        jbSourceFile.setText("...");
        jbSourceFile.addActionListener(new
                ConvertFileDialog_jbSourceFile_actionAdapter(this));
        jlSourceFile.setText("<unassigned>");
        jLabel3.setText(" Source File: ");
        getContentPane().add(panel1, null);
        insetsPanel1.add(jbConvert, null);
        insetsPanel1.add(jButton1);
        panel1.add(insetsPanel1, BorderLayout.SOUTH);
        panel1.add(jPanel1, java.awt.BorderLayout.CENTER);
        jpSouth.add(jLabel1, java.awt.BorderLayout.WEST);
        jpSouth.add(jlTargetFile, java.awt.BorderLayout.CENTER);
        jpSouth.add(jbTargetFile, java.awt.BorderLayout.EAST);
        jpNorth.add(jLabel3, java.awt.BorderLayout.WEST);
        jpNorth.add(jlSourceFile, java.awt.BorderLayout.CENTER);
        jpNorth.add(jbSourceFile, java.awt.BorderLayout.EAST);
        jPanel1.add(jpNorth, java.awt.BorderLayout.NORTH);
        jPanel1.add(jpSouth, java.awt.BorderLayout.SOUTH);
        super.setResizable(false);
        this.setSize(DIALOG_DIMENSION);
    }

    public void setModal(boolean value) {
        super.setModal(true);
    }

    public void setResizable() {
        super.setResizable(false);
    }

    public Dimension getPreferredSize(){
        return DIALOG_DIMENSION;
    }


    public void cancelDialog(ActionEvent e) {
        dispose();
    }


    public void conversionComplete(){
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    public void convertFile(ActionEvent e) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        long startTime = System.currentTimeMillis();
        parentUI.converFile(jlSourceFile.getText(),jlTargetFile.getText());
        long runTime = ((System.currentTimeMillis() - startTime) / 1000);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        jbConvert.setText("File Converted in " + runTime + " seconds");
        jbConvert.setEnabled(false);
        jbSourceFile.setEnabled(false);
        jbTargetFile.setEnabled(false);
//        this.dispose();
    }

    public void selectSourceFile(ActionEvent e) {
        try{
            fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Sourcea File to be Encdrypted");
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                System.out.println(">>>>|=======> " + fileChooser.getSelectedFile().getAbsolutePath());
                jlSourceFile.setText(fileChooser.getSelectedFile().getAbsolutePath());
                jbTargetFile.setEnabled(true);
            }
        }catch(Exception ex){
            parentUI.showErrorMessage(ex);
        }
    }

    public void selectTargetFile(ActionEvent e) {
        try{
            fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Source File to be Encdrypted");
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                System.out.println(">>>>|=======> " + fileChooser.getSelectedFile().getAbsolutePath());
                if(jlSourceFile.getText().equals(fileChooser.getSelectedFile().getAbsolutePath())){
                    if(JOptionPane.showConfirmDialog(
                            this,
                            "The Target file is the same as the Source file.\nOverwrite Existing File?",
                            "Confirm Target File",
                            JOptionPane.YES_NO_OPTION) == 0){
                        jlTargetFile.setText(fileChooser.getSelectedFile().getAbsolutePath());
                        jbConvert.setEnabled(true);
                    }
                }else{
                    jlTargetFile.setText(fileChooser.getSelectedFile().getAbsolutePath());
                    jbConvert.setEnabled(true);
                }
            }
        }catch(Exception ex){
            parentUI.showErrorMessage(ex);
        }
    }
}


class ConvertFileDialog_jbTargetFile_actionAdapter implements ActionListener {
    private ConvertFileDialog adaptee;
    ConvertFileDialog_jbTargetFile_actionAdapter(ConvertFileDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.selectTargetFile(e);
    }
}

class ConvertFileDialog_jbSourceFile_actionAdapter implements ActionListener {
    private ConvertFileDialog adaptee;
    ConvertFileDialog_jbSourceFile_actionAdapter(ConvertFileDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.selectSourceFile(e);
    }
}


class ConvertFileDialog_jbConvert_actionAdapter implements ActionListener {
    private ConvertFileDialog adaptee;
    ConvertFileDialog_jbConvert_actionAdapter(ConvertFileDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.convertFile(e);
    }
}


class ConvertFileDialog_jButton1_actionAdapter implements ActionListener {
    private ConvertFileDialog adaptee;
    ConvertFileDialog_jButton1_actionAdapter(ConvertFileDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cancelDialog(e);
    }
}
