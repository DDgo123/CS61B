package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import static gitlet.Repository.STAGE_DIR;
import static gitlet.Utils.join;
import static gitlet.Utils.writeObject;

public class Stage implements Serializable {
    final File stageFile = join(STAGE_DIR, "stage");
    private final Map<String, String> addStage = new TreeMap<>();
    private final Set<String> removeStage = new TreeSet<>();
    private final Map<String, String> branchMap = new TreeMap<>();
    private String curBranch;

    public Stage(String branch, String head) {
        this.branchMap.put(branch, head);
    }

    public void save() {
        writeObject(stageFile, this);
        //writeContents(join(STAGE_DIR, "stage.txt"), addStage.toString(),
        // removeStage.toString(), branchMap.toString(), curBranch);
    }

    public void add(String key, String value) {
        addStage.put(key, value);
    }

    public String getBranchHead(String branchName) {
        return branchMap.get(branchName);
    }

    public void setHead(String branchName, String head) {
        branchMap.put(branchName, head);
    }

    public String getCurCommitId() {
        return branchMap.get(curBranch);
    }

    public String getCurBranch() {
        return curBranch;
    }

    public void setCurBranch(String branchName) {
        curBranch = branchName;
    }

    public void printStatus() {
        StringBuilder branches = new StringBuilder("=== Branches ===\n");
        for (String key : branchMap.keySet()) {
            if (key.equals(curBranch)) {
                key = String.format("*%s", key);
            }
            branches.append(String.format("%s\n", key));
        }
        StringBuilder stageFiles = new StringBuilder("=== Staged Files ===\n");
        for (String key : addStage.keySet()) {
            stageFiles.append(String.format("%s\n", key));
        }
        StringBuilder removleFiles = new StringBuilder("=== Removed Files ===\n");
        for (String key : removeStage) {
            removleFiles.append(String.format("%s\n", key));
        }
        String modifications = "=== Modifications Not Staged For Commit ===\n";
        String untracked = "=== Untracked Files ===\n";
        String[] stringsToPrint = {branches.toString(), stageFiles.toString(),
                removleFiles.toString(), modifications, untracked};
        for (String str : stringsToPrint) {
            System.out.println(str);
        }
    }

    public void clearAll() {
        addStage.clear();
        removeStage.clear();
    }

    public Set<String> getRmStage() {
        return removeStage;
    }

    public Map<String, String> getAddStage() {
        return addStage;
    }

    public boolean containsBranch(String branchName) {
        return branchMap.containsKey(branchName);
    }

    public void rmBranch(String branchName) {
        branchMap.remove(branchName);

    }
}

