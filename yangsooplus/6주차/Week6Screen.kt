package com.yangsooplus.composepractice

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.yangsooplus.composepractice.ui.theme.ComposePracticeTheme

fun LazyListState.isScrolledToTheEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Week6Screen(
    products: List<Product>,
    onReachEnd: () -> Unit,
) {
    val listState = rememberLazyListState()
    val showSurvey by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }
    val reachedEnd by remember { derivedStateOf { listState.isScrolledToTheEnd() } }
    var menuExpanded by remember { mutableStateOf(false) }
    var currentPosition by remember { mutableStateOf("화곡제1동") }
    var selectedItem by remember { mutableStateOf(0) }

    val navigationItems = listOf(
        "Home" to Icons.Filled.Home,
        "Nearby" to Icons.Filled.LocationOn,
        "Post" to Icons.Filled.Create,
        "Inbox" to Icons.Filled.MailOutline,
        "MyKarrot" to Icons.Filled.Person,
    )

    LaunchedEffect(key1 = reachedEnd) {
        onReachEnd()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(text = currentPosition)
                        IconButton(onClick = { menuExpanded = true }) {
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowDown,
                                contentDescription = null,
                            )
                        }
                    }

                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false },
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = "화곡제1동") },
                            onClick = {
                                currentPosition = "화곡제1동"
                                menuExpanded = false
                            },
                        )
                        DropdownMenuItem(
                            text = { Text(text = "화곡제2동") },
                            onClick = {
                                currentPosition = "화곡제2동"
                                menuExpanded = false
                            },
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = null)
                    }
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Filled.Settings, contentDescription = null)
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = null,
                        )
                    }
                },
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                contentColor = Color.Black,
            ) {
                navigationItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = { selectedItem = index },
                        icon = {
                            Icon(
                                imageVector = item.second,
                                contentDescription = null,
                            )
                        },
                        label = {
                            Text(
                                text = item.first,
                            )
                        },
                    )
                }
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            AnimatedVisibility(visible = showSurvey) {
                Survey(
                    modifier = Modifier.height(100.dp),
                ) { }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState,
            ) {
                items(products.size) {
                    ProductItem(product = products[it])
                }
            }
        }
    }
}

@Composable
fun Survey(
    modifier: Modifier = Modifier,
    onReply: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xFFFF5722))
            .padding(horizontal = 80.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "만족하며 거래하고 있나요?", color = Color.White)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            OutlinedButton(
                onClick = { onReply("NO") },
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White,
                    containerColor = Color(0xFFFF5722),
                ),
                border = BorderStroke(width = 1.dp, color = Color.White),
                shape = RoundedCornerShape(4.dp),
            ) {
                Text(text = "아니요", modifier = Modifier.padding(horizontal = 10.dp))
            }
            Button(
                onClick = { onReply("YES") },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xFFFF5722),
                    containerColor = Color.White,
                ),
                shape = RoundedCornerShape(4.dp),
            ) {
                Text(text = "그럼요!", modifier = Modifier.padding(horizontal = 10.dp))
            }
        }
    }
}

@Composable
fun ProductItem(
    product: Product,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        AsyncImage(model = product.uri, contentDescription = null)
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(text = product.title)
            Text(text = "${product.position} - ${product.from}")
            Text(text = "${product.price}원")
        }
    }
}

@Preview
@Composable
fun Week6ScreenPreview() {
    ComposePracticeTheme {
        Week6Screen(
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
        ) {
        }
    }
}
