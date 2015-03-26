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
import java.awt.Dimension;
import java.util.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;


public class SetKeyValueDialog extends JDialog implements ActionListener {
    private GuloshManager cypherManager = new GuloshManager();
    private ButtonGroup keySelectionGroup = new ButtonGroup();
    private ButtonGroup keySizeGroup = new ButtonGroup();
    private int result = -1;
    private Dimension DIALOG_DIMENSION = new Dimension(450, 275);
    private Hashtable<Integer, JLabel> sliderLabel = new Hashtable<Integer, JLabel>();
    private JFileChooser fileChooser = null;
    private byte[] inputBytes;
    private FileInputStream inputStream = null;
    private int availableBytes = 0;

    JPanel panel1 = new JPanel();
    JPanel insetsPanel1 = new JPanel();
    JButton button1 = new JButton();
    ImageIcon image1 = new ImageIcon();
    BorderLayout borderLayout1 = new BorderLayout();
    JButton jButton1 = new JButton();
    FlowLayout flowLayout1 = new FlowLayout();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    BorderLayout borderLayout3 = new BorderLayout();
    BorderLayout borderLayout4 = new BorderLayout();
    JRadioButton manualRadioButton = new JRadioButton();
    JPanel jPanel4 = new JPanel();
    BorderLayout borderLayout5 = new BorderLayout();
    JTextField jtManualKey = new JTextField();
    JPanel jPanel5 = new JPanel();
    BorderLayout borderLayout6 = new BorderLayout();
    JPanel jPanel6 = new JPanel();
    FlowLayout flowLayout2 = new FlowLayout();
    JRadioButton jrUseFullKey = new JRadioButton();
    JRadioButton jrLimitKeyLength = new JRadioButton();
    JLabel jLabel1 = new JLabel();
    JPanel jPanel7 = new JPanel();
    BorderLayout borderLayout7 = new BorderLayout();
    JPanel jPanel8 = new JPanel();
    FlowLayout flowLayout3 = new FlowLayout();
    JButton jbFileSelect = new JButton();
    JLabel lblSize = new JLabel();
    JPanel jPanel9 = new JPanel();
    JPanel jPanel10 = new JPanel();
    BorderLayout borderLayout8 = new BorderLayout();
    JRadioButton keyFileRadioButton = new JRadioButton();
    JLabel jLabel3 = new JLabel();
    JPanel jPanel1 = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    JLabel textFileName = new JLabel();
    JPanel jPanel11 = new JPanel();
    BorderLayout borderLayout9 = new BorderLayout();
    JPanel jPanel12 = new JPanel();
    BorderLayout borderLayout10 = new BorderLayout();
    JLabel jLabel4 = new JLabel();
    JSlider keySizeSlider = new JSlider();
    BorderLayout borderLayout11 = new BorderLayout();
    private GuloshUI parentReference = null;
    JPanel jPanel13 = new JPanel();
    FlowLayout flowLayout4 = new FlowLayout();
    JLabel jlAvailable = new JLabel();
    JLabel jLabel2 = new JLabel();
    private byte localCypher[] = null;
    public SetKeyValueDialog(GuloshUI parent) {
        super(parent);
        parentReference = parent;
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            initDictionary();
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void initDictionary() {
        sliderLabel.put(1, new JLabel("8"));
        sliderLabel.put(2, new JLabel("16"));
        sliderLabel.put(3, new JLabel("32"));
        sliderLabel.put(4, new JLabel("64"));
        sliderLabel.put(5, new JLabel("128"));
        sliderLabel.put(6, new JLabel("256"));
        sliderLabel.put(7, new JLabel("512"));
        sliderLabel.put(8, new JLabel("1024"));
    }

    public SetKeyValueDialog() {
        this(null);
    }

    /**
     * Component initialization.
     *
     * @throws java.lang.Exception
     */
    private void jbInit() throws Exception {
        image1 = new ImageIcon(com.semaphoreit.editor.GuloshUI.class.
                               getResource("cypher.jpg"));
        setTitle("Set Key Value");
        panel1.setLayout(borderLayout1);
        button1.setText("Set Key Value");
        button1.addActionListener(this);
        jButton1.setText("Cancel");
        jButton1.addActionListener(new SetKeyValueDialog_jButton1_actionAdapter(this));
        insetsPanel1.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.RIGHT);
        flowLayout1.setHgap(2);
        flowLayout1.setVgap(2);
        jPanel2.setLayout(borderLayout3);
        jPanel3.setLayout(borderLayout4);
        manualRadioButton.setMaximumSize(new Dimension(126, 25));
        manualRadioButton.setMinimumSize(new Dimension(126, 25));
        manualRadioButton.setPreferredSize(new Dimension(126, 25));
        manualRadioButton.setSelected(true);
        manualRadioButton.setText("Manual Key Value");
        manualRadioButton.addActionListener(new
                                        SetKeyValueDialog_jRadioButton2_actionAdapter(this));
        jPanel4.setLayout(borderLayout5);
        jPanel5.setLayout(borderLayout6);
        jPanel6.setLayout(flowLayout2);
        jrUseFullKey.setSelected(true);
        jrUseFullKey.setText("Use Full Key");
        jrUseFullKey.addActionListener(new
                                       SetKeyValueDialog_jrUseFUllKey_actionAdapter(this));
        jrLimitKeyLength.setText("Limit Key Length");
        jrLimitKeyLength.addActionListener(new
                                           SetKeyValueDialog_jrLimitKeyLength_actionAdapter(this));
        jLabel1.setText("     ");
        flowLayout2.setAlignment(FlowLayout.LEFT);
        flowLayout2.setHgap(2);
        flowLayout2.setVgap(2);
        jPanel7.setLayout(borderLayout7);
        jPanel8.setLayout(flowLayout3);
        jbFileSelect.setEnabled(false);
        jbFileSelect.setText("...");
        jbFileSelect.addActionListener(new
                                       SetKeyValueDialog_jButton2_actionAdapter(this));
        lblSize.setText("Full Key");
        flowLayout3.setHgap(2);
        flowLayout3.setVgap(2);
        jPanel9.setLayout(borderLayout11);
        jPanel10.setLayout(borderLayout8);
        keyFileRadioButton.setText("Use Key File");
        keyFileRadioButton.addActionListener(new
                                        SetKeyValueDialog_jRadioButton5_actionAdapter(this));
        jLabel3.setFont(new java.awt.Font("Tahoma", Font.BOLD, 13));
        jLabel3.setText(" Manage Key Length");
        jPanel1.setLayout(borderLayout2);
        jPanel11.setLayout(borderLayout9);
        jPanel12.setLayout(borderLayout10);
        textFileName.setToolTipText("");
        textFileName.setText("   <unassigned>");
        jLabel4.setText("Key Size:  ");
        keySizeSlider.setLabelTable(sliderLabel);
        keySizeSlider.setMinorTickSpacing(1);
        keySizeSlider.setEnabled(false);
        keySizeSlider.addChangeListener(new
                SetKeyValueDialog_keySizeSlider_changeAdapter(this));
        jPanel13.setLayout(flowLayout4);
        flowLayout4.setAlignment(FlowLayout.LEFT);
        flowLayout4.setHgap(2);
        flowLayout4.setVgap(2);
        jlAvailable.setMaximumSize(new Dimension(106, 16));
        jlAvailable.setMinimumSize(new Dimension(106, 16));
        jlAvailable.setPreferredSize(new Dimension(106, 16));
        jLabel2.setToolTipText("");
        jLabel2.setText("  Available Bytes:  ");
        getContentPane().add(panel1, null);
        insetsPanel1.add(button1, null);
        insetsPanel1.add(jButton1);
        panel1.add(insetsPanel1, BorderLayout.SOUTH);
        jPanel3.add(jPanel12, java.awt.BorderLayout.NORTH);
        jPanel2.add(jPanel5, java.awt.BorderLayout.CENTER);
        jPanel6.add(jLabel1);
        jPanel6.add(jrUseFullKey);
        jPanel6.add(jrLimitKeyLength);
        jPanel2.add(jLabel3, java.awt.BorderLayout.NORTH);
        jPanel5.add(jPanel7, java.awt.BorderLayout.CENTER);
        jPanel5.add(jPanel6, java.awt.BorderLayout.NORTH);

        super.setResizable(false);
        super.setModal(true);
        this.pack();
        keySizeGroup.add(jrLimitKeyLength);
        keySizeGroup.add(jrUseFullKey);
        jPanel8.add(jLabel4);
        jPanel8.add(lblSize);
        jPanel7.add(keySizeSlider, java.awt.BorderLayout.NORTH);

        keySelectionGroup.add(manualRadioButton);
        keySelectionGroup.add(keyFileRadioButton);
        panel1.add(jPanel9, java.awt.BorderLayout.NORTH);
        jPanel10.add(jPanel1, java.awt.BorderLayout.CENTER);
        jPanel1.add(jPanel11, java.awt.BorderLayout.NORTH);
        jPanel11.add(jbFileSelect, java.awt.BorderLayout.WEST);
        jPanel11.add(textFileName, java.awt.BorderLayout.CENTER);
        jPanel10.add(jPanel13, java.awt.BorderLayout.NORTH);
        jPanel13.add(keyFileRadioButton);
        jPanel13.add(jLabel2);
        jPanel13.add(jlAvailable);
        jPanel12.add(jPanel4, java.awt.BorderLayout.CENTER);
        jPanel4.add(jtManualKey, java.awt.BorderLayout.SOUTH);
        jPanel12.add(manualRadioButton, java.awt.BorderLayout.NORTH);
        jPanel7.add(jPanel8, java.awt.BorderLayout.SOUTH);
        jPanel9.add(jPanel3, java.awt.BorderLayout.NORTH);
        jPanel9.add(jPanel10, java.awt.BorderLayout.CENTER);
        jPanel9.add(jPanel2, java.awt.BorderLayout.SOUTH);
        this.setSize(DIALOG_DIMENSION);

        keySizeSlider.setMinimum(1);
        keySizeSlider.setMaximum(8);
        keySizeSlider.setPaintTicks(true);
        keySizeSlider.setPaintLabels(true);
        keySizeSlider.setEnabled(false);
        lblSize.setText("Full Key");
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

    /**
     * Close the dialog on a button event.
     *
     * @param actionEvent ActionEvent
     */
    public void actionPerformed(ActionEvent actionEvent) {
        boolean hasError = false;
        String errorMessage = "";
        if (actionEvent.getSource() == button1) {
            if(manualRadioButton.isSelected()){
                if(jtManualKey.getText().length() < 1){
                    hasError = true;
                    errorMessage = "Manual Key Length is Empty";
                }else{
                    localCypher = cypherManager.generateCypher(jtManualKey.getText().getBytes());
                }
            }else{
                if(jrUseFullKey.isSelected()){
                    if(availableBytes == 0){
                        hasError = true;
                        errorMessage ="Selected File is 0 bytes";
                    }else{
                        try{
                            localCypher = cypherManager.openKeyFile(textFileName.getText(), availableBytes);
                        }catch(Exception ex){
                            hasError = true;
                            errorMessage = ex.getMessage();
                        }
                    }
                }else{
                    if (availableBytes < Integer.parseInt(lblSize.getText())) {
                        hasError = true;
                        errorMessage = "Selected Key Length (" + lblSize.getText() + ") is Greated than Key File (" + availableBytes + ")";
                    }else{
                        try{
                            localCypher = cypherManager.openKeyFile(textFileName.getText(), Integer.parseInt(lblSize.getText()));
                        }catch(Exception ex){
                            hasError = true;
                            errorMessage = ex.getMessage();
                        }
                    }
                }
            }
//            manualRadioButton
//            keyFileRadioButton
            if(hasError){
                showError(errorMessage);
            }else{
                result = 0;
                dispose();
            }
        }
    }

    public void showError(String msg){
        parentReference.showErrorMessage(msg);
    }

    public boolean isKeySet() {
        return (result == 0) ? true : false;
    }

    public int getKeySize(){
        return localCypher.length;
    }

    public byte[] fetchCypher(){
        return localCypher;
    }

    public void cancelKeyAction(ActionEvent e) {
        dispose();
    }

    public void useFullKeyEvent(ActionEvent e) {
        lblSize.setText("Full Key");
        keySizeSlider.setEnabled(false);
    }

    public void limitKeyLengthEvent(ActionEvent e) {
        keySizeSlider.setEnabled(true);
        lblSize.setText(sliderLabel.get(keySizeSlider.getValue()).getText());
    }

    public void keyValueLengthChanged(ChangeEvent e) {
        try {
            if (!keySizeSlider.getValueIsAdjusting()) {
                lblSize.setText(sliderLabel.get(keySizeSlider.getValue()).getText());
            }
        } catch (NullPointerException npe) {}
    }

    public void selectKeyFile(ActionEvent e) {
        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Key File");
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            textFileName.setText(fileChooser.getSelectedFile().getAbsolutePath());
            try{
                inputStream = new FileInputStream(fileChooser.getSelectedFile());
                availableBytes = inputStream.available();
                if(availableBytes > 0){
                    jlAvailable.setText(availableBytes + " bytes");
                }else{
                    availableBytes = 0;
                    showError("Selected File is 0 bytes");
                }
//                inputBytes = new byte[inputStream.available()];
//                inputStream.read(inputBytes);
            }catch(Exception ex){
                parentReference.showErrorMessage(ex);
            }finally{
                try{inputStream.close();}catch(Exception ex){}
            }


        }
    }

