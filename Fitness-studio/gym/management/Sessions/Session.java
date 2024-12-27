package gym.management.Sessions;
import gym.customers.Client;
import gym.management.Instructor;
import java.util.ArrayList;
// Abstract class representing a gym session with shared properties and methods.
// Includes functionality to manage clients and retrieve session details.
abstract public class Session {
    protected SessionType type;
    protected String date;
    protected ForumType pType;
    protected Instructor instructor;
    protected final ArrayList<Client> clients ;

    protected Session (SessionType type, String date, ForumType pType, Instructor instructor){
        this.type=type;
        this.date=date;
        this.pType=pType;
        this.instructor=instructor;
        this.clients = new ArrayList<>();
    }

    public abstract int getMaxParticipents();
    public abstract int getSessionPrice ();
    public ArrayList<Client> getClients() {
        return this.clients;
    }

    public void addClient(Client c) {
        clients.add(c);
    }
    public ForumType getpType(){
        return this.pType;
    }
    public SessionType getType() {
        return type;
    }
    public String getDate() {
        return date;
    }
    public Instructor getInstructor() {
        return instructor;
    }
}
