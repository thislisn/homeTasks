package mentoring.homeTask1;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class RectanglePrinter {

    private final static char CHAR_RECTANGLE = '1';
    private final static char CHAR_BACKGROUND = '0';

    static void drawBackground() {
        char[][] background = TwoDimensionalArray.getArray();
        for (char[] line : background) {
            for (int i = 0; i < line.length; i++)
                line[i] = CHAR_BACKGROUND;
        }
        TwoDimensionalArray.setArray(background);
    }

    static void drawRectangle(Side rows, Side columns) {
        char[][] background = TwoDimensionalArray.getArray();
        for (int y = 0; y < background.length; y++) {
            char[] line = background[y];
            for (int x = 0; x < line.length; x++) {
                boolean isOnRow = (y >= rows.startDot) && (y <= rows.endDot);
                boolean isOnColumn = (x >= columns.startDot) && (x <= columns.endDot);
                if (isOnColumn && isOnRow) {
                    line[x] = CHAR_RECTANGLE;
                }
            }
        }
        TwoDimensionalArray.setArray(background);
    }

    static List<Rectangle> getRectangles() {
        char[][] array = TwoDimensionalArray.getArray();
        Rectangle rectangle = new Rectangle();
        List<Rectangle> foundRectangles = new ArrayList<>();

        for (int y = 0; y < array.length; y++) {
            for (int x = 0; x < array[y].length; x++) {
                Coordinate coordinate = new Coordinate(x, y);
                if (isOnLeftTopCorner(array, x, y)) {
                    rectangle.setLeftTopCorner(coordinate);
                } else if (isOnRightTopCorner(array, x, y) && rectangle.getLeftTopCorner().getY() == y) {
                    rectangle.setRightTopCorner(coordinate);
                } else if (isOnLeftBottomCorner(array, x, y) && rectangle.getLeftTopCorner().getX() == x) {
                    rectangle.setLeftBottomCorner(coordinate);
                } else if (isOnRightBottomCorner(array, x, y)
                        && rectangle.getLeftBottomCorner().getY() == y
                        && rectangle.getRightTopCorner().getX() == x) {
                    rectangle.setRightBottomCorner(coordinate);
                }
                if (rectangle.isAllCornersFound()) {
                    foundRectangles.add(rectangle);
                    System.out.println(String.format("found rectangle: %s", rectangle.toString()));
                    rectangle = new Rectangle();
                }
            }
        }
        return foundRectangles;
    }

    private static boolean isOnLeftTopCorner(char[][] array, int x, int y) {
        return array[y][x] == CHAR_RECTANGLE
                && isBackground(array, x - 1, y)
                && isBackground(array, x, y - 1);
    }

    private static boolean isBackground(char[][] array, int x, int y) {
        boolean b;
        try {
            b = array[y][x] == CHAR_BACKGROUND;
        } catch (IndexOutOfBoundsException e) {
            b = true;
        }
        return b;
    }

    private static boolean isOnRightTopCorner(char[][] array, int x, int y) {
        return array[y][x] == CHAR_RECTANGLE
                && isBackground(array, x + 1, y)
                && isBackground(array, x, y - 1);
    }

    private static boolean isOnLeftBottomCorner(char[][] array, int x, int y) {
        return array[y][x] == CHAR_RECTANGLE
                && isBackground(array, x - 1, y)
                && isBackground(array, x, y + 1);
    }

    private static boolean isOnRightBottomCorner(char[][] array, int x, int y) {
        return array[y][x] == CHAR_RECTANGLE
                && isBackground(array, x + 1, y)
                && isBackground(array, x, y + 1);
    }

    static class Side {
        private final int startDot;
        private final int endDot;

        Side(int startDot, int endDot) {
            this.startDot = startDot;
            this.endDot = endDot;
        }
    }

    @Getter
    static class RectangleSides{
        private final Side rows;
        private final Side columns;

        RectangleSides(Side rows, Side columns){
            this.rows = rows;
            this.columns = columns;
        }
    }

    static class TwoDimensionalArray {
        private static char[][] array;

        static char[][] getArray() {
            return array;
        }

        static void setArray(char[][] array) {
            TwoDimensionalArray.array = array;
        }

        static void printArray() {
            Arrays.stream(getArray())
                    .forEach(System.out::println);
        }
    }


}
