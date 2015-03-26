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
import java.util.logging.*;
import java.io.*;

public class GuloshLogHandler {
    private static Logger logger = null;
    private static GuloshLogHandler singleton = null;
    private final static String LOG_FILE = "gulosh.log";
    private GuloshLogHandler() throws Exception{
        logger = Logger.getLogger("com.semaphoreit.editor.GuloshLogHandler");
//        logger.addHandler(new ConsoleHandler());
//        SimpleFormatter formatter = new SimpleFormatter();
        FileHandler handler = new FileHandler(LOG_FILE);
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);
    }

    public static void getInstance() {
        try {
          if (singleton == null) singleton = new GuloshLogHandler();
        } catch (Exception excp) {
            logger.warning("Could not create log file [" + LOG_FILE + "]");
            logger.log(Level.FINEST,"Error Creating Log File",excp);
        }
    }

    public static void log(String msg){
        if (singleton == null) {getInstance();}
        logger.info(msg);
    }
    public static void log(String msg, Throwable excp){
        if (singleton == null) {getInstance();}
        logger.log(Level.WARNING,msg,excp);
    }
    public static void log(Throwable excp){
        if (singleton == null) {getInstance();}
        logger.log(Level.WARNING,"",excp);
    }
}
