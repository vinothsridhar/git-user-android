package sri.git.user.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import sri.git.user.model.GitUser;
import sri.git.user.utils.L;

/**
 * Created by sridhar on 9/5/17.
 */

public class DbHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DbHelper.class.getSimpleName();

    private static final String DB_NAME = "gituser.db";
    private static final int DB_VERSION = 1;

    private Dao<GitUser, Integer> gitUserDao;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, GitUser.class);
        } catch (java.sql.SQLException e) {
            L.logException(TAG, e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    public Dao<GitUser, Integer> getGitUserDao() throws SQLException {
        if (gitUserDao == null) {
            gitUserDao = getDao(GitUser.class);
        }

        return gitUserDao;
    }

    @Override
    public void close() {
        super.close();

    }
}
