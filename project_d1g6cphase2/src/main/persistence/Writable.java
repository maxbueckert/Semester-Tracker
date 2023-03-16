package persistence;

import org.json.JSONObject;

public interface Writable {
    // * This code was copied from CPSC210 WorkRoomApp
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
