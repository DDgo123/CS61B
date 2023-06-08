package gh2;
import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

import java.security.PublicKey;

/**
 * A client that uses the synthesizer package to replicate a plucked guitar string sound
 */
public class GuitarHero {

    static String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    public static void main(String[] args) {
        GuitarString[] strings = new GuitarString[keyboard.length()];

        for (int i = 0; i < keyboard.length(); i++) {
            double frequency = 440 * Math.pow(2, (i - 24) / 12.0);
            strings[i] = new GuitarString(frequency);
        }

        while (true) {


            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index =keyboard.indexOf(key);
                if (index != -1) {
                    strings[index].pluck();
                }
            }
            double sample = 0;
            for (GuitarString string : strings) {
                sample += string.sample();
            }

            StdAudio.play(sample);

            for (GuitarString string : strings) {
                string.tic();
            }


            /* compute the superposition of samples */

        }
    }
}

