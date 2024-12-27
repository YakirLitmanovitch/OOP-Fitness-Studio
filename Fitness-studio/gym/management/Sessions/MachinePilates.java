package gym.management.Sessions;
import gym.management.Instructor;

public class MachinePilates extends Session{
    public MachinePilates (String date, ForumType pType, Instructor instructor){
        super(SessionType.MachinePilates,date,pType,instructor);
    }
    @Override
    public int getMaxParticipents() {
        return 10;
    }

    @Override
    public int getSessionPrice() {
        return 80;
    }
}
