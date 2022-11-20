import javax.print.Doc;
import java.lang.Math; 
import java.util.Random;

public class Main_Script {

    public static void print_all_stats(Server receptions, Server senior_doctor, Server junior_doctor) {

    }

    public static PatientQueue generate_patient_data() {
        // generate: arrival time, severity, receptionist service time 
        int t = 0;
        Random random = new Random();
        PatientQueue patients = new PatientQueue();
        for (int i = 0; i < 100; i++) {
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

//        time units are in minutes
        int doctor_break_time = 15;
        int machine_breakdown_time = 15;

        int hours_of_simulation = 48;
        long max_time = 60*hours_of_simulation;

        PatientQueue entry_queue = new PatientQueue();
        PatientQueue senior_doctor_queue = new PatientQueue();
        PatientQueue junior_doctor_queue = new PatientQueue();

        Receptionist receptionist = new Receptionist();
        receptionist.setLinked_queue(entry_queue);

        Doctor senior_doctor = new Doctor();
        senior_doctor.setLinked_queue(senior_doctor_queue);

        Doctor junior_doctor = new Doctor();
        junior_doctor.setLinked_queue(junior_doctor_queue);


        // This should cause the servers to start pulling from their queue, waiting for the first patients (while no patients wait until patients etc)
        receptionist.run_server();
        senior_doctor.run_server();
        junior_doctor.run_server();

//        TODO: User generation and adding to first queue.
        PatientQueue patients = generate_patient_data();
        entry_queue.add_user(patients.get_next_user());
        User next_patient = patients.get_next_user();

        for (int i = 0; i< max_time; i++) {

            if (next_patient != null && next_patient.getTime_arrived() == i) {
                entry_queue.add_user(next_patient);
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
