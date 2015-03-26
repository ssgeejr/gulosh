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
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.*;
import java.io.*;
import java.awt.BorderLayout;

public class GuloshUI extends JFrame {
    private final String DEFUALT_FILE_NAME = "*.gef";
    private GuloshManager cypherManager = new GuloshManager();
    private String openFile = DEFUALT_FILE_NAME;
    private GuloshFileFilter fileFilter = new GuloshFileFilter();
    private Dimension FRAME_SIZE = new Dimension(800,600);
    private boolean cypherKeyEnabled = false;
    private byte cypherKey[] = null;
    JPanel contentPane;
    BorderLayout borderLayout1 = new BorderLayout();
    JMenuBar jMenuBar1 = new JMenuBar();
    JMenu jMenuFile = new JMenu();
    JMenuItem jMenuFileExit = new JMenuItem();
    JMenu jMenuHelp = new JMenu();
    JMenuItem jMenuHelpAbout = new JMenuItem();
    JPanel statusPanel = new JPanel();
    JMenuItem jmiSetKey = new JMenuItem();
    JMenuItem jmiConvertExternalFile = new JMenuItem();
    JMenuItem jmiSaveFile = new JMenuItem();
    private JFileChooser fileChooser = null;
    JMenuItem jmiNewFile = new JMenuItem();
    JScrollPane mainScrollPane = new JScrollPane();
    JTextPane editorPane = new JTextPane();
    FlowLayout flowLayout1 = new FlowLayout();
    JLabel jLabel1 = new JLabel();
    JLabel jlState = new JLabel();
    JMenuItem jmiOpenGEFFileItem = new JMenuItem();
    JMenuItem jmiOpenASCIIFile = new JMenuItem();
    JMenuItem jmiLicense = new JMenuItem();

    public GuloshUI() {
        try {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            jbInit();
        } catch (Exception exception) {
            showErrorMessage(exception);
            System.exit(-1);
        }
    }

    /**
     * Component initialization.
     *
     * @throws java.lang.Exception
     */
    private void jbInit() throws Exception {
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(borderLayout1);
        setSize(new Dimension(400, 300));
        setTitle("Gulosh");
        jMenuFile.setText("File");
        jMenuFileExit.setText("Exit");
        jMenuFileExit.addActionListener(new
                                        GuloshUI_jMenuFileExit_ActionAdapter(this));
        jMenuHelp.setText("Help");
        jMenuHelpAbout.setText("About");
        jMenuHelpAbout.addActionListener(new
                                         GuloshUI_jMenuHelpAbout_ActionAdapter(this));
        jmiSetKey.setText("Set Key Value");
        jmiSetKey.addActionListener(new GuloshUI_jmiSetKey_actionAdapter(this));
        jmiConvertExternalFile.setText("Convert External File");
        jmiConvertExternalFile.addActionListener(new
                GuloshUI_jmiConvertExternalFile_actionAdapter(this));
        jmiSaveFile.setText("Save File");
        jmiSaveFile.addActionListener(new GuloshUI_jmiSaveFile_actionAdapter(this));
        jmiNewFile.setText("New File");
        jmiNewFile.addActionListener(new GuloshUI_jmiNewFile_actionAdapter(this));
        statusPanel.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        flowLayout1.setHgap(2);
        flowLayout1.setVgap(2);
        jLabel1.setText("Key State: ");
        jlState.setForeground(Color.red);
        jlState.setText("unloaded");
        jmiOpenGEFFileItem.setText("Open GEF File");
        jmiOpenGEFFileItem.addActionListener(new
                GuloshUI_jmiOpenFileItem_actionAdapter(this));
        jmiOpenASCIIFile.setText("Open ASCII File");
        jmiOpenASCIIFile.addActionListener(new
                GuloshUI_jmiOpenASCIIFile_actionAdapter(this));
        jmiLicense.setText("License");
        jmiLicense.addActionListener(new GuloshUI_jmiLicense_actionAdapter(this));
        jMenuBar1.add(jMenuFile);
        jMenuFile.add(jmiOpenGEFFileItem);
        jMenuFile.add(jmiOpenASCIIFile);
        jMenuFile.add(jmiNewFile);
        jMenuFile.add(jmiSaveFile);
        jMenuFile.addSeparator();
        jMenuFile.add(jmiSetKey);
        jMenuFile.add(jmiConvertExternalFile);
        jMenuFile.addSeparator();
        jMenuFile.add(jMenuFileExit);
        jMenuBar1.add(jMenuHelp);
        jMenuHelp.add(jMenuHelpAbout);
        jMenuHelp.add(jmiLicense);
        setJMenuBar(jMenuBar1);
        contentPane.add(statusPanel, BorderLayout.SOUTH);
        statusPanel.add(jLabel1);
        statusPanel.add(jlState);
        contentPane.add(mainScrollPane, java.awt.BorderLayout.CENTER);
        mainScrollPane.getViewport().add(editorPane);
        this.setSize(FRAME_SIZE);
        jmiOpenGEFFileItem.setEnabled(false);
        jmiOpenASCIIFile.setEnabled(false);
        jmiSaveFile.setEnabled(false);
        jmiConvertExternalFile.setEnabled(false);
        this.setResizable(true);

    }

