package practica_9;

import java.util.ArrayList;

public class BNode {
    static int m;
    ArrayList<Integer> key;
    ArrayList<BNode> child;
    BNode parent;
    boolean leaf;
    
    public BNode() {
        key = new ArrayList<>();
        child = new ArrayList<>();
        leaf = true;
        parent = null;
    }
    
    public int getKey( int i ){
        return this.key.get(i);
    }
    
    public BNode getChild( int i ){
        try{
            this.child.get(i);
        }catch(Exception e){
            return null;
        }
        return this.child.get(i);
    }
    
    public void setM( int m ){
        BNode.m = m;
    }
    
    public void setKeys( ArrayList<Integer> list ){
        this.key = list;
    }
    
    public void setChildren( ArrayList<BNode> list  ){
        this.child = list;
    }
    
    public int getChildIndex(){
        if( parent == null ){
            return -1;
        }
        else{
            BNode padre = parent;
            for( int i = 0 ; i < padre.child.size() ; i++ ){
                if( padre.child.get(i) == this )
                    return i;
            }
            return -1;
        }
    }
    
    public void mostrarLlaves(){
        for (Integer integer : key) System.out.print(integer + " ");
    }
}
