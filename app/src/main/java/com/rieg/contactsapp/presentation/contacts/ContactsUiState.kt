package com.rieg.contactsapp.presentation.contacts

import com.rieg.contactsapp.domain.model.Contact

data class ContactsUiState(
    val contacts: Map<Char, List<Contact>> = emptyMap()
)