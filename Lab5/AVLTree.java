import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

class AVLTree<T extends Comparable<T>> {
    Nodo<T> raiz;

    Nodo<T> searchNodo(Nodo<T> nodo, T valor) {
        if (nodo == null || valor.compareTo(nodo.valor) == 0)
            return nodo;

        if (valor.compareTo(nodo.valor) < 0)
            return searchNodo(nodo.izquierdo, valor);

        return searchNodo(nodo.derecho, valor);
    }

    boolean search(T valor) {
        Nodo<T> resultado = searchNodo(raiz, valor);
        return resultado != null;
    }

    Nodo<T> getNodoMin(Nodo<T> nodo) {
        Nodo<T> actual = nodo;

        while (actual.izquierdo != null)
            actual = actual.izquierdo;

        return actual;
    }

    T getMin() {
        Nodo<T> nodoMinimo = getNodoMin(raiz);
        return nodoMinimo.valor;
    }

    Nodo<T> getNodoMax(Nodo<T> nodo) {
        Nodo<T> actual = nodo;

        while (actual.derecho != null)
            actual = actual.derecho;

        return actual;
    }

    T getMax() {
        Nodo<T> nodoMaximo = getNodoMax(raiz);
        return nodoMaximo.valor;
    }

    Nodo<T> obtenerPadre(Nodo<T> nodo) {
        return nodo.padre;
    }

    Nodo<T> parent(Nodo<T> nodo) {
        return obtenerPadre(nodo);
    }

    Nodo<T> obtenerHijoIzquierdo(Nodo<T> nodo) {
        return nodo.izquierdo;
    }

    Nodo<T> leftSon(Nodo<T> nodo) {
        return obtenerHijoIzquierdo(nodo);
    }

    Nodo<T> obtenerHijoDerecho(Nodo<T> nodo) {
        return nodo.derecho;
    }

    Nodo<T> rightSon(Nodo<T> nodo) {
        return obtenerHijoDerecho(nodo);
    }

    Nodo<T> insertarNodo(Nodo<T> nodo, T valor) {
        if (nodo == null)
            return (new Nodo<>(valor));

        if (valor.compareTo(nodo.valor) < 0) {
            nodo.izquierdo = insertarNodo(nodo.izquierdo, valor);
            nodo.izquierdo.padre = nodo;
        } else if (valor.compareTo(nodo.valor) > 0) {
            nodo.derecho = insertarNodo(nodo.derecho, valor);
            nodo.derecho.padre = nodo;
        } else
            return nodo;

        nodo.altura = alturaMax(getAltura(nodo.izquierdo), getAltura(nodo.derecho)) + 1;

        int factorEquilibrio = obtenerFactorEquilibrio(nodo);

        if (factorEquilibrio > 1 && valor.compareTo(nodo.izquierdo.valor) < 0)
            return rotacionDerecha(nodo);

        if (factorEquilibrio < -1 && valor.compareTo(nodo.derecho.valor) > 0)
            return rotacionIzquierda(nodo);

        if (factorEquilibrio > 1 && valor.compareTo(nodo.izquierdo.valor) > 0) {
            nodo.izquierdo = rotacionIzquierda(nodo.izquierdo);
            return rotacionDerecha(nodo);
        }

        if (factorEquilibrio < -1 && valor.compareTo(nodo.derecho.valor) < 0) {
            nodo.derecho = rotacionDerecha(nodo.derecho);
            return rotacionIzquierda(nodo);
        }

        return nodo;
    }

    void insertar(T valor) {
        raiz = insertarNodo(raiz, valor);
    }

