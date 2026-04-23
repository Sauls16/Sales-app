package edu.itvo.sales2.data.repository

import edu.itvo.sales2.data.remote.datasource.CustomerFirebaseDataSource
import edu.itvo.sales2.domain.model.Customer
import edu.itvo.sales2.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FirestoreCustomerRepository @Inject constructor(
    private val firebaseDataSource: CustomerFirebaseDataSource
) : CustomerRepository {

    override fun getCustomers(): Flow<List<Customer>> {
        return firebaseDataSource.getCustomers()
    }

    override suspend fun findCustomerByCode(customerCode: String): Customer? {
        return firebaseDataSource.findCustomerByCode(customerCode)
    }

    override suspend fun saveCustomer(customer: Customer) {
        firebaseDataSource.saveCustomer(customer)
    }

    override suspend fun deleteCustomer(customerCode: String) {
        firebaseDataSource.deleteCustomer(customerCode)
    }
}