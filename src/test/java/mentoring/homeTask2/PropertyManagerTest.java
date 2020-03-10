package mentoring.homeTask2;

import mentoring.homeTask2.exceptions.DuplicateInPropertyFileWasFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import static mentoring.homeTask2.PropertyManager.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PropertyManagerTest {
    final private PropertyManager propertyManager = new PropertyManager();
    Path oldPath = Paths.get("configOld.properties");
    Path path = Paths.get("config.properties");

    @Before
    public void setUp() throws IOException {
        clear();
        Properties prop = new Properties();
        prop.setProperty("12", "54");
        prop.setProperty("13", "41");
        prop.setProperty("14", "56");
        prop.setProperty("15", "92");
        prop.setProperty("16", "2");

        //uncomment to fail
//        prop.setProperty("15", "56");
        Files.createFile(oldPath);

        assertThat(String.format("File not exists %s", PROPERTY_FILE_PATH),
                Files.exists(oldPath),
                is(equalTo(true)));

        assertThat(String.format("File is not writable %s", PROPERTY_FILE_PATH),
                Files.isWritable(oldPath),
                is(equalTo(true)));
        propertyManager.writeToPropertyFile(prop, PROPERTY_FILE_PATH);
    }


    void clear() throws IOException {
        Files.deleteIfExists(oldPath);
        assertThat(String.format("File still exists %s", PROPERTY_FILE_PATH),
                Files.exists(oldPath),
                is(equalTo(false)));

        Files.deleteIfExists(path);
        assertThat(String.format("File still exists %s", PROPERTY_FILE_PATH_NEW),
                Files.exists(path),
                is(equalTo(false)));
    }

    @Test
    public void testCombination() throws DuplicateInPropertyFileWasFoundException, IOException {
        assertThat(String.format("File is not readable %s", PROPERTY_FILE_PATH),
                Files.isReadable(oldPath),
                is(equalTo(true)));

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
        Files.createFile(path);

        assertThat(String.format("File not exists %s", PROPERTY_FILE_PATH),
                Files.exists(path),
                is(equalTo(true)));

        assertThat(String.format("File is not writable %s", PROPERTY_FILE_PATH_NEW),
                Files.isWritable(path),
                is(equalTo(true)));

        Files.write(path, () -> sortMap.entrySet().stream()
                .<CharSequence>map(e -> e.getKey() + getRandomDelimiter() + e.getValue()).iterator());

        assertThat(String.format("File owner is not same %s", PROPERTY_FILE_PATH_NEW),
                Files.getOwner(oldPath),
                is(equalTo(Files.getOwner(path))));
        assertThat(String.format("File store is not same %s", PROPERTY_FILE_PATH_NEW),
                Files.getFileStore(oldPath),
                is((Files.getFileStore(path))));
        File file = new File(path.toString());
        File oldFile = new File(oldPath.toString());

        assertThat(String.format("File store is not same %s", PROPERTY_FILE_PATH_NEW),
                file.getUsableSpace(),
                is((oldFile.getUsableSpace())));
    }


}



