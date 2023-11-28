## LazyList
- 많은 수의 항목 or 길이를 알 수 없는 목록을 표시해야 하는 경우 사용
- 표시 영역에 표시되는 항목만 구성하여 배치

### item 추가 방법

```kotlin
LazyColumn(
    contentPadding = PaddingValues(8.dp),
    verticalArrangement = Arrangement.spacedBy(4.dp),
) {
    item {
        MyListItem(text = "item")
    }

    items(10) {
        MyListItem(text = "count: $it items")
    }

    val textList = listOf("list1", "list2", "list3", "list4", "list5")
    items(textList) {
        MyListItem(text = it)
    }
}
```
<img width="299" alt="스크린샷 2023-11-28 오전 11 41 36" src="https://github.com/YAPP-Github/23rd-Study-jetpack-compose/assets/69582122/8c211560-ad63-4654-ab62-524452a3e723">
<img width="301" alt="스크린샷 2023-11-28 오전 11 42 16" src="https://github.com/YAPP-Github/23rd-Study-jetpack-compose/assets/69582122/0f1c482c-f43d-4e95-aa12-1138a1075eab">


### inline, crossinline
```kotlin
inline fun <T> LazyListScope.items(
    items: List<T>,
    noinline key: ((item: T) -> Any)? = null,
    noinline contentType: (item: T) -> Any? = { null },
    crossinline itemContent: @Composable LazyItemScope.(item: T) -> Unit
) = items(
    count = items.size,
    key = if (key != null) { index: Int -> key(items[index]) } else null,
    contentType = { index: Int -> contentType(items[index]) }
) {
    itemContent(items[it])
}
```
- `items`는 inline fun
- `noinline`, `crossinline` 이라는 키워드도 등장
- 관련 내용 정리가 잘 되어있는 포스팅
https://leveloper.tistory.com/171

➡️ `key`, `contentType`은 `items()`에 파라미터로 전달해야 하므로 `noinline` 파라미터

➡️ `itemContent`는 LazyItemScope 라는 다른 실행 컨텍스트를 실행시키므로 crossinline?

