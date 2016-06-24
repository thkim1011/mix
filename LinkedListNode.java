public class LinkedListNode<T> {
    T myValue;
    LinkedListNode<T> myNode;

    public LinkedListNode(T value, LinkedListNode<T> node) {
        myValue = value;
        myNode = node;
    }

    public LinkedListNode<T> getNext() {
        return myNode;
    }

    public T getValue() {
        return myValue;
    }

    public void setValue(T value) {
        myValue = value;
    }

    public void setNext(LinkedListNode<T> next) {
        myNode = next;
    }
}