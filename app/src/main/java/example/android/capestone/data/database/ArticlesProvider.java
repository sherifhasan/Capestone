package example.android.capestone.data.database;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by sheri on 2/12/2018.
 */
@ContentProvider(authority = ArticlesProvider.AUTHORITY, database = ArticlesContract.ArticlesDatabase.class)
public class ArticlesProvider {
    public static final String AUTHORITY = "example.android.capestone";

    @TableEndpoint(table = ArticlesContract.ArticlesDatabase.ARTICLES_TABLE)
    public static class Articles {

        @ContentUri(path = ArticlesContract.ArticlesDatabase.ARTICLES_TABLE,
                type = "vnd.android.cursor.dir/list")
        public static final Uri articleUri = Uri.parse("content://" + AUTHORITY)
                .buildUpon()
                .appendPath(ArticlesContract.ArticlesDatabase.ARTICLES_TABLE)
                .build();
    }
}
