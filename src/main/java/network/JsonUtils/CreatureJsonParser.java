package network.JsonUtils;

import gameLogic.Invocator.Card.Card;
import gameLogic.Receptors.Creature;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreatureJsonParser {
    private final JsonUtil jsonUtil = new JsonUtil();

    public Creature parseJson(String jsonReceived) throws JSONException {
        JSONObject obj = new JSONObject(jsonUtil.getJsonContent(jsonReceived));

        String sprite = obj.getString("sprite");
        int HP = obj.getInt("healthpoints");
        int armor = obj.getInt("armor");
        int MP = obj.getInt("mp");
        int damage = obj.getInt("damages");
        boolean sleeping = obj.getBoolean("sleeping");

        Creature c = new Creature(sprite, HP, MP, damage);
        return c;
    }
}
