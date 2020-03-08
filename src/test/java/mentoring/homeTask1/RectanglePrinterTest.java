package mentoring.homeTask1;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static mentoring.homeTask1.RectanglePrinter.TwoDimensionalArray.printArray;
import static mentoring.homeTask1.RectanglePrinter.drawBackground;
import static mentoring.homeTask1.RectanglePrinter.drawRectangle;
import static mentoring.homeTask1.RectanglePrinter.getRectangles;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static mentoring.homeTask1.RectanglePrinter.*;

@RunWith(Parameterized.class)
public class RectanglePrinterTest {

    @Parameterized.Parameters(name
            = "{index}: Test with count of drawing rectangles")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                { Arrays.asList(new RectangleSides(new Side(2, 10), new Side(2, 10)),
                        new RectangleSides(new Side(12, 17), new Side(13, 17)),
                        new RectangleSides(new Side(19, 20), new Side(3, 25))),
                        3
                },
                { Arrays.asList(new RectangleSides(new Side(2, 10), new Side(2, 10)),
                        new RectangleSides(new Side(12, 17), new Side(13, 17)),
                        new RectangleSides(new Side(19, 20), new Side(3, 25)),
                        new RectangleSides(new Side(22,23), new Side(4, 8))),
                        4
                },
        });
    }

    private final List<RectangleSides> rectangleSides;
    private final int expected;

    public RectanglePrinterTest(List<RectangleSides> rectangleSides, int expected) {
        this.rectangleSides = rectangleSides;
        this.expected = expected;
    }

    @Before
    public void setUp() {
        RectanglePrinter.TwoDimensionalArray.setArray(new char[25][25]);
        drawBackground();
    }

    @Test
    public void testCombination() {
        rectangleSides.forEach(rectangleSide ->
                drawRectangle(rectangleSide.getRows(), rectangleSide.getColumns()));
        printArray();
        assertThat(getRectangles().size(), is(expected));
    }
}



