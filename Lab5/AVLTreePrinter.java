import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.*;


public class AVLTreePrinter {
    private AVLTree<Integer> arbol;
    private Graph graph;

    public AVLTreePrinter(AVLTree<Integer> arbol) {
        this.arbol = arbol;
        this.graph = new SingleGraph("Árbol AVL");
        System.setProperty("org.graphstream.ui", "swing");
    }

    private void agregarNodo(Nodo<Integer> nodo, Node padreNode) {
        Node nodoActual = graph.addNode(String.valueOf((char) nodo.valor.intValue())); //creando nodo actual a trabajar
        //Convirtiendo los valores ascii a valores char
        nodoActual.setAttribute("ui.label", Character.toString((char) nodo.valor.intValue()));

        // Modificar el tamaño de la etiqueta
        nodoActual.setAttribute("ui.style", "text-size: 50px;");
        
        //nodoActual.setAttribute("layout.weight",0);
        //Si el nodo actual sera padre
        if (padreNode != null) {
            graph.addEdge(padreNode.getId() + nodoActual.getId(), padreNode.getId(), nodoActual.getId());
            nodoActual.setAttribute("xy",0.0,-1.0); //posicion xy
            nodoActual.setAttribute("layout.frozen");//estabilidad en el grafico 
        }
        //Si el nodo actual sera hizo izquierdo
        if (nodo.izquierdo != null) {
            agregarNodo(nodo.izquierdo, nodoActual);
            nodoActual.setAttribute("xy",1.0,0.0);
            nodoActual.setAttribute("layout.frozen");
        }
        //Si el nodo actual sera hijo derecho
        if (nodo.derecho != null) {
            agregarNodo(nodo.derecho, nodoActual);
            nodoActual.setAttribute("xy",-1.0,0.0);
            nodoActual.setAttribute("layout.frozen");
        }
    }
    public void imprimirArbol() {
        agregarNodo(arbol.raiz, null);
        Viewer viewer =graph.display();
        // Colorear el nodo raíz
        Node nodoRaiz = graph.getNode(0);
        nodoRaiz.setAttribute("ui.style", "fill-color: red;");
        nodoRaiz.setAttribute("layout.frozen");
        graph.setAttribute("layout.force",1);                  
        viewer.disableAutoLayout();
    }
}
