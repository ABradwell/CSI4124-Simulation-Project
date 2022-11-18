public class Receptionist extends Server {

    private Queue doc_one_queue;
    private Queue doc_two_queue;

    public Queue getDoc_one_queue() {
        return doc_one_queue;
    }

    public void setDoc_one_queue(Queue doc_one_queue) {
        this.doc_one_queue = doc_one_queue;
    }

    public Queue getDoc_two_queue() {
        return doc_two_queue;
    }

    public void setDoc_two_queue(Queue doc_two_queue) {
        this.doc_two_queue = doc_two_queue;
    }

    @Override
    public void serve_user() {
        /**
         * New patient arrives at receptionist desk. Receptionist determines which queue to send them to from here
         *
         * @param u: patient that is now arriving at the desk.
         */

    }

    @Override
    public void run_server() {
        /**
         * Asynchronous method to be called, launches the receptionist into work, pulling from queue and serving if able.
         */
    }

    @Override
    public void stop_server() {

    }

}
