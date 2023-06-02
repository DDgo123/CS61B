package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeAListConstruction();
    }


    public static void timeAListConstruction() {
        AList<Integer> Ns = new AList<>();
        AList<Double> Times = new AList<>();
        AList<Integer> OpCounts = new AList<>();
        for (int N = 1000 ; N <= 128000; N *=2){
            AList<Integer> Test = new AList<>();
            Stopwatch sw =new Stopwatch();
            for (int i = 0;i < N;i++){
                Test.addLast(i);
            }
            double timeInSeconds = sw.elapsedTime();
            Ns.addLast(N);
            Times.addLast(timeInSeconds);
            OpCounts.addLast(N);
        }
        printTimingTable(Ns,Times,OpCounts);
    }
}
