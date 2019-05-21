public class Constants {

    public static final int MAZE_PORT = 5000;

    public static final int MAZE_HUB_PORT = 5001;

    public static final String MAZE_BIND_URL = "rmi://localhost:" + MAZE_PORT +"/messageService";

    public static final String MAZE_HUB_BIND_URL = "rmi://localhost:" + MAZE_HUB_PORT +"/messageService";
}
