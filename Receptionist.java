import java.lang.Math;

public class Receptionist extends Server {

    private static final int MAX_DISCRIMINANT_DIFFERENCE = 30;

    private static final int JDOC_SERVICE_TIME_C1 = 395;
    private static final int JDOC_SERVICE_TIME_C2 = 403;
    private static final int JDOC_SERVICE_TIME_C3 = 200;
    private static final int JDOC_SERVICE_TIME_C4 = 66;
    private static final int JDOC_SERVICE_TIME_C5 = 37;


    private PatientQueue seniorDoc_queue;
    private PatientQueue juniorDoc_queue;

    public Receptionist(String name, long hourly_wage, int max_number_of_users, PatientQueue seniorDoc_queue, PatientQueue juniorDoc_queue) {
        super(name, hourly_wage, max_number_of_users);
        this.seniorDoc_queue = seniorDoc_queue;
        this.juniorDoc_queue = juniorDoc_queue;
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

        User next_usr = my_queue.get_next_user();
        if (next_usr == null) {
            isBusy = false;
            return false;
        }
        
        number_served += 1;
        current_user_being_served = next_usr;
        remaining_service_time = 2 * (5-next_usr.getSeverity()) + 3;

        if (assignJuniorDoctor(next_usr) == true) {
            next_usr.setService_time(calculateServiceTime(next_usr, false));
            juniorDoc_queue.add_user(next_usr);
        }
        else {
            next_usr.setService_time(calculateServiceTime(next_usr, true));
            seniorDoc_queue.add_user(next_usr);
        }

        return true;
    }

    private boolean assignJuniorDoctor(User usr) {
        if (Math.abs(seniorDoc_queue.getTotal_wait_time() - juniorDoc_queue.getTotal_wait_time()) <= MAX_DISCRIMINANT_DIFFERENCE) {
            if (usr.getSeverity() <= 3) {
                return false;
            }
            else {
                return true;
            }
        }
        else {
            if (seniorDoc_queue.getTotal_wait_time() <= juniorDoc_queue.getTotal_wait_time()) {
                return false;
            }
            else {
                return true;
            }
        }
    }

    private int calculateServiceTime(User usr, boolean isSeniorDoctor) {
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

    @Override
    public void run_server() {
        /**
         * Asynchronous method to be called, launches the receptionist into work, pulling from queue and serving if able.
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

        isMaintenance = false;
        return false;
    }
}
