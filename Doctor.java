public class Doctor extends Server {

    // time units are in minutes
    private static final int ON_BREAK_FREQUENCY = 300;
    private static final int ON_BREAK_DURATION = 15;
    private static final int BREAKDOWN_FREQUENCY = 600;
    private static final int BREAKDOWN_DURATION = 10;

    private long on_break_time_start = 0;
    private long breakdown_time_start = 0;
    private Equipment equipment;
    private boolean isSeniorDoctor;
    private boolean isBrokenDown = false;
    private boolean isOnBreak = false;

    public Doctor(String name, long hourly_wage, int max_number_of_users, boolean isSeniorDoctor, Equipment equipment) {
        super(name, hourly_wage, max_number_of_users);
        this.isSeniorDoctor = isSeniorDoctor;
        this.equipment = equipment;
    }

    @Override
    public boolean serve_user() {
        /**
         * Deal with current user being served, if empty pull from associated queue.
         * Uses equipment during, and when done with user the user is released from the system all together
         *
         * @return whether a user has been served
         */
        
        if (isUnderMaintenance()) {
            return false;
        }

        User next_usr = my_queue.get_next_user();
        if (next_usr == null) {
            isBusy = false;
            return false;
        }
        
        number_served += 1;
        current_user_being_served = next_usr;
        remaining_service_time = next_usr.getService_time();

        return true;
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

    public boolean isUnderMaintenance() {
        /**
         * Called between patients to check if maintenance is required
         * @return: whether the server is under maintenance
         */

        if (isBrokenDown) {
            if (curr_time >= breakdown_time_start + BREAKDOWN_DURATION) {
                isBrokenDown = false;
                breakdown_time_start += BREAKDOWN_DURATION + BREAKDOWN_FREQUENCY;
            }
        }
        else {
            if (curr_time >= breakdown_time_start) {
                isBrokenDown = true;
            }
        }

        if (isOnBreak) {
            if (curr_time >= on_break_time_start + ON_BREAK_DURATION) {
                isOnBreak = false;
                on_break_time_start += ON_BREAK_DURATION + ON_BREAK_FREQUENCY;
            }
        }
        else {
            if (curr_time >= on_break_time_start) {
                isOnBreak = true;
            }
        }
        isMaintenance = (isOnBreak || isBrokenDown);
        return (isOnBreak || isBrokenDown);
    }
}
