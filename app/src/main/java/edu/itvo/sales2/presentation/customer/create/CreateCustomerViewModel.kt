package edu.itvo.sales2.presentation.customer.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.itvo.sales2.domain.model.Customer
import edu.itvo.sales2.domain.usecase.customer.CreateCustomerUseCase
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CreateCustomerViewModel @Inject constructor(
    private val createCustomerUseCase: CreateCustomerUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CreateCustomerUIState())
    val state: StateFlow<CreateCustomerUIState> = _state

    private val _effect = Channel<CreateCustomerUIEffect>()
    val effect = _effect.receiveAsFlow()



    fun onEvent(event: CreateCustomerUIEvent) {
        when (event) {
            is CreateCustomerUIEvent.CodeChanged ->
                _state.update { it.copy(code = event.value) }

            is CreateCustomerUIEvent.NameChanged ->
                _state.update { it.copy(name = event.value) }

            is CreateCustomerUIEvent.EmailChanged ->
                _state.update  { it.copy(email = event.value) }

            CreateCustomerUIEvent.SaveClicked ->
                saveCustomer()
        }
    }



    private fun saveCustomer() {
        val s = _state.value
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                if (s.code.isBlank() || s.name.isBlank()) {
                    _effect.send(CreateCustomerUIEffect.ShowError("Campos obligatorios vacíos"))
                } else {
                    val customer = Customer(s.code, s.name, s.email)
                    createCustomerUseCase(customer)
                    _effect.send(CreateCustomerUIEffect.ShowSuccess("Cliente guardado con éxito"))
                    delay(800)
                    _effect.send(CreateCustomerUIEffect.NavigateBack)
                }
            } catch (e: Exception) {
                _effect.send(CreateCustomerUIEffect.ShowError(e.message ?: "Error al guardar"))
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
}