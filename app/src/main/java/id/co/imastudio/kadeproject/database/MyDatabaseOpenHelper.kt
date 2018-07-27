package id.co.imastudio.kadeproject.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import id.co.imastudio.kadeproject.model.Team
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoritesEvent.db", null, 3) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(Favorite.TABLE_FAVORITE, true,
                Favorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Favorite.EVENT_ID to TEXT + UNIQUE,
                Favorite.EVENT_DATE to TEXT,
                Favorite.HOME_TEAM to TEXT,
                Favorite.AWAY_TEAM to TEXT,
                Favorite.HOME_SCORE to TEXT,
                Favorite.AWAY_SCORE to TEXT,
                Favorite.HOME_SHOTS to TEXT,
                Favorite.AWAY_SHOTS to TEXT,
                Favorite.HOME_GOAL to TEXT,
                Favorite.AWAY_GOAL to TEXT,
                Favorite.HOME_YELLOW to TEXT,
                Favorite.AWAY_YELLOW to TEXT,
                Favorite.HOME_RED to TEXT,
                Favorite.AWAY_RED to TEXT,
                Favorite.ID_HOME to TEXT,
                Favorite.ID_AWAY to TEXT)

        db.createTable(Team.TABLE_TEAM, true,
                Team.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Team.TEAM_ID to TEXT + UNIQUE,
                Team.TEAM_NAME to TEXT,
                Team.STADIUM to TEXT,
                Team.TEAM_YEAR to TEXT,
                Team.DESCRIPTION to TEXT,
                Team.TEAM_LOGO to TEXT,
                Team.FANART1 to TEXT,
                Team.FANART2 to TEXT,
                Team.FANART3 to TEXT,
                Team.FANART4 to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(Favorite.TABLE_FAVORITE, true)
        db.dropTable(Team.TABLE_TEAM, true)
    }
}

// Access property for Context
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)