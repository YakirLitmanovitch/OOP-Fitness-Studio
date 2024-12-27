package gym.management;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import gym.Exception.ClientNotRegisteredException;
import gym.Exception.DuplicateClientException;
import gym.Exception.InstructorNotQualifiedException;
import gym.Exception.InvalidAgeException;
import gym.customers.Client;
import gym.management.Sessions.ForumType;
import gym.management.Sessions.Session;
import gym.management.Sessions.SessionType;
import gym.management.Sessions.sessionFactory;

// Secretary class, inheriting from Person, handles client registration, session management,
// hiring instructors, and administrative tasks related to gym operations.
public class Secretary extends Person{
    protected Person sec;
    protected int Salary;
    private boolean active = true;
    private boolean doUpdate = true;

    public Secretary(Person sec, int Salary) {
        super(sec);
        this.Salary = Salary;
    }

    public void setActive() {
        this.active = false;
    }
    public int getSalary(){return this.Salary;}
    /**
     * Ensures the secretary is active before performing any action.
     * Throws a NullPointerException if the secretary is inactive.
     */

    protected void checkActive() throws NullPointerException {
        if (!active)
            throw new NullPointerException();
    }
    /**
     * Registers a new client with the gym, performing age and duplicate checks.
     *
     * @param p The person details of the client to register.
     * @return The newly created Client object.
     * @throws InvalidAgeException If the client is underage.
     * @throws DuplicateClientException If the client is already registered.
     */
    public Client registerClient(Person p) throws InvalidAgeException, DuplicateClientException {
        checkActive();
        int age = checkAge(p.getBirthDay());
        if (age < 18) {
            throw new InvalidAgeException("Error: Client must be at least 18 years old to register");
        }
        if (isClientAlreadyRegistered(p)) {
            throw new DuplicateClientException("Error: The client is already registered");
        }
        Client c = new Client(p);
        Gym.getInstance().setClients(c);//11
        Gym.getInstance().addToListAction("Registered new client: " + c.getName());
        return c;
    }
    /**
     * Calculates the age of a client based on their birth date.
     *
     * @param birth The birth date of the client in the format "dd-MM-yyyy".
     * @return The calculated age of the client.
     */
    public int checkAge(String birth) {
        checkActive();
        LocalDate today = LocalDate.now();
        String[] birthDateParts = birth.split("-");
        int birthYear = Integer.parseInt(birthDateParts[2]);
        int birthMonth = Integer.parseInt(birthDateParts[1]);
        int birthDay = Integer.parseInt(birthDateParts[0]);
        int currentYear = today.getYear();
        int currentMonth = today.getMonthValue();
        int currentDay = today.getDayOfMonth();
        int age = currentYear - birthYear;
        if (currentMonth < birthMonth || (currentMonth == birthMonth && currentDay < birthDay)) {
            age--;
        }
        return age;
    }
    /**
     * Checks if a client is already registered by comparing their name.
     *
     * @param p The person's details.
     * @return true if the client is already registered, false otherwise.
     */
    private boolean isClientAlreadyRegistered(Person p) {
        checkActive();
        for (Client client : Gym.getInstance().getClients()) {
            if (client.getName().equals(p.getName())) {
                return true;
            }
        }
        return false;
    }
    /**
     * Unregisters a client from the gym.
     *
     * @param c The client to unregister.
     * @throws ClientNotRegisteredException If the client is not registered.
     */
    public void unregisterClient(Client c) throws ClientNotRegisteredException {
        checkActive();
        if (!Gym.getInstance().getClients().contains(c)) {
            throw new ClientNotRegisteredException("Error: Registration is required before attempting to unregister");
        }
        Gym.getInstance().setDelClients(c);//11
        Gym.getInstance().addToListAction("Unregistered client: " + c.getName());

    }
    /**
     * Adds a session to the gym schedule after ensuring the instructor is qualified.
     *
     * @param type The type of session to add.
     * @param date The date of the session.
     * @param pType The forum type for the session (e.g., male, female, all).
     * @param instructor The instructor conducting the session.
     * @return The newly created session.
     * @throws InstructorNotQualifiedException If the instructor is not qualified for the session type.
     */
    public Session addSession(SessionType type, String date, ForumType pType, Instructor instructor) throws InstructorNotQualifiedException {
        checkActive();
        if (!instructor.getListType().contains(type)) {
            throw new InstructorNotQualifiedException("Error: Instructor is not qualified to conduct this session type.");
        }
        String fixedDate = dateFormat(date);
        Session session = sessionFactory.createSession(type, date, pType, instructor);
        Gym.getInstance().getAllSessions().add(session);
        Gym.getInstance().addToListAction("Created new session: " + type + " on " + fixedDate + " with instructor: " + instructor.getName());
        return session;

    }
    /**
     * Hires a new instructor and adds them to the gym's instructor list.
     *
     * @param p The person details of the instructor.
     * @param selPerHour The salary per hour for the instructor.
     * @param listType The types of sessions the instructor is qualified to teach.
     * @return The newly hired instructor.
     */
    public Instructor hireInstructor(Person p, int selPerHour, ArrayList<SessionType> listType) {
        checkActive();
        Gym.getInstance().addToListAction("Hired new instructor: " + p.getName() + " with salary per hour: " + selPerHour);
        Instructor i = new Instructor(p, selPerHour, listType);
        Gym.getInstance().addToInstructorsList(i);
        return i;
    }
    /**
     * Registers a client to a specific session, checking for available spots, balance, and session compatibility.
     *
     * @param c The client to register.
     * @param s The session to register the client to.
     * @throws DuplicateClientException If the client is already registered for the session.
     * @throws ClientNotRegisteredException If the client is not registered with the gym.
     */
    public void registerClientToLesson(Client c, Session s) throws DuplicateClientException, ClientNotRegisteredException {
        checkActive();
        doUpdate = true;
        if (s.getClients().contains(c)) {
            throw new DuplicateClientException("Error: The client is already registered for this lesson");
        }
        if (!Gym.getInstance().getClients().contains(c)) {
            throw new ClientNotRegisteredException("Error: The client is not registered with the gym and cannot enroll in lessons");
        }

        LocalDateTime sessionDateTime = LocalDateTime.parse(s.getDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        LocalDateTime currentDateTime = LocalDateTime.now();

        if (!sessionDateTime.isAfter(currentDateTime)) {
            Gym.getInstance().addToListAction("Failed registration: Session is not in the future");
            doUpdate = false;
            //return;
        }

        if (s.getClients().size() >= s.getMaxParticipents()) {
            Gym.getInstance().addToListAction("Failed registration: No available spots for session");
            doUpdate = false;
            //return;
        }
        ForumType f = s.getpType();
        isForumTypeCompatible(c, f, s);

        if (c.getBalance() < s.getSessionPrice()) {
            Gym.getInstance().addToListAction("Failed registration: Client doesn't have enough balance");
            doUpdate = false;
            //return;
        }


        clientDoUpdate(c,s);

    }
    /**
     * Checks the compatibility of the client's gender and age with the session's requirements.
     *
     * @param c The client to check.
     * @param f The forum type (male, female, seniors, all).
     * @param s The session to check.
     */
    private void isForumTypeCompatible(Client c, ForumType f, Session s) {
        int age = checkAge(c.getBirthDay());
        if (f == ForumType.All) {
            return;
        }
        if (f == ForumType.Male && c.getGender() == Gender.Male) {
            return;
        }
        if (f == ForumType.Female && c.getGender() == Gender.Female) {

            return;
        }
        if ((f == ForumType.Male && c.getGender() == Gender.Female) || (f == ForumType.Female && c.getGender() == Gender.Male)) {
            Gym.getInstance().addToListAction("Failed registration: Client's gender doesn't match the session's gender requirements");
            doUpdate = false;
            return;
        }
        if (f == ForumType.Seniors && age < 64) {

            Gym.getInstance().addToListAction("Failed registration: Client doesn't meet the age requirements for this session (Seniors)");
            doUpdate = false;
        }
    }

    /**
     * Updates the client's registration and balance once all conditions are met.
     *
     * @param c The client to update.
     * @param s The session to update the client for.
     */
    private void clientDoUpdate(Client c, Session s) {
        checkActive();
        if (doUpdate) {
            String date = dateFormat(s.getDate());
            c.data.setBalance(c.data.getBalance() - s.getSessionPrice());
            Gym.getInstance().setBalance(Gym.getInstance().getBalance() + s.getSessionPrice());
            Gym.getInstance().addToListAction("Registered client: " + c.getName() + " to session: " +
                    s.getType() + " on " + date + " for price: " + s.getSessionPrice());
            s.addClient(c);
        }
        else doUpdate = true;
    }
    /**
     * Converts the date format from "dd-MM-yyyy HH:mm" to "yyyy-MM-dd'T'HH:mm".
     *
     * @param date The date string to convert.
     * @return The formatted date string.
     */
    public String dateFormat(String date){
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date, inputFormatter);
        return dateTime.format(outputFormatter);
    }
    /**
     * Sends notifications to clients or sessions based on the provided arguments.
     *
     * This method implements the Observer design pattern. In this pattern:
     * - The Secretary (Subject) sends updates (notifications) to the Clients (Observers).
     * - The Clients subscribe to notifications and update their personal notification lists accordingly.
     *
     * Depending on the parameters passed, notifications can be sent to:
     * 1. All clients in the system.
     * 2. Clients registered for a specific session.
     * 3. Clients registered for sessions on a specific date.
     *
     * @param args Variable arguments that define the notification target and content.
     *             Can include a message to all clients, a session-specific message, or a date-specific message.
     */
    public void notify(Object... args) {
        checkActive();
        if (args.length == 1 && args[0] instanceof String s) { //Notify for all clients.
            for (Client client : Gym.getInstance().getClients())
                client.addNotifications(s);
            Gym.getInstance().addToListAction("A message was sent to all gym clients: " + s);
        }
        //Notify for specific session.
        else if (args.length == 2 && args[0] instanceof Session session && args[1] instanceof String s) {
            String date = dateFormat(session.getDate());
            for (Client client : session.getClients())
                client.addNotifications(s);
            Gym.getInstance().addToListAction("A message was sent to everyone registered for session " + session.getType() +
                    " on " + date + " : " + s);
        }
        //Notify for specific date.
        else if (args.length == 2 && args[0] instanceof String d && args[1] instanceof String s) {
            String date = dateFormat(d + " 00:00");
            String [] year = date.split("T");
            for (int i = 0; i < Gym.getInstance().getAllSessions().size(); i++) {
                String[] fullDate = Gym.getInstance().getAllSessions().get(i).getDate().split(" ");
                if (Objects.equals(fullDate[0],d)) {
                    for (Client client : Gym.getInstance().getAllSessions().get(i).getClients()) {
                        client.addNotifications(s);
                    }
                }
            }
            Gym.getInstance().addToListAction("A message was sent to everyone registered for a session on "
                    + year[0] + " : " + s);
        } else throw new IllegalArgumentException();
    }
    /**
     * Pays the salaries of all instructors and the secretary.
     */
    public void paySalaries() {
        checkActive();
        int salaries = 0;
        for (int i = 0; i < Gym.getInstance().getAllSessions().size(); i++) {
            salaries += Gym.getInstance().getAllSessions().get(i).getInstructor().getSelPerHour();
            Gym.getInstance().getAllSessions().get(i).getInstructor().data.setBalance(Gym.getInstance().getAllSessions().get(i).getInstructor().data.getBalance()
                   + Gym.getInstance().getAllSessions().get(i).getInstructor().getSelPerHour());
        }
        Gym.getInstance().setBalance(Gym.getInstance().getBalance() - salaries);
        Gym.getInstance().setBalance(Gym.getInstance().getBalance()-this.Salary);
        data.setBalance(data.getBalance()+Salary);
        Gym.getInstance().addToListAction("Salaries have been paid to all employees");
    }
    /**
     * Prints all the actions performed by the gym.
     */
    public void printActions() {
        checkActive();
        for (String action : Gym.getInstance().getAllActionList()) {
            System.out.println(action);
        }
    }
}