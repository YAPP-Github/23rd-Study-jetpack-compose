package com.jinukeu.playground.homework3

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jinukeu.playground.HomeWorkNavigationBarItem
import com.jinukeu.playground.homework2.AllScreen
import com.jinukeu.playground.homework2.FolderScreen
import com.jinukeu.playground.homework2.Homework2TabRow
import kotlinx.coroutines.launch

enum class Homework3ScreenPage(val title: String) {
    FOR_SALE("For Sale"),
    MY_COMMUNITY("My Community"),
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Homework3Screen() {
    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState {
        Homework3ScreenPage.values().size
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = "화곡제 1동", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
                }

                Spacer(modifier = Modifier.weight(1f))

                Icon(imageVector = Icons.Default.Search, contentDescription = null)
                Icon(imageVector = Icons.Default.List, contentDescription = null)
                Icon(imageVector = Icons.Default.Notifications, contentDescription = null)
            }
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .height(80.dp),
                containerColor = Color.LightGray,
            ) {
                repeat(4) {
                    HomeWorkNavigationBarItem()
                }
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
        ) {
            Homework2TabRow(
                tabTitleList = Homework3ScreenPage.values().map { it.title },
                selectedTabIndex = pagerState.currentPage,
                onClick = { page ->
                    scope.launch {
                        pagerState.animateScrollToPage(page)
                    }
                },
            )

            HorizontalPager(
                state = pagerState,
            ) { page ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopStart,
                ) {
                    when (Homework3ScreenPage.values()[page]) {
                        Homework3ScreenPage.FOR_SALE -> ForSaleScreen()
                        Homework3ScreenPage.MY_COMMUNITY -> ForSaleScreen()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun Homework3ScreenPreview() {
    Homework3Screen()
}
