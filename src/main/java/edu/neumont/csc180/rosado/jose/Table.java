package edu.neumont.csc180.rosado.jose;


import java.util.List;
import java.util.Map;

public class Table {
    private String tableName;
    private Map<String, String> columnAndRegexPairMap;
    private String filePath;

    public Table(String tableName, Map<String, String> columnAndRegexPairMap, String filePath) {
        this.tableName = tableName;
        this.columnAndRegexPairMap = columnAndRegexPairMap;
        this.filePath = filePath;
    }

    public String getTableName() {
        return tableName;
    }

    public Map<String, String> getColumnAndRegexPairList() {
        return columnAndRegexPairMap;
    }

    public String getFilePath() {
        return filePath;
    }
}
