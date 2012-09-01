import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DequeTest {

    private Deque<Integer> deque;

    @Before
    public void setUp() throws Exception {
        deque = new Deque<Integer>();
    }

    @Test
    public void addFirstAndRemoveLast() {
        deque.addFirst(1);
        assertThat(deque.isEmpty(), is(false));
        assertThat(deque.size(), is(1));

        deque.addFirst(2);
        assertThat(deque.isEmpty(), is(false));
        assertThat(deque.size(), is(2));

        deque.addFirst(3);
        assertThat(deque.isEmpty(), is(false));
        assertThat(deque.size(), is(3));

        Integer tail = deque.removeLast();
        assertThat(tail, is(1));
        assertThat(deque.isEmpty(), is(false));
        assertThat(deque.size(), is(2));

        tail = deque.removeLast();
        assertThat(tail, is(2));
        assertThat(deque.isEmpty(), is(false));
        assertThat(deque.size(), is(1));

        tail = deque.removeLast();
        assertThat(tail, is(3));
        assertThat(deque.isEmpty(), is(true));
        assertThat(deque.size(), is(0));
    }

    @Test
    public void addLastAndRemoveFirst() {
        deque.addLast(1);
        assertThat(deque.isEmpty(), is(false));
        assertThat(deque.size(), is(1));

        deque.addLast(2);
        assertThat(deque.isEmpty(), is(false));
        assertThat(deque.size(), is(2));

        deque.addLast(3);
        assertThat(deque.isEmpty(), is(false));
        assertThat(deque.size(), is(3));

        Integer first = deque.removeFirst();
        assertThat(first, is(1));
        assertThat(deque.isEmpty(), is(false));
        assertThat(deque.size(), is(2));

        first = deque.removeFirst();
        assertThat(first, is(2));
        assertThat(deque.isEmpty(), is(false));
        assertThat(deque.size(), is(1));

        first = deque.removeFirst();
        assertThat(first, is(3));
        assertThat(deque.isEmpty(), is(true));
        assertThat(deque.size(), is(0));
    }

    @Test
    public void emptyDeque() {
        assertThat(deque.isEmpty(), is(true));
        assertThat(deque.size(), is(0));
    }

    @Test(expected = NoSuchElementException.class)
    public void removeFirstFromEmptyDeque() {
        deque.removeFirst();
    }

    @Test(expected = NoSuchElementException.class)
    public void removeLastFromEmptyDeque() {
        deque.removeLast();
    }

    @Test(expected = NullPointerException.class)
    public void addFirstNull() {
        deque.addFirst(null);
    }

    @Test(expected = NullPointerException.class)
    public void addLastNull() {
        deque.addLast(null);
    }

}
