package gym.management;
import gym.PersonData;
// Represents a person with attributes such as name, balance, gender, birthday, and unique ID.
// Utilizes a shared PersonData object for encapsulating personal data and includes ID auto-incrementation.
public class Person {
    protected final PersonData data;
    private static int idDefault = 1111;

    public Person(String name, int balance, Gender gender, String birthDay) {
        this.data = new PersonData(name, balance, gender, birthDay, idDefault++);
    }

    protected Person(Person other) {
        this.data = other.data;
    }
    public int getBalance() {
        return data.getBalance();
    }

    public void setBalance(int balance) {
        data.setBalance(balance);
    }
    public int getId(){
        return data.getId();
    }
    public String getName (){
        return data.getName();
    }

    public String getBirthDay() {
        return data.getBirthDay();
    }
    public Gender getGender(){
        return data.getGender();
    }
}
