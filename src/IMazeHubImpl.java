import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class IMazeHubImpl extends UnicastRemoteObject implements IMazeHub, Serializable {

    private List<IMaze> iMazeList = new ArrayList<>();

    protected IMazeHubImpl() throws RemoteException {}

    @Override
    public void createMaze(int width, int height) throws RemoteException {
        if (height > 0 && width > 0){
            IMaze iMaze = new IMazeImpl();
            iMaze.create(height, width);
            iMazeList.add(iMaze);
        }
    }

    @Override
    public IMaze getMaze(int index) throws RemoteException {
        if(index < 0 || index >= iMazeList.size())
            return null;
        return iMazeList.get(index);
    }

    @Override
    public boolean removeMaze(int index) throws RemoteException {
        if(index < 0 || index >= iMazeList.size())
            return false;
        iMazeList.remove(index);
        return true;
    }
}
