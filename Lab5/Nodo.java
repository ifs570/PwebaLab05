class Nodo<T> {
    T valor;
    int altura;
    Nodo<T> izquierdo;
    Nodo<T> derecho;
    Nodo<T> padre;

    Nodo(T valor) {
        this.valor = valor;
        this.altura = 1;
    }
}
