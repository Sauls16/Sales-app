package edu.itvo.sales2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.itvo.sales2.data.local.dao.CustomerDao
import edu.itvo.sales2.data.local.dao.ProductDao
import edu.itvo.sales2.data.local.entity.ProductEntity
import edu.itvo.sales2.data.local.entity.CustomerEntity

@Database(
    entities = [ProductEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SalesDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
}
