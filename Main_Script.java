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

    public static void print_all_stats(Server receptions, Server senior_doctor, Server junior_doctor, PatientQueue exit_patients) {
        /**
         *     This function outputs the information for the system.
         *     Used to display and verify the system is correctly functioning
         */
        System.out.println(receptions);
        System.out.println(senior_doctor);
        System.out.println(junior_doctor);
        System.out.println(exit_patients);
    }

    public static PatientQueue generate_patient_data() {
        /**
         *  Using out papers-dataset, generate a queue of customers
         *  to arrive to the clinic over the course of the simulation
         */
        // generate: arrival time, severity, receptionist service time 
        int t = 0;

        Random random = new Random();
        PatientQueue patients = new PatientQueue();

        for (int i = 0; i < 1000; i++) {

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
        int hours_of_simulation = 48;
        long max_time = 60*hours_of_simulation;

        // Create out 3 queues
        PatientQueue receptionist_queue = new PatientQueue(MAX_NUM_USERS_REC);
        PatientQueue senior_doctor_queue = new PatientQueue(MAX_NUM_USERS_JDOC);
        PatientQueue junior_doctor_queue = new PatientQueue(MAX_NUM_USERS_SDOC);
        PatientQueue exit_patients = new PatientQueue();

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
        PatientQueue patients = generate_patient_data();

        // Get first patient from incoming queue, prepare to run simulation
        User first_patient = patients.get_next_user();
        first_patient.start_waiting(0);
        receptionist_queue.add_user(first_patient);

        User next_patient = patients.get_next_user();

        // simulation timer, each tick is 1 minute of simulation
        for (int i = 0; i< max_time; i++) {

            // If a new patient has arrived this minute, or we are out of patients
            if (next_patient != null && next_patient.getTime_arrived() == i) {
                next_patient.start_waiting(i);
                receptionist_queue.add_user(next_patient);
                next_patient = patients.get_next_user();
            }

            // Advance a minute forward
            receptionist.tick();
            senior_doctor.tick();
            junior_doctor.tick();
        }

        // View if system is working correctly
        print_all_stats(senior_doctor, junior_doctor, receptionist, exit_patients);
    }


}
