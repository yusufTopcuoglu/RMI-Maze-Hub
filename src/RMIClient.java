import java.util.Scanner;



public class RMIClient {
    public static void main(String[] args) {
        
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
                    break;
                case DELETE_MAZE:
                    break;
                case SELECT_MAZE:
                    break;
                case PRINT_MAZE:
                    break;
                case CREATE_OBJECT:
                    break;
                case DELETE_OBJECT:
                    break;
                case LIST_AGENTS:
                    break;
                case MOVE_AGENT:
                    break;
                case QUIT:
                    break;
            }
        }
    }
}
