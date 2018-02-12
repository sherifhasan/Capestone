package example.android.capestone.data;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by sheri on 2/12/2018.
 */

public class ArticlesContract {

    public interface ArticlesTableEntry {
        @DataType(DataType.Type.TEXT)
        String COLUMN_TITLE = "title";
        @DataType(DataType.Type.TEXT)
        String COLUMN_DESCRIPTION = "desc";
        @DataType(DataType.Type.TEXT)
        String COLUMN_URL = "url";
        @DataType(DataType.Type.TEXT)
        String COLUMN_AUTHOR = "author";
    }

    @Database(version = ArticlesDatabase.DATABASE_VERSION)
    public final class ArticlesDatabase {
        public static final int DATABASE_VERSION = 1;
        @Table(ArticlesTableEntry.class)
        public static final String ARTICLES_TABLE = "articles";
    }
}
