package edu.itvo.sales2.domain.repository

import edu.itvo.sales2.domain.model.Customer
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {
    suspend fun saveCustomer(customer: Customer)

    suspend fun findCustomerByCode(customerCode: String): Customer?

    suspend fun deleteCustomer(customerCode: String)

    fun getCustomers(): Flow<List<Customer>>

}