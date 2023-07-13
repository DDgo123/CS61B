package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static gitlet.Repository.COMMIT_DIR;
import static gitlet.Utils.*;


public class Commit implements Serializable {

    private final Date time;
    private final String message;
    private final List<String> parentsId;
    private final String id;
    private Map<String, String> nameToBlobId = new TreeMap<>();


    public Commit(String message, List<String> parents, Date time, Map<String, String> nameToBlobID) {
        this.message = message;
        this.parentsId = parents;
        this.time = time;
        this.id = generateID();
        this.nameToBlobId = nameToBlobID;

    }

    public static Commit read(String commitId) {
        if (commitId.length() < 40) {
            List<String> commitIdList = Utils.plainFilenamesIn(COMMIT_DIR);
            assert commitIdList != null;
            for (String fullCommitId : commitIdList) {
                if (fullCommitId.startsWith(commitId)) {
                    commitId = fullCommitId;
                    break;
                }
            }
        }
        File commitfile = join(COMMIT_DIR, commitId);
        if (!commitfile.exists()) {
            return null;
        } else {
            Commit commit = readObject(join(COMMIT_DIR, commitId), Commit.class);
            return commit;
        }
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
        if (parentsId.isEmpty()) {
            return null;
        }

        return parentsId.get(0);
    }

    private String generateID() {
        String sha1 = sha1(this.TimeToString(), message, parentsId.toString());
        return sha1;
    }

    public String getId() {
        return id;
    }

    public String toString() {
        String log = String.format("===\ncommit %s\n", id);
        log += String.format("Date: %s\n", TimeToString());
        log += String.format("%s\n", message);
        return log;
    }

    public String getMessage() {
        return message;
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
