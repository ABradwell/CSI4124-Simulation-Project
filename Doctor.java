import java.lang.Math;

public class Doctor extends Server {

    // time units are in minutes
    private static final int ON_BREAK_FREQUENCY = 300;
    private static final int ON_BREAK_DURATION = 15;
    private static final int BREAKDOWN_FREQUENCY = 600;
    private static final int BREAKDOWN_DURATION = 10;

    private static final int JDOC_SERVICE_TIME_C1 = (int) Math.round(395/3);
    private static final int JDOC_SERVICE_TIME_C2 = (int) Math.round(403/3);
    private static final int JDOC_SERVICE_TIME_C3 = (int) Math.round(200/3);
    private static final int JDOC_SERVICE_TIME_C4 = (int) Math.round(66/3);
    private static final int JDOC_SERVICE_TIME_C5 = (int) Math.round(37/3);

    private long on_break_time_start = 0;
    private long breakdown_time_start = 0;

    private PatientQueue other_queue;
    private PatientQueue exitPatients;
    private boolean isSeniorDoctor;
    private boolean isBrokenDown = false;
    private boolean isOnBreak = false;

    public Doctor(String name, long hourly_wage, int max_number_of_users, boolean isSeniorDoctor, PatientQueue exit_patients, PatientQueue other_queue) {
        super(name, hourly_wage, max_number_of_users);
        this.isSeniorDoctor = isSeniorDoctor;
        this.exitPatients = exit_patients;
        this.other_queue = other_queue;
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
            next_usr = other_queue.get_next_user();
            if (next_usr == null) {
                return false;
            } else {
                next_usr.setService_time(calculateServiceTime(next_usr));
            }
        }

        next_usr.setTimeToDoctor(this.curr_time);
        next_usr.start_service(this.curr_time);

        number_served += 1;
        current_user_being_served = next_usr;
        remaining_service_time = next_usr.getService_time();
        next_usr.setTimeInED(this.curr_time + remaining_service_time);
        
        exitPatients.add_user(next_usr);

        return true;
    }

    private int calculateServiceTime(User usr) {
        /**
         * This function calculates how long it will take to service the patient, based off
         * the expertise of their doctor chosen. Senior doctor works faster, but takes on more
         * severe cases
         *
         * @param usr: Current user being calculated:
         * @returns int: service time calculated
         */

        int serviceTime;
        if (usr.getSeverity() == 1) {
            serviceTime = JDOC_SERVICE_TIME_C1;
        }
        else if (usr.getSeverity() == 2) {
            serviceTime = JDOC_SERVICE_TIME_C2;
        }
        else if (usr.getSeverity() == 3) {
            serviceTime = JDOC_SERVICE_TIME_C3;
        }
        else if (usr.getSeverity() == 4) {
            serviceTime = JDOC_SERVICE_TIME_C4;
        }
        else if (usr.getSeverity() == 5) {
            serviceTime = JDOC_SERVICE_TIME_C5;
        }
        else {
            serviceTime = -1;
        }

        if (isSeniorDoctor) {
            serviceTime /= 2;
        }
        
        return serviceTime;
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
