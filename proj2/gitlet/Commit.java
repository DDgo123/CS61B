package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static gitlet.Repository.COMMIT_DIR;
import static gitlet.Utils.*;

/**
 * Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 * @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /**
     * The message of this Commit.
     */
    private String message;
    private List<String> parentsId;
    private final Date time;
    private String id;
    private Map<String, String> nameToBlobId = new TreeMap<>();


    /* TODO: fill in the rest of this class. */
    public Commit(String message, List<String> parents, Date time, Map<String, String> nameToBlobID) {
        this.message = message;
        this.parentsId = parents;
        this.time = time;
        this.id = generateID();
        this.nameToBlobId = nameToBlobID;

    }

    public void save() {

        Commit commit = this;
        File commitFile = join(COMMIT_DIR, id);
        writeObject(commitFile, commit);
    }

    public String TimeToString() {
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.US);
        return dateFormat.format(time);
    }

    public String getParentsId() {
        if (parentsId.isEmpty()){
            return  null;
        }

        return parentsId.get(0);
    }

    private String generateID() {
        String sha1 = sha1(this.TimeToString(), message, parentsId.toString());
        return sha1;
    }

    public String getId() {
        return new String(id);
    }

    public static Commit read(String commitId) {
        Commit commit = readObject(join(COMMIT_DIR, commitId), Commit.class);
        return commit;
    }

    public String toString() {
        String log = String.format("===\ncommit %s\n", id);
        log += String.format("Date: %s\n", TimeToString());
        log += String.format("%s\n", message);
        return log;
    }

    public String getMessage() {
        return new String(message);
    }

    public Map<String, String> getFileMap() {
        return nameToBlobId;

    }

    public Boolean containsBlob(String blob) {
        return nameToBlobId.containsValue(blob);
    }

    public Boolean containsFile(String fileName) {
        return nameToBlobId.containsKey(fileName);
    }


}
