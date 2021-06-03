import java.io.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class HeapCopyGc{

    ArrayList<Node> nodes;
    HashMap<Integer,Integer> indices;
    HeapCopyGc (String csvPathHeap,String csvPathPointers,String rootPath,String output){
        nodes=new ArrayList<>();
        indices=new HashMap<>();
        Reader r=new Reader();
        r.readCSVHeap(nodes,indices,csvPathHeap);
        r.readCSVPointers(nodes,indices,csvPathPointers);
        setResultArray(rootPath,output);
    }

    //copy GC
    void setResultArray(String txtpath,String output){
        Reader r = new Reader();
        ArrayList<Node>  result = new ArrayList<>();
        ArrayList<Integer>identifiersOfRoots = r.readTxt(txtpath);
        for (int  i = 0 ; i < identifiersOfRoots.size();i++){
            int indexOfRoot = indices.get(identifiersOfRoots.get(i));
            result.add(nodes.get(indexOfRoot));
        }
        finishResultArray(result,output);
    }
    void finishResultArray(ArrayList<Node> result,String output){
        int i =0;
        int lastByte=-1;
        while(i!=result.size()){
            Node temp = result.get(i);
            int size = temp.memoryEnd- temp.memoryStart;
            lastByte++;
            result.get(i).memoryStart=lastByte;
            result.get(i).memoryEnd=lastByte+size;
            lastByte=lastByte+size;
            ArrayList<Integer> indexOfChildren = temp.children;
            for (int j = 0 ; j< indexOfChildren.size();j++){
                result.add(nodes.get(indexOfChildren.get(j)));
            }
            i++;
        }
        writeToCsvFile(result,output);
    }
    static public void writeToCsvFile(ArrayList<Node> result,String output){
        try {
            FileWriter csWriter = new FileWriter(output);
            for (int i = 0 ; i <result.size();i++){
                Node temp = result.get(i);
                List<String> list = Arrays.asList(temp.identifier+"",temp.memoryStart+"",temp.memoryEnd+"");
                csWriter.append(String.join(",",list));
                csWriter.append("\n");
            }
            csWriter.flush();
            csWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void main(String []  args){
        HeapCopyGc heap = new HeapCopyGc(args[0],args[1],args[2],args[3]);
    }
}