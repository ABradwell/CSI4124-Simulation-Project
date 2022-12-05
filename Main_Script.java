import java.util.Random;

public class Main_Script {

    private static final String NAME_REC = "Mr. Mazharul Maaz";
    private static final String NAME_JDOC = "Dr. Salwan Aiden Jr";
    private static final String NAME_SDOC = "Dr. Maja Zohaib Sr";

    private static final int HOURLY_WAGE_REC = 25;
    private static final int HOURLY_WAGE_JDOC = 86;
    private static final int HOURLY_WAGE_SDOC = 165;

    private static final int MAX_NUM_USERS_REC = Integer.MAX_VALUE;
    private static final int MAX_NUM_USERS_JDOC = 150;
    private static final int MAX_NUM_USERS_SDOC = 150;

    private static final int NUM_PATIENTS = 10;

    public static void print_all_stats(Server receptionist, Server senior_doctor, Server junior_doctor, PatientQueue exit_patients) {
        /**
         *     This function outputs the information for the system.
         *     Used to display and verify the system is correctly functioning
         */
        System.out.println(receptionist);
        System.out.println(senior_doctor);
        System.out.println(junior_doctor);
        System.out.println("-------------------------------------------------\n");
        System.out.println(exit_patients);
    }

    public static PatientQueue generate_patient_data(int num_patients) {
        /**
         *  Using out papers-dataset, generate a queue of customers
         *  to arrive to the clinic over the course of the simulation
         */
        // generate: arrival time, severity, receptionist service time
        int t = 0;

        Random random = new Random();
        PatientQueue patients = new PatientQueue();

        for (int i = 0; i < num_patients; i++) {

                int rand_arrival =random.ints(1, 101).findFirst().getAsInt();
                int rand_severity = random.ints(1, 1001).findFirst().getAsInt();
                int arrival = 0;
                int severity = 0;

                if (i == 0) {
                        arrival = 0;
                } else if (rand_arrival <= 57) {
                        arrival = 10;
                } else if (rand_arrival <= 74) {
                        arrival = 20;
                } else if (rand_arrival <= 96) {
                        arrival = 30;
                } else {
                        arrival = 40;
                }
                if (rand_severity <= 5) {
                        severity = 1;
                } else if (rand_severity <= 190) {
                        severity = 2;
                } else if (rand_severity <= 685) {
                        severity = 3;
                } else if (rand_severity <= 969) {
                        severity = 4;
                } else {
                        severity = 5;
                }

                t += arrival;
                patients.add_user(new User(severity, t));
        }
        return patients;
    }

