package com.example.composehomework2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import com.example.composehomework2.ui.theme.ComposeHomework2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeHomework2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Homework2Screen()
                }
            }
        }
    }
}

enum class Homework2TabItem(val title: String) {
    All("모두"),
    Folder("폴더"),
    Product("상품"),
    Picture("사진"),
    Housewarming("집들이"),
    Knowhow("노하우"),
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Homework2Screen(modifier: Modifier = Modifier) {
    var pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    var selectedTabIndex = pagerState.currentPage

    val tabs: Array<Homework2TabItem> = Homework2TabItem.values()
//    val list: List<String> = Homework2TabItem.values().map { it.title }

    Scaffold(
        // 상단바
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "뒤로가기",
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "공유하기",
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.White,
                    navigationIconContentColor = Color.Black,
                    actionIconContentColor = Color.Black,
                ),
            )
        },
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(paddingValues),
        ) {
            // 타이틀
            Text(
                text = "스크랩북",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = modifier.align(Alignment.CenterHorizontally),
            )
            Spacer(modifier = modifier.size(16.dp))
            // 프로필
            Row(
                modifier = modifier.align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = modifier
                        .size(18.dp)
                        .clip(shape = CircleShape)
                        .background(Color.Blue),
                )
                Spacer(modifier = modifier.size(8.dp))
                Text(
                    text = "id_abc_123",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(modifier = modifier.size(24.dp))

            // 탭
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.White,
                contentColor = Color.DarkGray,
                edgePadding = 0.dp,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = modifier.tabIndicatorOffset(currentTabPosition = tabPositions[selectedTabIndex]),
                        height = 4.dp,
                        color = Color(0xFF00CEF4),
                    )
                },
            ) {
                tabs.forEachIndexed { index, tabItem ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        selectedContentColor = Color(0xFF00CEF4),
                        unselectedContentColor = Color.DarkGray,
                    ) {
                        Text(
                            text = tabItem.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            modifier = modifier.padding(horizontal = 4.dp, vertical = 12.dp),
                        )
                    }
                }
            }

            // 페이저
            HorizontalPager(
                pageCount = tabs.size,
                state = pagerState,
            ) { index ->
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopStart,
                ) {
                    when (tabs[index]) {
                        Homework2TabItem.All -> AllScreen(itemList)
                        Homework2TabItem.Folder -> FolderScreen(itemList)
                        Homework2TabItem.Product -> ProductScreen("Product")
                        Homework2TabItem.Picture -> PictureScreen("Picture")
                        Homework2TabItem.Housewarming -> HousewarmingScreen("Housewarming")
                        Homework2TabItem.Knowhow -> KnowhowScreen("Knowhow")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Homework2ScreenPreview() {
    ComposeHomework2Theme {
        Homework2Screen()
    }
}
