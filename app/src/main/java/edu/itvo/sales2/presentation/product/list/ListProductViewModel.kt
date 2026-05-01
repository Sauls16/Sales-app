package edu.itvo.sales2.presentation.product.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.itvo.sales2.data.repository.ProductRepositoryImpl
import edu.itvo.sales2.domain.model.Product
import edu.itvo.sales2.domain.usecase.product.DeleteProductUseCase
import edu.itvo.sales2.domain.usecase.product.ListProductsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListProductViewModel @Inject constructor(
    getProductsUseCase: ListProductsUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val repository: ProductRepositoryImpl
) : ViewModel() {

    init {
        viewModelScope.launch {
            repository.syncToServer()
        }
    }

    val uiState: StateFlow<ListProductUiState> =
        getProductsUseCase()
            .map { products ->
                ListProductUiState(
                    isLoading = false,
                    products = products
                )
            }
            .onStart {
                emit(ListProductUiState(isLoading = true))
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                ListProductUiState()
            )
    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            deleteProductUseCase(product.code)
        }
    }
}

data class ListProductUiState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList()
)