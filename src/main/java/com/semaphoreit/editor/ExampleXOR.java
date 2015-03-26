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

public class ExampleXOR {
    public ExampleXOR() {
    }
    public String xorEnc(int encKey, String toEnc) {
            /*
                Usage: str = xorEnc(integer_key,string_to_encrypt);
                Created by Matthew Shaffer (matt-shaffer.com)
            */
            int t=0;
            String s1="";
            String tog="";
            if(encKey>0) {
                while(t < toEnc.length()) {
                    int a=toEnc.charAt(t);
                    int c=a ^ encKey;
                    char d=(char)c;
                    tog=tog+d;
                    t++;
                }

            }
            return tog;
        }





        public String xorEncStr(String encKey, String toEnc) {

            /*
                Usage: str = xorEnc(string_key,string_to_encrypt);
                Created by Matthew Shaffer (matt-shaffer.com)
            */
            int t=0;
            int encKeyI=0;

            while(t < encKey.length()) {
                encKeyI+=encKey.charAt(t);
                System.out.println(">" + encKey.charAt(t)  + " [" + (int)encKey.charAt(t) + "] [" + encKeyI + "]");
                t+=1;
            }
            return xorEnc(encKeyI,toEnc);
    }

    public byte[] testGuloshXOR(byte in[], byte toEncrypt[]){
        int counter = 0;
        int keyLength = in.length;
        int bytesRead = 0;
        int bytesAvailable = toEncrypt.length;
        boolean readingFile = true;

        int x = 0;
        int y = 0;

        StringBuffer buffer = new StringBuffer();

        //--example
        //<string>.getBytes();


        while (readingFile) {
            for (counter = 0; counter < keyLength; counter++) {
                if (bytesRead < bytesAvailable) {
                    out("Key(" + counter + "): " + in[counter]);
                    out("bytesRead/bytesAvailable: " + bytesRead + "/" +
                        bytesAvailable);
                    out("key/value: " + in[counter] + "/" + toEncrypt[bytesRead]);
                    out(">2: " + ((char) (in[counter] ^ toEncrypt[bytesRead])));
                    buffer.append((char) (in[counter] ^ toEncrypt[bytesRead]));
                    toEncrypt[bytesRead] = (byte) (in[counter] ^
                                                   toEncrypt[bytesRead]);
                } else {
                    readingFile = false;
                }
                bytesRead++;
            }

            out(">>> [" + buffer.toString() + "]");

        }
    return toEncrypt;
    }
    private void out(String in){
        System.out.println(in);
    }

    public void workingExampletestGuloshXOR(String in, String toEncrypt){
        int counter = 0;
        int keyLength = in.length();
        int targetLength = toEncrypt.length();
        int bytesRead = 0;
        int bytesAvailable = toEncrypt.length();
        boolean readingFile = true;

        int x = 0;
        int y = 0;

        StringBuffer buffer = new StringBuffer();

        //--example
        toEncrypt.getBytes();


        while(readingFile){
            for (counter = 0; counter < keyLength; counter++) {
                if(bytesRead < bytesAvailable){
                    out("Key(" + counter + "): " + in.charAt(counter));
                    out("bytesRead/bytesAvailable: " + bytesRead + "/" + bytesAvailable);
                    out("key/value: " + in.charAt(counter) + "/" + toEncrypt.charAt(bytesRead));
                    out(">2: " + ( (char)(in.charAt(counter) ^ toEncrypt.charAt(bytesRead)) ) );
                    buffer.append( (char)(in.charAt(counter) ^ toEncrypt.charAt(bytesRead)) );
                }else{
                     readingFile = false;
                }
                bytesRead++;
            }

            out(">>> [" + buffer.toString() + "]");

        }

    }

    public static void main(String args[]){
        ExampleXOR ex = new ExampleXOR();
//        System.out.println(">> " + ex.testGuloshXOR("X123","ABC"));
        String key = "X123";
        String file = "ABCDEFGHIJKLMNOP1234567890";
        byte[] doit = ex.testGuloshXOR(key.getBytes(),file.getBytes());
        ex.out("BYTE-VERSION: [" + new String(doit) + "]");
        doit = ex.testGuloshXOR(key.getBytes(),doit);
        ex.out("REVERSED: [" + new String(doit) + "]");
    }

}
