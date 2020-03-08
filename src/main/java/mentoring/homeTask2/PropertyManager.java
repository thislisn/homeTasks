package mentoring.homeTask2;

import mentoring.homeTask2.exceptions.PropertiesReaderException;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class PropertyManager {

    final static String PROPERTY_FILE_PATH = "config.properties";
    final static String PROPERTY_FILE_PATH_NEW = "configSort.properties";

    static <T> Map<T, Long> getItemsOccurrenceInList(List<T> list) {
        return list.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
    }

    Map<Integer, String> readPropertyFile(String path) {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream(path)) {
            prop.load(input);
        } catch (IOException e) {
            throw new PropertiesReaderException("Cannot load configuration file " + path + ". ", e);
        }
        return prop.entrySet()
                .stream()
                .collect(Collectors.toMap(map -> (Integer.parseInt((String) map.getKey())),
                        map -> (String) map.getValue(), (a, b) -> b));
    }

    Map<Integer, String> swapKeyValues(Map<Integer, String> inputMap) {
        return inputMap.entrySet()
                .stream().collect(Collectors.toMap(map ->
                        Integer.parseInt(map.getValue()), map -> map.getKey().toString(), (a, b) -> b));
    }

    Map<Integer, String> sortMapByKey(Map<Integer, String> inputMap) {
        return inputMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    void writeToPropertyFile(Properties prop, String propertyPath) {
        try (OutputStream output = new FileOutputStream(propertyPath)) {
            prop.store(output, "1");
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    static class SortedProperties extends Properties {
        @Override
        public synchronized Enumeration<Object> keys() {
            return Collections.enumeration(new TreeSet<>(super.keySet()));
        }
    }
}
