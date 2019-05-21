import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class RMIClient {

    private static IMazeHub iMazeHub = null;
    private static int selectMazeIndex = -1;

    public static void main(String[] args) {

        try {
            iMazeHub = (IMazeHub) Naming.lookup(Constants.MAZE_HUB_BIND_URL);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        ParsedInput parsedInput = null;
        String input;
        while( true ) {
            input = scanner.nextLine();
            try {
                parsedInput = ParsedInput.parse(input);
            }
            catch (Exception ex) {
                parsedInput = null;
            }
            if ( parsedInput == null ) {
                System.out.println("Wrong input format. Try again.");
                continue;
            }
            switch(parsedInput.getType()) {
                case CREATE_MAZE:
                    createMaze(parsedInput.getArgs());
                    break;
                case DELETE_MAZE:
                    deleteMaze(parsedInput.getArgs());
                    break;
                case SELECT_MAZE:
                    selectMaze(parsedInput.getArgs());
                    break;
                case PRINT_MAZE:
                    printMaze();
                    break;
                case CREATE_OBJECT:
                    createObject(parsedInput.getArgs());
                    break;
                case DELETE_OBJECT:
                    break;
                case LIST_AGENTS:
                    listAgents();
                    break;
                case MOVE_AGENT:
                    break;
                case QUIT:
                    break;
            }
        }
    }

    private static void createMaze(Object[] args){
        try {
            if (iMazeHub != null) {
                iMazeHub.createMaze((int) args[0],(int) args[1]);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private static void deleteMaze(Object[] args){

    }

    private static void selectMaze(Object[] args){
        selectMazeIndex = (int) args[0];
    }

    private static void printMaze(){
        try {
            IMaze iMaze = iMazeHub.getMaze(selectMazeIndex);
            if(iMaze != null)
                System.out.println(iMaze.print());
            else
                System.out.println("Selected Maze index out of bounds");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private static void listAgents(){
        try {
            IMaze iMaze = iMazeHub.getMaze(selectMazeIndex);
            if(iMaze != null){
                Agent[] agents = iMaze.getAgents();
                for (int i = 0; i < agents.length; i++){
                    System.out.println(agents[i].print());
                }
            } else {
                System.out.println("Selected Maze index out of bounds");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private static void createObject(Object[] args){
        try {
            IMaze iMaze = iMazeHub.getMaze(selectMazeIndex);
            if (iMaze != null){
                 printOpResult(iMaze.createObject(new Position((int) args[0], (int) args[1]), (MazeObjectType) args[2]));
            } else {
                System.out.println("Selected Maze index out of bounds");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    private static void printOpResult(boolean opResult){
        if (opResult)
            System.out.println("Operation Success.");
        else
            System.out.println("Operation Failed.");
    }
}
