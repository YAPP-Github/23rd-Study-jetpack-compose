# Phase

## Compose의 3가지 Phase
<img src="https://developer.android.com/static/images/jetpack/compose/compose-phases.png"/>


1. Composition
   - 어떤 UI를 보여줄 지 정한다
2. Layout
    - UI를 배치할 위치를 정한다. 
    - 측정, 배치 두 단계로 구성
3. Drawing
    - UI를 화면에 그린다.

- 예외) BoxWithConstraints, LazyColumn, LazyRow
  - 하위 항목의 구성이 상위 항목의 레이아웃 단계에 따라 달라진다.

### Composition
- Composable 함수를 실행하고 UI 트리 구조 생성
- 다음 단계에 필요한 정보를 담은 Layout Node로 구성
  (사진)

### Layout
- Composition 단계에서 생성된 UI 트리를 바탕으로 각 노드의 크기와 위치를 결정
- 각 노드는 한 번만 방문
  (사진)
- Layout Tree를 탐색하는 방법
1. 하위 노드가 있으면 하위 노드를 측정
2. 모든 하위 노드를 측정하면 자신의 크기를 결정
3. 하위 노드를 배치
- Layout 단계에서 포함되는 정보: 너비, 높이, 그릴 (x, y) 좌표

<img src = "https://developer.android.com/static/images/jetpack/compose/code-subsection.png"/>
1. Row의 자식인 Image, Column Measure
2. Image는 자식이 없으므로 크기를 결정하고 부모 Row에게 알려줌
3. Column의 자식인 2개의 Text Measure
4. 각 Text는 자식이 없으므로 크기를 결정하고 Column에게 알려줌
5. Column은 자식의 크기를 사용하여 자체 크기 결정
    - 최대 자식의 너비와 높이의 합을 사용
6. Column을 기준으로 자식을 배치
7. Row가 자신의 크기 결정 후 하위 항목 배치

### Drawing
- 노드를 화면에 그리는 단계
- 트리가 위에서 아래로 다시 탐색됨

<img src = "https://developer.android.com/static/images/jetpack/compose/code-subsection.png"/>
1. Row를 그림 (Background color 등..)
2. Image 그림
3. Column 그림
4. Text 2개 그림

## Phase 별 State

### Composition
- State 변경
  - 이 상태를 읽는 모든 Composable 함수의 재실행 예약
  - Composition 결과에 따라 Layout -> Drawing 단계 거침
  - 단, 콘텐츠가 동일하게 유지되고, 크기와 레이아웃이 변경되지 않으면 건너뜀

### Layout
- 측정과 배치 2단계로 구성
  - 두 단계는 별개의 단계이지만 서로 관련된 경우가 많음
  - 배치 단계가 측정 단계에 속하는 restart scope에 영향을 줄 수 있음
- 측정 단계에서 읽는 것 - 크기 관련
  - Layout Composable에 전달된 측정 람다
  - LayoutModifier의 MesureScope.measure()
- 배치 단계에서 읽는 것 - 위치 관련
  - 함수의 배치 블록
  - Modifier.offset{} 람다 블록

### Drawing
- Canvas(), Modifier.drawBehind, Modifier.drawWithContent 등
```kotlin
var color by remember { mutableStateOf(Color.Red) }
Canvas(modifier = modifier) {
    // The `color` state is read in the drawing phase
    // when the canvas is rendered.
    // Changes in `color` restart the drawing.
    drawRect(color)
}
```
- 이 경우 color의 상태 변화는 Drawing Phase만 실행하게 된다.

<img src = "https://developer.android.com/static/images/jetpack/compose/phases-state-read-draw.svg?hl=ko"/>

### State 읽기 최적화
- 적절한 단계에서 각 상태를 읽어 작업량을 최소화

```kotlin
Box {
    val listState = rememberLazyListState()

    Image(
        // Non-optimal implementation!
        Modifier.offset(
            with(LocalDensity.current) {
                // State read of firstVisibleItemScrollOffset in composition
                (listState.firstVisibleItemScrollOffset / 2).toDp()
            }
        )
    )

    LazyColumn(state = listState)
}
```
- `firstVisibleItemScrollOffset` 상태 값을 읽음
  - `Modifier.offset` 함수에 값을 전달
  - 스크롤하면? `firstVisibleItemScrollOffset` 상태 값이 변함
  - 전체 Composable이 다시 Composition->Layout->Drawing 과정을 거치게 됨
- 스크롤하여 항목은 변경되지 않고 위치만 변경된 경우
  - 얄쨜없이 모든 Compose phase트리거 하게 됨
  - 위치를 정하는 Layout 단계만 트리거하도록 최적화할 수 있음

