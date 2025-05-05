package com.rieg.contactsapp.domain.repository

import com.rieg.contactsapp.domain.model.Contact

interface ContactRepository {
    suspend fun getContacts(): List<Contact>
}