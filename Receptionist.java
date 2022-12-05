import java.lang.Math;

public class Receptionist extends Server {

    private static final int JDOC_SERVICE_TIME_C1 = (int) Math.round(395/3);
    private static final int JDOC_SERVICE_TIME_C2 = (int) Math.round(403/3);
    private static final int JDOC_SERVICE_TIME_C3 = (int) Math.round(200/3);
    private static final int JDOC_SERVICE_TIME_C4 = (int) Math.round(66/3);
    private static final int JDOC_SERVICE_TIME_C5 = (int) Math.round(37/3);


    private PatientQueue seniorDoc_queue;
    private PatientQueue juniorDoc_queue;

    private boolean queues_both_full;
    private int ctas3_junior_doc = 1;

    public Receptionist(String name, long hourly_wage, int max_number_of_users, PatientQueue seniorDoc_queue, PatientQueue juniorDoc_queue) {
        super(name, hourly_wage, max_number_of_users);
        this.seniorDoc_queue = seniorDoc_queue;
        this.juniorDoc_queue = juniorDoc_queue;
        this.queues_both_full = false;
    }

    public PatientQueue getSeniorDoc_queue() {
        return seniorDoc_queue;
    }

    public void setSeniorDoc_queue(PatientQueue seniorDoc_queue) {
        this.seniorDoc_queue = seniorDoc_queue;
    }

    public PatientQueue getJuniorDoc_queue() {
        return juniorDoc_queue;
    }

    public void setJuniorDoc_queue(PatientQueue juniorDoc_queue) {
        this.juniorDoc_queue = juniorDoc_queue;
    }

    @Override
    public boolean serve_user() {
        /**
         * New patient arrives at receptionist desk. Receptionist determines which queue to send them to from here
         *
         * @return whether a user has been served
         */

        if (isUnderMaintenance()) {
            return false;
        }

//        queues_both_full = !juniorDoc_queue.room_for_more_patients() && !seniorDoc_queue.room_for_more_patients();

//        if () {
//            System.out.println("Both Queue full!");
//        }
//        else if (!seniorDoc_queue.room_for_more_patients()) {
//            System.out.println("Senior Queue full!");
//
//        }
//        else if (!juniorDoc_queue.room_for_more_patients()) {
//            System.out.println("Junior queue full!");
//
//        }

        if(!seniorDoc_queue.room_for_more_patients() && !juniorDoc_queue.room_for_more_patients()) {
            queues_both_full = true;
            System.out.println("Both queues full!");
            remaining_service_time = 1;
            return false;
        }


        User next_usr = my_queue.get_next_user();

        if (next_usr == null) {
            return false;
        }


        next_usr.start_service(this.curr_time);

        number_served += 1;
        current_user_being_served = next_usr;
        remaining_service_time = 2 * (next_usr.getSeverity()) + 1;
        next_usr.start_waiting(this.curr_time + remaining_service_time);

        if (assignJuniorDoctor(next_usr) && juniorDoc_queue.room_for_more_patients() || !seniorDoc_queue.room_for_more_patients()) {
            next_usr.setService_time(calculateServiceTime(next_usr, false));
            next_usr.setPriority(calculatePriority(next_usr));
            juniorDoc_queue.add_user(next_usr);
        }
        else if (seniorDoc_queue.room_for_more_patients()) {
            next_usr.setService_time(calculateServiceTime(next_usr, true));
            next_usr.setPriority(calculatePriority(next_usr));
            seniorDoc_queue.add_user(next_usr);
        } else {
            queues_both_full = true;
            System.out.println("SOMETHING FAILED");
            return false;
        }

        return true;
    }

    private boolean assignJuniorDoctor(User usr) {
        /**
         * Discriminating function, where the receptionist determines if the junior doctor should be chosen.
         * Either there are too many people waiting for the senior queue,
         * or the total wait time for one line is significantly greater than another
         *
         * @param usr: Current user being served by the receptionist.
         */

        if (usr.getSeverity() <= 2) {
            return false;
        }
        else if (usr.getSeverity() == 3) {
            switch(ctas3_junior_doc) {  
              case 1:
                ctas3_junior_doc++;
                return false;
              case 2:
                ctas3_junior_doc++;
                return true;
              case 3:
                ctas3_junior_doc = 1;
                return true;
              default:
                return false;
            }
        }
        else {
            return true;
        }
    }

    private int calculateServiceTime(User usr, boolean isSeniorDoctor) {
        /**
         * This function calculates how long it will take to service the patient, based off
         * the expertise of their doctor chosen. Senior doctor works faster, but takes on more
         * severe cases
         *
         * @param usr: Current user being calculated:
         * @param isSeniorDoctor: used to determine how long it will take to service
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

    private int calculatePriority(User usr) {
        int priority;
        if (usr.getSeverity() == 1) {
            priority = 1;
        }
        else if (usr.getSeverity() == 2) {
            priority = 81+curr_time;
        }
        else if (usr.getSeverity() == 3) {
            priority = 151+curr_time;
        }
        else if (usr.getSeverity() == 4) {
            priority = 158+curr_time;
        }
        else if (usr.getSeverity() == 5) {
            priority = 146+curr_time;
        }
        else {
            priority = -1;
        }
        return priority;
        // if (usr.getSeverity() == 1) {
        //     priority_adjust = 24;
        // }
        // else if (usr.getSeverity() == 2) {
        //     priority_adjust = 81;
        // }
        // else if (usr.getSeverity() == 3) {
        //     priority_adjust = 151;
        // }
        // else if (usr.getSeverity() == 4) {
        //     priority_adjust = 158;
        // }
        // else if (usr.getSeverity() == 5) {
        //     priority_adjust = 146;
        // }
        // else {
        //     priority_adjust = -1;
        // }

        // return Math.min(usr.getSeverity()-1, 1)*(priority_adjust+curr_time);
    }


    public boolean isUnderMaintenance() {
        /**
         * Called between patients to check if maintenance is required
         * In our simulation the receptionist does not go on break.
         *
         * @return: whether the server is under maintenance
         */

        isMaintenance = false;
        return false;
    }
}
