package gym;
import gym.management.Gender;
/**
 * The PersonData class holds the personal details of an individual, including their name,
 * gender, birthdate, and balance. This class is used to store and manage the fundamental
 * attributes of a person in the gym system.
 *
 * The class provides synchronized methods for safely accessing and modifying the balance,
 * ensuring thread-safe operations in a multi-threaded environment.
 */
public class PersonData {
    protected String name;
    protected int balance;
    protected Gender gender;
    protected String birthDay;
    protected final int id;

    public PersonData(String name, int balance, Gender gender, String birthDay, int id) {
        this.name = name;
        this.balance = balance;
        this.gender = gender;
        this.birthDay = birthDay;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public String getBirthDay() {
        return birthDay;
    }

    // Getters and setters with synchronization
    public synchronized void setBalance(int balance) {
        this.balance = balance;
    }

    public synchronized int getBalance() {
        return balance;
    }

}