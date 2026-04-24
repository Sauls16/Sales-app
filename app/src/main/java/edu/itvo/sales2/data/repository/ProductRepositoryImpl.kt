package edu.itvo.sales2.data.repository

import android.util.Log
import edu.itvo.sales2.data.local.datasource.ProductLocalDataSource
import edu.itvo.sales2.data.local.entity.ProductEntity
import edu.itvo.sales2.data.local.mapper.toDomain
import edu.itvo.sales2.data.local.mapper.toEntity
import edu.itvo.sales2.data.remote.datasource.ProductRemoteDataSource
import edu.itvo.sales2.data.remote.mapper.ProductRemoteMapper.toDomain
import edu.itvo.sales2.data.remote.mapper.ProductRemoteMapper.toDto
import edu.itvo.sales2.data.remote.mapper.ProductRemoteMapper.toEntity
import edu.itvo.sales2.domain.model.Product
import edu.itvo.sales2.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remote: ProductRemoteDataSource,
    private val local: ProductLocalDataSource
) : ProductRepository {

    override fun getProducts(): Flow<List<Product>> = flow {

        // 🔥 1. Intentar actualizar desde API
        try {
            val products = remote.getProducts().data
                .map { it.toDomain() }

            Log.d(
                "PRODUCTS",
                products.joinToString(separator = "\n") { product ->
                    "code=${product.code}, desc=${product.description}, price=${product.price}"
                }
            )

            local.replaceAll(products.map { it.toEntity() })

        } catch (e: Exception) {
            Log.e("PRODUCTS_ERROR", e.message ?: "Unknown error", e)
        }

        // 🔥 2. Emitir datos locales (flow infinito)
        emitAll(
            local.getProducts()
                .map { list -> list.map { it.toDomain() } }
        )
    }


    override suspend fun findProductByCode(productCode: String): Product? {

        // 1. Buscar local primero
        val localProduct = local.findProductByCode(productCode)
        if (localProduct != null) {
            return localProduct.toDomain()
        }

        // 2. Si no existe, buscar remoto
        return try {
            val remoteProduct = remote.findProductByCode(productCode)

            // guardar en local
            local.saveProduct(remoteProduct.toEntity())

            remoteProduct.toDomain()

        } catch (e: Exception) {
            null
        }
    }

    override suspend fun saveProduct(product: Product) {
        try {
            remote.saveProduct(product.toDto())
            local.saveProduct(product.toEntity())
        } catch (e: Exception) {
            local.saveProduct(product.toEntity())
        }
    }

    override suspend fun deleteProduct(productCode: String) {
        try {
            //Eliminar en remoto primero
            remote.deleteProduct(productCode)

            //Si falla eliminamos en local
            local.deleteProduct(productCode)

            Log.d("DELETE_SYNC", "Producto $productCode eliminado de forma remota y local")
        } catch (e: Exception){
            Log.e("DELETE_ERROR", "No se pudo eliminar del servidor ${e.message}")
        }
    }
}
