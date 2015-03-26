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

import java.io.*;

public class GuloshManager {
    private final int ANSWER_TO_ALL_THINGS = 42;
    private FileInputStream inputStream = null;
    private FileOutputStream outputStream = null;
//    private DataOutputStream outputStream = null;

    public GuloshManager() { }

    public byte[] generateCypher(String cypher){
        return generateCypher(cypher.getBytes());
    }

    public byte[] generateCypher(byte[] bytes){
        int byteSize = bytes.length;
        for(int loc = 0; loc < byteSize;loc++){
            if(bytes[loc] == 0) bytes[loc] = ANSWER_TO_ALL_THINGS;
        }
        return bytes;
    }

    public byte[] openKeyFile(String fileName, int length) throws Exception{
        byte[] inputBytes;
        try{
            inputStream = new FileInputStream(new File(fileName));
//            inputBytes = new byte[inputStream.available()];
            inputBytes = new byte[length];
            inputStream.read(inputBytes);
        }catch(Exception ex){
            //--we will log this, but let the actual program handle the error
            //--log message here
            throw ex;
        }finally{
            try{inputStream.close();}catch(Exception ex){}
        }
        return generateCypher(inputBytes);
    }

    public byte[] openSourceFile(String fileName) throws Exception{
        byte[] inputBytes;
        try{
            inputStream = new FileInputStream(new File(fileName));
            inputBytes = new byte[inputStream.available()];
            inputStream.read(inputBytes);
        }catch(Exception ex){
            //--we will log this, but let the actual program handle the error
            //--log message here
            throw ex;
        }finally{
            try{inputStream.close();}catch(Exception ex){}
        }
        return inputBytes;
    }

    public void persistFile(String fileName, byte[] sourceData, byte[] cypher) throws Exception{
        try{
            System.out.println("FILENAME: " + fileName);
            System.out.println("SOURCE.SIZE: " + sourceData.length);
            System.out.println("CYPHER.SIZE: " + cypher.length);


//DataOutputStream thefileOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileOut)));
            File file = new File(fileName);
            boolean success = file.delete();
            System.out.println("SUCCESSFUL DELETE: " + success);

            outputStream = new FileOutputStream(file);

//            outputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
//            if (outputStream.exists()) outputStream.delete();

            outputStream.write(XOR(cypher,sourceData));
            outputStream.flush();
        }catch(Exception ex){
            throw ex;
        }finally{
            try{outputStream.close();}catch(Exception ex){}
        }
    }

    public void convertFile(String fileSource, String fileTarget, byte cypher[]) throws Exception{
        try{
            System.out.println("SOURCE: " + fileSource);
            System.out.println("TARGET: " + fileTarget);
            System.out.println("CYPHER: " + new String(cypher));

            File tmpFile = new File(fileSource);
            if(!tmpFile.exists()) throw new Exception("Source File (" + fileSource + ") does not exist");

//            tmpFile = new File(fileTarget);
//            if(tmpFile.exists()) tmpFile.delete();

            byte[] tmp = openSourceFile(fileSource);
//            System.out.println("byteSize: " + tmp.length);
//            System.out.println("byteString: " + new String(tmp));
//            System.out.println("cypher1ByteString: " + new String(XOR(tmp,cypher)));
            persistFile(fileTarget,tmp,cypher);
        }finally{
            System.gc();
        }
    }

    public byte[] XOR(byte keyValues[], byte sourceBytes[]){
        System.out.println("XXXXXXXXOOOOOOORRRRRRRR HAS BEEN CALLED XXXXXXXXXX");
//        Integer.parseInt ("XXXXXXXXOOOOOOORRRRRRRR HAS BEEN CALLED XXXXXXXXXX");
        int counter = 0;
        int keyLength = keyValues.length;
        int bytesRead = 0;
        int bytesAvailable = sourceBytes.length;
        boolean readingFile = true;
        StringBuffer buffer = new StringBuffer();
        while (readingFile) {
            for (counter = 0; counter < keyLength; counter++) {
                if (bytesRead < bytesAvailable) {
//                    out("Key(" + counter + "): " + keyValues[counter]);
//                    out("bytesRead/bytesAvailable: " + bytesRead + "/" + bytesAvailable);
//                    out("key/value: " + keyValues[counter] + "/" + sourceBytes[bytesRead]);
                    out(">>: [" + counter + "|" + bytesRead + "] " + ((char)keyValues[counter] + " ^ " +  (char)sourceBytes[bytesRead]));
                    buffer.append((char) (keyValues[counter] ^ sourceBytes[bytesRead]));
                    sourceBytes[bytesRead] = (byte) (keyValues[counter] ^ sourceBytes[bytesRead]);
                } else {
                    readingFile = false;
                    break;
                }
                bytesRead++;
            }
       }
       System.out.println("ENCRYPTED STRING: " + buffer.toString());
        return sourceBytes;
    }

    private void out(String in){
        System.out.println(in);
    }



}
