package gitlet;

import static gitlet.Repository.exitWithMessage;

/**
 * Driver class for Gitlet, a subset of the Git version-control system.
 *
 * @author TODO
 */
public class Main {

    /**
     * Usage: java gitlet.Main ARGS, where ARGS contains
     * <COMMAND> <OPERAND1> <OPERAND2> ...
     */
    public static void main(String[] args) {
        validateNotEmptyArgs(args);
        // TODO: what if args is empty?
        String firstArg = args[0];
        switch (firstArg) {
            case "init":
                validateNumArgs(args, 1);
                Repository.init();
                break;
            case "add":
                validateCWD();
                validateNumArgs(args, 2);
                // TODO: handle the `add [filename]` command
                Repository.add(args[1]);
                break;
            case "commit":
                validateCWD();
                validateNumArgs(args, 2);
                // TODO: handle the `add [filename]` command
                Repository.commit(args[1]);
                break;
            case "rm":
                validateCWD();
                validateNumArgs(args, 2);
                Repository.rm(args[1]);
                break;
            case "log":
                validateCWD();
                validateNumArgs(args, 1);
                Repository.log();
                break;
            case "global-log":
                validateCWD();
                validateNumArgs(args, 1);
                Repository.globalLog();
                break;
            case "find":
                validateCWD();
                validateNumArgs(args, 2);
                Repository.find(args[1]);
                break;
            case "status":
                validateCWD();
                validateNumArgs(args, 1);
                Repository.status();
                break;
            case "checkout":
                validateCWD();
                Repository.checkout(args);
                break;
            case "branch":
                validateCWD();
                validateNumArgs(args, 2);
                Repository.branch(args[1]);
                break;
            case "rm-branch":
                validateCWD();
                validateNumArgs(args, 2);
                Repository.rmBranch(args[1]);
                break;
            case "reset":
                validateCWD();
                validateNumArgs(args, 2);
                Repository.reset(args[1]);
                break;
            case "merge":
                validateCWD();
                validateNumArgs(args, 2);
                Repository.merge(args[1]);
                break;
            default:
                validateCWD();
                exitWithMessage("No command with that name exists.");
                break;
            // TODO: FILL THE REST IN
        }

    }

    public static void validateNotEmptyArgs(String[] args) {
        if (args.length == 0) {
            exitWithMessage("Please enter a command.");
        }
    }

    public static void validateNumArgs(String[] args, int n) {
        if (args.length != n) {
            exitWithMessage("Incorrect operands.");
        }
    }

    public static void validateCWD() {
        if (!Repository.GITLET_DIR.exists()) {
            exitWithMessage("Not in an initialized Gitlet directory.");
        }
    }
}
