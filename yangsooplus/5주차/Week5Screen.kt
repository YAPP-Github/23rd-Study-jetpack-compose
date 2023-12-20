package com.yangsooplus.composepractice

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.addOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yangsooplus.composepractice.ui.theme.ComposePracticeTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Week5Screen() {
    val tabTitleList = listOf("모두", "폴더", "상품", "사진", "집들이", "노하우", "기타")
    val pagerState = rememberPagerState(initialPage = 0)
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
            ) {
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Filled.Share, contentDescription = null)
                }
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                text = "스크랩북",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier.fillMaxWidth(),
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        color = W5Color.SkyBlue,
                    )
                },
                edgePadding = 0.dp,
            ) {
                tabTitleList.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        selectedContentColor = W5Color.SkyBlue,
                        unselectedContentColor = W5Color.Black,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(index)
                            }
                        },
                    ) {
                        Text(
                            text = title,
                            modifier = Modifier.padding(vertical = 12.dp),
                        )
                    }
                }
            }
            HorizontalPager(
                modifier = Modifier.weight(1f),
                pageCount = tabTitleList.size,
                state = pagerState,
            ) {
                when (pagerState.currentPage) {
                    0 -> Week5ContentAll(modifier = Modifier.fillMaxSize())
                    1 -> Week5ContentFolder(modifier = Modifier.fillMaxSize())
                    else -> Spacer(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
fun Week5Merchandise(
    modifier: Modifier = Modifier,
    uri: String = "https://picsum.photos/500",
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
) {
    Box(
        modifier = modifier,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(uri)
                .crossfade(true)
                .build(),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = Modifier.fillMaxSize(),
        )
        Text(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp)
                .background(W5Color.Black.copy(alpha = 0.5f))
                .padding(8.dp),
            text = "상품",
            color = W5Color.White,
            fontSize = 12.sp,
        )
    }
}

@Composable
fun Week5Folder(
    folderName: String,
    elementCount: Int,
    modifier: Modifier = Modifier,
    uri: String = "https://picsum.photos/500",
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
) {
    Box(
        modifier = modifier.aspectRatio(1f),
    ) {
        if (elementCount > 0) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(uri)
                    .crossfade(true)
                    .build(),
                contentDescription = contentDescription,
                contentScale = contentScale,
                modifier = Modifier.fillMaxSize(),
            )
        }

        Column(
            modifier = Modifier.fillMaxSize()
                .background(color = W5Color.Black.copy(alpha = 0.5f)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = folderName, color = W5Color.White)
            Spacer(modifier = Modifier.height(12.dp))
            Row {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = null,
                    tint = W5Color.White,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = elementCount.toString(), color = W5Color.White)
            }
        }
    }
}

@Composable
fun Week5NewFolder(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .dashedBorder(color = W5Color.Gray, shape = RectangleShape)
            .clickable { onClick() },
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(text = "폴더 추가")
            FilledIconButton(
                onClick = { },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = W5Color.SkyBlue,
                    contentColor = W5Color.White,
                ),
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        }
    }
}

@Composable
fun Week5ContentAll(
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(10) {
            Week5Merchandise()
        }
    }
}

@Composable
fun Week5ContentFolder(
    modifier: Modifier = Modifier,
) {
    val folderList = listOf("수납" to 1, "커튼" to 1, "위시리스트" to 0, "스크랩북" to 0)
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            Week5NewFolder {}
        }
        items(folderList) {
            Week5Folder(folderName = it.first, elementCount = it.second)
        }
    }
}

@Preview
@Composable
fun Week5ScreenPreview() {
    ComposePracticeTheme {
        Week5Screen()
    }
}

object W5Color {
    val SkyBlue = Color(0xFF0BC5F3)
    val Black = Color.Black
    val Gray = Color.Gray
    val White = Color.White
}

fun Modifier.dashedBorder(
    color: Color,
    shape: Shape,
    strokeWidth: Dp = 1.dp,
    dashWidth: Dp = 4.dp,
    gapWidth: Dp = 4.dp,
    cap: StrokeCap = StrokeCap.Round,
) = this.drawWithContent {
    val outline = shape.createOutline(size, layoutDirection, this)

    val path = Path().apply { addOutline(outline) }

    val stroke = Stroke(
        cap = cap,
        width = strokeWidth.toPx(),
        pathEffect = PathEffect.dashPathEffect(
            intervals = floatArrayOf(dashWidth.toPx(), gapWidth.toPx()),
            phase = 0f,
        ),
    )

    drawContent()

    drawPath(
        path = path,
        style = stroke,
        color = color,
    )
}
