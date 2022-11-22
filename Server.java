public abstract class Server {

    protected int curr_time = 0;

    protected int time_busy = 0;
    protected int time_idle = 0;
    protected int time_maintainence = 0;
    protected int number_served = 0;
    protected User current_user_being_served = null;
    protected int remaining_service_time = 0;
    protected boolean isRunning = false;
    protected boolean isBusy = false;
    protected boolean isMaintenance = false;
    protected String name;
    protected long hourly_wage;
    protected PatientQueue my_queue;

    public Server(String name, long hourly_wage, int max_number_of_users) {
        this.name = name;
        this.hourly_wage = hourly_wage;
        this.my_queue = new PatientQueue(max_number_of_users);
    }

    public int getNumber_served() {
        return number_served;
    }

    public void setNumber_served(int number_served) {
        this.number_served = number_served;
    }

    public long getHourly_wage() {
        return hourly_wage;
    }

    public void setHourly_wage(long hourly_wage) {
        this.hourly_wage = hourly_wage;
    }

    public User getCurrent_user_being_served() {
        return current_user_being_served;
    }

    public void setCurrent_user_being_served(User current_user_being_served) {
        this.current_user_being_served = current_user_being_served;
    }

    public int getCurr_time() {
        return curr_time;
    }

    public void setcurr_time(int curr_time) {
        this.curr_time = curr_time;
    }

    public PatientQueue getMy_queue() {
        return my_queue;
    }

    public void setMy_queue(PatientQueue my_queue) {
        this.my_queue = my_queue;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    //    ---------------------------------------------------------------------------------

    public abstract boolean serve_user();
    
    public abstract boolean isUnderMaintenance();

    public void tick() {
        /**
         * This is the timing synchronization feature of the servers. Since 3 servers are
         * running in parallel, this method assures that each server steps forward at the same time.
         */

        curr_time++;



        if (isMaintenance) {
            time_maintainence++;
        }
        else if (isBusy) {
            time_busy++;
        }
        else {
            time_idle++;
        }

        if (isBusy) {
            remaining_service_time--;
        } 
        if (!isBusy || remaining_service_time < 0) {
            isBusy = serve_user();
        }
    }

    public String toString() {
        String msg = "";
        msg += String.format("Name: %s\n", name);
        msg += String.format("  Wage: %s\n", hourly_wage);
        msg += String.format("  Cust. Served: %s\n", number_served);
        msg += String.format("  Time Busy: %s\n", time_busy);
        msg += String.format("  Time Idle: %s\n", time_idle);
        msg += String.format("  Maintainence Time: %s\n", time_maintainence);
        
        return msg;
    }
}
