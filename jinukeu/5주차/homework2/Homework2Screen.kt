package com.jinukeu.playground.homework2

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

enum class Homework2ScreenPage(val idx: Int, val title: String) {
    ALL(0, "모두"),
    FOLDER(1, "폴더"),
    PICTURE(2, "사진"),
    KNOW_HOW(3, "노하우"),
    ETC(4, "기타"),
    ;

    companion object {
        fun valueOf(idx: Int): Homework2ScreenPage {
            return values().find { it.idx == idx } ?: throw IllegalArgumentException()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Homework2Screen() {
    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState {
        Homework2ScreenPage.values().size
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Homework2TabRow(
            tabTitleList = Homework2ScreenPage.values().map { it.title },
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
                when (Homework2ScreenPage.valueOf(page)) { // page 대신 pagerState.currentPage 사용했더니 엄청난 버퍼링을 겪었어요 ...
                    Homework2ScreenPage.ALL -> AllScreen()
                    Homework2ScreenPage.FOLDER -> FolderScreen()
                    Homework2ScreenPage.PICTURE -> Text("PICTURE")
                    Homework2ScreenPage.KNOW_HOW -> Text("KNOW_HOW")
                    Homework2ScreenPage.ETC -> Text("ETC")
                }
            }
        }
    }
}
