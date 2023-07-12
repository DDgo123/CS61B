package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import static gitlet.Repository.STAGE_DIR;
import static gitlet.Utils.*;

public class Stage implements Serializable {
    private Map<String, String> addStage = new TreeMap<>();
    private Set<String> removeStage = new TreeSet<>();
    File stageFile = join(STAGE_DIR, "stage");
    private String curBranch;
    private Map<String, String> branchMap = new TreeMap<>();

    public Stage(String branch, String head) {
        this.branchMap.put(branch, head);
    }

    public void save() {
        writeObject(stageFile, this);
        //writeContents(join(STAGE_DIR, "stage.txt"), addStage.toString(), removeStage.toString(), branchMap.toString(), curBranch);
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

    public void setCurBranch(String branchName) {
        curBranch = branchName;
    }

    public String getCurCommitId() {
        return branchMap.get(curBranch);
    }

    public String getCurBranch() {
        return curBranch;
    }


    public void printStatus() {
        String branches = "=== Branches ===\n";
        for (String key : branchMap.keySet()) {
            if (key.equals(curBranch)) {
                key = String.format("*%s", key);
            }
            branches += String.format("%s\n", key);
        }
        String stageFiles = "=== Staged Files ===\n";
        for (String key : addStage.keySet()) {
            stageFiles += String.format("%s\n", key);
        }
        String removleFiles = "=== Removed Files ===\n";
        for (String key : removeStage) {
            removleFiles += String.format("%s\n", key);
        }
        String modifications = "=== Modifications Not Staged For Commit ===\n";
        String untracked = "=== Untracked Files ===\n";
        String[] stringsToPrint = {branches, stageFiles, removleFiles, modifications, untracked};
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

