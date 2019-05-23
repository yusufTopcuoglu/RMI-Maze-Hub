import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class RMIClient {

    private static IMazeHub iMazeHub = null;
    private static IMaze selectedMaze = null;

    public static void main(String[] args) {
        boolean run = true;

        try {
            iMazeHub = (IMazeHub) Naming.lookup(Constants.MAZE_HUB_BIND_URL);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        ParsedInput parsedInput;
        String input;
        while( run ) {
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
                    deleteObject(parsedInput.getArgs());
                    break;
                case LIST_AGENTS:
                    listAgents();
                    break;
                case MOVE_AGENT:
                    moveAgent(parsedInput.getArgs());
                    break;
                case QUIT:
                    run = false;
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
        try {
            printOpResult(iMazeHub.removeMaze((int) args[0]));
            selectedMaze = null;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private static void moveAgent(Object[] args){
        int agentId = (int) args[0];
        Position targetPosition = new Position((int) args[1], (int) args[2]);
        try {
            printOpResult(selectedMaze.moveAgent(agentId, targetPosition));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private static void deleteObject(Object[] args){
        try {
            if (selectedMaze != null){
                printOpResult(selectedMaze.deleteObject( new Position ((int) args[0], (int) args[1])));
            } else {
                System.out.println("Selected Maze null");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    private static void selectMaze(Object[] args){
        try {
            selectedMaze = iMazeHub.getMaze((int) args[0]);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private static void printMaze(){
        try {
            if(selectedMaze != null)
                System.out.println(selectedMaze.print());
            else
                System.out.println("Selected Maze null");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private static void listAgents(){
        try {
            if(selectedMaze != null){
                Agent[] agents = selectedMaze.getAgents();
                for (Agent agent : agents) {
                    if(agent != null)
                        System.out.println(agent.print());
                }
            } else {
                System.out.println("Selected Maze null");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private static void createObject(Object[] args){
        try {
            if (selectedMaze != null){
                 printOpResult(selectedMaze.createObject(new Position((int) args[0], (int) args[1]), (MazeObjectType) args[2]));
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
