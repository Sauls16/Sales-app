package edu.itvo.sales2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.itvo.sales2.data.local.dao.CustomerDao
import edu.itvo.sales2.data.local.dao.ProductDao
import edu.itvo.sales2.data.local.entity.CustomerEntity

@Database(
    entities = [CustomerEntity::class],
    version = 1,
    exportSchema = false
)

abstract class CustomerDatabase: RoomDatabase(){

    abstract fun customerDao(): CustomerDao
}



