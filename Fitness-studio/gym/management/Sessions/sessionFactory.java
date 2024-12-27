package gym.management.Sessions;
import gym.management.Instructor;
/**
 * The sessionFactory class implements the Factory Design Pattern.
 * It creates different types of session objects (e.g., Ninja, Pilates, MachinePilates, ThaiBoxing)
 * based on the provided session type. This allows for flexibility and scalability in adding new session types
 * without modifying the core logic of the system.
 */
public class sessionFactory {
        public static Session createSession (SessionType type, String date, ForumType pType, Instructor instructor)
        {
            if (type == null) {
                throw new IllegalArgumentException("Class type cannot be null");
            }
            switch (type) {
                case Ninja -> {
                    return new Ninja(date, pType, instructor);
                }
                case Pilates -> {
                    return new Pilates(date, pType, instructor);
                }
                case MachinePilates -> {
                    return new MachinePilates(date, pType, instructor);
                }
                case ThaiBoxing -> {
                    return new ThaiBoxing(date, pType, instructor);
                }
                default -> throw new IllegalArgumentException("Unknown class type: " + type);
            }
        }

    }
