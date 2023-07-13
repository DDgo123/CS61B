package gitlet;

import java.io.File;
import java.util.*;

import static gitlet.Utils.*;

public class Repository {

    public static final File CWD = new File(System.getProperty("user.dir"));

    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File COMMIT_DIR = join(GITLET_DIR, "commit");
    public static final File BLOB_DIR = join(GITLET_DIR, "blob");
    public static final File STAGE_DIR = join(GITLET_DIR, "stage");
    public static final File HEAD_DIR = join(GITLET_DIR, "head");
    public static final File BRANCH_DIR = join(GITLET_DIR, "branch");
    public static final File MASTER_DIR = join(BRANCH_DIR, "master");
    private static Stage stage;

    public static void init() {
        /*build gitlet and gitlet/objects dir */
        if (!GITLET_DIR.exists()) {
            createDir(GITLET_DIR, COMMIT_DIR, BLOB_DIR, STAGE_DIR,
                    HEAD_DIR, BRANCH_DIR, MASTER_DIR);
            // create dir
            Date initTime = new Date(0);
            List<String> emptyList = new ArrayList<>();
            TreeMap<String, String> emptyMap = new TreeMap<>();
            Commit init = new Commit("initial commit", emptyList, initTime, emptyMap);

            stage = new Stage("master", init.getId());
            stage.setCurBranch("master");
            stage.save();
            init.save();
        } else {
            exitWithMessage("A Gitlet version-control system already exists "
                    + "in the current directory.");
        }
    }

    private static void createDir(File... directories) {
        for (File directory : directories) {
            if (!directory.exists()) {
                directory.mkdir();
            }
        }
    }

    public static Stage loadStage() {
        return readObject(join(STAGE_DIR, "stage"), Stage.class);
    }

    public static Commit loadCurCommit() {
        stage = loadStage();
        return Commit.read(stage.getCurCommitId());
    }

    public static void add(String fileName) {
        File file = join(CWD, fileName);
        if (!file.exists()) {
            exitWithMessage("File does not exist.");
        }
        String contents = readContentsAsString(file);
        Blob blob = new Blob(fileName, contents);
        stage = loadStage();
        Commit curCommit = loadCurCommit();
        if (stage.getRmStage().contains(fileName)) {
            stage.getRmStage().remove(fileName);
            stage.save();
            return;
        }
        if (curCommit.containsBlob(blob.getId())) {
            //If the added file is the same version as the current commit
            stage.getAddStage().remove(fileName);
            stage.save();
            return;
        }
        blob.save();
        stage.add(fileName, blob.getId());

        stage.save();
        //printStage();
    }

    public static void commit(String message) {
        stage = loadStage();
        if (message.isEmpty()) {
            exitWithMessage("Please enter a commit message.");
        }
        if (stage.getRmStage().isEmpty() && stage.getAddStage().isEmpty()) {
            exitWithMessage("No changes added to the commit.");
        }
        Date curTime = new Date();
        Commit curCommit = loadCurCommit();
        List<String> preCommitId = new ArrayList<>(Collections.singleton(curCommit.getId()));
        Map<String, String> newMap = curCommit.getFileMap();
        newMap.putAll(stage.getAddStage());
        for (String value : stage.getRmStage()) {
            newMap.remove(value);
        }
        Commit newCommit = new Commit(message, preCommitId, curTime, newMap);
        newCommit.save();
        stage.setHead(stage.getCurBranch(), newCommit.getId());
        stage.clearAll();
        stage.save();
    }

    public static void rm(String filename) {
        stage = loadStage();
        Commit curCommit = loadCurCommit();
        if (stage.getAddStage().containsKey(filename)) {
            stage.getAddStage().remove(filename);
            stage.save();
            return;
        }
        if (curCommit.containsFile(filename)) {
            stage.getRmStage().add(filename);
            restrictedDelete(join(CWD, filename));
            stage.save();
            return;
        }
        exitWithMessage("No reason to remove the file.");

    }

    public static void log() {
        stage = loadStage();
        String commitId = stage.getCurCommitId();
        while (commitId != null) {
            Commit curCommit = Commit.read(commitId);
            System.out.println(curCommit);
            commitId = curCommit.getParentsId();
        }
    }

    public static void globalLog() {
        List<String> commitList = plainFilenamesIn(COMMIT_DIR);
        for (String commitId : commitList) {
            Commit curCommit = Commit.read(commitId);
            System.out.println(curCommit);
        }

    }

