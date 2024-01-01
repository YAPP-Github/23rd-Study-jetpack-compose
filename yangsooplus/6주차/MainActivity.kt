package com.yangsooplus.composepractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.yangsooplus.composepractice.ui.theme.ComposePracticeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var data by remember {
                mutableStateOf(
                    listOf(
                        Product(),
                        Product(),
                        Product(),
                        Product(),
                        Product(),
                        Product(),
                        Product(),
                        Product(),
                    ),
                )
            }
            ComposePracticeTheme {
                Week6Screen(products = data) {
                    data = data + listOf(Product(), Product(), Product(), Product(), Product())
                }
            }
        }
    }
}

data class Product(
    val uri: String = "https://picsum.photos/200",
    val title: String = "물건 이름",
    val position: String = "가츠1동",
    val from: String = "Just now",
    val price: Int = 10000,
)
