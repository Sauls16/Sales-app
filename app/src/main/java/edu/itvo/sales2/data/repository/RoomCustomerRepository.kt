package edu.itvo.sales2.data.repository

import edu.itvo.sales2.data.local.dao.CustomerDao
import edu.itvo.sales2.data.local.mapper.toDomain
import edu.itvo.sales2.data.local.mapper.toEntity
import edu.itvo.sales2.domain.model.Customer
import edu.itvo.sales2.domain.repository.CustomerRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class RoomCustomerRepository @Inject constructor(
    private val dao: CustomerDao
) : CustomerRepository {

    override fun getCustomers(): Flow<List<Customer>> {
        return dao.getCustomers()
            .map { list -> list.map { it.toDomain() } }
    }

    override suspend fun findCustomerByCode(customerCode: String): Customer? {
        return dao.findByCode(customerCode)?.toDomain()
    }

    override suspend fun saveCustomer(customer: Customer) {
        dao.insert(customer.toEntity())
    }

    override suspend fun deleteCustomer(customerCode: String) {
        dao.deleteByCode(customerCode)
    }
}