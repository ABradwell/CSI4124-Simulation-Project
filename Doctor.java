public class Doctor extends Server {

    private Equipment equipment;
    private long time_to_break;
    private boolean senior_doctor;
    private boolean waiting_for_break;

    @Override
    public void serve_user() {
        /**
         * Deal with current user being served, if empty pull from associated queue.
         * Uses equipment during, and when done with user the user is released from the system all together
         *
         */
    }

    @Override
    public void run_server() {
        /**
         * Asynchronous method to be called, launches the doctor into work, pulling from queue and serving if able.
         */
    }

    @Override
    public void stop_server() {

    }

    public long maintenance() {
        /**
         * Called between patients, and if waiting_for_break then Doctor goes on break
         * @return: long, length of time until break is over. if 0, break not needed.
         */

        return 0;
    }
}
