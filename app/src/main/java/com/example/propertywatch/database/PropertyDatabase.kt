import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.propertywatch.database.PropertyDao
import com.example.propertywatch.database.PropertyWatchList

@Database(entities = [PropertyWatchList::class], version = 5, exportSchema = false)
abstract class PropertyDatabase : RoomDatabase() {
    abstract fun propertyDao(): PropertyDao

    companion object {
        @Volatile
        private var instance: PropertyDatabase? = null

        fun getInstance(context: Context): PropertyDatabase {
            return instance ?: synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    PropertyDatabase::class.java, "property-database.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                instance = db
                db
            }
        }
    }
}
