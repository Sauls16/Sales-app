package edu.itvo.sales2.data.remote.datasource

import edu.itvo.sales2.data.remote.api.ApiResponse
import edu.itvo.sales2.data.remote.api.ProductApiService
import edu.itvo.sales2.data.remote.dto.ProductDto
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(
    private val api: ProductApiService
) {

    suspend fun findProductByCode(code: String): ProductDto {
        return api.findProductByCode(code)
    }

    suspend fun saveProduct(product: ProductDto): ProductDto {
        return api.saveProduct(product)
    }

    suspend fun getProducts(): ApiResponse<List<ProductDto>> {
        return api.getProducts()
    }

    suspend fun deleteProduct(code: String){
        api.deleteProduct(code)
    }
}