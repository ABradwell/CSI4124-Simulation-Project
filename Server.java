public abstract class Server {

    protected int curr_time = -1;

    protected int time_busy = 0;
    protected int time_idle = -1;
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

    public int getTime_busy() {
        return time_busy;
    }

    public void setTime_busy(int time_busy) {
        this.time_busy = time_busy;
    }

    public int getTime_idle() {
        return time_idle;
    }

    public void setTime_idle(int time_idle) {
        this.time_idle = time_idle;
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

    public int getRemaining_service_time() {
        return remaining_service_time;
    }

    public void setRemaining_service_time(int remaining_service_time) {
        this.remaining_service_time = remaining_service_time;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean isBusy) {
        this.isBusy = isBusy;
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


    // Abstract since the doctors will serve using equipment, while the receptionist uses a determining equation
    public abstract boolean serve_user();

    // Method which launches a constant loop asynchronously running the servers inner workings (taking patients from linked queue)
    public abstract void run_server();
    
    public abstract boolean isUnderMaintenance();

    public abstract void stop_server();

    public void tick() {

        curr_time++;

        if (isMaintenance) {
            time_maintainence++;
        }
        if (isBusy) {
            time_busy++;
            remaining_service_time--;
            if (remaining_service_time == 0) {
                isBusy = serve_user();
            }
        } else {
            time_idle++;
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
