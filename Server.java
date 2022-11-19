public abstract class Server {

    private int cur_time;

    private int time_busy;
    private int time_idle;
    private int number_served;
    private long hourly_wage;
    private User current_user_being_served;
    private int time_serving_current_user;
    private boolean running;
    private boolean busy;

    private PatientQueue linked_queue;

    public Server() {

    }

    public Server(int time_busy, int time_idle, int number_served, long hourly_wage, User current_user_being_served, boolean running, boolean busy, PatientQueue linked_queue, int time_serving_current_user) {
        this.time_busy = time_busy;
        this.time_idle = time_idle;
        this.number_served = number_served;
        this.hourly_wage = hourly_wage;
        this.current_user_being_served = current_user_being_served;
        this.time_serving_current_user = time_serving_current_user;
        this.running = running;
        this.busy = busy;
        this.linked_queue = linked_queue;
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

    public int getCur_time() {
        return cur_time;
    }

    public void setCur_time(int cur_time) {
        this.cur_time = cur_time;
    }

    public int getTime_serving_current_user() {
        return time_serving_current_user;
    }

    public void setTime_serving_current_user(int time_serving_current_user) {
        this.time_serving_current_user = time_serving_current_user;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public PatientQueue getLinked_queue() {
        return linked_queue;
    }

    public void setLinked_queue(PatientQueue linked_queue) {
        this.linked_queue = linked_queue;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    //    ---------------------------------------------------------------------------------


    // Abstract since the doctors will serve using equipment, while the receptionist uses a determining equation
    public abstract void serve_user();

    // Method which launches a constant loop asynchronously running the servers inner workings (taking patients from linked queue)
    public abstract void run_server();

    public abstract void stop_server();

    public void tick() {

        cur_time++;

        if (busy) {
            time_busy++;
        } else {
            time_idle++;
        }

        time_serving_current_user++;
    }

}
