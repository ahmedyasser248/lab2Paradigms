import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public  class Reader {
    public  void readCSVHeap(ArrayList<Node> nodes,HashMap<Integer,Integer> indices, String csvPath) {
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(csvPath));
            String row;
            int i=0;

            while ((row = csvReader.readLine()) != null) {


                String data[] = row.split(",");
                if(i==0){
                    data[0]=data[0].substring(1);
                }
                Node element = new Node(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]));
                nodes.add(element);
                indices.put(Integer.parseInt(data[0]),i++);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public  void readCSVPointers(ArrayList<Node>nodes,HashMap<Integer,Integer>indices,String csvPath) {
        int parent=0;
        int parentIndex=-1;
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(csvPath));
            String row;
            row=csvReader.readLine();
            String[] data=row.split(",");
            data[0]=data[0].substring(1);
            nodes.get(indices.get(Integer.parseInt(data[0]))).children.add(indices.get(Integer.parseInt(data[1])));



            while ((row = csvReader.readLine()) != null) {
                data = row.split(",");
                nodes.get(indices.get(Integer.parseInt(data[0]))).children.add(indices.get(Integer.parseInt(data[1])));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public   ArrayList<Integer> readTxt(String txtPath ){
        ArrayList<Integer> identifiers=new ArrayList<>();
        try {
            BufferedReader txtReader=new BufferedReader(new FileReader(txtPath));
            String row;
            while((row=txtReader.readLine())!=null){
                identifiers.add(Integer.parseInt(row));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return identifiers;
    }
}

