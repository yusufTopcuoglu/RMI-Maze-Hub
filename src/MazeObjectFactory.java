
public class MazeObjectFactory {

    public static int agentId = 0;

    public static MazeObject factoryMazeObject(Position position, MazeObjectType type){
        if(type == MazeObjectType.AGENT){
            return new Agent(position, agentId++);
        } else {
            return new MazeObject(position, type);
        }
    }
}
