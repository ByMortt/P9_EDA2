package practica_9;
import java.util.Scanner;
public class Menu {
    //crear método de menú para el usuario que permita:
    /*
    1. Crear un árbol B con un valor de m ingresado por el usuario.
    2. Insertar un valor en el árbol B.
    3. Buscar un valor en el árbol B.
    4. Imprimir el árbol B.
    5. Eliminar un valor del árbol.
     */
    public void menu(){

        BTree arbol = new BTree();
        int opcion;
        do {
            System.out.println("1. Crear un árbol B con un valor de m ingresado por el usuario.");
            System.out.println("2. Insertar un valor en el árbol B.");
            System.out.println("3. Buscar un valor en el árbol B.");
            System.out.println("4. Imprimir el árbol B.");
            System.out.println("5. Eliminar un valor del árbol.");
            System.out.println("6. Salir.");
            System.out.println("Ingrese una opción: ");
            opcion = new Scanner(System.in).nextInt();
            switch (opcion){
                case 1:
                    System.out.println("Ingrese el valor de m: ");
                    int m = new Scanner(System.in).nextInt();
                    arbol = new BTree(m);
                    break;
                case 2:
                    System.out.println("Ingrese el valor a insertar: ");
                    int n = new Scanner(System.in).nextInt();
                    arbol.add(n);
                    break;
                case 3:
                    System.out.println("Ingrese el valor a buscar: ");
                    int n2 = new Scanner(System.in).nextInt();
                    if(arbol.find(n2)){
                        System.out.println("El valor "+n2+" se encuentra en el árbol.");
                    }
                    else{
                        System.out.println("El valor "+n2+" no se encuentra en el árbol.");
                    }
                    break;
                case 4:
                    arbol.mostrarArbol();
                    break;
                case 5:
                    System.out.println("Ingrese el valor a eliminar: ");
                    int n3 = new Scanner(System.in).nextInt();
                    arbol.remove(n3);
                    break;
                case 6:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }while(opcion != 6);
    }

}
