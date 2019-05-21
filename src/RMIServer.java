import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RMIServer {
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        IMazeHub iMazeHub = new IMazeHubImpl();

        LocateRegistry.createRegistry(Constants.MAZE_HUB_PORT);

        Naming.rebind(Constants.MAZE_HUB_BIND_URL, iMazeHub) ;
    }
}
