package gym.management.Sessions;
import gym.management.Instructor;

public class Pilates extends Session{

    public Pilates (String date, ForumType pType, Instructor instructor){
        super(SessionType.Pilates,date,pType,instructor);
    }
    @Override
    public int getMaxParticipents() {
        return 30;
    }

    @Override
    public int getSessionPrice() {
        return 60;
    }
}
