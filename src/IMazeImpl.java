import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class IMazeImpl extends UnicastRemoteObject implements IMaze, Serializable {

    private int height, width;
    private MazeObject[][] mazeObjectList;

    protected IMazeImpl() throws RemoteException {}

    @Override
    public void create(int height, int width) throws RemoteException {
        this.height = height;
        this.width = width;
        mazeObjectList = new MazeObject[width][height];
        System.out.println("create");
    }

    @Override
    public MazeObject getObject(Position position) throws RemoteException {
        System.out.println("getObject");
        if (isInside(position))
            return mazeObjectList[position.getX()][position.getY()];
        return null;
    }

    @Override
    public boolean createObject(Position position, MazeObjectType type) throws RemoteException {
        if (isEmpty(position)){
            add(position, type);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteObject(Position position) throws RemoteException {
        if(isEmpty(position)){
            return false;
        }
        mazeObjectList[position.getX()][position.getY()] = null;
        return true;
    }

    @Override
    public Agent[] getAgents() throws RemoteException {
        Agent[] agents = new Agent[MazeObjectFactory.agentId];
        int i = 0;
        for (int x = 0 ; x < width ; x++){
            for (int y = 0 ; y < height; y++){
                if (mazeObjectList[x][y] != null && mazeObjectList[x][y].getType() == MazeObjectType.AGENT ){
                    agents[i++] = (Agent) mazeObjectList[x][y];
                }
            }
        }
        return agents;
    }

    @Override
    public boolean moveAgent(int id, Position position) throws RemoteException {
        return false;
    }

    @Override
    public String print() throws RemoteException {
        StringBuilder sb = new StringBuilder();
        sb.append("+");
        for (int i = 0; i < width; i++){
            sb.append("-");
        }
        sb.append("+\n");

        for (int i = 0; i < height; i++){
            sb.append("|");
            for (int j = 0; j < width; j++){
                if(mazeObjectList[j][i] == null)
                    sb.append(" ");
                else
                    sb.append(mazeObjectList[j][i].toString());
            }
            sb.append("|\n");
        }

        sb.append("+");
        for (int i = 0; i < width; i++){
            sb.append("-");
        }
        sb.append("+\n");
        String result = sb.toString();
        System.out.println(result);
        return result;
    }

    private void add(Position position, MazeObjectType type){
        MazeObject mazeObject = MazeObjectFactory.factoryMazeObject(position, type);
        mazeObjectList[position.getX()][position.getY()] = mazeObject;
    }

    private boolean isInside(Position position){
        return ( position.getX() >= 0 && position.getX() < width && position.getY() >= 0 && position.getY() < height);
    }

    private boolean isEmpty(Position position){
        if (isInside(position)){
            return mazeObjectList[position.getX()][position.getY()] == null;
        }
        return false;
    }

}
