/*----------------------------------------------------------------
 *  Author:        Aliaksandr Sazonenka
 *  Written:       9/1/2012
 *  Last updated:  9/1/2012
 *
 *  Models a randomized queue which is similar to a queue, except
 *  that the item removed is chosen uniformly at random from items
 *  in the data structure.
 *
 *----------------------------------------------------------------*/

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] values; // The resizing array to keep the queue
    private int size; // The number of elements in the queue

    /**
     * Creates the randomized queue object
     */
    public RandomizedQueue() {
        this.values = (Item[]) new Object[1];
        this.size = 0;
    }

    /**
     * Adds the item to the end of the queue.
     *
     * @param item the item to add
     * @throws NullPointerException if the item is null
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        if (size == values.length) {
            resizeValuesArray(values.length * 2);
        }
        values[size++] = item;
    }

    /**
     * Deletes and returns a random item from the queue.
     *
     * @return a random item from the queue
     * @throws NoSuchElementException if the queue is empty
     */
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int index = StdRandom.uniform(size);
        Item item = values[index];

        values[index] = values[size - 1];

        values[size - 1] = null;
        size--;

        if (size > 0 && size <= values.length / 4) {
            resizeValuesArray(values.length / 2);
        }

        return item;
    }

    /**
     * Returns (but does not delete) a random item from the queue.
     *
     * @return a random item from the queue
     * @throws NoSuchElementException if the queue is empty
     */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int index = StdRandom.uniform(size);
        Item item = values[index];
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

    private void resizeValuesArray(int newSize) {
        Item[] newValues = (Item[]) new Object[newSize];
        System.arraycopy(values, 0, newValues, 0, size);

        values = newValues;
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    /**
     * An independent iterator over items in random order.
     */
    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] iteratorValues;
        private int marker;

        public RandomizedQueueIterator() {
            this.iteratorValues = (Item[]) new Object[size];
            System.arraycopy(values, 0, this.iteratorValues, 0, size);
            StdRandom.shuffle(this.iteratorValues);

            this.marker = 0;
        }

        @Override
        public boolean hasNext() {
            return marker < iteratorValues.length;
        }

        @Override
        public Item next() {
            try {
                return iteratorValues[marker++];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}
