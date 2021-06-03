import java.io.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Heap{

    ArrayList<Node> nodes;
    HashMap<Integer,Integer> indices;
    Heap (String csvPathHeap,String csvPathPointers,String rootPath,String output){
        nodes=new ArrayList<>();
        indices=new HashMap<>();
        Reader r=new Reader();
        r.readCSVHeap(nodes,indices,csvPathHeap);
        r.readCSVPointers(nodes,indices,csvPathPointers);
        markAndCompact(rootPath,output);
    }
    private void markFalse(Node parent){
        if(parent.marked==true) {
            parent.marked = false;
            for (int i = 0; i < parent.children.size(); i++) {
                Node child = nodes.get(parent.children.get(i));
                markFalse(child);
            }
        }
    }
    private void mark(String txtPath){//txtPath is the path of roots.txt
        Reader r=new Reader();
        ArrayList<Integer> identifiers=r.readTxt(txtPath);
        //Loop on the objects and delete the mark of objects that have references
        for(int i=0;i<identifiers.size();i++){
            int index=indices.get(identifiers.get(i));
            markFalse(nodes.get(index));
        }
    }


    public void markAndCompact(String txtPath,String output){
        mark(txtPath);
        int start=0;
        FileWriter csvWriter= null;
        try {
            csvWriter = csvWriter=new FileWriter(output);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i=0;i<nodes.size();i++){
            Node node=nodes.get(i);
            try {
                if(node.marked==false){
                    String data[]=new String[] {String.valueOf(node.identifier),String.valueOf(start),String.valueOf(start+(node.memoryEnd-node.memoryStart))};
                    csvWriter.append(String.join(",",data));
                    csvWriter.append("\n");
                    start+=node.memoryEnd-node.memoryStart+1;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String []  args){
        Heap heap = new Heap(args[0],args[1],args[2],args[3]);
    }
}