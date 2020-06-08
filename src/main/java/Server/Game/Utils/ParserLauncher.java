package Server.Game.Utils;

import Server.Game.Game;
import org.json.JSONException;

import java.io.*;

public class ParserLauncher {
    public static void main(String[] args) {
        String file = "src/main/resources/game.json";

        Game game = parseJsonGame(file);
        game.startGame();
    }

    private static Game parseJsonGame(String file) {
        Game game = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader br = new BufferedReader( new InputStreamReader(fileInputStream));

            StringBuilder sb = new StringBuilder();
            String line;
            while(( line = br.readLine()) != null ) {
                sb.append( line );
                sb.append( '\n' );
            }

            System.out.println("Read " + file);
            game = GameJsonParser.parseJson(sb.toString(), "src/main/resources/");

            fileInputStream.close();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return game;
    }
}
