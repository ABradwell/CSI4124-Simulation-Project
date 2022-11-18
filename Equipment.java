public class Equipment {

    private int time_to_service;
    private boolean in_service;

    public Equipment() {
    }

    public Equipment(int time_to_service, boolean in_service) {
        this.time_to_service = time_to_service;
        this.in_service = in_service;
    }

    public int getTime_to_service() {
        return time_to_service;
    }

    public void setTime_to_service(int time_to_service) {
        this.time_to_service = time_to_service;
    }

    public boolean isIn_service() {
        return in_service;
    }

    public void setIn_service(boolean in_service) {
        this.in_service = in_service;
    }

    public int use_machine(int time_to_use) {
        /**
         *  take a time to work. If the machine needs to be serviced during this usage,
         *  return the amount of time not completed.
         *
         *  For example, if needed for 20 minutes, and only 15 minutes until service_time, then 5
         *  Otherwise a return value of 0 will represent full task completed
         *
         * @param time_to_use: Integer of how long the machine is about to be used
         * @return: time remaining in current service request
         */


        return 0;
    }

    public int service_equipment() {
        /**
         * Machine broken down event.
         *
         * @return: time until machine is running
         */

        return 0;
    }

}
