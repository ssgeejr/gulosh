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

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class GuloshFileFilter extends FileFilter{
    public GuloshFileFilter() {}

    public boolean accept(File file)
    {
         //Convert to lower case before checking extension
        return (file.getName().toLowerCase().endsWith(".gef")  || file.isDirectory());
   }

   public String getDescription()
   {
       return "Gulosh Encrypted File (*.gef)";
    }


}
