/*----------------------------------------------------------------
 *  Author:        Aliaksandr Sazonenka
 *  Written:       9/1/2012
 *  Last updated:  9/1/2012
 *
 *  Models a double-ended queue that supports inserting and removing items
 *  from either the front or the back of the data structure.
 *
 *----------------------------------------------------------------*/

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node<Item> head; // The front node of the queue
    private Node<Item> tail; // The end node of the queue

    private int size; // The size of the queue

    /**
     * Creates the deque object
     */
    public Deque() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Inserts the item at the front of the queue.
     *
     * @param item the item to add
     * @throws NullPointerException if the item is null
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        Node<Item> newNode = new Node<Item>();
        newNode.setItem(item);
        newNode.setNext(head);
        newNode.setPrevious(null);

        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            head.setPrevious(newNode);
            head = newNode;
        }
        size++;
    }

    /**
     * Inserts the item at the end of the queue.
     *
     * @param item the item to add
     * @throws NullPointerException if the item is null
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        Node<Item> newNode = new Node<Item>();
        newNode.setItem(item);
        newNode.setNext(null);
        newNode.setPrevious(tail);

        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }

    /**
     * Deletes and returns the item at the front of the queue.
     *
     * @return the item at the front
     * @throws NoSuchElementException if the queue is empty
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = head.getItem();

        size--;
        if (isEmpty()) {
            head = null;
            tail = null;
        } else {
            head.getNext().setPrevious(null);
            head = head.getNext();
        }

        return item;
    }

    /**
     * Deletes and returns the item at the end of the queue.
     *
     * @return the item at the end
     * @throws NoSuchElementException if the queue is empty
     */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = tail.getItem();

        size--;
        if (isEmpty()) {
            head = null;
            tail = null;
        } else {
            tail.getPrevious().setNext(null);
            tail = tail.getPrevious();
        }

        return item;
    }

    /**
     * Checks if the queue is empty.
     *
     * @return true if the queue is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of items in the queue.
     *
     * @return the number of items in the queue.
     */
    public int size() {
        return size;
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    /**
     * An iterator over items of the queue in order from front to end.
     */
    private class DequeIterator implements Iterator<Item> {
        private Node<Item> current = head; // The current node of traversal

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (current == null) {
                throw new NoSuchElementException();
            }

            Item item = current.getItem();
            current = current.getNext();
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Models a node in a linked list.
     *
     * @param <Item> element's type
     */
    private static class Node<Item> {
        private Item item; // The payload element
        private Node<Item> next; // The pointer to the next node
        private Node<Item> previous; // The pointer to the previous node

        /** Returns the payload element. */
        public Item getItem() { return item; }
        /** Sets the payload element. */
        public void setItem(Item newItem) { this.item = newItem; }

        /** Returns the pointer to the next node */
        public Node<Item> getNext() { return next; }
        /** Sets the pointer to the next node */
        public void setNext(Node<Item> newNext) { this.next = newNext; }

        /** Returns the pointer to the previous node */
        public Node<Item> getPrevious() { return previous; }
        /** Sets the pointer to the previous node */
        public void setPrevious(Node<Item> newPrevious) {
            this.previous = newPrevious;
        }
    }

}