    /**
     * File | Exit action performed.
     *
     * @param actionEvent ActionEvent
     */
    void jMenuFileExit_actionPerformed(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Help | About action performed.
     *
     * @param actionEvent ActionEvent
     */
    void jMenuHelpAbout_actionPerformed(ActionEvent actionEvent) {
        GuloshUI_AboutBox dlg = new GuloshUI_AboutBox(this);
        Dimension dlgSize = dlg.getPreferredSize();
        Dimension frmSize = getSize();
        Point loc = getLocation();
        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.setModal(true);
        dlg.pack();
        dlg.setVisible(true);
    }


    public void converFile(String source, String target){
//        Thread t = new Thread();
//        ConversionTimer timer = new ConversionTimer(label);
        try{
            cypherManager.convertFile(source,target,cypherKey);
//            timer.start();
//            t.sleep(5000);
//            timer.stopTimer();
        }catch(Exception ex){
            showErrorMessage(ex);
        }
//        showErrorMessage("File Conversion Complete");

    }
//
    public void convertExternalFileAction(ActionEvent e) {

//        String source = "C:\\Users\\ioexcept\\Documents\\sixbyte.txt";
//        String target = "C:\\Users\\ioexcept\\Documents\\sixbyte.gef";
//        cypherKey = cypherManager.generateCypher("ABC123");
//        converFile(source,target);

//        /*
        if(!cypherKeyEnabled){
            showErrorMessage("The Cypher Key Has Not Yet Been Enabled\nPlease select 'File \\ Set Key Value' to enable the cypher key. \nThis is the same key you MUST use to decrypt the file ");
        }else{
            ConvertFileDialog dlg = new ConvertFileDialog(this);
            Dimension dlgSize = dlg.getPreferredSize();
            Dimension frmSize = getSize();
            Point loc = getLocation();
            dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
            dlg.setVisible(true);
        }
//        */
    }

    public void showErrorMessage(String errMsg) {
                GuloshLogHandler.log(errMsg);
                ErrorDialog dlg = new ErrorDialog(this,errMsg);
                Dimension dlgSize = dlg.getPreferredSize();
                Dimension frmSize = getSize();
                Point loc = getLocation();
                dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
                dlg.setVisible(true);
    }

    public void showErrorMessage(Throwable errEx) {
                GuloshLogHandler.log(errEx);
                StackTraceElement[] errorlist = errEx.getStackTrace();
                StringBuffer error = new StringBuffer();
                int rownum = 0;

                while(rownum < errorlist.length){
                    error.append(errorlist[rownum]).append("\n");
                    rownum++;
                }

                ErrorDialog dlg = new ErrorDialog(this,error.toString());
                Dimension dlgSize = dlg.getPreferredSize();
                Dimension frmSize = getSize();
                Point loc = getLocation();
                dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
                dlg.setVisible(true);
    }

    public void setKeyValueAction(ActionEvent e) {
        SetKeyValueDialog dlg = new SetKeyValueDialog(this);
        Dimension dlgSize = dlg.getPreferredSize();
        Dimension frmSize = getSize();
        Point loc = getLocation();
        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.setVisible(true);
        if(dlg.isKeySet()){
            cypherKeyEnabled = true;
            cypherKey = dlg.fetchCypher();
            jlState.setForeground(Color.blue);
            jlState.setText("Key Strength set at " + dlg.getKeySize() + " bytes");
            jmiOpenGEFFileItem.setEnabled(true);
            jmiOpenASCIIFile.setEnabled(true);
            jmiSaveFile.setEnabled(true);
            jmiConvertExternalFile.setEnabled(true);
        }
    }

    public void saveFileAction(ActionEvent e) {
        if(!cypherKeyEnabled){
            showErrorMessage("The Cypher Key Has Not Yet Been Enabled\nPlease select 'File \\ Set Key Value' to enable the cypher key. \nThis is the same key you MUST use to decrypt the file ");
        }else{
       try{
           fileChooser = new JFileChooser();
           fileChooser.setDialogTitle("Save File With Encryption");
       //            fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
       //            fileChooser.showDialog(this,"Save File With Encryption")
       //            filter.addExtension("jpg");
       //            filter.addExtension("gif");

           fileChooser.addChoosableFileFilter(fileFilter);
           fileChooser.setSelectedFile(new File(openFile));
           if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
               out(fileChooser.getSelectedFile().getAbsolutePath());
               cypherManager.persistFile(fileChooser.getSelectedFile().getAbsolutePath(),editorPane.getText().getBytes(),cypherKey);
           }
//            final JFileChooser fc = new JFileChooser();
//            int returnVal = fc.showSaveDialog(FileChooserDemo.this);
//            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//
//            JFileChooser fc = new JFileChooser();
//            int returnVal = fc.showDialog(FileChooserDemo2.this, "Attach");

        }catch(Exception ex){
            showErrorMessage(ex);
        }
    }
    }