    Nodo<T> eliminarNodo(Nodo<T> nodo, T valor) {
        if (nodo == null)
            return nodo;

        if (valor.compareTo(nodo.valor) < 0)
            nodo.izquierdo = eliminarNodo(nodo.izquierdo, valor);
        else if (valor.compareTo(nodo.valor) > 0)
            nodo.derecho = eliminarNodo(nodo.derecho, valor);
        else {
            if ((nodo.izquierdo == null) || (nodo.derecho == null)) {
                Nodo<T> temp;
                if (nodo.izquierdo != null)
                    temp = nodo.izquierdo;
                else
                    temp = nodo.derecho;

                if (temp == null) {
                    temp = nodo;
                    nodo = null;
                } else
                    nodo = temp;
            } else {
                Nodo<T> temp = getNodoMin(nodo.derecho);
                nodo.valor = temp.valor;
                nodo.derecho = eliminarNodo(nodo.derecho, temp.valor);
            }
        }

        if (nodo == null)
            return nodo;

        nodo.altura = alturaMax(getAltura(nodo.izquierdo), getAltura(nodo.derecho)) + 1;

        int factorEquilibrio = obtenerFactorEquilibrio(nodo);

        if (factorEquilibrio > 1 && obtenerFactorEquilibrio(nodo.izquierdo) >= 0)
            return rotacionDerecha(nodo);

        if (factorEquilibrio > 1 && obtenerFactorEquilibrio(nodo.izquierdo) < 0) {
            nodo.izquierdo = rotacionIzquierda(nodo.izquierdo);
            return rotacionDerecha(nodo);
        }

        if (factorEquilibrio < -1 && obtenerFactorEquilibrio(nodo.derecho) <= 0)
            return rotacionIzquierda(nodo);

        if (factorEquilibrio < -1 && obtenerFactorEquilibrio(nodo.derecho) > 0) {
            nodo.derecho = rotacionDerecha(nodo.derecho);
            return rotacionIzquierda(nodo);
        }

        return nodo;
    }

    void eliminar(T valor) {
        raiz = eliminarNodo(raiz, valor);
    }

    int getAltura(Nodo<T> nodo) {
        if (nodo == null)
            return 0;

        return nodo.altura;
    }

    int alturaMax(int a, int b) {
        return Math.max(a, b);
    }

    int obtenerFactorEquilibrio(Nodo<T> nodo) {
        if (nodo == null)
            return 0;

        return getAltura(nodo.izquierdo) - getAltura(nodo.derecho);
    }

    Nodo<T> rotacionDerecha(Nodo<T> nodo) {
        Nodo<T> nodoIzquierdo = nodo.izquierdo;
        Nodo<T> nodoDerecho = nodoIzquierdo.derecho;

        nodoIzquierdo.derecho = nodo;
        nodo.izquierdo = nodoDerecho;

        nodo.altura = alturaMax(getAltura(nodo.izquierdo), getAltura(nodo.derecho)) + 1;
        nodoIzquierdo.altura = alturaMax(getAltura(nodoIzquierdo.izquierdo), getAltura(nodoIzquierdo.derecho)) + 1;

        return nodoIzquierdo;
    }

    Nodo<T> rotacionIzquierda(Nodo<T> nodo) {
        Nodo<T> nodoDerecho = nodo.derecho;
        Nodo<T> nodoIzquierdo = nodoDerecho.izquierdo;

        nodoDerecho.izquierdo = nodo;
        nodo.derecho = nodoIzquierdo;

        nodo.altura = alturaMax(getAltura(nodo.izquierdo), getAltura(nodo.derecho)) + 1;
        nodoDerecho.altura = alturaMax(getAltura(nodoDerecho.izquierdo), getAltura(nodoDerecho.derecho)) + 1;

        return nodoDerecho;
    }
}

class Nodo<T extends Comparable<T>> {
    T valor;
    Nodo<T> izquierdo;
    Nodo<T> derecho;
    Nodo<T> padre;
    int altura;

    Nodo(T valor) {
        this.valor = valor;
        altura = 1;
    }
}

public class Main {
    public static void main(String[] args) {
        AVLTree<Integer> arbol = new AVLTree<>();

        arbol.insertar(10);
        arbol.insertar(20);
        arbol.insertar(30);
        arbol.insertar(40);
        arbol.insertar(50);
        arbol.insertar(25);

        System.out.println("El árbol AVL contiene el valor 30: " + arbol.search(30));
        System.out.println("El mínimo valor del árbol AVL es: " + arbol.getMin());
        System.out.println("El máximo valor del árbol AVL es: " + arbol.getMax());

        arbol.eliminar(40);

        System.out.println("El árbol AVL contiene el valor 40 después de eliminarlo: " + arbol.search(40));
    }
}

