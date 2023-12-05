package com.yangsooplus.composepractice.week4

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yangsooplus.composepractice.ui.theme.ComposePracticeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Week4Screen() {
    var selectedItem by remember { mutableStateOf(0) }
    val navigationItems = listOf(
        "홈" to Icons.Filled.Home,
        "혜택" to Icons.Filled.ThumbUp,
        "송금" to Icons.Filled.Send,
        "전체" to Icons.Filled.Menu,
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFE8EAEC),
        contentColor = Color.White,
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = { },
                ) {
                    Icon(
                        imageVector = Icons.Filled.Notifications,
                        contentDescription = null,
                        tint = Color.Gray,
                    )
                }
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth().height(80.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                    ),
            ) {
                navigationItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = Color.Black,
                            indicatorColor = Color.White,
                            unselectedIconColor = Color.LightGray,
                            unselectedTextColor = Color.LightGray,
                        ),
                        label = { Text(text = item.first) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index },
                        icon = { Icon(imageVector = item.second, contentDescription = null) },
                    )
                }
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxWidth().padding(innerPadding)
                .verticalScroll(rememberScrollState()),
        ) {
            Week4Card {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                ) {
                    Text(
                        text = "수진님의 지갑",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Column {
                            Text(
                                text = "토스머니",
                                fontWeight = FontWeight.Light,
                                fontSize = 14.sp,
                                color = Color.Gray,
                            )
                            Text(
                                text = "144,552원",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 36.sp,
                            )
                        }
                        Button(
                            onClick = {},
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF3D73FD),
                                contentColor = Color.White,
                            ),
                        ) {
                            Text(text = "돈 보내기")
                        }
                    }
                    Divider(color = Color.LightGray)
                    Week4ListMenuItem(
                        imagePainter = ColorPainter(Color(0xFF2196F3)),
                        text = "교통카드 잔액보고 충전하기",
                        label = "코레일 교통",
                        onMenuSelected = {},
                    )
                    Week4ListMenuItem(
                        imagePainter = ColorPainter(Color(0xFF009688)),
                        text = "토스머니 내역 보기",
                        onMenuSelected = {},
                    )
                    Week4ListMenuItem(
                        imagePainter = ColorPainter(Color(0xFFFFC107)),
                        text = "토스머니 충전하기",
                        onMenuSelected = {},
                    )
                }
            }
            Week4Card {
                Column {
                    Text(
                        text = "용돈기입장",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Week4ListMenuItem(
                        imagePainter = ColorPainter(Color(0xFF20CF75)),
                        text = "25,600원",
                        label = "이번달 쓴 돈",
                        onMenuSelected = {},
                    )
                }
            }
            Week4Card {
                Text(text = "Jetpack Compose Study 파이팅")
            }
        }
    }
}

@Composable
fun Week4Card(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        colors = CardDefaults.cardColors(
            contentColor = Color.Black,
            containerColor = Color.White,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp),
        shape = RoundedCornerShape(20.dp),
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 36.dp),
        ) {
            content()
        }
    }
}

@Composable
fun Week4ListMenuItem(
    imagePainter: Painter,
    text: String,
    onMenuSelected: () -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
) {
    Row(
        modifier = modifier.fillMaxWidth().clickable { onMenuSelected() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = imagePainter,
            modifier = Modifier.size(50.dp).clip(CircleShape),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(16.dp))
        if (label == null) {
            Text(text = text, modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
        } else {
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = label,
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp,
                    color = Color.Gray,
                )
                Text(text = text, fontWeight = FontWeight.Bold)
            }
        }
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                tint = Color.LightGray,
                contentDescription = null,
            )
        }
    }
}

@Preview
@Composable
fun Week4ScreenPreview() {
    ComposePracticeTheme {
        Week4Screen()
    }
}
