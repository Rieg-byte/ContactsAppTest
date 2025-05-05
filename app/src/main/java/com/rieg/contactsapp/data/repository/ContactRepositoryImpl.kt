package com.rieg.contactsapp.data.repository

import com.rieg.contactsapp.data.datasource.ContactDataSource
import com.rieg.contactsapp.data.mappers.toContact
import com.rieg.contactsapp.domain.model.Contact
import com.rieg.contactsapp.domain.repository.ContactRepository
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(
    private val contactDataSource: ContactDataSource
): ContactRepository {
    override suspend fun getContacts(): List<Contact> {
        return contactDataSource.getContacts().map { it.toContact() }
    }
}