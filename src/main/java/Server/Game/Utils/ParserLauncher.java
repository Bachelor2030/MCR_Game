package Server.Game.Utils;

import Server.Game.Card.Card;
import Server.Game.Utils.Parsers.CardsJsonParser;
import org.json.JSONException;

import java.io.*;
import java.util.ArrayList;

public class ParserLauncher {
    public static void main(String[] args) {
        String file = "src/main/resources/cards.json";
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
            ArrayList<Card> cards = CardsJsonParser.parseJson(sb.toString());
            for (Card card : cards) {
                System.out.println(card);
            }

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
}
