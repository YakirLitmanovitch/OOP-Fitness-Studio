package gym.management.Sessions;
import gym.management.Instructor;

class Ninja extends Session{
    public Ninja (String date, ForumType pType, Instructor instructor){
        super(SessionType.Ninja,date,pType,instructor);
    }

    @Override
    public int getMaxParticipents() {
        return 5;
    }

    @Override
    public int getSessionPrice() {
        return 150;
    }
}
