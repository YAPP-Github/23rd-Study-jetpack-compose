package com.example.composehomework3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composehomework3.ui.theme.ComposeHomework3Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeHomework3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Homework3Screen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Homework3Screen(
    modifier: Modifier = Modifier,
) {
    var pagerState = rememberPagerState()
    var selectedTabIndex = pagerState.currentPage
    val scope = rememberCoroutineScope()

    val pagerTabs: List<String> = HorizontalPagerItem.values().map { it.label }

    Scaffold(
        topBar = {
            TopAppBar()
        },
        bottomBar = {
            BottomNavigationBar()
        },
    ) { paddingValues ->
        Column(
            modifier = modifier.padding(paddingValues),
        ) {
            // 상단 페이저 탭
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.White,
                contentColor = Color.Black,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = modifier.tabIndicatorOffset(currentTabPosition = tabPositions[selectedTabIndex]),
                        height = 4.dp,
                        color = Color.Black,
                    )
                },
            ) {
                pagerTabs.forEachIndexed { index, tabItem ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        selectedContentColor = Color.Black,
                        unselectedContentColor = Color.Gray,
                    ) {
                        Text(
                            text = tabItem,
                            fontWeight = FontWeight.Bold,
                            modifier = modifier.padding(vertical = 14.dp),
                        )
                    }
                }
            }
            // 페이저
            HorizontalPager(
                pageCount = pagerTabs.size,
                state = pagerState,
            ) { index ->
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopStart,
                ) {
                    when (HorizontalPagerItem.values()[index]) {
                        HorizontalPagerItem.FORSALE -> ForSaleScreen(forSaleItemList)
                        HorizontalPagerItem.MYCOMMUNITY -> MyCommunityScreen("My Community")
                    }
                }
            }
        }
    }
}

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // 주소
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "화곡제1동",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
            )
            Spacer(modifier = modifier.size(2.dp))
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = null,
                tint = Color.Gray,
                modifier = modifier.size(18.dp),
            )
        }
        Spacer(modifier = modifier.weight(1f))
        // 오른쪽 버튼
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null,
            )
            Icon(
                imageVector = Icons.Outlined.List,
                contentDescription = null,
            )
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = null,
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
) {
    val tabs = listOf(
        BottomNavigationItem.HOME,
        BottomNavigationItem.NEARBY,
        BottomNavigationItem.POST,
        BottomNavigationItem.INBOX,
        BottomNavigationItem.MYKARROT,
    )

    var selectedItem by remember { mutableStateOf(0) }

    BottomAppBar(
        containerColor = Color.White,
    ) {
        tabs.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = { selectedItem = index },
                icon = {
                    val icon = if (selectedItem == index) item.selectedIcon else item.unselectedIcon
                    Icon(imageVector = icon, contentDescription = null)
                },
                alwaysShowLabel = true,
                label = {
                    Text(text = item.label)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    unselectedIconColor = Color.Black,
                    selectedTextColor = Color.Black,
                    unselectedTextColor = Color.Black,
                    indicatorColor = Color.White,
                ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Homework3ScreenPreview() {
    ComposeHomework3Theme {
        Homework3Screen()
    }
}
