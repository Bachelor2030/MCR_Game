
import java.io.*;

public class Launcher {
    public static void main(String[] args) {
        System.out.println("Hello, I am an awesome game !");
        GameCreator gameCreator = gameCreatorFromFile("src/main/resources/cards.json");


    }

    private static GameCreator gameCreatorFromFile(String fileName) {
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
            return new GameCreator(fileAsString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
