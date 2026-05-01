package edu.itvo.sales2.data.local.datasource

import edu.itvo.sales2.data.local.dao.CustomerDao
import edu.itvo.sales2.data.local.entity.CustomerEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CustomerLocalDataSource @Inject constructor(
    private val dao: CustomerDao
){
    fun getCustomers(): Flow<List<CustomerEntity>>{
        return dao.getCustomers()
    }

    suspend fun findCustomerByCode(customerCode: String): CustomerEntity?{
        return dao.findByCode(customerCode)
    }

    suspend fun saveCustomer(customer: CustomerEntity){
        dao.insert(customer)
    }

    suspend fun deleteCustomer(customerCode: String){
        dao.deleteByCode(customerCode)
    }

    suspend fun saveCustomers(customers: List<CustomerEntity>) {
        dao.insertAll(customers)
    }

    suspend fun replaceAll(customers: List<CustomerEntity>){
        dao.replaceAll(customers)
    }

    suspend fun updateCustomer(customer: CustomerEntity){
        dao.update(customer)
    }
}