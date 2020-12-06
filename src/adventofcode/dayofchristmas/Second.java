package adventofcode.dayofchristmas;

import adventofcode.elfs.FileElf;

import java.util.ArrayList;
import java.util.function.Function;

public class Second {

    private static final Function<Rule, Boolean> ruleOne = r -> {
        long letterCount = r.getPassword().chars().boxed().filter(c -> c.equals(Integer.valueOf(r.getLetter()))).count();
        return letterCount >= r.getMin() && letterCount <= r.getMax();
    };

    private static final Function<Rule, Boolean> ruleTwo = r -> {
        char[] chars = r.getPassword().toCharArray();
        return chars[r.getMin() - 1] == r.getLetter() ^ chars[r.getMax() - 1] == r.getLetter();
    };

    public static void main(String[] args) {
        ArrayList<String> lines = FileElf.getLinesFromFile("SecondInput.txt");
        Second second = new Second();
        System.out.println(second.start(lines, ruleOne));
        System.out.println(second.start(lines, ruleTwo));
    }

    public long start(ArrayList<String> lines, Function<Rule, Boolean> check) {
        return lines.stream().filter(l -> check.apply(new Rule(l))).count();
    }

    private class Rule {

        private Integer min;
        private Integer max;
        private Character letter;
        private String password;

        public Rule(String pwRule) {
            String[] tokens = pwRule.split(" ");
            String[] boundaries = tokens[0].split("-");
            min = Integer.valueOf(boundaries[0]);
            max = Integer.valueOf(boundaries[1]);
            letter = tokens[1].replace(":", "").charAt(0);
            password = tokens[2].trim();
        }

        @Override
        public String toString() {
            return "Rule{" +
                    "min=" + min +
                    ", max=" + max +
                    ", letter='" + letter + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }

        public Character getLetter() {
            return letter;
        }

        public Integer getMax() {
            return max;
        }

        public Integer getMin() {
            return min;
        }

        public String getPassword() {
            return password;
        }
    }

}
