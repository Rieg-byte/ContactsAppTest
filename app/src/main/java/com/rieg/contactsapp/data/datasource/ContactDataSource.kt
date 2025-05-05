package com.rieg.contactsapp.data.datasource

import android.content.ContentResolver
import android.provider.ContactsContract
import com.rieg.contactsapp.data.dto.ContactDto
import javax.inject.Inject

class ContactDataSource @Inject constructor(
    private val contentResolver: ContentResolver
) {
    fun getContacts(): List<ContactDto> {
        val contacts = mutableListOf<ContactDto>()

        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
        )

        contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        )?.use { cursor ->
            while (cursor.moveToNext()) {
                val id = cursor.getString(0)
                val name = cursor.getString(1)
                val phoneNumber = cursor.getString(2)
                contacts.add(ContactDto(id, name, phoneNumber))
            }
        }

        return contacts
    }

}