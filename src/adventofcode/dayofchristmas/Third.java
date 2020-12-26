package adventofcode.dayofchristmas;

import adventofcode.elfs.FileElf;

public class Third {
    public static void main(String[] args) {
        char[][] map = FileElf.getCharArraysFromFile("ThirdInput.txt");

        Third third = new Third();

        long countTreesHitSlope1 = third.treesHit(map, 1, 1);
        long countTreesHitSlope2 = third.treesHit(map, 3, 1);
        long countTreesHitSlope3 = third.treesHit(map, 5, 1);
        long countTreesHitSlope4 = third.treesHit(map, 7, 1);
        long countTreesHitSlope5 = third.treesHit(map, 1, 2);

        System.out.println("Solution Part 1 :" + countTreesHitSlope2);
        System.out.println("Solution Part 2 :" + (countTreesHitSlope1 * countTreesHitSlope2 * countTreesHitSlope3 * countTreesHitSlope4 * countTreesHitSlope5));
    }

    private long treesHit(char[][] map, int horizontalStep, int verticalStep) {
        int mapWidth = map[0].length;

        int horizontalCoordinate = 0;
        int verticalCoordinate = 0;
        long treeCount = 0;

        while (verticalCoordinate < map.length) {
            treeCount += map[verticalCoordinate][horizontalCoordinate] == '#' ? 1 : 0;
            horizontalCoordinate = (horizontalCoordinate + horizontalStep) % mapWidth;
            verticalCoordinate += verticalStep;
        }
        return treeCount;
    }


}