    public static void find(String commitMessage) {
        List<String> commitList = plainFilenamesIn(COMMIT_DIR);
        boolean find = false;
        for (String commitId : commitList) {
            Commit curCommit = Commit.read(commitId);
            if (curCommit.getMessage().equals(commitMessage)) {
                find = true;
                System.out.println(commitId);
            }
        }
        if (!find) {
            exitWithMessage("Found no commit with that message");
        }

    }

    public static void status() {
        stage = loadStage();
        stage.printStatus();
    }

    public static void checkout(String[] args) {
        stage = loadStage();
        if (args.length == 3 && args[1].equals("--")) {
            checkoutFile(stage.getCurCommitId(), args[2]);

        } else if (args.length == 4 && args[2].equals("--")) {
            checkoutFile(args[1], args[3]);
        } else if (args.length == 2) {
            if (stage.getCurBranch().equals(args[1])) {
                exitWithMessage("No need to checkout the current branch.");
            }
            if (!stage.containsBranch(args[1])) {
                exitWithMessage("No such branch exists.");
            }
            checkoutBranch(args[1]);

        } else {
            exitWithMessage("Incorrect operands.");
        }

    }

    private static void checkoutBranch(String branchName) {
        stage = loadStage();
        String commitId = stage.getBranchHead(branchName);
        checkoutCommit(commitId);
        stage.clearAll();
        stage.setCurBranch(branchName);
        stage.save();
    }

    public static void branch(String branchName) {
        stage = loadStage();
        if (stage.containsBranch(branchName)) {
            exitWithMessage("A branch with that name already exists.");
        }
        stage.setHead(branchName, stage.getCurCommitId());
        stage.save();
    }

    public static void rmBranch(String branchName) {
        stage = loadStage();
        if (!stage.containsBranch(branchName)) {
            exitWithMessage("A branch with that name does not exist.");
        }
        if (stage.getCurBranch().equals(branchName)) {
            exitWithMessage("Cannot remove the current branch.");
        }
        stage.rmBranch(branchName);
        stage.save();
    }

    public static void reset(String commitId) {
        if (!join(COMMIT_DIR, commitId).exists()) {
            exitWithMessage("No commit with that id exists.");
        }
        stage = loadStage();
        checkoutCommit(commitId);
        stage.setHead(stage.getCurBranch(), commitId);
        stage.clearAll();
        stage.save();

    }

    public static void merge(String arg) {

    }


    private static void checkoutCommit(String commitId) {
        stage = loadStage();
        Commit targetCommit = Commit.read(commitId);
        List<String> untrackFileList = getUntrackFileList();
        for (String fileName : targetCommit.getFileMap().keySet()) {
            if (untrackFileList.contains(fileName)) {

                exitWithMessage("There is an untracked file in the way; delete it,"
                        + " or add and commit it first.");
            }
            checkoutFile(commitId, fileName);
        }
        checkoutCommitDelete(commitId);
    }

    private static List<String> getUntrackFileList() {
        stage = loadStage();
        Commit curCommit = Commit.read(stage.getCurCommitId());
        List<String> result = new ArrayList<>();
        List<String> cwdList = plainFilenamesIn(CWD);
        for (String fileName : cwdList) {
            boolean staged = stage.getAddStage().containsKey(fileName);
            boolean tracked = curCommit.getFileMap().containsKey(fileName);
            if ((!staged && !tracked)) {
                result.add(fileName);
            }
        }
        return result;
    }

    private static void checkoutCommitDelete(String targetCommitId) {

        Commit targetCommit = Commit.read(targetCommitId);
        Commit curCommit = loadCurCommit();
        Set targetCommitFileSet = targetCommit.getFileMap().keySet();
        for (String fileName : curCommit.getFileMap().keySet()) {
            if (!targetCommitFileSet.contains(fileName)) {
                join(CWD, fileName).delete();
            }
        }
    }

    private static void checkoutFile(String commitId, String fileName) {
        stage = loadStage();
        Commit curCommit = Commit.read(commitId);
        if (curCommit == null) {
            exitWithMessage("No commit with that id exists.");
        }
        String blobId = curCommit.getFileMap().get(fileName);
        if (blobId == null) {
            exitWithMessage("File does not exist in that commit.");
        }
        Blob blob = Blob.read(join(BLOB_DIR, blobId));
        writeContents(join(CWD, fileName), blob.getContents());

    }


    static void message(String msg, Object... args) {
        System.out.printf(msg, args);
        System.out.println();
    }

    static void exitWithMessage(String msg) {
        message(msg);
        System.exit(0);
    }


}
