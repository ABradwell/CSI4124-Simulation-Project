import javax.print.Doc;

public class Main_Script {

    public static void print_all_stats() {

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

        for (int i = 0; i< max_time; i++) {


            receptionist.tick();
            senior_doctor.tick();
            junior_doctor.tick();
        }


        print_all_stats(senior_doctor, junior_doctor, receptionist);
    }


}
