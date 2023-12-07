package com.yapp.composehomework1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yapp.composehomework1.ui.theme.ComposeHomework1Theme

class Homework1 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeHomework1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Homework1Screen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Homework1Screen() {
    val bottomItems = listOf("홈", "혜택", "송금", "전체")
    var bottomNavItem by rememberSaveable { mutableStateOf(0) }

    Scaffold(
        // 상단바
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.Notifications,
                        contentDescription = "알림",
                        tint = Color(0xFFA7AFB9),
                    )
                }
            }
        },

        // 하단 네비게이션
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
                containerColor = Color.White,
                tonalElevation = BottomAppBarDefaults.ContainerElevation,
            ) {
                bottomItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = Color.Black,
                            unselectedIconColor = Color.LightGray,
                            unselectedTextColor = Color.LightGray,
                            indicatorColor = Color.White,
                        ),
                        label = {
                            Text(text = item)
                        },
                        icon = {
                            when (item) {
                                "홈" -> {
                                    Icon(imageVector = Icons.Filled.Home, contentDescription = null)
                                }

                                "혜택" -> {
                                    Icon(imageVector = Icons.Filled.Star, contentDescription = null)
                                }

                                "송금" -> {
                                    Icon(imageVector = Icons.Filled.Send, contentDescription = null)
                                }

                                "전체" -> {
                                    Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
                                }
                            }
                        },
                        selected = index == bottomNavItem,
                        onClick = { bottomNavItem = index },
                    )
                }
            }
        },
        containerColor = Color(0xFFF0F2F5),
    ) { paddingValues ->
        // 스크롤 영역
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
        ) {
            ContentSection()
        }
    }
}

@Composable
fun ContentSection() {
    CardSection(title = "예빈님의 지갑") {
        Spacer(modifier = Modifier.size(36.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Text(
                    text = "토스머니",
                    fontSize = 12.sp,
                    color = Color.Gray,
                )
                Text(
                    text = "144,552원",
                    color = Color.Black,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
            }

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4972F5),
                    contentColor = Color.White,
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.padding(horizontal = 0.dp, vertical = 20.dp),
                contentPadding = PaddingValues(12.dp, 0.dp),
                onClick = {},
            ) {
                Text(
                    text = "돈 보내기",
                    fontSize = 12.sp,
                )
            }
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(start = 20.dp, top = 0.dp, end = 0.dp, bottom = 0.dp),
        )

        Spacer(modifier = Modifier.size(20.dp))
        ItemSection(
            icon = Icons.Filled.Face,
            color = Color.Cyan,
            subTitle = "코레일 교통",
            title = "교통카드 잔액보고 충전하기",
        )
        Spacer(modifier = Modifier.size(20.dp))
        ItemSection(
            icon = Icons.Filled.ThumbUp,
            color = Color.Green,
            subTitle = null,
            title = "토스머니 내역 보기",
        )
        Spacer(modifier = Modifier.size(20.dp))
        ItemSection(
            icon = Icons.Filled.Favorite,
            color = Color.Yellow,
            subTitle = null,
            title = "토스머니 충전하기",
        )
    }

    CardSection(title = "용돈기입장") {
        Spacer(modifier = Modifier.size(20.dp))

        ItemSection(
            icon = Icons.Filled.DateRange,
            color = Color.Green,
            subTitle = "이번달 쓴 돈",
            title = "25,600원",
        )
    }
}

@Composable
fun CardSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(Color.White),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 0.dp, vertical = 24.dp),
        ) {
            Text(
                text = title,
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 0.dp),
            )
            content()
        }
    }
}

@Composable
fun ItemSection(
    icon: ImageVector,
    color: Color,
    subTitle: String?,
    title: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(shape = CircleShape)
                .background(color),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center),
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 24.dp, vertical = 0.dp),
        ) {
            if (subTitle == null) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            } else {
                Text(
                    text = subTitle,
                    fontSize = 12.sp,
                    color = Color.Gray,
                )
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
            }
        }

        Icon(
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = "상세보기",
            tint = Color.LightGray,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopAppBarPreview() {
    ComposeHomework1Theme {
        Homework1Screen()
    }
}
