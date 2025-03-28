/**
	
	@author Jerold Luther P. Aquino (230413)
    @author Hanzo Ricardo M. Castillo (231365)
	@version May 14, 2024
	
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of my program.
**/

/* GameStarter class instantiates and runs the client to the main method.
*/

public class GameStarter {

	/**
	 * main method for the game client that runs all frame methods
	 * @param args
	 */
    public static void main(String[] args) {

        String host = args[0];
        int port = Integer.parseInt(args[1]);
        
        GameFrame gf = new GameFrame();

        gf.connectToServer(host, port);

        gf.setUpGUI();
        gf.addKeyBindings();
        gf.setUpTimeListen();

    }
}
