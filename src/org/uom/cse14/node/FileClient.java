package org.uom.cse14.node;

import org.uom.cse14.FileServer.FileInterface;

import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import static org.uom.cse14.node.util.NetworkConstants.TCP_PORT_OFFSET;



public class FileClient{
    
    String downlFilePath;
    
    public FileClient(String dFilePath) {
       downlFilePath = dFilePath;
    }
    public int downloadFile(String fileName, String serverIp,int serverPort) throws NoSuchAlgorithmException {
        
        try {
            Registry registry = LocateRegistry.getRegistry(serverIp,serverPort+TCP_PORT_OFFSET);
            FileInterface stub = (FileInterface) registry.lookup("Hello");
            //byte[] fileData = stub.downloadFile("TestFile.txt");
            fileName =fileName.replace("_"," ");
            byte[] fileData = stub.downloadFile(fileName);
            File file = new File(fileName);
            try (BufferedOutputStream output = new
                            BufferedOutputStream(new FileOutputStream(downlFilePath+"/"+file.getName()))) {
                output.write(fileData,0,fileData.length);
                output.flush();
            }
            
            String hashValue = stub.getHash(fileName);
            System.out.println(hashValue);
            MessageDigest shaDigest = MessageDigest.getInstance("SHA-1");
            String shaChecksum = getFileChecksum(shaDigest, new File(downlFilePath+"/"+fileName));
            if(shaChecksum.equals(hashValue)){
                System.out.println("Download Completed");
                return 1;
            }
            else{
                return 0;
            }

        } catch(Exception e) {
            System.err.println("FileClient exception: "+ e.getMessage());
            return 0;
        }
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