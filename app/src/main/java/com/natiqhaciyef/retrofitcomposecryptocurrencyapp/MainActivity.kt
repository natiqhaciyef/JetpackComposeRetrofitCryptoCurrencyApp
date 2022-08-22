package com.natiqhaciyef.retrofitcomposecryptocurrencyapp

import android.os.Bundle
import android.widget.Space
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.natiqhaciyef.retrofitcomposecryptocurrencyapp.model.CryptoModel
import com.natiqhaciyef.retrofitcomposecryptocurrencyapp.service.CryptoApi
import com.natiqhaciyef.retrofitcomposecryptocurrencyapp.ui.theme.RetrofitComposeCryptoCurrencyAppTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//https://raw.githubusercontent.com/


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrofitComposeCryptoCurrencyAppTheme {
                MainScreen()
            }
        }
    }
}


@Composable
fun MainScreen() {
    val cryptoModels = remember { mutableStateListOf<CryptoModel>() }

    val baseUrl = "https://raw.githubusercontent.com/"
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CryptoApi::class.java)

    val call = retrofit.getData()
    call.enqueue(object : Callback<List<CryptoModel>> {
        override fun onResponse(
            call: Call<List<CryptoModel>>,
            response: Response<List<CryptoModel>>
        ) {
            if (response.isSuccessful) {
                response.body()?.let {
                    cryptoModels.addAll(it)
                }
            }
        }

        override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
            t.printStackTrace()
        }
    })



    Scaffold(topBar = { AppBar() }) {
        CryptoList(cryptoModels = cryptoModels)
    }
}


@Composable
fun CryptoList(cryptoModels: List<CryptoModel>) {
    LazyColumn {
        items(cryptoModels) { cryptoModel ->
            CustomRowItem(cryptoModel = cryptoModel)
        }
    }
}

@Composable
fun CustomRowItem(cryptoModel: CryptoModel) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.surface)
    ) {
        Text(text = cryptoModel.currency, fontSize = 25.sp)
        Spacer(modifier = Modifier.padding(2.dp))
        Text(text = cryptoModel.price, fontSize = 17.sp)
    }
}


@Composable
fun AppBar() {
    TopAppBar(
        backgroundColor = Color.Cyan,
        contentPadding = PaddingValues(10.dp)
    ) {
        Text(
            text = "Retrofit CryptoCurrency App",
            fontStyle = FontStyle.Italic
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RetrofitComposeCryptoCurrencyAppTheme {
        MainScreen()
//        CustomRowItem(cryptoModel = CryptoModel(currency = "BTC","40000"))
    }
}