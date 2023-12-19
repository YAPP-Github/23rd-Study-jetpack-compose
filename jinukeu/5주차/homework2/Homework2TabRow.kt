package com.jinukeu.playground.homework2

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Divider
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Homework2TabRow(
    tabTitleList: List<String>,
    selectedTabIndex: Int,
    onClick: (page: Int) -> Unit,
) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = Color.Transparent,
        divider = {
            Divider(
                thickness = 2.dp,
                color = Color.LightGray,
            )
        },
        indicator = { tabPositions ->
            if (selectedTabIndex < tabPositions.size) {
                Homework2TabRowIndicator(tabPositions, selectedTabIndex)
            }
        },
        tabs = {
            tabTitleList.forEachIndexed { index, tab ->
                HomeworkTab(selectedTabIndex, index, tab, onClick)
            }
        },
    )
}

@Composable
private fun Homework2TabRowIndicator(
    tabPositions: List<TabPosition>,
    selectedTabIndex: Int
) {
    val currentTabWidth by animateDpAsState(
        targetValue = tabPositions[selectedTabIndex].width,
        label = "",
    )
    val indicatorOffset by animateDpAsState(
        targetValue = tabPositions[selectedTabIndex].left,
        label = "",
    )
    Divider(
        modifier = Modifier
            .wrapContentSize(Alignment.BottomStart)
            .offset(x = indicatorOffset)
            .width(currentTabWidth),
        thickness = 2.dp,
        color = Color.Blue,
    )
}

@Composable
private fun HomeworkTab(
    selectedTabIndex: Int,
    index: Int,
    tab: String,
    onClick: (page: Int) -> Unit
) {
    val selected = selectedTabIndex == index
    Tab(
        modifier = Modifier
            .height(36.dp),
        text = {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = tab,
                    color = if (selected) Color.Blue else Color.LightGray,
                    textAlign = TextAlign.Center,
                )
            }
        },
        selected = selected,
        selectedContentColor = Color.White, // Ripple 제거
        onClick = { onClick(index) },
    )
}

@Preview(showBackground = true)
@Composable
fun Homework2TabRowPreview() {
    val tabTitleList = listOf("모두", "폴더", "사진", "노하우", "기타")
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    Homework2TabRow(
        tabTitleList = tabTitleList,
        selectedTabIndex = selectedTabIndex,
        onClick = { page ->
            selectedTabIndex = page
        },
    )
}
