package com.example.assasmen2_6706223161.ui.screen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Refresh
import com.example.assasmen2_6706223161.R
import com.example.assasmen2_6706223161.navigation.Screen
import com.example.assasmen2_6706223161.ui.theme.Assasmen2_6706223161Theme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PesanScreen(navController: NavHostController) {
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
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.Desc.route) }) {
                        Icon(
                            imageVector = Icons.Outlined.List,
                            contentDescription = stringResource(R.string.tentang_aplikasi),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

            )
        }
    ) { padding ->
        PesanContent(Modifier.padding(padding))
    }
}

@SuppressLint("StringFormatMatches")
@Composable
fun PesanContent(modifier: Modifier) {
    var harga by rememberSaveable { mutableStateOf("") }
    var hargaError by rememberSaveable { mutableStateOf(false) }

    var diskon by rememberSaveable { mutableStateOf("") }
    var diskonError by rememberSaveable { mutableStateOf(false) }

    var fee by remember { mutableIntStateOf(0) }

    var keterangan by rememberSaveable { mutableStateOf("") }

    val radioOptions = listOf(
        stringResource(id = R.string.offline),
        stringResource(id = R.string.online)
    )
    val jenisGambar = remember { mutableStateOf(R.mipmap.barang) }
    var jenis by remember { mutableStateOf(radioOptions[0]) }

    var hargaDiskon by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current
    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.selamat),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = harga ,
            onValueChange = {harga = it },
            label = { Text(text = stringResource(R.string.harga)) },
            isError = hargaError,
            trailingIcon = { IconPicker(hargaError, "Rp") },
            supportingText = { ErrorHint(hargaError)},
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = diskon,
            onValueChange = { diskon = it},
            label = { Text(text = stringResource(R.string.diskon)) },
            isError = diskonError,
            trailingIcon = { IconPicker(diskonError, "%" ) },
            supportingText = { ErrorHint(diskonError)},
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Row (
            modifier = Modifier
                .padding(top = 6.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
        ){
            radioOptions.forEach { text ->
                JenisOption(
                    label = text,
                    isSelected = jenis == text,
                    modifier = Modifier
                        .selectable(
                            selected = jenis == text,
                            onClick = { jenis = text },
                            role = Role.RadioButton
                        )
                        .weight(1f)
                        .padding(16.dp)
                )
            }
        }

        Button(
            onClick = {
                hargaError = (harga == "" || harga == "0")
                diskonError = (diskon == "" || diskon == "0")
                if(hargaError || diskonError) return@Button

                val isOnline = jenis == radioOptions[1]

                fee = getfee(isOnline)

                val hargaFloat = harga.toFloat()
                val diskonFloat = diskon.toFloat()
                keterangan = if (isOnline) "Karena anda memesan secara online maka anda terkena pajak 5000" else "Dapatkan promo menarik lainnya dari TRINSMART"
                val hargaDiskonFloat = (hargaFloat - (hargaFloat * (diskonFloat/100) - fee))

                val imageResId = if (isOnline) {
                    R.mipmap.barang
                } else {
                    R.mipmap.apk
                }
                jenisGambar.value = imageResId

                hargaDiskon = String.format("%.2f", hargaDiskonFloat)
            },
            modifier = Modifier.padding(top = 8.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(R.string.hitung))
        }

        if (hargaDiskon.isNotEmpty()){
            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp
            )
            Text(text = stringResource(R.string.harga_diskon_x, hargaDiskon),
                style = MaterialTheme.typography.titleLarge
            )
            Text(text = "Keterangan: $keterangan")

            Image(
                painter = painterResource(id = jenisGambar.value),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
            Button(
                onClick = {
                    shareData(
                        context = context,
                        message = context.getString(R.string.bagikan_template,
                            harga, diskon, jenis, hargaDiskon,
                            context.getString(fee).uppercase()))
                },
                modifier = Modifier.padding(top = 8.dp),
                contentPadding = PaddingValues(horizontal=32.dp, vertical=16.dp)
            ) {
                Text(text = stringResource(R.string.bagikan))
            }
        }
        }

    }


private fun getfee(isOnline: Boolean): Int {
    val biayaPesan = if (isOnline) {
        5000
    } else {
        0
    }
    return biayaPesan
}

@Composable
fun JenisOption(label: String, isSelected: Boolean, modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = isSelected, onClick = null)
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun IconPicker(isError: Boolean, unit: String) {
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null )
    } else {
        Text(text = unit)
    }
}

@Composable
fun  ErrorHint(isError: Boolean){
    if (isError) {
        Text(text = stringResource(R.string.input_invalid))
    }
}

private fun shareData(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareIntent)
    }
}


@Composable
fun ScrollableLayout(items: List<String>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(items) { item ->
            Text(text = item)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScrollableLayoutPreview() {
    val items = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
    ScrollableLayout(items = items)
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PesanPreview() {
    Assasmen2_6706223161Theme {
        PesanScreen (rememberNavController())
    }
}
