/**
 * Created by Raymond on 02.11.2018.
 */
public class Node {
    private final String name;
    private Node next;
    private Node parent;

    public Node() {
        this.name = null;
    }

    public Node(final String name) {
        this.name = name;
    }

    public Node(final String name, final Node parent) {
        this.name = name;
        this.parent = parent;
    }

    public Node getParent() {
        return this.parent;
    }

    public void setParent(final Node parent) {
        this.parent = parent;
    }

    public String getName() {
        return this.name;
    }

    public void setNext(final Node next) {
        this.next = next;
    }

    public Node getNext() {
        return this.next;
    }
}