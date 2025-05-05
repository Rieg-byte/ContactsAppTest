package com.rieg.contactsapp

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.rieg.contactsapp.presentation.contacts.ContactsNotGrantedScreen
import com.rieg.contactsapp.presentation.contacts.ContactsScreen
import com.rieg.contactsapp.ui.theme.ContactsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val contactsPermissions = rememberMultiplePermissionsState(
                listOf(
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.CALL_PHONE
                )
            )
            ContactsAppTheme {
                Surface {
                    when {
                        contactsPermissions.allPermissionsGranted -> {
                            ContactsScreen(context = context)
                        }
                        !contactsPermissions.allPermissionsGranted -> {
                            LaunchedEffect(Unit) {
                                contactsPermissions.launchMultiplePermissionRequest()
                            }
                            ContactsNotGrantedScreen(
                                onClick = {
                                    val intent = Intent(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                        Uri.fromParts("package", context.packageName, null)
                                    )
                                    startActivity(intent)
                                }
                            )

                        }
                    }
                }
            }
        }
    }
}

