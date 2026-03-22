package edu.itvo.sales2.data.local.remote

import edu.itvo.sales2.domain.model.Product
import edu.itvo.sales2.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class ProductRepositoryImpl : ProductRepository {
    override suspend fun saveProduct(product: Product) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteProduct(productCode: String) {
        TODO("Not yet implemented")
    }

    override suspend fun findProductByCode(productCode: String): Product? {
        TODO("Not yet implemented")
    }

    override fun getProducts(): Flow<List<Product>> {
        TODO("Not yet implemented")
    }


}