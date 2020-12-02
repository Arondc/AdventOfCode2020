package adventofcode.dayofchristmas;

import adventofcode.elfs.FileElf;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * https://adventofcode.com/2020/day/1
 */
public class First {

    public static void main(String[] args) throws Exception {
        ArrayList<Long> longs = FileElf.getLongsFromFile("FirstInput.txt");

        First first = new First();

        System.out.println(first.startPaare(longs));
        System.out.println(first.startTriple(longs));
    }

    /**
     * Part 1
     *
     * @param longs Zahlen aus denen zwei die Summe 2020 bilden sollen
     * @return Produkt der zwei gefundenen Zahlen oder -1 wenn es keine Lösung gab
     */
    public Long startPaare(ArrayList<Long> longs) {
        List<Long> summenLongs = longs.stream()
                .filter(l -> longs.contains(2020l - (long) l))
                .collect(Collectors.toList());
        if (summenLongs.isEmpty()) {
            return -1l;
        }

        System.out.println(summenLongs.get(0) + " " + summenLongs.get(1));
        return summenLongs.get(0) * summenLongs.get(1);
    }

    /**
     * Part 2
     *
     * @param longs Zahlen aus denen drei die Summe 2020 bilden sollen
     * @return Produkt der drei gefundenen Zahlen oder -1 wenn es keine Lösung gab
     */
    public Long startTriple(ArrayList<Long> longs) {
        for (int i1 = 0; i1 < longs.size(); i1++) {
            for (int i2 = i1 + 1; i2 < longs.size(); i2++) {
                for (int i3 = i2 + 1; i3 < longs.size(); i3++) {
                    if (longs.get(i1) + longs.get(i2) + longs.get(i3) == 2020l) {
                        System.out.println(longs.get(i1) + " " + longs.get(i2) + " " + longs.get(i3));
                        return longs.get(i1) * longs.get(i2) * longs.get(i3);
                    }
                }
            }
        }
        return -1l;
    }
}
