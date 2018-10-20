package org.uom.cse14.FileServer;

import java.io.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class FileServer implements FileInterface {
    public static void main(String argv[]) throws IOException {

       /* if(System.getSecurityManager() == null) {
            System.setProperty("java.security.policy","file:/media/pasindu/newvolume/distributedFileShare/src/org/uom/cse14/FileServer/policy.txt");

            System.setSecurityManager(new RMISecurityManager());
        }*/
    /*    try {
            FileInterface fi = new FileImpl("FileServer");
            Naming.rebind("//localhost:1024/Hello", fi);
        } catch(Exception e) {
            System.out.println("FileServer: "+e.getMessage());
            e.printStackTrace();
        }*/

    try{
        System.setProperty("java.rmi.server.hostname","192.168.8.102");
        FileServer obj = new FileServer();
        FileInterface stub = (FileInterface)UnicastRemoteObject.exportObject(obj, 0);
        Registry registry = LocateRegistry.createRegistry(3045);
        registry.bind("Hello", stub);

    } catch (Exception e) {
        System.err.println("Server exception: " + e.toString());
        e.printStackTrace();
    }

    }

    public byte[] downloadFile(String fileName){
        try {
            File file = new File(FileServer.class.getProtectionDomain().getCodeSource().getLocation().getPath()+"/org/uom/cse14/FileServer/"+fileName);
            byte buffer[] = new byte[(int)file.length()];
            BufferedInputStream input = new
                    BufferedInputStream(new FileInputStream(FileServer.class.getProtectionDomain().getCodeSource().getLocation().getPath()+"/org/uom/cse14/FileServer/"+fileName));
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