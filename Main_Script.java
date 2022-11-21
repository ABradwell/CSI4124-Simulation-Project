import java.util.Random;

public class Main_Script {

    private static final String NAME_REC = "Mr. Mazharul Maaz";
    private static final String NAME_JDOC = "Dr. Salwan Aiden Jr";
    private static final String NAME_SDOC = "Dr. Maja Zohaib Sr";

    private static final int HOURLY_WAGE_REC = 25;
    private static final int HOURLY_WAGE_JDOC = 86;
    private static final int HOURLY_WAGE_SDOC = 165;

    private static final int MAX_NUM_USERS_REC = 150;
    private static final int MAX_NUM_USERS_JDOC = 150;
    private static final int MAX_NUM_USERS_SDOC = 150;

    private static final int EQUIPMENT_LIFETIME_JDOC = 600;
    private static final int EQUIPMENT_LIFETIME_SDOC = 600;

    public static void print_all_stats(Server receptions, Server senior_doctor, Server junior_doctor) {
        System.out.println(receptions);
        System.out.println(senior_doctor);
        System.out.println(junior_doctor);
    }

    public static PatientQueue generate_patient_data() {
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
                patients.add_user(new User(0, severity, 0, t));
        }
        return patients;
    }

    public static void main(String[] args) {


        // time units are in minutes
        int hours_of_simulation = 48;
        long max_time = 60*hours_of_simulation;

        PatientQueue receptionist_queue = new PatientQueue(MAX_NUM_USERS_REC);
        PatientQueue senior_doctor_queue = new PatientQueue(MAX_NUM_USERS_JDOC);
        PatientQueue junior_doctor_queue = new PatientQueue(MAX_NUM_USERS_SDOC);

        Receptionist receptionist = new Receptionist(NAME_REC, HOURLY_WAGE_REC, MAX_NUM_USERS_REC, senior_doctor_queue, junior_doctor_queue);
        receptionist.setMy_queue(receptionist_queue);

        Equipment sr_equipment = new Equipment(EQUIPMENT_LIFETIME_SDOC, false);
        Doctor senior_doctor = new Doctor(NAME_SDOC, HOURLY_WAGE_SDOC, MAX_NUM_USERS_SDOC, true, sr_equipment);
        senior_doctor.setMy_queue(senior_doctor_queue);

        Equipment jr_equipment = new Equipment(EQUIPMENT_LIFETIME_JDOC, false);
        Doctor junior_doctor = new Doctor(NAME_JDOC, HOURLY_WAGE_JDOC, MAX_NUM_USERS_JDOC, false, jr_equipment);
        junior_doctor.setMy_queue(junior_doctor_queue);


        // This should cause the servers to start pulling from their queue, waiting for the first patients (while no patients wait until patients etc)
        receptionist.run_server();
        senior_doctor.run_server();
        junior_doctor.run_server();

        PatientQueue patients = generate_patient_data();
        receptionist_queue.add_user(patients.get_next_user());
        User next_patient = patients.get_next_user();

        for (int i = 0; i< max_time; i++) {

            if (next_patient != null && next_patient.getTime_arrived() == i) {
                receptionist_queue.add_user(next_patient);
                next_patient = patients.get_next_user();
            }

            receptionist.tick();
            senior_doctor.tick();
            junior_doctor.tick();
        }

//        TODO: Add stopping functionality to servers

        print_all_stats(senior_doctor, junior_doctor, receptionist);
    }


}
