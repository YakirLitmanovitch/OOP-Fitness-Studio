package gym.management.Sessions;
import gym.management.Instructor;

public class ThaiBoxing extends Session{
    public ThaiBoxing (String date, ForumType pType, Instructor instructor){
        super(SessionType.ThaiBoxing,date,pType,instructor);
    }
    @Override
    public int getMaxParticipents() {
        return 20;
    }

    @Override
    public int getSessionPrice() {
        return 100;
    }
}
