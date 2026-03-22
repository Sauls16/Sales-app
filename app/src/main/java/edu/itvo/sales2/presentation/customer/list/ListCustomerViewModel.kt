package edu.itvo.sales2.presentation.customer.list


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.itvo.sales2.domain.model.Customer
import edu.itvo.sales2.domain.usecase.customer.DeleteCustomerUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import edu.itvo.sales2.domain.usecase.customer.ListCustomerUseCase
import edu.itvo.sales2.presentation.product.list.ListProductUiState
import kotlin.code


@HiltViewModel
class ListCustomerViewModel @Inject constructor(
     getCustomersUseCase: ListCustomerUseCase,
    private val deleteCustomerUseCase: DeleteCustomerUseCase
) : ViewModel() {

    val uiState: StateFlow<ListCustomerUiState> =
        getCustomersUseCase()
            .map { customers ->
                ListCustomerUiState(
                    isLoading = false,
                    customers = customers
                )
            }
            .onStart {
                emit(ListCustomerUiState(isLoading = true))
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                ListCustomerUiState()
            )

    fun deleteCustomer(customer: Customer) {
        viewModelScope.launch {
            deleteCustomerUseCase(customer.code)
        }
    }
}
data class ListCustomerUiState(
    val isLoading: Boolean = false,
    val customers: List<Customer> = emptyList()
)