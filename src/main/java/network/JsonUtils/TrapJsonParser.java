package network.JsonUtils;

import gameLogic.Invocator.Card.Card;
import gameLogic.Receptors.Creature;
import gameLogic.Receptors.Trap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrapJsonParser {
    private final JsonUtil jsonUtil = new JsonUtil();

    public Trap jsonParser(String jsonReceived) throws JSONException {
        JSONObject obj = new JSONObject(jsonUtil.getJsonContent(jsonReceived));

        String sprite = obj.getString("sprite");
        String effect = obj.getString("effect");

        Trap t = new Trap(sprite, effect);

        return t;
    }
}
