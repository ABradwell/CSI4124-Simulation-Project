public class Doctor extends Server {

    // time units are in minutes
    private static final int ON_BREAK_FREQUENCY = 300;
    private static final int ON_BREAK_DURATION = 15;
    private static final int BREAKDOWN_FREQUENCY = 600;
    private static final int BREAKDOWN_DURATION = 10;


    private long on_break_time_start = 0;
    private long breakdown_time_start = 0;

    private PatientQueue exitPatients;
    private boolean isSeniorDoctor;
    private boolean isBrokenDown = false;
    private boolean isOnBreak = false;

    public Doctor(String name, long hourly_wage, int max_number_of_users, boolean isSeniorDoctor, PatientQueue exit_patients) {
        super(name, hourly_wage, max_number_of_users);
        this.isSeniorDoctor = isSeniorDoctor;
        this.exitPatients = exit_patients;
//        this.equipment = equipment;
    }

    @Override
    public boolean serve_user() {
        /**
         * New user has been admitted to the waiting room. The doctor may be on
         * break, or the machines may be down. Stats for current doctor updated,
         * and new patient begins being serviced if available.
         *
         * @returns: boolean if new user is accepted and valid
         */
        
        if (isUnderMaintenance()) {
            return false;
        }

        User next_usr = my_queue.get_next_user();
        if (next_usr == null) {
            return false;
        }

        next_usr.start_service(this.curr_time);

        number_served += 1;
        current_user_being_served = next_usr;
        remaining_service_time = next_usr.getService_time();
        next_usr.start_waiting(this.curr_time + remaining_service_time);
        
        exitPatients.add_user(next_usr);

        return true;
    }

    public boolean isUnderMaintenance() {
        /**
         * Checks if a break is required, or if the doctor's equipment is scheduled
         * for a breakdown.
         *
         * @return: boolean if either the machine or doctor are broken down
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
