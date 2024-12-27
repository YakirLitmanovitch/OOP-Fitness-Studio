package gym.customers;
import gym.management.Person;
import gym.management.Sessions.*;
import gym.PersonData;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Client extends Person{
    private ForumType fType;
    private ArrayList<ForumType> arrType ;
    protected ArrayList<Object> notifications = new ArrayList<>();


    public Client(Person person) {
        super(person);
        this.arrType = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }

    public void addNotifications(Object notification){
        notifications.add(notification);
    }
    public String getNotifications(){
        return  notifications.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", ", "[", "]"));
    }

}
