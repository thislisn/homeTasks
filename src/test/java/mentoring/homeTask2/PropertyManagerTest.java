package mentoring.homeTask2;

import mentoring.homeTask2.exceptions.DuplicateInPropertyFileWasFoundException;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static mentoring.homeTask2.PropertyManager.PROPERTY_FILE_PATH;
import static mentoring.homeTask2.PropertyManager.PROPERTY_FILE_PATH_NEW;
import static mentoring.homeTask2.PropertyManager.getItemsOccurrenceInList;

public class PropertyManagerTest {
    final private PropertyManager propertyManager = new PropertyManager();

    @Before
    public void setUp() {
        Properties prop = new Properties();
        prop.setProperty("12", "54");
        prop.setProperty("13", "41");
        prop.setProperty("14", "56");
        prop.setProperty("15", "92");
        prop.setProperty("16", "2");

        //uncomment to fail
//        prop.setProperty("15", "56");
        propertyManager.writeToPropertyFile(prop, PROPERTY_FILE_PATH);
    }

    @Test
    public void testCombination() throws DuplicateInPropertyFileWasFoundException {
        Map<Integer, String> rowMap = propertyManager.readPropertyFile(PROPERTY_FILE_PATH);

        Map<String, Long> occurrenceMap = getItemsOccurrenceInList(new ArrayList<>(rowMap.values()));
        for (Map.Entry<String, Long> entry : occurrenceMap.entrySet())
            if (entry.getValue() > 1) {
                throw new DuplicateInPropertyFileWasFoundException(String.format("Duplicate value '%s' was found", entry.getKey()));
            }

        Map<Integer, String> swapMap = propertyManager.swapKeyValues(rowMap);
        Map<Integer, String> sortMap = propertyManager.sortMapByKey(swapMap);
        PropertyManager.SortedProperties sortProp = new PropertyManager.SortedProperties();
        for (Map.Entry<Integer, String> entry : sortMap.entrySet()) {
            sortProp.setProperty(entry.getKey().toString(), entry.getValue());
        }

        propertyManager.writeToPropertyFile(sortProp, PROPERTY_FILE_PATH_NEW);
    }
}



