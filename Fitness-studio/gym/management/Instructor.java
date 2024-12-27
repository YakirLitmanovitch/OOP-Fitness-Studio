package gym.management;
import gym.management.Sessions.SessionType;
import java.util.ArrayList;
// Represents an instructor with a salary per hour and a list of session types they are certified to teach.
// Inherits common attributes and behavior from the Person class.
public class Instructor extends Person{
    private final int selPerHour;

    private final ArrayList<SessionType> listType;

    public Instructor(Person p, int selPerHour, ArrayList<SessionType> listType) {
        super(p);
        this.listType = new ArrayList<>(listType);
        this.selPerHour = selPerHour;
    }

    public ArrayList<SessionType> getListType (){
        return listType;
    }
    public int getSelPerHour(){
        return selPerHour;
    }
}
