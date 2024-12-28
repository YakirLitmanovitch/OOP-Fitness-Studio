package gym.management;
import gym.customers.Client;
import gym.management.Sessions.Session;
import gym.management.Sessions.SessionType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
// The Gym class implements the Singleton design pattern.
// This ensures that only one instance of the Gym class can be created throughout the application.
public class Gym {
    private static Gym instance;
    private String name;
    private Secretary sec;


    private int balance;
    private final ArrayList<Client> clients = new ArrayList<>();
    private final ArrayList<Session> allSessions = new ArrayList<>();
    private final ArrayList<Instructor> allInstructorsEmployees = new ArrayList<>();
    private final ArrayList<Secretary> allSecretariatEmployees = new ArrayList<>();
    private ArrayList<String> allActionList = new ArrayList<>();

    private Gym() {
    }

    public static Gym getInstance() {
        if (instance == null) {
            instance = new Gym();
        }
        return instance;
    }
    public List<Instructor> getAllInstructorsEmployees() {
        return allInstructorsEmployees;
    }
    public List<Secretary> getAllSecretariatEmployees() {
        return allSecretariatEmployees;
    }
    public List<Session> getAllSessions() {
        return allSessions;
    }

    public List<Client> getClients() {
        return Collections.unmodifiableList(clients);
    }

    public void setClients(Client client) {
        clients.add(client);
    }

    public void setDelClients(Client client) {
        clients.remove(client);
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSecretary(Person p, int Sal) {
        if (this.sec != null) {
            this.sec.setActive();
            allSecretariatEmployees.remove(sec);
        }
        addToListAction("A new secretary has started working at the gym: " + p.getName());
        this.sec = new Secretary(p, Sal);
        allSecretariatEmployees.add(sec);
    }


    public Secretary getSecretary() {
        return sec;
    }

    public void addToListAction(String str) {
        this.allActionList.add(str);
    }

    public ArrayList<String> getAllActionList() {
        return this.allActionList;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Gym Name: " + name+"\n");
        for (Secretary secretary : allSecretariatEmployees){
            s.append(String.format(
                    "Gym Secretary: ID: %d | Name: %s | Gender: %s | Birthday: %s | Age: %d | Balance: %d | Role: %s | Salary per Month: %d\n",
                    secretary.getId(), secretary.getName(), secretary.getGender(), secretary.getBirthDay(), sec.checkAge(secretary.getBirthDay()),
                    secretary.getBalance(), "Secretary", secretary.getSalary()));
        }
        s.append("Gym Balance: " + balance+"\n");
        s.append("\nClients Data:\n");
        for (Client client : clients) {
            s.append(String.format("ID: %d | Name: %s | Gender: %s | Birthday: %s | Age: %d | Balance: %d\n",
                    client.getId(), client.getName(), client.getGender(), client.getBirthDay(), sec.checkAge(client.getBirthDay()), client.getBalance()));
        }
        s.append("\n");
        s.append("Employees Data:\n");
        for (Instructor instructor : allInstructorsEmployees) {
            StringBuilder sb = new StringBuilder();
            for (SessionType sessionType : instructor.getListType()) {
                sb.append(sessionType.toString()).append(", ");
            }
            if (sb.length() > 1) {
                sb.delete(sb.length() - 2, sb.length());
            }

            String joinedNames = sb.toString();
            s.append(String.format(
                    "ID: %d | Name: %s | Gender: %s | Birthday: %s | Age: %d | Balance: %d | Role: %s | Salary per Hour: %d | Certified Classes: %s\n",
                    instructor.getId(), instructor.getName(), instructor.getGender(), instructor.getBirthDay(), sec.checkAge(instructor.getBirthDay()),
                    instructor.getBalance(), "Instructor", instructor.getSelPerHour(), joinedNames));
        }
        for (Secretary secretary : allSecretariatEmployees){
            s.append(String.format(
                    "ID: %d | Name: %s | Gender: %s | Birthday: %s | Age: %d | Balance: %d | Role: %s | Salary per Month: %d\n",
                    secretary.getId(), secretary.getName(), secretary.getGender(), secretary.getBirthDay(), sec.checkAge(secretary.getBirthDay()),
                    secretary.getBalance(), "Secretary", secretary.getSalary()));
        }
        //s.append("\n");
        s.append("\nSessions Data:\n");
        for (Session session : allSessions) {
            s.append(String.format("Session Type: %s | Date: %s | Forum: %s | Instructor: %s | Participants: %d/%d\n",
                    session.getType(), session.getDate(), session.getpType(), session.getInstructor().getName(), session.getClients().size(), session.getMaxParticipents()));
        }
        String result = s.toString();
        if (result.endsWith("\n")) {
            result = result.substring(0, result.length() - 1);  // Removing the last enter.
        }
        return result;

    }

    public void addToInstructorsList(Instructor instructor) {
        this.allInstructorsEmployees.add(instructor);
    }
}