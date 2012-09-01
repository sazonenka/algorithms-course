import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class RandomizedQueueTest {

    private RandomizedQueue<Integer> queue;

    @Before
    public void setUp() throws Exception {
        queue = new RandomizedQueue<Integer>();
    }

    @Test
    public void enqueue1AndDequeue1() {
        queue.enqueue(1);
        assertThat(queue.isEmpty(), is(false));
        assertThat(queue.size(), is(1));

        Integer item = queue.dequeue();
        assertThat(item, is(1));
        assertThat(queue.isEmpty(), is(true));
        assertThat(queue.size(), is(0));

        queue.enqueue(1);
        assertThat(queue.isEmpty(), is(false));
        assertThat(queue.size(), is(1));

        item = queue.dequeue();
        assertThat(item, is(1));
        assertThat(queue.isEmpty(), is(true));
        assertThat(queue.size(), is(0));
    }

    @Test
    public void enqueue5AndDequeue5() {
        queue.enqueue(1);
        assertThat(queue.isEmpty(), is(false));
        assertThat(queue.size(), is(1));

        queue.enqueue(2);
        assertThat(queue.isEmpty(), is(false));
        assertThat(queue.size(), is(2));

        queue.enqueue(3);
        assertThat(queue.isEmpty(), is(false));
        assertThat(queue.size(), is(3));

        queue.enqueue(4);
        assertThat(queue.isEmpty(), is(false));
        assertThat(queue.size(), is(4));

        queue.enqueue(5);
        assertThat(queue.isEmpty(), is(false));
        assertThat(queue.size(), is(5));

        Integer randomItem = queue.dequeue();
        assertThat(queue.isEmpty(), is(false));
        assertThat(queue.size(), is(4));

        randomItem = queue.dequeue();
        assertThat(queue.isEmpty(), is(false));
        assertThat(queue.size(), is(3));

        randomItem = queue.dequeue();
        assertThat(queue.isEmpty(), is(false));
        assertThat(queue.size(), is(2));

        randomItem = queue.dequeue();
        assertThat(queue.isEmpty(), is(false));
        assertThat(queue.size(), is(1));

        randomItem = queue.dequeue();
        assertThat(queue.isEmpty(), is(true));
        assertThat(queue.size(), is(0));
    }

    @Test
    public void emptyQueue() {
        assertThat(queue.isEmpty(), is(true));
        assertThat(queue.size(), is(0));
    }

    @Test(expected = NoSuchElementException.class)
    public void dequeueFromEmptyQueue() {
        queue.dequeue();
    }

    @Test(expected = NoSuchElementException.class)
    public void sampleFromEmptyQueue() {
        queue.sample();
    }

    @Test(expected = NullPointerException.class)
    public void enqueueNull() {
        queue.enqueue(null);
    }

}
