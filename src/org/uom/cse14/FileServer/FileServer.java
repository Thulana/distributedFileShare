package org.uom.cse14.FileServer;

import javax.swing.*;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public String getHash(String fileName) {
    
        MessageDigest shaDigest = null;
        try {
            shaDigest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(FileServer.class.getName()).log(Level.SEVERE, null, ex);
        }
 
//SHA-1 checksum
        File file = new File(uploadFilePath+"/"+fileName);
        String shaChecksum = null;
        try {
            shaChecksum = getFileChecksum(shaDigest, file);
        } catch (IOException ex) {
            Logger.getLogger(FileServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return shaChecksum;

    }
    
    private static String getFileChecksum(MessageDigest digest, File file) throws IOException
    {
        //Create byte array to read data in chunks
        try ( //Get file input stream for reading the file content
                FileInputStream fis = new FileInputStream(file)) {
            //Create byte array to read data in chunks
            byte[] byteArray = new byte[1024];
            int bytesCount = 0;
            //Read file data and update in message digest
            while ((bytesCount = fis.read(byteArray)) != -1) {
                digest.update(byteArray, 0, bytesCount);
            }       ;
            //close the stream; We don't need it now.
        }
     
        byte[] bytes = digest.digest();
    
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}