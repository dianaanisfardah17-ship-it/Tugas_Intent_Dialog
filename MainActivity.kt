package com.diana_2430511046.registrasiseminarcompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FormRegistrasiScreen(onSubmit = { nama, nim, prodi, email, semester ->
                        val intent = Intent(this, HasilActivity::class.java).apply {
                            putExtra("NAMA", nama)
                            putExtra("NIM", nim)
                            putExtra("PRODI", prodi)
                            putExtra("EMAIL", email)
                            putExtra("SEMESTER", semester)  // Field baru
                        }
                        startActivity(intent)
                    })
                }
            }
        }
    }
}

@Composable
fun FormRegistrasiScreen(onSubmit: (String, String, String, String, String) -> Unit) {
    var nama by remember { mutableStateOf("") }
    var nim by remember { mutableStateOf("") }
    var prodi by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var semester by remember { mutableStateOf("") }  // Field baru
    var showWarningDialog by remember { mutableStateOf(false) }
    var showConfirmDialog by remember { mutableStateOf(false) }
    var showResetDialog by remember { mutableStateOf(false) }  // Dialog reset

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Form Registrasi Seminar Mahasiswa",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nama,
            onValueChange = { nama = it },
            label = { Text("Nama Mahasiswa") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = nim,
            onValueChange = { nim = it },
            label = { Text("NIM") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = prodi,
            onValueChange = { prodi = it },
            label = { Text("Program Studi") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Field baru: semester
        OutlinedTextField(
            value = semester,
            onValueChange = { semester = it },
            label = { Text("semester") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Tombol Daftar
        Button(
            onClick = {
                if (nama.isBlank() || nim.isBlank() || prodi.isBlank()
                    || email.isBlank() || semester.isBlank()
                ) {
                    showWarningDialog = true
                } else {
                    showConfirmDialog = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Daftar Seminar")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Tombol Reset
        Button(
            onClick = { showResetDialog = true },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Reset")
        }
    }

    // Dialog peringatan field kosong
    if (showWarningDialog) {
        AlertDialog(
            onDismissRequest = { showWarningDialog = false },
            title = { Text("Peringatan") },
            text = { Text("Semua data harus diisi terlebih dahulu.") },
            confirmButton = {
                TextButton(onClick = { showWarningDialog = false }) {
                    Text("OK")
                }
            }
        )
    }

    // Dialog konfirmasi daftar
    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = { Text("Konfirmasi") },
            text = { Text("Apakah data registrasi seminar akan dikirim?") },
            confirmButton = {
                TextButton(onClick = {
                    showConfirmDialog = false
                    onSubmit(nama, nim, prodi, email, semester)
                }) {
                    Text("Ya")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }

    // Dialog konfirmasi reset
    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text("Konfirmasi Reset") },
            text = { Text("Apakah Anda yakin ingin menghapus semua data yang telah diisi?") },
            confirmButton = {
                TextButton(onClick = {
                    nama = ""
                    nim = ""
                    prodi = ""
                    email = ""
                    semester = ""
                    showResetDialog = false
                }) {
                    Text("Ya, Reset")
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }
}