import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DequeIteratorTest {

    private Deque<Integer> deque;

    @Before
    public void setUp() throws Exception {
        deque = new Deque<Integer>();

        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
    }

    @Test
    public void iteratesFromFrontToEnd() {
        StringBuilder sb = new StringBuilder();
        for (Integer i : deque) {
            sb.append(i);
        }
        assertThat(sb.toString(), is("321"));
    }

    @Test
    public void iteratorsAreIndependent() {
        Iterator<Integer> iteratorI = deque.iterator();
        Iterator<Integer> iteratorJ = deque.iterator();

        Integer i1 = iteratorI.next();
        assertThat(i1, is(3));
        Integer i2 = iteratorI.next();
        assertThat(i2, is(2));

        Integer j1 = iteratorJ.next();
        assertThat(j1, is(3));
        Integer j2 = iteratorJ.next();
        assertThat(j2, is(2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void removeIsUnsupported() {
        deque.iterator().remove();
    }

}