    private void out(String out){
        System.out.println(">> " + out);
    }

    public void newFileAction(ActionEvent e) {
        openFile = DEFUALT_FILE_NAME;
        editorPane.setText("");
    }

    public void openFileAction(ActionEvent e) {
        editorPane.setText("");
        if(!cypherKeyEnabled){
            showErrorMessage("The Cypher Key Has Not Yet Been Enabled\nPlease select 'File \\ Set Key Value' to enable the cypher key. \nThis is the same key you MUST use to decrypt the file ");
        }else{
            try {
                fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Open GEF Encrypted  File");
                fileChooser.addChoosableFileFilter(fileFilter);
                fileChooser.setSelectedFile(new File(openFile));
                if (fileChooser.showOpenDialog(this) ==
                    JFileChooser.APPROVE_OPTION) {
                    byte inputBytes[] = cypherManager.openSourceFile(
                            fileChooser.getSelectedFile().getAbsolutePath());
                    inputBytes = cypherManager.XOR(cypherKey, inputBytes);
                    editorPane.setText(new String(inputBytes));
                    openFile = fileChooser.getSelectedFile().getAbsolutePath();
                }
            } catch (Exception ex) {
               showErrorMessage(ex);
            }
        }
    }

    public void openASCIIFile(ActionEvent e) {
        editorPane.setText("");
        try{
            fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Open Standard ASCII File");
            fileChooser.setSelectedFile(new File(""));
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                byte inputBytes[] = cypherManager.openSourceFile(fileChooser.getSelectedFile().getAbsolutePath());
                editorPane.setText(new String(inputBytes));
            }
            openFile = fileChooser.getSelectedFile().getAbsolutePath();
        }catch(Exception ex){
            showErrorMessage(ex);
        }
    }

    public void showGPLLicense(ActionEvent e) {
        GPLDialog dlg = new GPLDialog(this);
        Dimension dlgSize = dlg.getPreferredSize();
        Dimension frmSize = getSize();
        Point loc = getLocation();
        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.setVisible(true);
    }
}


class GuloshUI_jmiLicense_actionAdapter implements ActionListener {
    private GuloshUI adaptee;
    GuloshUI_jmiLicense_actionAdapter(GuloshUI adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.showGPLLicense(e);
    }
}


class GuloshUI_jmiOpenFileItem_actionAdapter implements ActionListener {
    private GuloshUI adaptee;
    GuloshUI_jmiOpenFileItem_actionAdapter(GuloshUI adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.openFileAction(e);
    }
}


class GuloshUI_jmiOpenASCIIFile_actionAdapter implements ActionListener {
    private GuloshUI adaptee;
    GuloshUI_jmiOpenASCIIFile_actionAdapter(GuloshUI adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.openASCIIFile(e);
    }
}


class GuloshUI_jmiNewFile_actionAdapter implements ActionListener {
    private GuloshUI adaptee;
    GuloshUI_jmiNewFile_actionAdapter(GuloshUI adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.newFileAction(e);
    }
}


class GuloshUI_jmiSaveFile_actionAdapter implements ActionListener {
    private GuloshUI adaptee;
    GuloshUI_jmiSaveFile_actionAdapter(GuloshUI adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.saveFileAction(e);
    }
}


class GuloshUI_jmiSetKey_actionAdapter implements ActionListener {
    private GuloshUI adaptee;
    GuloshUI_jmiSetKey_actionAdapter(GuloshUI adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.setKeyValueAction(e);
    }
}


class GuloshUI_jMenuFileExit_ActionAdapter implements ActionListener {
    GuloshUI adaptee;

    GuloshUI_jMenuFileExit_ActionAdapter(GuloshUI adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jMenuFileExit_actionPerformed(actionEvent);
    }
}


class GuloshUI_jmiConvertExternalFile_actionAdapter implements ActionListener {
    private GuloshUI adaptee;
    GuloshUI_jmiConvertExternalFile_actionAdapter(GuloshUI adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.convertExternalFileAction(e);
    }
}

//class ErrorDialogListenerAdapter implements ErrorDialogListener{
//    GuloshUI adaptee;
//    ErrorDialogListenerAdapter(GuloshUI _adaptee){adaptee = _adaptee;}
//    public void showError(String msg){adaptee.showErrorMessage(msg);}
//}

class GuloshUI_jMenuHelpAbout_ActionAdapter implements ActionListener {
    GuloshUI adaptee;

    GuloshUI_jMenuHelpAbout_ActionAdapter(GuloshUI adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jMenuHelpAbout_actionPerformed(actionEvent);
    }
}
