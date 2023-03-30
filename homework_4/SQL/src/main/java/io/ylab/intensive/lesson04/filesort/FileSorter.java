package io.ylab.intensive.lesson04.filesort;

import java.io.File;
import java.sql.SQLException;

public interface FileSorter {
    File sort(File data) throws SQLException;
}