    public void useManualKey(ActionEvent e) {
        handleSelectionType(false);
    }

    public void useKeyFileEvent(ActionEvent e) {
        handleSelectionType(true);
    }

    private void handleSelectionType(boolean val) {
        jtManualKey.setEnabled(!val);
        jbFileSelect.setEnabled(val);
        textFileName.setEnabled(val);
    }
}


class SetKeyValueDialog_jRadioButton2_actionAdapter implements ActionListener {
    private SetKeyValueDialog adaptee;
    SetKeyValueDialog_jRadioButton2_actionAdapter(SetKeyValueDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.useManualKey(e);
    }
}


class SetKeyValueDialog_jButton2_actionAdapter implements ActionListener {
    private SetKeyValueDialog adaptee;
    SetKeyValueDialog_jButton2_actionAdapter(SetKeyValueDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.selectKeyFile(e);
    }
}


class SetKeyValueDialog_jRadioButton5_actionAdapter implements ActionListener {
    private SetKeyValueDialog adaptee;
    SetKeyValueDialog_jRadioButton5_actionAdapter(SetKeyValueDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.useKeyFileEvent(e);
    }
}


class SetKeyValueDialog_keySizeSlider_changeAdapter implements ChangeListener {
    private SetKeyValueDialog adaptee;
    SetKeyValueDialog_keySizeSlider_changeAdapter(SetKeyValueDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void stateChanged(ChangeEvent e) {
        adaptee.keyValueLengthChanged(e);
    }
}


class SetKeyValueDialog_jrLimitKeyLength_actionAdapter implements
        ActionListener {
    private SetKeyValueDialog adaptee;
    SetKeyValueDialog_jrLimitKeyLength_actionAdapter(SetKeyValueDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.limitKeyLengthEvent(e);
    }
}

class SetKeyValueDialog_jrUseFUllKey_actionAdapter implements ActionListener {
    private SetKeyValueDialog adaptee;
    SetKeyValueDialog_jrUseFUllKey_actionAdapter(SetKeyValueDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.useFullKeyEvent(e);
    }
}


class SetKeyValueDialog_jButton1_actionAdapter implements ActionListener {
    private SetKeyValueDialog adaptee;
    SetKeyValueDialog_jButton1_actionAdapter(SetKeyValueDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cancelKeyAction(e);
    }
}
