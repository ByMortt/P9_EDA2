package practica_9;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BTree {
    
    int m;
    BNode root;

    public BTree() {
    }

    public BTree(int m ){
        this.m = m;
        root = new BNode();
        root.setM(m);
    }
    
    public void add( int n ){
        if( find(n) ){
            System.out.println("La clave ya existe en el árbol.");
        }
        else{
            BNode hoja = leafNode( root, n );
            addToNode( hoja, n);
        }
    
    }
    
    private BNode leafNode( BNode nodo, int n ){
        if( nodo.leaf ){
            return nodo;
        }
        else{
            int i = 0;
            for(; i < nodo.key.size() ; i++ ){
                if( n < nodo.getKey(i) ){
                    i++;
                    break;
                }
            }
            
            if( n < nodo.getKey(i-1) )
                i--;
            
            return leafNode( nodo.getChild(i), n );
            
        }
    }
    
    private void addToNode( BNode nodo, int n ){
        if( nodo.key.size() < m - 1 )  {
            System.out.println("nodo key size"+nodo.key.size());
            insert( nodo, n );
        }
        else  
            divisionCelular( nodo, n ); 
        
        
    }
    
    private void insert( BNode nodo, int n ){
        int i = 0;
        while( i < nodo.key.size()  && n > nodo.getKey(i) ){
            i++;
        }   
        nodo.key.add(i, n);
    }
    
    private void divisionCelular( BNode nodo, int n ){
         
        int h = (m-1)/2;    
        if( m % 2 != 0){
            insert(nodo,n);
        }
        int medio = nodo.key.get(h);
            
        ArrayList<Integer> key1 = new ArrayList<>( nodo.key.subList( 0, h ) );
        ArrayList<Integer> key2 = new ArrayList<>( nodo.key.subList( h + 1 , 2*h + 1) );
        ArrayList<BNode> child1 = new ArrayList<>();
        ArrayList<BNode> child2 = new ArrayList<>();
        
        if( nodo == root ){
            
            BNode nuevoNodo1 = new BNode();
            BNode nuevoNodo2 = new BNode();
            nuevoNodo1.leaf = nuevoNodo2.leaf = nodo.leaf;
            
            nuevoNodo1.setKeys(key1);
            nuevoNodo2.setKeys(key2);
            nodo.key.clear();
            nodo.key.add(medio);
            
            if( m % 2 == 0 ){
                if( n < medio )
                    insert(nuevoNodo1, n);
                else
                    insert(nuevoNodo2, n);
            }
             
            if( !nodo.leaf ){
                if( m % 2 != 0 ){
                    child1 = new ArrayList<>( nodo.child.subList( 0, h+1 ) );
                    child2 = new ArrayList<>( nodo.child.subList( h+1, m+1 ) );
                }
                else{
                    if( n < medio ){
                        child1 = new ArrayList<>( nodo.child.subList( 0, h+2 ) );
                        child2 = new ArrayList<>( nodo.child.subList( h+2, m+1 ) );
                    }
                    if( n > medio ){
                        child1 = new ArrayList<>( nodo.child.subList( 0, h+1 ) );
                        child2 = new ArrayList<>( nodo.child.subList( h+1, m+1 ) );
                    }  
                }
                
                nuevoNodo1.setChildren( child1 );
                nuevoNodo2.setChildren( child2 );
                for( BNode i : nuevoNodo1.child )
                    i.parent = nuevoNodo1;
                for( BNode i : nuevoNodo2.child )
                    i.parent = nuevoNodo2;
                
            }
                
            nodo.child.clear();
            nodo.child.add(nuevoNodo1);
            nodo.child.add(nuevoNodo2);
            nuevoNodo1.parent = nuevoNodo2.parent = root;
            nodo.leaf = false;
                        
        }
        else{
            
            BNode nuevoNodo = new BNode();
            nuevoNodo.leaf = nodo.leaf;
            nuevoNodo.parent = nodo.parent;

            int childIndex = nodo.getChildIndex();
            
            nodo.setKeys(key1);
            nuevoNodo.setKeys(key2);
            
            if( m % 2 == 0 ){
                if( n < medio )
                    insert(nodo, n);
                else
                    insert(nuevoNodo, n);
            }
            
            if( !nodo.leaf ){
                if( m % 2 != 0 ){
                    child1 = new ArrayList<>( nodo.child.subList( 0, h+1 ) );
                    child2 = new ArrayList<>( nodo.child.subList( h+1, m+1 ) );
                }
                else{
                    if( n < medio ){
                        child1 = new ArrayList<>(nodo.child.subList( 0, h+2 ));
                        child2 = new ArrayList<>(nodo.child.subList( h+2, m+1 ));
                    }
                    if( n > medio ){
                        child1 = new ArrayList<>( nodo.child.subList( 0, h+1 ) );
                        child2 = new ArrayList<>( nodo.child.subList( h+1, m+1 ) );
                    }   
                }
                nodo.setChildren( child1 );
                nuevoNodo.setChildren( child2 );
                for( BNode i : nodo.child )
                    i.parent = nodo;
                for( BNode i : nuevoNodo.child )
                    i.parent = nuevoNodo;
            } 
            
            
            nodo.parent.child.add( childIndex + 1, nuevoNodo );
            
            addToNode( nodo.parent, medio );
            
        }
        
    }
    
    public void mostrarArbol(){
        if(root.child.isEmpty() && root.key.isEmpty()){
            System.out.println("No hay elementos aun");
            return ;
        }
        Queue<BNode> nodos = new LinkedList<>();
        nodos.add(root);
        BNode parent=null;
        while( !nodos.isEmpty() ){
            
            BNode v = nodos.poll();
            if(v.parent==null){
                System.out.print("Nodo Raiz: ");
            }
            if(parent!=v.parent){
                System.out.print("\n\n\nNodo Padre: ");
                v.parent.mostrarLlaves();
                parent=v.parent;
                System.out.print("\n\t\tNodos:");
            }
            System.out.print("\n\t\t");
            v.mostrarLlaves();

            nodos.addAll(v.child);
        }
        System.out.println("\n");
    }
    
    public boolean find(int value){
        if(root.child.isEmpty() && root.key.isEmpty()){
            return false;
        }
        return find(value,root);
    }
    
    private boolean find(int v,BNode n){
        if(n==null){
            return false;
        }
        
        int i;
        
        if( n.getKey(0) > v )
            return find(v,n.getChild(0));
        
        for(i=0;i<n.key.size()-1;i++){
            
            if(n.getKey(i)==v){
                return true;
            }
            
            if(n.getKey(i)<v && n.getKey(i+1)>v){
                return find(v,n.getChild(i+1));
            }
            
        }
        
        if(n.getKey(i)==v){
            return true;
        }
        else{
            if( n.getKey(i) < v)
                return find(v,n.getChild(i+1));
            else
                return find(v,n.getChild(i));
        }
        
    }
    
    // Implementación de la función de eliminación de un valor en el árbol B.
    /*
    se deben de tener en cuenta la siguiente información:
    half full: (m-1)/2
    1. Si la clave a eliminar se encuentra en un nodo hoja, se elimina directamente.
    2. Si al realizar la eliminación, el nodo mantiene el mínimo número de claves, finaliza, en caso contrario se realiza la redistribución.
    3. Si el elemento no se encuentra en unahoja, se debe "subir" la clave que se encuentra más a la derecha del subárbol izquierdo o más a la izquierda del subárbol derecho.
    4. Si al subir la clave, el nodo mantiene el mínimo número de claves, finaliza, en caso contrario se realiza la redistribución.

    Redistribución:
    a) Si una hoja vecina inmediata tiene suficientes claves disponibles (préstamo):
        - la clave que se encuentre más a la izquierda "sube"
        - la clave del nodo padre "baja" a la hoja
    b) Si una hoja vecina inmediata no tiene suficientes claves disponibles (fusión):
        - la clave del nodo padre "baja" a la hoja
        - la hoja se fusiona con la hoja vecina
     */

    public void remove(int value) {
        if (root.key.isEmpty()) {
            System.out.println("El árbol está vacío. No se puede eliminar nada.");
            return;
        }
        remove(value, root);
    }

    private void remove(int value, BNode node) {
        int index = 0;

        while (index < node.key.size() && value > node.getKey(index)) {
            index++;
        }

        if (node.leaf) {
            // Caso 1: La clave a eliminar se encuentra en un nodo hoja.
            if (index < node.key.size() && value == node.getKey(index)) {
                node.key.remove(index);
            } else {
                System.out.println("La clave no se encuentra en el árbol.");
            }
        } else {
            // Caso 2: La clave a eliminar no se encuentra en un nodo hoja.
            if (index < node.key.size() && value == node.getKey(index)) {
                BNode pred = getPredecessor(node, index);
                int predKey = pred.getKey(pred.key.size() - 1);
                node.key.set(index, predKey);
                remove(predKey, pred);
            } else {
                remove(value, node.getChild(index));
            }
        }

        // Rebalance the tree if necessary
        if (node != root) {
            if (node.key.size() < m / 2) {
                BNode leftSibling = getLeftSibling(node);
                BNode rightSibling = getRightSibling(node);

                if (leftSibling != null && leftSibling.key.size() > m / 2) {
                    // Caso a: Prestamos una clave del hermano izquierdo.
                    borrowFromLeftSibling(node, leftSibling);
                } else if (rightSibling != null && rightSibling.key.size() > m / 2) {
                    // Caso a: Prestamos una clave del hermano derecho.
                    borrowFromRightSibling(node, rightSibling);
                } else if (leftSibling != null) {
                    // Caso b: Fusionamos con el hermano izquierdo.
                    mergeWithLeftSibling(node, leftSibling);
                } else if (rightSibling != null) {
                    // Caso b: Fusionamos con el hermano derecho.
                    mergeWithRightSibling(node, rightSibling);
                }
            }
        }
    }

    private BNode getPredecessor(BNode node, int index) {
        BNode current = node.getChild(index);
        while (!current.leaf) {
            current = current.getChild(current.key.size());
        }
        return current;
    }

    private BNode getLeftSibling(BNode node) {
        if (node.parent == null) {
            return null;
        }
        int index = node.parent.child.indexOf(node);
        if (index > 0) {
            return node.parent.child.get(index - 1);
        }
        return null;
    }

    private BNode getRightSibling(BNode node) {
        if (node.parent == null) {
            return null;
        }
        int index = node.parent.child.indexOf(node);
        if (index < node.parent.child.size() - 1) {
            return node.parent.child.get(index + 1);
        }
        return null;
    }

    private void borrowFromLeftSibling(BNode node, BNode leftSibling) {
        int borrowedKey = leftSibling.key.remove(leftSibling.key.size() - 1);
        node.key.add(0, borrowedKey);
        if (!leftSibling.leaf) {
            BNode borrowedChild = leftSibling.child.remove(leftSibling.child.size() - 1);
            node.child.add(0, borrowedChild);
            borrowedChild.parent = node;
        }
        node.parent.key.set(node.parent.child.indexOf(node) - 1, node.key.get(0));
    }

    private void borrowFromRightSibling(BNode node, BNode rightSibling) {
        int borrowedKey = rightSibling.key.remove(0);
        node.key.add(borrowedKey);
        if (!rightSibling.leaf) {
            BNode borrowedChild = rightSibling.child.remove(0);
            node.child.add(borrowedChild);
            borrowedChild.parent = node;
        }
        node.parent.key.set(node.parent.child.indexOf(node), rightSibling.key.get(0));
    }

    private void mergeWithLeftSibling(BNode node, BNode leftSibling) {
        int mergeKey = node.parent.key.remove(node.parent.child.indexOf(node) - 1);
        leftSibling.key.add(mergeKey);
        leftSibling.key.addAll(node.key);
        if (!node.leaf) {
            leftSibling.child.addAll(node.child);
            for (BNode child : node.child) {
                child.parent = leftSibling;
            }
        }
        node.parent.child.remove(node);
        if (node.parent == root && node.parent.key.isEmpty()) {
            root = leftSibling;
            leftSibling.parent = null;
        }
    }

    private void mergeWithRightSibling(BNode node, BNode rightSibling) {
        int mergeKey = node.parent.key.remove(node.parent.child.indexOf(node));
        rightSibling.key.add(0, mergeKey);
        rightSibling.key.addAll(0, node.key);
        if (!node.leaf) {
            rightSibling.child.addAll(0, node.child);
            for (BNode child : node.child) {
                child.parent = rightSibling;
            }
        }
        node.parent.child.remove(node);
        if (node.parent == root && node.parent.key.isEmpty()) {
            root = rightSibling;
            rightSibling.parent = null;
        }
    }

}
