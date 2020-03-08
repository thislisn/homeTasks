package mentoring.homeTask1;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class Coordinate {
    private final int x;
    private final int y;

    @Override
    public String toString() {
        return String.format("{x=%S, y=%s}", getX(), getY());
    }
}
