public class Main {
    public static void main(String[] args) {
        // Crear el árbol AVL
        AVLTree<Integer> arbol = new AVLTree<>();

        // Insertar elementos en el árbol
        arbol.insertar(10);
        arbol.insertar(5);
        arbol.insertar(15);
        arbol.insertar(3);
        arbol.insertar(7);
        arbol.insertar(12);
        arbol.insertar(18);

        // Imprimir el árbol antes de realizar modificaciones
        System.out.println("Árbol AVL original:");
        arbol.imprimirArbol();

        // Verificar si un elemento existe en el árbol
        int elemento = 7;
        boolean existe = arbol.buscar(elemento);
        System.out.println("El elemento " + elemento + " existe en el árbol: " + existe);

        // Eliminar un elemento del árbol
        arbol.eliminar(7);
        System.out.println("Árbol AVL después de eliminar el elemento 7:");
        arbol.imprimirArbol();

        // Obtener la altura del árbol
        int altura = arbol.altura();
        System.out.println("Altura del árbol: " + altura);

        // Obtener el mínimo y máximo elemento del árbol
        int minimo = arbol.minimo();
        int maximo = arbol.maximo();
        System.out.println("Mínimo elemento: " + minimo);
        System.out.println("Máximo elemento: " + maximo);

        // Obtener el recorrido en orden del árbol (elementos ordenados)
        List<Integer> recorrido = arbol.recorridoEnOrden();
        System.out.println("Recorrido en orden del árbol:");
        for (int valor : recorrido) {
            System.out.print(valor + " ");
        }
        System.out.println();

        // Vaciar el árbol
        arbol.vaciar();
        System.out.println("Árbol AVL después de vaciarlo:");
        arbol.imprimirArbol();
    }
}

