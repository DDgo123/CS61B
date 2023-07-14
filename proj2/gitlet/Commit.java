package gitlet;


import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static gitlet.Repository.COMMIT_DIR;
import static gitlet.Utils.*;


public class Commit implements Serializable {

    private final Date time;
    private final String message;
    private final List<String> parentsId;
    private final String id;
    private final Map<String, String> nameToBlobId;


    public Commit(String message, List<String> parents, Date time, Map<String, String> map) {
        this.message = message;
        this.parentsId = parents;
        this.time = time;
        this.id = generateID();
        this.nameToBlobId = map;

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
            return readObject(join(COMMIT_DIR, commitId), Commit.class);
        }
    }

    public void save() {

        Commit commit = this;
        File commitFile = join(COMMIT_DIR, id);
        writeObject(commitFile, commit);
    }

    public String timeToString() {
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.US);
        return dateFormat.format(time);
    }

    public String getFirstParentId() {
        if (parentsId.isEmpty()) {
            return null;
        }
        return parentsId.get(0);
    }

    public List<String> getAllParentsId() {
        return parentsId;
    }

    private String generateID() {
        return sha1(this.timeToString(), message, parentsId.toString());
    }

    public String getId() {
        return id;
    }

    public String toString() {
        String log = String.format("===\ncommit %s\n", id);
        log += String.format("Date: %s\n", timeToString());
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