```kotlin
Box {
    val listState = rememberLazyListState()

    Image(
        Modifier.offset {
            // State read of firstVisibleItemScrollOffset in Layout
            IntOffset(x = 0, y = listState.firstVisibleItemScrollOffset / 2)
        }
    )

    LazyColumn(state = listState)
}
```
- `Modifier.offset(offset: Density.() -> IntOffset)`로 교체
  - 결과 offset을 람다 블록에서 반환
  - 이 람다 블록은 Layout 단계에서 호출됨
    - Composition 단계에서 firstVisibleItemScrollOffset 상태를 읽지 않음
    - 스크롤 하는 프레임마다 변경 -> 상태 읽기를 레이아웃 단계로 연기하여 계속된 리컴포지션 방지
  - 만약 Composition 단계에서 상태를 읽어야 한다면
    - derivedStateOf를 이용하여 리컴포지션 수를 최소화

## Recomposition Loop
- Compose의 Phase는 역행하지 않음
- 같은 프레임에서는 그렇지만 다른 프레임에서 컴포지션 루프에 들어갈 수 있음

```kotlin
Box {
    var imageHeightPx by remember { mutableStateOf(0) }

    Image(
        painter = painterResource(R.drawable.rectangle),
        contentDescription = "I'm above the text",
        modifier = Modifier
            .fillMaxWidth()
            .onSizeChanged { size ->
                // Don't do this
                imageHeightPx = size.height
            }
    )

    Text(
        text = "I'm below the image",
        modifier = Modifier.padding(
            top = with(LocalDensity.current) { imageHeightPx.toDp() }
        )
    )
}
```
- Modifier.onSizeChanged(): 이미지 크기 파악
- Text의 Modifier.padding: 이미지 크기만큼 Text를 아래로 내리고 잇음
- 문제점: 단일 프레임 내에서 최종 레이아웃에 도달하지 못함 
  - 프레임1) 이미지 크기 파악 -> 프레임2) 이미지 크기에 맞게 Text 위치 변경
  - 사용자의 화면에서 UI가 이리저리 이동하게 됨

- 프레임1 - Composition
  - imageHeightPx = 0
  - Text의 Modifier.padding(top = 0)
- 프레임1 - Layout
  - onSizeChange 콜백 호출
  - imageHeightPx = 이미지 실제 높이
  - 상태 변경이 트리거 -> 다음 프레임의 Recomposition 예약
- 프레임1 - Drawing
  - imageHeightPx의 값이 변했어도 Text의 padding은 0
  - 값의 변경사항이 아직 반영되지 않았음. (다음 프레임에 반영됨)
- 프레임2 - Composition
  - 이제 State인 imageHeightPx을 읽음
  - Text의 padding에 imageHeightPx 값 제공
- 프레임2 - Layout
  - imageHeightPx 값은 이미지 크기가 변하지 않아 동일 -> Recomposition 예약하지 않음

# 성능

### Compose 3 Phase가 성능에 미치는 영향
- Composition: 표시할 항목 결정하여 UI 트리 빌드
- Layout: UI 트리 요소 크기와 배치 결정
- Drawing: UI 요소 렌더링 
- 필요하지 않은 단계를 지능적으로 건너뛸 수 있다
  - 크기가 같은 두 아이콘 간의 전환
    - Composition, Layout을 건너뛰고 Drawing만 다시 하여 그리기만 함
- 성능 개선의 원칙
  1. 가능한 Composable 함수 외부에서 계산하기
     - 내부에 있으면 그 계산 과정이 잠재적으로 애니메이션의 모든 프레임에서 다시 실행될 수 있음
     - UI를 빌드하는데 필요한 정보만으로 제한해야 함
  2. 최대한 오래 State 읽기를 연기하기
     - State 읽기를 하위 컴포저블이나 다음 Phase로 이동
     - Recomposition 최소화하거나 Phase 건너뛰기로 성능 이점
     - 자주 변경되는 상태 값 대신 람다 함수를 전달
     - 자주 변경되는 상태를 전달할 때는 람다 기반 수정자를 기본으로 선택

## 권장사항

### remember을 사용하여 계산 결과 저장
- 계산을 한번 실행하고 필요할 때마다 결과를 가져오기
- 비용이 많이 드는 계산 최소화

```kotlin
@Composable
fun ContactList(
    contacts: List<Contact>,
    comparator: Comparator<Contact>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier) {
        // DON’T DO THIS
        items(contacts.sortedWith(comparator)) { contact ->
            // ...
        }
    }
}
```
- ContactsList의 Recomposition 마다 contacts가 변경되지 않았더라도 다시 정렬된다.
- 목록을 스크롤 할때마다 Recomposition 트리거 

```kotlin
@Composable
fun ContactList(
    contacts: List<Contact>,
    comparator: Comparator<Contact>,
    modifier: Modifier = Modifier
) {
    val sortedContacts = remember(contacts, sortComparator) {
        contacts.sortedWith(sortComparator)
    }

    LazyColumn(modifier) {
        items(sortedContacts) {
          // ...
        }
    }
}
```
- 외부에서 정렬하고, 정렬한 목록을 remember로 저장
- contacts가 변경될 때에만 정렬 과정을 수행하여 결과 값 저장

### LazyLayout에서 Key 사용하기
- 이 친구.. 최대한 지능적으로 재사용하기 위해 노력하고 있음
- LazyLayout에서는 call site에 따른 구분을 할 수 없음
- key가 없을 경우, 순서로 객체를 구분함
  - 항목 변경 없이 순서만 바뀌어도 모든 항목을 Recomposition
