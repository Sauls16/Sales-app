package edu.itvo.sales2.data.remote.datasource

import com.google.firebase.firestore.FirebaseFirestore
import edu.itvo.sales2.domain.model.Customer
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CustomerFirebaseDataSource @Inject constructor() {

    private val firestore = FirebaseFirestore.getInstance()
    private val collection = firestore.collection("customers")

    fun getCustomers(): Flow<List<Customer>> = callbackFlow {
        val listener = collection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                return@addSnapshotListener
            }

            val customers = snapshot?.documents?.mapNotNull {
                it.toObject(Customer::class.java)
            } ?: emptyList()

            trySend(customers)
        }
        awaitClose { listener.remove() }
    }

    suspend fun findCustomerByCode(code: String): Customer? {
        val doc = collection.document(code).get().await()
        return doc.toObject(Customer::class.java)
    }

    suspend fun saveCustomer(customer: Customer) {
        collection.document(customer.code).set(customer).await()
    }

    suspend fun deleteCustomer(code: String) {
        collection.document(code).delete().await()
    }

    suspend fun updateCustomer(customer: Customer){

    }
}