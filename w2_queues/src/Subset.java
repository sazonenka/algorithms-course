/*----------------------------------------------------------------
 *  Author:        Aliaksandr Sazonenka
 *  Written:       9/1/2012
 *  Last updated:  9/1/2012
 *
 *  Client program for RandomizedQueue.
 *
 *  % echo A B C D E F G H I | java Subset 3
 *  C
 *  G
 *  A
 *
 *----------------------------------------------------------------*/

public class Subset {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Provide k as a first param.");
            return;
        }
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (true) {
            if (!StdIn.isEmpty()) {
                String nToken = StdIn.readString();
                queue.enqueue(nToken);
            } else {
                break;
            }
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(queue.dequeue());
        }
    }

}
