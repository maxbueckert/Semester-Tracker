package persistence;

import model.Semester;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Represents a writer that writes JSON representation of semester to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // * This code was copied from CPSC210 WorkRoomApp
    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // * This code was copied from CPSC210 WorkRoomApp
    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // * This code was adapted from CPSC210 WorkRoomApp
    // MODIFIES: this
    // EFFECTS: writes JSON representation of semester to file
    public void write(Semester semester) {
        JSONObject json = semester.toJson();
        saveToFile(json.toString(TAB));
    }

    // * This code was copied from CPSC210 WorkRoomApp
    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // * This code was copied from CPSC210 WorkRoomApp
    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
