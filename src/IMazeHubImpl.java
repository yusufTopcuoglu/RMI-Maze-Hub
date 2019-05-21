import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class IMazeHubImpl extends UnicastRemoteObject implements IMazeHub, Serializable {

    private List<IMaze> iMazeList = new ArrayList<>();
    private IMaze selectedMaze = null;

    protected IMazeHubImpl() throws RemoteException {}

    @Override
    public void createMaze(int width, int height) throws RemoteException {
        IMaze iMaze = new IMazeImpl();
        iMaze.create(height, width);

        iMazeList.add(iMaze);
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

    @Override
    public String printSelectedMaze() throws RemoteException {
        if (selectedMaze != null){
            try {
                return  selectedMaze.print();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Select a Maze first");
        return "";
    }

    @Override
    public void selectMaze(int index) throws RemoteException {
        if (index >= 0 && index < iMazeList.size()){
            selectedMaze = iMazeList.get(index);
        } else {
            System.out.println("could not select maze, index out of bound");
        }
    }
}
