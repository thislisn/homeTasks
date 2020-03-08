package mentoring.homeTask1;

import lombok.Data;

@Data
public class Rectangle {
    private Coordinate leftTopCorner;
    private Coordinate rightTopCorner;
    private Coordinate leftBottomCorner;
    private Coordinate rightBottomCorner;

    boolean isAllCornersFound() {
        return leftBottomCorner != null
                && rightBottomCorner != null
                && leftTopCorner != null
                && rightTopCorner != null;
    }

    @Override
    public String toString() {
        return String.format("Left top = %s, Right top = %s, Left bottom = %s, Right bottom = %s",
                getLeftTopCorner().toString(), getRightTopCorner().toString(), getLeftBottomCorner().toString(), getRightBottomCorner().toString());
    }
}
