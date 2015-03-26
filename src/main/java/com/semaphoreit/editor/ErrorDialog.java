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
import java.awt.BorderLayout;


public class ErrorDialog extends JDialog implements ActionListener {
    private Dimension DIALOG_SIZE = new Dimension(600,450);
    JPanel panel1 = new JPanel();
    ImageIcon image1 = new ImageIcon();
    JLabel jLabel1 = new JLabel();
    JPanel jPanel1 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JScrollPane jScrollPane1 = new JScrollPane();
    JTextArea errroMessage = new JTextArea();
    BorderLayout borderLayout2 = new BorderLayout();
    JPanel jPanel2 = new JPanel();
    BorderLayout borderLayout3 = new BorderLayout();
    JTextArea textAreaErrorMessage = new JTextArea();
    JPanel jPanel3 = new JPanel();
    FlowLayout flowLayout1 = new FlowLayout();
    JButton jButton1 = new JButton();

    public ErrorDialog(Frame parent, String msg) {
        super(parent);
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            setErrorMessage(msg);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    public ErrorDialog(Frame parent) {
        super(parent);
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void setErrorMessage(String msg){
        textAreaErrorMessage.setText(msg);
    }

    public ErrorDialog() {
        this(null);
    }

    /**
     * Component initialization.
     *
     * @throws java.lang.Exception
     */
    private void jbInit() throws Exception {
//        image1 = new ImageIcon(com.semaphoreit.editor.GuloshUI.class.getResource("error.gif"));
        image1 = new ImageIcon(com.semaphoreit.editor.GuloshUI.class.getResource("error-small.gif"));
//        jLabel1.setSize(50,50);
        setTitle("Unrecoverable Error");
        panel1.setLayout(borderLayout2);
        jLabel1.setIcon(image1);
        jPanel1.setLayout(borderLayout1);
        errroMessage.setText("jTextArea1");
        jPanel2.setLayout(borderLayout3);
        jPanel3.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.RIGHT);
        jButton1.setText("Ok");
        jButton1.addActionListener(new ErrorDialog_jButton1_actionAdapter(this));
        textAreaErrorMessage.setLineWrap(true);
        textAreaErrorMessage.setWrapStyleWord(true);
        getContentPane().add(panel1, null);
        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        jScrollPane1.getViewport().add(textAreaErrorMessage);
        panel1.add(jPanel1, java.awt.BorderLayout.CENTER);
        panel1.add(jPanel2, java.awt.BorderLayout.WEST);
        jPanel2.add(jLabel1, java.awt.BorderLayout.NORTH);
        panel1.add(jPanel3, java.awt.BorderLayout.SOUTH);
        jPanel3.add(jButton1);
        super.setResizable(true);
        super.setModal(true);
        this.setSize(DIALOG_SIZE);
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


class ErrorDialog_jButton1_actionAdapter implements ActionListener {
    private ErrorDialog adaptee;
    ErrorDialog_jButton1_actionAdapter(ErrorDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}
