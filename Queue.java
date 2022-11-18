public class Queue {

    private int max_number_of_users;

    private int current_user_count;
    private int current_users_in_queue;
    private int total_wait_time;

    private Queue users;

    public Queue() {
    }

    public Queue(int max_number_of_users, int current_user_count, int current_users_in_queue, int total_wait_time, Queue users) {
        this.max_number_of_users = max_number_of_users;
        this.current_user_count = current_user_count;
        this.current_users_in_queue = current_users_in_queue;
        this.total_wait_time = total_wait_time;
        this.users = users;
    }

    public int getMax_number_of_users() {
        return max_number_of_users;
    }

    public void setMax_number_of_users(int max_number_of_users) {
        this.max_number_of_users = max_number_of_users;
    }

    public int getCurrent_user_count() {
        return current_user_count;
    }

    public void setCurrent_user_count(int current_user_count) {
        this.current_user_count = current_user_count;
    }

    public int getCurrent_users_in_queue() {
        return current_users_in_queue;
    }

    public void setCurrent_users_in_queue(int current_users_in_queue) {
        this.current_users_in_queue = current_users_in_queue;
    }

    public int getTotal_wait_time() {
        return total_wait_time;
    }

    public void setTotal_wait_time(int total_wait_time) {
        this.total_wait_time = total_wait_time;
    }

    public Queue getUsers() {
        return users;
    }

    public void setUsers(Queue users) {
        this.users = users;
    }


//    ------------------------------------------------------------

    public void add_user(User u) {
        /**
         * New patient arrives in this queue, updating the current variables accordingly
         *
         * @Param u: new user to be added to the queue
         */
    }

    public User get_next_user(User u) {
        /**
         * return next user waiting in line, if present. Otherwise null.
         */

        return null;
    }
}
