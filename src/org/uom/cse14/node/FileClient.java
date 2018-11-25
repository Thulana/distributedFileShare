package org.uom.cse14.node;

import org.uom.cse14.FileServer.FileInterface;

import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import static org.uom.cse14.node.util.NetworkConstants.TCP_PORT_OFFSET;



public class FileClient{
    
    String downlFilePath;
    
    public FileClient(String dFilePath) {
       downlFilePath = dFilePath;
    }
    public  void downloadFile(String fileName, String serverIp,int serverPort) {
        
        try {
            Registry registry = LocateRegistry.getRegistry(serverIp,serverPort+TCP_PORT_OFFSET);
            FileInterface stub = (FileInterface) registry.lookup("Hello");
            //byte[] fileData = stub.downloadFile("TestFile.txt");
            byte[] fileData = stub.downloadFile(fileName);
            System.out.printf(Integer.toString(fileData.length));
            File file = new File(fileName);
            try (BufferedOutputStream output = new
                            BufferedOutputStream(new FileOutputStream(downlFilePath+"/"+file.getName()))) {
                output.write(fileData,0,fileData.length);
                output.flush();
            }
            String response = Integer.toString(stub.addTwo(5,10));
            System.out.println("response: " + response);
        } catch(IOException | NotBoundException e) {
            System.err.println("FileServer exception: "+ e.getMessage());
        }
    }
}