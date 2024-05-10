package com.example.assasmen2_6706223161.ui.screen


import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.assasmen2_6706223161.R
import com.example.assasmen2_6706223161.database.KatalogDb
import com.example.assasmen2_6706223161.model.Katalog
import com.example.assasmen2_6706223161.ui.theme.Assasmen2_6706223161Theme
import com.example.assasmen2_6706223161.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val db = KatalogDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var nama_barang by remember { mutableStateOf("") }
    var kode_barang by remember { mutableStateOf("") }
    var jenis_barang by remember { mutableStateOf("") }
    var deskripsi by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        if (id == null) return@LaunchedEffect
        val katalog: Katalog = viewModel.getKatalog(id) ?: return@LaunchedEffect
        nama_barang = katalog.nama_barang
        kode_barang = katalog.kode_barang
        jenis_barang = katalog.jenis_barang
        deskripsi = katalog.deskripsi
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    if (id == null)
                        Text(text = stringResource(id = R.string.tambah_mahasiswa))
                    else
                        Text(text = stringResource(id = R.string.edit_mahasiswa))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        if (nama_barang == "" || kode_barang == "" || jenis_barang == "" || deskripsi == "") {
                            Toast.makeText(context, R.string.invalid, Toast.LENGTH_LONG).show()
                            return@IconButton
                        }
                        if (id == null) {
                            viewModel.insert(nama_barang, kode_barang, jenis_barang, deskripsi)
                        } else {
                            viewModel.update(id, nama_barang, kode_barang, jenis_barang, deskripsi)
                        }
                        navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (id != null) {
                        DeleteAction { showDialog = true }
                        DisplayAlertDialog(
                            openDialog = showDialog,
                            onDismissRequest = { showDialog = false }) {
                            showDialog = false
                            viewModel.delete(id)
                            navController.popBackStack()
                        }
                    }
                }
            )
        }
    ) {  padding ->
        Column(modifier = Modifier.padding(padding)) {
            FormMahasiswa(
                nama_barang = nama_barang,
                onNameChange = { nama_barang = it },
                kode_barang = kode_barang,
                onKode_barangChange = { kode_barang = it },
                selectedOption = jenis_barang,
                onOptionSelected = { jenis_barang = it },
                deskripsi = deskripsi,
                onDeskripsi_barangChange = { deskripsi = it },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun FormMahasiswa(
    nama_barang: String, onNameChange: (String) -> Unit,
    kode_barang: String, onKode_barangChange: (String) -> Unit,
    selectedOption: String, onOptionSelected: (String) -> Unit,
    deskripsi: String, onDeskripsi_barangChange: (String) -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = nama_barang,
            onValueChange = { onNameChange(it) },
            label = { Text(text = stringResource(R.string.nama)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = kode_barang,
            onValueChange = { onKode_barangChange(it) },
            label = { Text(text = stringResource(R.string.isi_mahasiswa)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
            ),
            modifier = Modifier.fillMaxWidth()
        )
        RadioButtonGroup(
            options = listOf("Pakaian", "Elektronik", "Makanan", "Rumah Tangga", "Otomotif"),
            selectedOption = selectedOption,
            onOptionSelected = onOptionSelected,
        )
        OutlinedTextField(
            value = deskripsi,
            onValueChange = { onDeskripsi_barangChange(it) },
            label = { Text(text = stringResource(R.string.deskripsi)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun RadioButtonGroup(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    val outlineColor = MaterialTheme.colorScheme.outline
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = outlineColor,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            options.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    RadioButton(
                        selected = option == selectedOption,
                        onClick = { onOptionSelected(option) }
                    )
                    Text(
                        text = option,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.lainnya),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.hapus))
                },
                onClick = {
                    expanded = false
                    delete()
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailScreenPreview() {
    Assasmen2_6706223161Theme {
        DetailScreen(rememberNavController())
    }
}

