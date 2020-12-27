package adventofcode.dayofchristmas;

import adventofcode.elfs.FileElf;

import java.util.*;

public class Fourth {
    public static void main(String[] args) {
        ArrayList<String> lines = FileElf.getLinesFromFile("FourthInput.txt");

        Fourth fourth = new Fourth();

        int numberOfValidPassports = fourth.start(lines, Arrays.asList("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"));

        System.out.println("Solution Part 1 :" + numberOfValidPassports);

        int numberOfValidPassportsEnhanced = fourth.startEnhanced(lines,
                Arrays.asList(birthYearValidator, issueYearValidator, expirationYearValidator, heightValidator,
                        hairColorValidator, eyeColorValidator, passportIdValidator));

        System.out.println("Solution Part 2 :" + numberOfValidPassportsEnhanced);

    }

    private int start(List<String> lines, List<String> requiredElements) {
        List<Passport> passports = new PassportBuilder().buildPassports(lines);
        return new PassportValidator().validatePassports(passports, requiredElements);
    }

    private int startEnhanced(List<String> lines, List<PassportFieldValidator> validators) {
        List<Passport> passports = new PassportBuilder().buildPassports(lines);
        return new PassportValidator().validatePassportsEnhanced(passports, validators);
    }

    public static class PassportBuilder {
        public List<Passport> buildPassports(List<String> lines) {
            List<Passport> passports = new ArrayList<>();

            List<String> currentPassportLines = new ArrayList<>();
            for (String line : lines) {
                if (line.isBlank()) {
                    passports.add(new Passport(currentPassportLines));
                    currentPassportLines.clear();
                }

                if (!line.isBlank()) {
                    currentPassportLines.add(line);
                }
            }

            //the last passport isn't terminated by a blank line
            if (!currentPassportLines.isEmpty()) {
                passports.add(new Passport(currentPassportLines));
            }

            return passports;
        }
    }

    public static class PassportValidator {
        //Start for part 1 of the puzzle (simple validation)
        public int validatePassports(List<Passport> passports, List<String> requiredElements) {
            int validPassports = 0;
            for (Passport passport : passports) {
                boolean passportIsValid = requiredElements.stream().allMatch(req -> passport.getPassportData().containsKey(req));

                validPassports += passportIsValid ? 1 : 0;
            }
            return validPassports;
        }

        //Start for part 2 of the puzzle (enhanced validation)
        public int validatePassportsEnhanced(List<Passport> passports, List<PassportFieldValidator> validators) {
            int validPassports = 0;
            for (Passport passport : passports) {
                boolean passportIsValid = false;

                try {
                    passportIsValid = validators.stream().allMatch(v -> v.validate(passport));
                } catch (Exception e) {
                    //if we catch any exception from a validator it most possibly is because of parsing errors -> invalid passport
                    //bad style in productive code -> at least log something in such cases!!!
                }

                validPassports += passportIsValid ? 1 : 0;
            }
            return validPassports;
        }
    }

    public static class Passport {
        private final Map<String, String> passportData = new HashMap<>();

        public Passport(List<String> data) {
            data.stream()
                    .flatMap(d -> Arrays.stream(d.split(" ")))
                    .forEach(kv -> {
                        String[] keyValue = kv.split(":");
                        passportData.put(keyValue[0], keyValue[1]);
                    });
        }

        public Map<String, String> getPassportData() {
            return passportData;
        }

        public String getField(String fieldName) {
            return passportData.get(fieldName);
        }
    }

    //Just to save a bit of typing work
    @FunctionalInterface
    public interface PassportFieldValidator {
        boolean validate(Passport passport);
    }

    private static final PassportFieldValidator birthYearValidator = p -> {
        Integer birthYear = Integer.valueOf(p.getField("byr"));
        return birthYear >= 1920 && birthYear <= 2002;
    };

    private static final PassportFieldValidator issueYearValidator = p -> {
        Integer issueYear = Integer.valueOf(p.getField("iyr"));
        return issueYear >= 2010 && issueYear <= 2020;
    };

    private static final PassportFieldValidator expirationYearValidator = p -> {
        Integer issueYear = Integer.valueOf(p.getField("eyr"));
        return issueYear >= 2020 && issueYear <= 2030;
    };

    private static final PassportFieldValidator heightValidator = p -> {
        String height = p.getField("hgt");
        if (height.length() < 4) {
            return false;
        }

        if (height.endsWith("cm")) {
            Integer heightValue = Integer.valueOf(height.substring(0, 3));
            return heightValue >= 150 && heightValue <= 193;
        } else if (height.endsWith("in")) {
            Integer heightValue = Integer.valueOf(height.substring(0, 2));
            return heightValue >= 59 && heightValue <= 76;
        }
        return false;
    };

    private static final PassportFieldValidator hairColorValidator = p -> p.getField("hcl").matches("#[0-9a-f]{6}");
    private static final PassportFieldValidator eyeColorValidator = p -> p.getField("ecl").matches("(amb|blu|brn|gry|grn|hzl|oth)");
    private static final PassportFieldValidator passportIdValidator = p -> p.getField("pid").matches("[0-9]{9}");

}
