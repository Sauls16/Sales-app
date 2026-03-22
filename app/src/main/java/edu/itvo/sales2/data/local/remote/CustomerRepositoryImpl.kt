package edu.itvo.sales2.data.local.remote

import edu.itvo.sales2.domain.model.Customer
import edu.itvo.sales2.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CustomerRepositoryImpl : CustomerRepository {

    override suspend fun saveCustomer(customer: Customer) {
        TODO("Not yet implemented")
    }

    override suspend fun findCustomerByCode(customerCode:String): Customer? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCustomer(customerCode: String) {
        TODO("Not yet implemented")
    }

    override fun getCustomers(): Flow<List<Customer>> {
        TODO("Not yet implemented")
    }
}