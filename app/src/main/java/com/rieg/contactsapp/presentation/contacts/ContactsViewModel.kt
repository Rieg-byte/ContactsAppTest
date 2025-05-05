package com.rieg.contactsapp.presentation.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rieg.contactsapp.domain.repository.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val contactRepository: ContactRepository
): ViewModel() {
    private val _contactsUiState = MutableStateFlow(ContactsUiState())
    val contactsUiState = _contactsUiState.asStateFlow()

    init {
        loadContacts()
    }


    fun loadContacts() {
        viewModelScope.launch {
            val contacts = contactRepository.getContacts().groupBy { contact ->
                contact.name.first()
            }
            _contactsUiState.update {
                it.copy(
                    contacts = contacts
                )
            }
        }
    }
}