    public static void main(String[] args) {

        // time units are in minutes
        int hours_of_simulation = 72;
        long max_time = 60*hours_of_simulation;

        // Temp testing param.


        int avg_CTAS_1_entered = 0;
        int avg_CTAS_2_entered = 0;
        int avg_CTAS_3_entered = 0;
        int avg_CTAS_4_entered = 0;
        int avg_CTAS_5_entered = 0;

        int avg_CTAS_1_total = 0;
        int avg_CTAS_2_total = 0;
        int avg_CTAS_3_total = 0;
        int avg_CTAS_4_total = 0;
        int avg_CTAS_5_total = 0;

        int avg_CTAS_1_served = 0;
        int avg_CTAS_2_served = 0;
        int avg_CTAS_3_served = 0;
        int avg_CTAS_4_served = 0;
        int avg_CTAS_5_served = 0;



        int avg_r_served = 0;
        int avg_r_time_busy = 0;
        int avg_r_time_idle = 0;

        int avg_s_served = 0;
        int avg_s_time_busy = 0;
        int avg_s_time_idle = 0;

        int avg_j_served = 0;
        int avg_j_time_busy = 0;
        int avg_j_time_idle = 0;

        int avg_r_longest_length = 0;
        int avg_r_current_size = 0;
//        int avg_r_through = 0;
        long avg_r_total_wait = 0;
        int avg_r_time_empty = 0;

        int avg_s_longest_length = 0;
        int avg_s_current_size = 0;
//        int avg_s_through = 0;
        long avg_s_total_wait = 0;
        int avg_s_time_empty = 0;

        int avg_j_longest_length = 0;
        int avg_j_current_size = 0;
//        int avg_j_through = 0;
        long avg_j_total_wait = 0;
        int avg_j_time_empty = 0;


        int number_of_runs = 100000;
        int n = 150;
        for (int iteration = 0; iteration < number_of_runs; iteration++) {


            // Create out 3 queues
            PatientQueue receptionist_queue = new PatientQueue(MAX_NUM_USERS_REC);
            PatientQueue senior_doctor_queue = new PatientQueue(MAX_NUM_USERS_JDOC);
            PatientQueue junior_doctor_queue = new PatientQueue(MAX_NUM_USERS_SDOC);
            PatientQueue exit_patients = new PatientQueue();
            PatientQueue entered_patients = new PatientQueue();

            // Create the receptionist, linked to the first queue
            Receptionist receptionist = new Receptionist(NAME_REC, HOURLY_WAGE_REC, MAX_NUM_USERS_REC, senior_doctor_queue, junior_doctor_queue);
            receptionist.setMy_queue(receptionist_queue);

            // Create the senior doctor, and assign their queue
            Doctor senior_doctor = new Doctor(NAME_SDOC, HOURLY_WAGE_SDOC, MAX_NUM_USERS_SDOC, true, exit_patients);
            senior_doctor.setMy_queue(senior_doctor_queue);

            // Create junior doctor, and assign their queue
            Doctor junior_doctor = new Doctor(NAME_JDOC, HOURLY_WAGE_JDOC, MAX_NUM_USERS_JDOC, false, exit_patients);
            junior_doctor.setMy_queue(junior_doctor_queue);

            // Create patients for simulation
            PatientQueue patients = generate_patient_data(n);

            avg_CTAS_1_total = avg_CTAS_1_total + (int) (patients.getUserCount(1));
            avg_CTAS_2_total = avg_CTAS_2_total + (int) (patients.getUserCount(2));
            avg_CTAS_3_total = avg_CTAS_3_total + (int) (patients.getUserCount(3));
            avg_CTAS_4_total = avg_CTAS_4_total + (int) (patients.getUserCount(4));
            avg_CTAS_5_total = avg_CTAS_5_total + (int) (patients.getUserCount(5));


            // Get first patient from incoming queue, prepare to run simulation
            User first_patient = patients.get_next_user();
            first_patient.start_waiting(0);
            receptionist_queue.add_user(first_patient);

            User next_patient = patients.get_next_user();

            // simulation timer, each tick is 1 minute of simulation
            for (int i = 0; i < max_time; i++) {
                
                receptionist_queue.setTotal_time_waited(receptionist_queue.getTotal_time_waited() + receptionist_queue.getCurrent_user_count());
                senior_doctor_queue.setTotal_time_waited(senior_doctor_queue.getTotal_time_waited() + senior_doctor_queue.getCurrent_user_count());
                junior_doctor_queue.setTotal_time_waited(junior_doctor_queue.getTotal_time_waited() + junior_doctor_queue.getCurrent_user_count());

                // If a new patient has arrived this minute, or we are out of patients
                if (next_patient != null && next_patient.getTime_arrived() == i) {
                    next_patient.start_waiting(i);
                    receptionist_queue.add_user(next_patient);


                    entered_patients.add_user(next_patient);

                    next_patient = patients.get_next_user();
                }

                // Advance a minute forward
                receptionist.tick();
                senior_doctor.tick();
                junior_doctor.tick();
            }

            // View if system is working correctly
            //        print_all_stats(receptionist, senior_doctor, junior_doctor, exit_patients);

            avg_r_served = avg_r_served + receptionist.getNumber_served();
            avg_r_time_busy = avg_r_time_busy  + receptionist.getTime_busy();
            avg_r_time_idle = avg_r_time_idle  + receptionist.getTime_idle();
            avg_s_served = avg_s_served + senior_doctor.getNumber_served();;
            avg_s_time_busy = avg_s_time_busy + senior_doctor.getTime_busy();
            avg_s_time_idle = avg_s_time_idle + senior_doctor.getTime_idle();
            avg_j_served = avg_j_served + junior_doctor.getNumber_served();;
            avg_j_time_busy = avg_j_time_busy + junior_doctor.getTime_busy();
            avg_j_time_idle = avg_j_time_idle + junior_doctor.getTime_idle();

            avg_r_longest_length = avg_r_longest_length + receptionist_queue.getLongest_queue_length();
            avg_r_current_size = avg_r_current_size + receptionist_queue.getCurrent_user_count();
//            avg_r_through = avg_r_through + receptionist_queue.get;
            avg_r_total_wait = avg_r_total_wait + receptionist_queue.getTotal_time_waited();
            avg_r_time_empty = avg_r_time_empty + receptionist_queue.getTime_empty();
            avg_s_longest_length = avg_s_longest_length + senior_doctor_queue.getLongest_queue_length();
            avg_s_current_size = avg_s_current_size + senior_doctor_queue.getCurrent_user_count();
//            avg_s_through = avg_s_through + ;
            avg_s_total_wait = avg_s_total_wait + senior_doctor_queue.getTotal_time_waited();

            avg_s_time_empty = avg_s_time_empty + senior_doctor_queue.getTime_empty();
            avg_j_longest_length = avg_j_longest_length + junior_doctor_queue.getLongest_queue_length();
            avg_j_current_size = avg_j_current_size + junior_doctor_queue.getCurrent_user_count();
//            avg_j_through = avg_j_through + ;
            avg_j_total_wait = avg_j_total_wait + junior_doctor_queue.getTotal_time_waited();
            avg_j_time_empty = avg_j_time_empty + junior_doctor_queue.getTime_empty();


            avg_CTAS_1_served = avg_CTAS_1_served + (int) (exit_patients.getUserCount(1));
            avg_CTAS_2_served = avg_CTAS_2_served + (int) (exit_patients.getUserCount(2));
            avg_CTAS_3_served = avg_CTAS_3_served + (int) (exit_patients.getUserCount(3));
            avg_CTAS_4_served = avg_CTAS_4_served + (int) (exit_patients.getUserCount(4));
            avg_CTAS_5_served = avg_CTAS_5_served + (int) (exit_patients.getUserCount(5));

            avg_CTAS_1_entered = avg_CTAS_1_entered + (int) (entered_patients.getUserCount(1));
            avg_CTAS_2_entered = avg_CTAS_2_entered + (int) (entered_patients.getUserCount(2));
            avg_CTAS_3_entered = avg_CTAS_3_entered + (int) (entered_patients.getUserCount(3));
            avg_CTAS_4_entered = avg_CTAS_4_entered + (int) (entered_patients.getUserCount(4));
            avg_CTAS_5_entered = avg_CTAS_5_entered + (int) (entered_patients.getUserCount(5));

//            if (iteration == number_of_runs - 1) {
//                print_all_stats(receptionist, senior_doctor, junior_doctor, exit_patients);
//            }
        }


        avg_r_served = avg_r_served / number_of_runs;
        avg_r_time_busy = avg_r_time_busy / number_of_runs;
        avg_r_time_idle = avg_r_time_idle / number_of_runs;
        avg_s_served = avg_s_served / number_of_runs;
        avg_s_time_busy = avg_s_time_busy / number_of_runs;
        avg_s_time_idle = avg_s_time_idle / number_of_runs;
        avg_j_served = avg_j_served / number_of_runs;
        avg_j_time_busy = avg_j_time_busy / number_of_runs;
        avg_j_time_idle = avg_j_time_idle / number_of_runs;
        avg_r_longest_length = avg_r_longest_length / number_of_runs;
        avg_r_current_size = avg_r_current_size / number_of_runs;
//        avg_r_through = avg_r_through / number_of_runs;
        avg_r_total_wait = avg_r_total_wait / number_of_runs;
        avg_r_time_empty = avg_r_time_empty / number_of_runs;
        avg_s_longest_length = avg_s_longest_length / number_of_runs;
        avg_s_current_size = avg_s_current_size / number_of_runs;
//        avg_s_through = avg_s_through / number_of_runs;
        avg_s_total_wait = avg_s_total_wait / number_of_runs;
        avg_s_time_empty = avg_s_time_empty / number_of_runs;
        avg_j_longest_length = avg_j_longest_length / number_of_runs;
        avg_j_current_size = avg_j_current_size / number_of_runs;
//        avg_j_through = avg_j_through / number_of_runs;
        avg_j_total_wait = avg_j_total_wait / number_of_runs;
        avg_j_time_empty = avg_j_time_empty / number_of_runs;


        avg_CTAS_1_total = avg_CTAS_1_total / number_of_runs;
        avg_CTAS_2_total = avg_CTAS_2_total / number_of_runs;
        avg_CTAS_3_total = avg_CTAS_3_total / number_of_runs;
        avg_CTAS_4_total = avg_CTAS_4_total / number_of_runs;
        avg_CTAS_5_total = avg_CTAS_5_total / number_of_runs;

        avg_CTAS_1_entered = avg_CTAS_1_entered / number_of_runs;
        avg_CTAS_2_entered = avg_CTAS_2_entered / number_of_runs;
        avg_CTAS_3_entered = avg_CTAS_3_entered / number_of_runs;
        avg_CTAS_4_entered = avg_CTAS_4_entered / number_of_runs;
        avg_CTAS_5_entered = avg_CTAS_5_entered / number_of_runs;

        avg_CTAS_1_served = avg_CTAS_1_served / number_of_runs;
        avg_CTAS_2_served = avg_CTAS_2_served / number_of_runs;
        avg_CTAS_3_served = avg_CTAS_3_served / number_of_runs;
        avg_CTAS_4_served = avg_CTAS_4_served / number_of_runs;
        avg_CTAS_5_served = avg_CTAS_5_served / number_of_runs;

        System.out.println("----------------------------------------");
        System.out.println("avg_CTAS_1_total: " + avg_CTAS_1_total);
        System.out.println("avg_CTAS_2_total: " + avg_CTAS_2_total);
        System.out.println("avg_CTAS_3_total: " + avg_CTAS_3_total);
        System.out.println("avg_CTAS_4_total: " + avg_CTAS_4_total);
        System.out.println("avg_CTAS_5_total: " + avg_CTAS_5_total);
        System.out.println("----------------------------------------");
        System.out.println("avg_CTAS_1_entered: " + avg_CTAS_1_entered);
        System.out.println("avg_CTAS_2_entered: " + avg_CTAS_2_entered);
        System.out.println("avg_CTAS_3_entered: " + avg_CTAS_3_entered);
        System.out.println("avg_CTAS_4_entered: " + avg_CTAS_4_entered);
        System.out.println("avg_CTAS_5_entered: " + avg_CTAS_5_entered);
        System.out.println("----------------------------------------");
        System.out.println("avg_CTAS_1_served: " + avg_CTAS_1_served);
        System.out.println("avg_CTAS_2_served: " + avg_CTAS_2_served);
        System.out.println("avg_CTAS_3_served: " + avg_CTAS_3_served);
        System.out.println("avg_CTAS_4_served: " + avg_CTAS_4_served);
        System.out.println("avg_CTAS_5_served: " + avg_CTAS_5_served);
        System.out.println("----------------------------------------");
        System.out.println("avg_r_served = " + avg_r_served);
        System.out.println("avg_r_time_busy = " + avg_r_time_busy);
        System.out.println("avg_r_time_idle = " + avg_r_time_idle);
        System.out.println("avg_s_served = " + avg_s_served);
        System.out.println("avg_s_time_busy = " + avg_s_time_busy);
        System.out.println("avg_s_time_idle = " + avg_s_time_idle);
        System.out.println("avg_j_served = " + avg_j_served);
        System.out.println("avg_j_time_busy = " + avg_j_time_busy);
        System.out.println("avg_j_time_idle = " + avg_j_time_idle);
        System.out.println("----------------------------------------");
        System.out.println("avg_r_longest_length = " + avg_r_longest_length);
        System.out.println("avg_r_current_size = " + avg_r_current_size);
//        System.out.println("avg_r_through = " + avg_r_through);
        System.out.println("avg_r_total_wait = " + avg_r_total_wait);
        System.out.println("avg_r_time_empty = " + avg_r_time_empty);
        System.out.println("avg_s_longest_length = " + avg_s_longest_length);
        System.out.println("avg_s_current_size = " + avg_s_current_size);
//        System.out.println("avg_s_through = " + avg_s_through);
        System.out.println("avg_s_total_wait = " + avg_s_total_wait);
        System.out.println("avg_s_time_empty = " + avg_s_time_empty);
        System.out.println("avg_j_longest_length = " + avg_j_longest_length);
        System.out.println("avg_j_current_size = " + avg_j_current_size);
//        System.out.println("avg_j_through = " + avg_j_through);
        System.out.println("avg_j_total_wait = " + avg_j_total_wait);
        System.out.println("avg_j_time_empty = " + avg_j_time_empty);



    }


}
