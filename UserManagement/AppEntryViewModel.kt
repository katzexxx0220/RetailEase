package com.example.testassignment.UserManagement

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testassignment.data.Item
import com.example.testassignment.data.User
import com.example.testassignment.data.AppRepository
import com.example.testassignment.data.StockMovement
import com.example.testassignment.branchData.Branch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.NumberFormat
import kotlin.String

class AppEntryViewModel(private val appRepository: AppRepository) : ViewModel() {
    var itemUiState by mutableStateOf(ItemUiState())
        private set

    var userUiState by mutableStateOf(UserUiState())

    // Track current logged-in user
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    // Expose user details as StateFlow - use current user ID
    val user: StateFlow<User?> = _currentUser
    /**
     * Updates the [itemUiState] or [userUiState] with the value provided in the argument. This method also triggers
     * a validation for input values.
     */
    fun updateItemUiState(itemDetails: ItemDetails) {
        itemUiState =
            ItemUiState(itemDetails = itemDetails, isEntryValid = validateItemInput(itemDetails))
    }

    fun updateUserUiState(userDetails: UserDetails) {
        userUiState =
            UserUiState(userDetails = userDetails, isEntryValid = validateUserInput(userDetails))
    }

    private fun validateItemInput(uiState: ItemDetails = itemUiState.itemDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && code.isNotBlank() && costPrice.isNotBlank() && retailPrice.isNotBlank() && branch.isNotBlank()
        }
    }

    private fun validateUserInput(uiState: UserDetails = userUiState.userDetails): Boolean {
        return with(uiState) {
            email.isNotBlank() && password.isNotBlank() && nickname.isNotBlank() && companyName.isNotBlank()
        }
    }

    // update user details
    fun updateUserDetails(updatedUser: User) {
        viewModelScope.launch {
            appRepository.updateUser(updatedUser)
            // Update the current user in the StateFlow
            _currentUser.value = updatedUser
        }
    }

    // delete user
    fun deleteUser(user: User?) {
        viewModelScope.launch {
            appRepository.deleteUser(user)
            // Clear the current user
            _currentUser.value = null
        }
    }

    /**
     * Check user login status
     */
    private val _loginState = MutableStateFlow<Boolean?>(null) // null = idle, true = success, false = fail
    val loginState: StateFlow<Boolean?> = _loginState
    var loginError = mutableStateOf<String?>(null)
        private set

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val user = appRepository.login(email, password)
            if (user != null) {
                _currentUser.value = user
                loginError.value = null
                _loginState.value = true
            } else {
                loginError.value = "Invalid email or password"
                _loginState.value = false
            }
        }
    }

    fun resetLoginState() {
        _loginState.value = null
    }

    // Set current user (useful for app initialization)
    fun setCurrentUser(user: User) {
        _currentUser.value = user
    }

    // Clear current user (for logout)
    fun clearCurrentUser() {
        _currentUser.value = null
    }

    fun register(email: String, password: String, nickname: String, companyName: String) {
        viewModelScope.launch {
            val newUser = User(email = email, password = password, nickname = nickname, companyName = companyName)
            appRepository.insertUser(newUser)
        }
    }


    // Stock movements
    val stockFlow: StateFlow<List<StockMovement>> = appRepository.getStockFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    // Items stream (for product selection)
    val items: StateFlow<List<Item>> = appRepository.getAllItemsStream()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun stockFlowForBranch(branchCode: String): StateFlow<List<StockMovement>> =
        appRepository.getStockFlowForBranch(branchCode)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun insertStockMovement(movement: StockMovement) {
        viewModelScope.launch {
            appRepository.insertStockMovement(movement)
        }
    }

    // Stock totals methods
    suspend fun getTotalStockIn(): Int? = appRepository.getTotalStockIn()
    suspend fun getTotalStockOut(): Int? = appRepository.getTotalStockOut()
    suspend fun getTotalStockInByBranch(branchCode: String): Int? = appRepository.getTotalStockInByBranch(branchCode)
    suspend fun getTotalStockOutByBranch(branchCode: String): Int? = appRepository.getTotalStockOutByBranch(branchCode)

}

    data class ItemUiState(
        val itemDetails: ItemDetails = ItemDetails(),
        val isEntryValid: Boolean = false
    )

    data class UserUiState(
        val userDetails: UserDetails = UserDetails(),
        val isEntryValid: Boolean = false
    )

    data class ItemDetails(
        val id: Int = 0,
        val name: String = "",
        val code: String = "",
        val costPrice: String = "",
        val retailPrice: String = "",
        val branch: String = ""
    )

    data class UserDetails(
        val id: Int = 0,
        val email: String = "",
        val password: String = "",
        val nickname: String = "",
        val companyName: String = ""
    )

    fun ItemDetails.toItem(): Item = Item(
        id = id,
        name = name,
        code = code,
        costPrice = costPrice.toDoubleOrNull() ?: 0.0,
        retailPrice = retailPrice.toDoubleOrNull() ?: 0.0,
        branch = branch
    )

    fun UserDetails.toUser(): User = User(
        id = id,
        email = email,
        password = password,
        nickname = nickname,
        companyName = companyName
    )

    fun Item.formatedCostPrice(): String {
        return NumberFormat.getCurrencyInstance().format(costPrice)
    }

    fun Item.formatedRetailPrice(): String {
        return NumberFormat.getCurrencyInstance().format(retailPrice)
    }

    /**
     * Extension function to convert [Item] to [ItemUiState]
     */
    fun Item.toItemUiState(isEntryValid: Boolean = false): ItemUiState = ItemUiState(
        itemDetails = this.toItemDetails(),
        isEntryValid = isEntryValid
    )

    /**
     * Extension function to convert [User] to [UserUiState]
     */
    fun User.toUserUiState(isEntryValid: Boolean = false): UserUiState = UserUiState(
        userDetails = this.toUserDetails(),
        isEntryValid = isEntryValid
    )

    /**
     * Extension function to convert [Item] to [ItemDetails]
     */
    fun Item.toItemDetails(): ItemDetails = ItemDetails(
        id = id,
        name = name,
        code = code,
        costPrice = costPrice.toString(),
        retailPrice = retailPrice.toString(),
        branch = branch
    )

    /**
     * Extension function to convert [User] to [UserDetails]
     */
    fun User.toUserDetails(): UserDetails = UserDetails(
    id = id,
    email = email,
        password = password,
        nickname = nickname,
        companyName = companyName
)






