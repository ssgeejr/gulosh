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
import java.awt.Dimension;
import java.io.*;

public class GPLDialog extends JDialog implements ActionListener {
    private Dimension DIALOG_SIZE = new Dimension(800,600);
    JPanel panel1 = new JPanel();
    ImageIcon image1 = new ImageIcon();
    JPanel jPanel1 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JScrollPane jScrollPane1 = new JScrollPane();
    JTextArea errroMessage = new JTextArea();
    BorderLayout borderLayout2 = new BorderLayout();
    JPanel jPanel2 = new JPanel();
    BorderLayout borderLayout3 = new BorderLayout();
    JTextArea taGPL = new JTextArea();
    JPanel jPanel3 = new JPanel();
    FlowLayout flowLayout1 = new FlowLayout();
    JButton jButton1 = new JButton();

    public GPLDialog(Frame parent) {
        super(parent);
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            loadGPL();
            taGPL.setCaretPosition(0);
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
        setTitle("GNU GENERAL PUBLIC LICENSE: Version 3, 29 June 2007");
        panel1.setLayout(borderLayout2);
        jPanel1.setLayout(borderLayout1);
        jPanel2.setLayout(borderLayout3);
        jPanel3.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.RIGHT);
        jButton1.setText("Ok");
        jButton1.addActionListener(new GPLDialog_jButton1_actionAdapter(this));
        taGPL.setLineWrap(true);
        taGPL.setWrapStyleWord(true);
        getContentPane().add(panel1, null);
        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        jScrollPane1.getViewport().add(taGPL);
        panel1.add(jPanel1, java.awt.BorderLayout.CENTER);
        panel1.add(jPanel2, java.awt.BorderLayout.WEST);
        panel1.add(jPanel3, java.awt.BorderLayout.SOUTH);
        jPanel3.add(jButton1);
        super.setResizable(true);
        super.setModal(true);
        this.setSize(DIALOG_SIZE);
    }

    private void loadGPL() throws Exception{
        BufferedReader reader = null;
        try{
           reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("gpl.txt")));
           StringBuffer gpl = new StringBuffer();
           String CR_LF = System.getProperties().getProperty("line.separator");
           String lineIn = null;
           while( (lineIn = reader.readLine()) != null){
               gpl.append(lineIn).append(CR_LF);
           }
           taGPL.setText(gpl.toString());
        }finally{
            try{reader.close();}catch(Exception ex){}
        }
    }

    public void setModal(boolean value){
        super.setModal(true);
    }
    public void setResizable(){
        super.setResizable(false);
    }
    public Dimension getPreferredSize(){
        return DIALOG_SIZE;
    }

    /**
     * Close the dialog on a button event.
     *
     * @param actionEvent ActionEvent
     */
    public void actionPerformed(ActionEvent actionEvent) {
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        this.setVisible(false);
    }
}


class GPLDialog_jButton1_actionAdapter implements ActionListener {
    private GPLDialog adaptee;
    GPLDialog_jButton1_actionAdapter(GPLDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}
