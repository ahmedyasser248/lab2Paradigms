import java.util.ArrayList;

public class Node {//Node Object stores the data of the object from the heap
    int identifier;
    int memoryStart;
    int memoryEnd;
    boolean marked;
    ArrayList<Integer> children;

    Node(int identifier, int memoryStart, int memoryEnd) {
        this.identifier = identifier;
        this.memoryStart = memoryStart;
        this.memoryEnd = memoryEnd;
        marked = true;
        children = new ArrayList<>();
    }

    @Override
    public String toString() {
        return this.identifier+" "+this.memoryStart+" "+this.memoryEnd;
    }
}
