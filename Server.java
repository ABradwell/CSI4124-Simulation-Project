public abstract class Server {

    private long time_busy;
    private long time_idle;
    private int number_served;
    private long hourly_wage;
    private User current_user_being_served;

    private boolean busy;

    private Queue linked_queue;

    public Server() {
    }

    public Server(long time_busy, long time_idle, int number_served, long hourly_wage, User current_user_being_served, boolean busy, Queue linked_queue) {
        this.time_busy = time_busy;
        this.time_idle = time_idle;
        this.number_served = number_served;
        this.hourly_wage = hourly_wage;
        this.current_user_being_served = current_user_being_served;
        this.busy = busy;
        this.linked_queue = linked_queue;
    }

    public long getTime_busy() {
        return time_busy;
    }

    public void setTime_busy(long time_busy) {
        this.time_busy = time_busy;
    }

    public long getTime_idle() {
        return time_idle;
    }

    public void setTime_idle(long time_idle) {
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

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public Queue getLinked_queue() {
        return linked_queue;
    }

    public void setLinked_queue(Queue linked_queue) {
        this.linked_queue = linked_queue;
    }

    //    ---------------------------------------------------------------------------------


    // Abstract since the doctors will serve using equipment, while the receptionist uses a determining equation
    public abstract void serve_user();

    // Method which launches a constant loop asynchronously running the servers inner workings (taking patients from linked queue)
    public abstract void run_server();


}
