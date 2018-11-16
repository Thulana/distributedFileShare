package org.uom.cse14.FileServer;

import javax.swing.*;
import java.io.*;

public class FileServer  extends JPanel implements  FileInterface {

 
    String uploadFilePath;
    public FileServer(String uFilePath) {
       uploadFilePath = uFilePath;
    }
        
    @Override
    public byte[] downloadFile(String fileName){
        try {
            File file = new File(uploadFilePath+"/"+fileName);
            byte buffer[] = new byte[(int)file.length()];
            BufferedInputStream input = new
                    BufferedInputStream(new FileInputStream(uploadFilePath+"/"+fileName));
            input.read(buffer,0,buffer.length);
            input.close();
            System.out.printf(Integer.toString(buffer.length));
            return(buffer);
        } catch(Exception e){
            System.out.println("FileImpl: "+e.getMessage());
            e.printStackTrace();
            return(null);
        }
    }

    @Override
    public int addTwo(int a, int b) {
        try {
        return a+b;
        } catch(Exception e){
            System.out.println("FileImpl: "+e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }
}