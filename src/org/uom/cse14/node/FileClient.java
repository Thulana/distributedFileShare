package org.uom.cse14.node;

import org.uom.cse14.FileServer.FileInterface;

import java.io.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class FileClient{
    public static void main(String argv[]) {
   /*     if(argv.length != 2) {
            System.out.println("Usage: java FileClient fileName servermachineName");
            System.exit(0);
        }
        try {
            String name = "//" + argv[1] + "/FileServer";
            FileInterface fi = (FileInterface) Naming.lookup(name);
            byte[] filedata = fi.downloadFile(argv[0]);
            File file = new File(argv[0]);
            BufferedOutputStream output = new
                    BufferedOutputStream(new FileOutputStream(file.getName()));
            output.write(filedata,0,filedata.length);
            output.flush();
            output.close();
        } catch(Exception e) {
            System.err.println("FileServer exception: "+ e.getMessage());
            e.printStackTrace();
        }*/

        try {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1",3045);
            FileInterface stub = (FileInterface) registry.lookup("Hello");
            String response = Integer.toString(stub.addTwo(5,10));
            System.out.println("response: " + response);

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }


    }
}