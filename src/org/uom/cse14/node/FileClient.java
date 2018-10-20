package org.uom.cse14.node;

import org.uom.cse14.FileServer.FileInterface;

import java.io.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class FileClient{
    public static void main(String argv[]) {

        System.setProperty("java.rmi.server.hostname","192.168.8.102");
        try {
            Registry registry = LocateRegistry.getRegistry("192.168.8.102",3045);
            FileInterface stub = (FileInterface) registry.lookup("Hello");
            byte[] fileData = stub.downloadFile("TestFile.txt");
            System.out.printf(Integer.toString(fileData.length));
            File file = new File("TestFile.txt");
            BufferedOutputStream output = new
                    BufferedOutputStream(new FileOutputStream(FileClient.class.getProtectionDomain().getCodeSource().getLocation().getPath()+"/org/uom/cse14/FileServer/"+file.getName()));
            output.write(fileData,0,fileData.length);
            output.flush();
            output.close();
            String response = Integer.toString(stub.addTwo(5,10));
            System.out.println("response: " + response);
        } catch(Exception e) {
            System.err.println("FileServer exception: "+ e.getMessage());
            e.printStackTrace();
        }
    }
}