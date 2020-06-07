
import Client.View.GameBoard;

import java.io.*;
import Common.GameBoard.Board;
import Common.Receptors.Creature;
import Server.Game.Card.Card;
import Server.Game.Card.CardType;
import Server.Game.Card.Commands.CommandName;
import Server.Game.Card.Commands.CreateCreature;
import Server.Game.ModelClasses.GameClient;
import Server.Game.ModelClasses.Macro;

public class Launcher {
    private static GameBoard gameBoard; //front-end GUI
    private static Board board; //back-end LOGIC

    public static void main(String[] args) throws IOException {
        System.out.println("Hello, I am an awesome game !");
        //GameClient gameCreator = gameCreatorFromFile("src/main/resources/cards.json");
        //gameCreator.createCommands();

        Card pierCard = new Card("pier", CardType.CREATURE, 2, new CommandName[]{CommandName.CREATE_CREATURE});
        Creature pier = new Creature("Pier", 12, 2, 4,null);

        CreateCreature createCreature = new CreateCreature(pier);
        pierCard.setCommand(new Macro(new CreateCreature[]{createCreature}));
        pierCard.play();
        pier.setOriginCard(pierCard);

        System.out.println(pier);

        //initGame();
    }

    private static void initGame() throws IOException {
        /*
        board = new Board();
        gameBoard = new GameBoard();
        gameBoard.start();
        */
    }

    private static GameClient gameCreatorFromFile(String fileName) {
        try {
            BufferedReader buf = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileName)
                    )
            );

            String line = buf.readLine(); StringBuilder sb = new StringBuilder();
            while(line != null) {
                sb.append(line).append("\n");
                line = buf.readLine();
            }
            String fileAsString = sb.toString();
            return new GameClient(fileAsString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