![스크린샷 2023-11-28 오후 1 34 44](https://github.com/YAPP-Github/23rd-Study-jetpack-compose/assets/69582122/e7709be6-4605-4d20-b776-c5b3936b8a97)




### LazyListState

```kotlin
    val lazyListState = rememberLazyListState()
    val fvii by remember { derivedStateOf { lazyListState.firstVisibleItemIndex } }
    val csf by remember { derivedStateOf { lazyListState.canScrollForward } }

    Column {
        Text(text = "firstVisibleItemIndex: $fvii")
        Text(text = "canScrollForward: $csf")

        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            state = lazyListState,
        ) {
            items(30) {
                MyListItem(text = "count: $it items")
            }
        }
    }
```

![lazyscrollstate](https://github.com/YAPP-Github/23rd-Study-jetpack-compose/assets/69582122/3f547d2f-6a21-4fd9-a268-30a74cda2e6c)

### Key

- 기본적으로 각 item의 상태는 위치를 기준으로 키가 지정
- 위치를 효율적으로 변경하는 항목에 상태가 저장되지 않음

key를 지정하지 않은 LazyColumn이 아래 그림과 같은 상태에서

![스크린샷 2023-11-28 오후 2 58 25](https://github.com/YAPP-Github/23rd-Study-jetpack-compose/assets/69582122/0a90c6db-8ea6-44d9-a662-307f5add5c47)


(왼쪽 밝은 숫자 - recomposition 횟수, 오른쪽 어두운 숫자 - recomposition 건너뛴 횟수)


**리스트 첫번째 item이 삽입되면?**
- 위치를 기준으로 키가 지정되어 있으므로 위치가 바뀌면서 recomposition 발생
  
![스크린샷 2023-11-28 오후 3 05 21](https://github.com/YAPP-Github/23rd-Study-jetpack-compose/assets/69582122/f19b8409-e6e4-43db-aede-4c4e656395cb)

**리스트 마지막에 item이 삽입되면?**
- 기존 아이템의 위치가 변하지 않으므로 recomposition 건너뜀
![스크린샷 2023-11-28 오후 2 58 35](https://github.com/YAPP-Github/23rd-Study-jetpack-compose/assets/69582122/2086b24b-c718-4de1-8129-11674f15c970)


**key를 지정해준다면?**

![스크린샷 2023-11-28 오후 3 06 00](https://github.com/YAPP-Github/23rd-Study-jetpack-compose/assets/69582122/29901581-2c48-4af9-b4a3-9564c2ee11bb)
  
- 이미 존재했던 item들은 key인 id로 같은 item이 구별되어 recomposition을 건너뛰었다



**key는 같지만 내용이 바뀐경우에는?**

![스크린샷 2023-11-28 오후 2 58 04](https://github.com/YAPP-Github/23rd-Study-jetpack-compose/assets/69582122/64df4e34-1b04-40fa-b23c-4d12e0be2956)

바뀐 아이템만 recompositon 발생

## LazyGrid

- Grid 배치 방법은 `GridCells`로 지정해주면 된다.
  - `GridCells.Adaptive(minSize: Dp)`: 최소 minSize 이상으로 공간을 균등하게 나눠서 배치
  - `GridCells.Fixed(count: Int)`: row/column에 count 개수 만큼 배치 (레이아웃 방향 따라)
  - `GridCells.FixedSize(size: Dp)`: size로 배치되며, 남은 공간은 축에 따라 정렬

## Dialog

- Dialog만 띄워보면? 어두운 뒷배경만 제공된다. 나머지는 Self 구현

![스크린샷 2023-11-28 오후 3 17 52](https://github.com/YAPP-Github/23rd-Study-jetpack-compose/assets/69582122/08b9e6b3-12fc-4db1-8802-bada78841e55)


- Dialog content에 Card만 넣어보면...

![스크린샷 2023-11-28 오후 3 20 06](https://github.com/YAPP-Github/23rd-Study-jetpack-compose/assets/69582122/aca80c28-bbe6-42e1-9d16-befe2c18a4f6)


### AlertDialog

- Material에서 제공하는 Slot api 형식으로 조립하는 다이얼로그

![스크린샷 2023-11-28 오후 3 23 15](https://github.com/YAPP-Github/23rd-Study-jetpack-compose/assets/69582122/0a99bf83-8a35-4d56-8255-bf526484548f)

```kotlin
@Composable
fun AlertDialog(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    dismissButton: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,
    text: @Composable (() -> Unit)? = null,
    shape: Shape = AlertDialogDefaults.shape,
    containerColor: Color = AlertDialogDefaults.containerColor,
    iconContentColor: Color = AlertDialogDefaults.iconContentColor,
    titleContentColor: Color = AlertDialogDefaults.titleContentColor,
    textContentColor: Color = AlertDialogDefaults.textContentColor,
    tonalElevation: Dp = AlertDialogDefaults.TonalElevation,
    properties: DialogProperties = DialogProperties()
)

```

- 디폹트 값으로 들어가 있는 `AlertDialogDefaults`는 object로 지정되어 있다.
- `AlertDialogDefaults` 속 `DialogToken`는 material 라이브러리 속 internal로 관리되고 있기 때문에 접근 불가
    - 최근 과제전형에서 Custom Theme를 사용했더니, Material을 사용하지 않는 Custom Component에선 이런 상수들에 접근할 수 없어서 이런 상수를 담는 object를 따로 만들어 사용했어요. 

```kotlin
object AlertDialogDefaults {
    /** The default shape for alert dialogs */
    val shape: Shape @Composable get() = DialogTokens.ContainerShape.toShape()

    /** The default container color for alert dialogs */
    val containerColor: Color @Composable get() = DialogTokens.ContainerColor.toColor()

    /** The default icon color for alert dialogs */
    val iconContentColor: Color @Composable get() = DialogTokens.IconColor.toColor()

    /** The default title color for alert dialogs */
    val titleContentColor: Color @Composable get() = DialogTokens.HeadlineColor.toColor()

    /** The default text color for alert dialogs */
    val textContentColor: Color @Composable get() = DialogTokens.SupportingTextColor.toColor()

    /** The default tonal elevation for alert dialogs */
    val TonalElevation: Dp = DialogTokens.ContainerElevation
}


internal object DialogTokens {
    val ActionFocusLabelTextColor = ColorSchemeKeyTokens.Primary
    val ActionHoverLabelTextColor = ColorSchemeKeyTokens.Primary
    val ActionLabelTextColor = ColorSchemeKeyTokens.Primary
    val ActionLabelTextFont = TypographyKeyTokens.LabelLarge
    val ActionPressedLabelTextColor = ColorSchemeKeyTokens.Primary
    val ContainerColor = ColorSchemeKeyTokens.Surface
    val ContainerElevation = ElevationTokens.Level3
    val ContainerShape = ShapeKeyTokens.CornerExtraLarge
    val ContainerSurfaceTintLayerColor = ColorSchemeKeyTokens.SurfaceTint
    val HeadlineColor = ColorSchemeKeyTokens.OnSurface
    val HeadlineFont = TypographyKeyTokens.HeadlineSmall
    val SupportingTextColor = ColorSchemeKeyTokens.OnSurfaceVariant
    val SupportingTextFont = TypographyKeyTokens.BodyMedium
    val IconColor = ColorSchemeKeyTokens.Secondary
    val IconSize = 24.0.dp
}

```

## SnackBar

```kotlin
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Show snackbar") },
                icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar("Snackbar")
                    }
                }
            )
        }
    ) { contentPadding ->
        // Screen content
    }
```
![스크린샷 2023-11-28 오후 3 33 26](https://github.com/YAPP-Github/23rd-Study-jetpack-compose/assets/69582122/4a57cbb9-7a3b-4a6f-b8d5-7c8b211b9f75)

![스크린샷 2023-11-28 오후 3 33 18](https://github.com/YAPP-Github/23rd-Study-jetpack-compose/assets/69582122/b5f388e3-5af5-4075-bd6e-7ac864d5019e)


🤔 SnackBar 없는 SnackBar?

- snackbarHostState.showSnackBar
  - suspend fun이어서 coroutineScope 안에서 호출해야 함
  - 파라미터에 내용을 담아 호출하기만 하면 스낵바가 알아서 표시된다

```kotlin
    suspend fun showSnackbar(
        message: String,
        actionLabel: String? = null,
        withDismissAction: Boolean = false,
        duration: SnackbarDuration =
            if (actionLabel == null) SnackbarDuration.Short else SnackbarDuration.Indefinite
    ): SnackbarResult =
        showSnackbar(SnackbarVisualsImpl(message, actionLabel, withDismissAction, duration))
```
```kotlin

    suspend fun showSnackbar(visuals: SnackbarVisuals): SnackbarResult = mutex.withLock {
        try {
            return suspendCancellableCoroutine { continuation ->
                currentSnackbarData = SnackbarDataImpl(visuals, continuation)
            }
        } finally {
            currentSnackbarData = null
        }
    }

```
- 동시에 여러 번 실행되는 것을 막기 위해서인지 mutex로 lock이 되어있음
- Continuation 기반으로 작동?


```kotlin

    private class SnackbarDataImpl(
        override val visuals: SnackbarVisuals,
        private val continuation: CancellableContinuation<SnackbarResult>
    ) : SnackbarData {

        override fun performAction() {
            if (continuation.isActive) continuation.resume(SnackbarResult.ActionPerformed)
        }

        override fun dismiss() {
            if (continuation.isActive) continuation.resume(SnackbarResult.Dismissed)
        }

      // 생략

```
- continuation이 살아있으면 계속 ActionPerformed 상태이고, 죽으면 Dismissed로 변경되어 스낵바가 사라지게 하는 것 같다

```kotlin

@Composable
fun SnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    snackbar: @Composable (SnackbarData) -> Unit = { Snackbar(it) }
) {
    val currentSnackbarData = hostState.currentSnackbarData
    val accessibilityManager = LocalAccessibilityManager.current
    LaunchedEffect(currentSnackbarData) {
        if (currentSnackbarData != null) {
            val duration = currentSnackbarData.visuals.duration.toMillis(
                currentSnackbarData.visuals.actionLabel != null,
                accessibilityManager
            )
            delay(duration)
            currentSnackbarData.dismiss()
        }
    }
    FadeInFadeOutWithScale(
        current = hostState.currentSnackbarData,
        modifier = modifier,
        content = snackbar
    )
}

```
- SnackbarHost에서 디폴트 파라미터로 Snackbar를 생성
  - SnackBar 또한 Surface를 기반으로 만들어진 UI
- LaunchedEffect 속에서 delay를 줬다가 사라지게 만드는 중
- FadeInFadeOutWithScale 표시되었다가 사라질 때 애니메이션 내용