- key를 지정하여 unique한 객체를 구분 -> Compose "이거 똑같으니까 Recomposition 건너 뛰자"

### derivedStateOf로 Recomposition 제한
- 상태가 빠르게 변경되면 UI가 필요 이상으로 재구성될 수 있는 위험이 있음
  - 스크롤 할때마다 listState가 변경됨 -> 계속 재구성
  - 새로운 항목이 하단에 표시될 때까지는 재구성하지 않아도 됨
```kotlin
// DONT
val listState = rememberLazyListState()

LazyColumn(state = listState) {
    // ...
}

val showButton = listState.firstVisibleItemIndex > 0

AnimatedVisibility(visible = showButton) {
    ScrollToTopButton()
  
  //DO
  val listState = rememberLazyListState()

  LazyColumn(state = listState) {
    // ...
  }

  val showButton by remember {
    derivedStateOf {
      listState.firstVisibleItemIndex > 0
    }
  }

  AnimatedVisibility(visible = showButton) {
    ScrollToTopButton()
  }
}
```
- 파생 상태를 사용하면 firstVisibleItemIndex가 0 -> false, 1 이상일 때 true
- 1에서 2로 변한다? showButton 상태는 변하지 않음 -> 재구성 일어나지 않음

### State 읽기를 최대한 연기
```kotlin
@Composable
fun SnackDetail() {
    // ...

    Box(Modifier.fillMaxSize()) { // Recomposition Scope Start
        val scroll = rememberScrollState(0)
        // ...
        Title(snack, scroll.value)
        // ...
    } // Recomposition Scope End
}

@Composable
private fun Title(snack: Snack, scroll: Int) {
    // ...
    val offset = with(LocalDensity.current) { scroll.toDp() }

    Column(
        modifier = Modifier
            .offset(y = offset)
    ) {
        // ...
    }
}
```
- 스크롤 상태 변경?
  - 가장 가까운 상위 Recomposition Scope를 찾음: SnackDetail
  - SnackDetail을 재구성하고 내부 컴포저블도 모두 재구성

```kotlin
@Composable
fun SnackDetail() {
    // ...

    Box(Modifier.fillMaxSize()) { // Recomposition Scope Start
        val scroll = rememberScrollState(0)
        // ...
        Title(snack) { scroll.value }
        // ...
    } // Recomposition Scope End
}

@Composable
private fun Title(snack: Snack, scrollProvider: () -> Int) {
    // ...
    val offset = with(LocalDensity.current) { scrollProvider().toDp() }
    Column(
        modifier = Modifier
            .offset(y = offset)
    ) {
    // ...
    }
}
```
- 이렇게 스크롤 매개변수를 람다로 전달하면?
- Title이 scroll 상태를 참조할 수 있지만, 매개변수로 받는게 아님
  - 람다로 전달하여 Title 내부에 진짜 필요한 곳에서 읽도록 함
- 스크롤이 변경되면?
  - 가장 가까운 재구성 범위: Title
  - 전체 Box를 재구성하지 않아도 됨

```kotlin
@Composable
private fun Title(snack: Snack, scrollProvider: () -> Int) {
    // ...
    Column(
        modifier = Modifier
            .offset { IntOffset(x = 0, y = scrollProvider()) }
    ) {
      // ...
    }
}
```
- 한번 더 수정해서, scroll을 제공하는 람다를 Modifier.offset에서 사용한다면?
  - Composition이 아닌 Layout 단계에서 scroll State를 읽게 됨
  - Composition 단계를 뛰어넘음


```kotlin
// Here, assume animateColorBetween() is a function that swaps between
// two colors
val color by animateColorBetween(Color.Cyan, Color.Magenta)

Box(Modifier.fillMaxSize().background(color))
```
- Cyan, Magenta 두 색상이 프레임 단위로 빠르게 전환되는 경우
- color가 변할 때마다 모든 프레임에서 Recomposition 발생

```kotlin
val color by animateColorBetween(Color.Cyan, Color.Magenta)
Box(
   Modifier
      .fillMaxSize()
      .drawBehind {
         drawRect(color)
      }
)
```
- Drwaing 단계에서 읽는 drawBehind를 사용하여 앞 단계를 건너뛰고 Drwaing 단계에서 색만 바꿔칠함

### 역방향 쓰기 방지
- 이미 읽힌 상태에 쓰지 않는다.
```kotlin
@Composable
fun BadComposable() {
  var count by remember { mutableStateOf(0) }

  // Causes recomposition on click
  Button(onClick = { count++ }, Modifier.wrapContentSize()) {
    Text("Recompose")
  }

  Text("$count")
  count++ // Backwards write, writing to state after it has been read
}
```
- Composable을 읽고 나서 마지막에 count를 업데이트 
  - Recomposition 발생하여 다시 읽음
  - 또 count 업데이트
  - -> 무한 루프로 count 계속 증가함
- Compositon에서는 State를 쓰지 않음으로서 역방향 쓰기 방지