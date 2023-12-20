# Composable Lifecycle

초기 Composition: UI Composable 추적

➡️ 상태 변경 시: Recomposition 예약 ➡️ Composable 재 실행하여 수정

## Composable in Composition

- call site: 컴포저블이 호출되는 소스 코드 위치로 고유한 인스턴스 식별

```kotlin
@Composable
fun LoginScreen(showError: Boolean) {
    if (showError) {
        LoginError()
    }
    LoginInput() // This call site affects where LoginInput is placed in Composition
}

@Composable
fun LoginInput() { /* ... */ }
```
- `showError`가 false -> true로 변화한다면?

<img src="https://developer.android.com/static/images/jetpack/compose/lifecycle-showerror.png?hl=ko">

- `LoginInput`은 매개변수가 변하지 않았기 때문에 리컴포지션 건너뜀

```kotlin
@Composable
fun MoviesScreen(movies: List<Movie>) {
    Column {
        for (movie in movies) {
            // MovieOverview composables are placed in Composition given its
            // index position in the for loop
            MovieOverview(movie)
        }
    }
}
```
- 이 경우, `MovieOverview`를 여러번 호출하여 컴포저블을 컴포지션에 여러 번 추가
- 호출하는 코드 위치 즉,call site가 동일함 -> 고유하게 식별할 수 없음
  - 실행 순서로 인스턴스를 구분함

**새 movie가 추가된다면?**

<img src="https://developer.android.com/static/images/jetpack/compose/lifecycle-newelement-bottom.png?hl=ko">

- 기존에 있던 두개의 컴포저블은 리컴포지션을 건너뛴다
- 새로운 컴포저블은 새로운 순서이기 때문에 초기 컴포지션을 한다.

**중간이나 첫 아이템을 삭제하거나 재정렬한다면?**

<img src="https://developer.android.com/static/images/jetpack/compose/lifecycle-newelement-top-all-recompose.png?hl=ko">

- **매개변수가 변한** 컴포저블이 리컴포지션 됨. (매개변수의 위치가 뒤섞였으니까)

**key를 지정하여 고유하게 식별할 수 있는 정보를 알려주면?**
<img src="https://developer.android.com/static/images/jetpack/compose/lifecycle-newelement-top-keys.png?hl=ko">
- 매개변수가 변하지 않는 한 리컴포지션 되지 않는다.
- key설정은 여러 컴포저블을 동일한 call site에서 호출되고 SideEffect 또는 내부 State가 포함되어 있을 때 중요

### Recomposition을 건너뛰는 기준
➡️ 모든 input이 Stable하고 변하지 않으면

Stable?
- 두 인스턴스의 `equals` 결과가 동일
- public property가 변경되면 컴포지션에 알림이 감
- 모든 public property가 stable한 속성
    - `@Stable` 로 명시된 클래스
    - primitive 타입: Boolean, Int, Long, Float, Char...
    - String
    - Lamdas
    - (변경할 수 없는 속성들이 Stable하다)

### MutableState
- Stable 하지만 Mutable함
- State의 .value가 변경되면 Compose에 알림 전송
  - 지가 변할 때 알림을 전송하고 있기 때문에 Stable하다고 간주할 수 있음

### @Stable
- Compose가 Stable하다고 추론할 수 없을 경우 `@Stable`로 명시
```kotlin
// Marking the type as stable to favor skipping and smart recompositions.
@Stable
interface UiState<T : Result<T>> {
    val value: T?
    val exception: Throwable?

    val hasError: Boolean
        get() = exception != null
}
```
- interface: Stable하지 않다고 간주됨
  - @Stable 붙여서 안정적이라고 말해주기

# Side-Effect

**Composable 밖에서 발생하는 State 변경사항**

- 일반적으로 컴포저블에는 SideEffect가 없는게 좋음
  - 리컴포지션을 예측할 수 없음
  - 다른 순서로 리컴포지션 실행
  - 삭제할 수 있는 리컴포지션
- SideEffect가 필요할 때도 있음
  - **일회성 이벤트를 트리거할 때**
    - ex) 스낵바 표시, 화면 이동...
    - Composable의 Lifecycle를 인식하고 관리하는 환경에서 호출하자


## Effect Api
- UI를 emit하지 않고 컴포지션이 완료될 때 SideEffect를 실행하는 Composable 함수

### LaunchedEffect
- Composable 내에서 suspend fun 호출
- 컴포지션 시작 -> 코드 블록으로 코루틴 실행
- LaunchedEffect 컴포지션 종료 -> 코루틴 취소
- LaunchedEffect key 변경 -> 코루틴 취소 후 새 코루틴 실행

### rememberCoroutineScope
- LaunchedEffect는 Composable 함수
- Composable 외부에서 컴포지션 종료 후 취소되는 scope를 가진 coroutine
- 코루틴의 수명 주기를 수동으로 관리할 때 사용
  - ex) 사용자 이벤트 발생 시 애니메이션 취소
- 호출되는 컴포지션 지점에 binding된 Scope를 가짐

### rememberUpdatedState
- 값이 변경되는 경우 다시 시작하지 않는 Effect에서 값 참조

```kotlin
@Composable
fun LandingScreen(onTimeout: () -> Unit) {

    // This will always refer to the latest onTimeout function that
    // LandingScreen was recomposed with
    val currentOnTimeout by rememberUpdatedState(onTimeout)

    // Create an effect that matches the lifecycle of LandingScreen.
    // If LandingScreen recomposes, the delay shouldn't start again.
    LaunchedEffect(true) {
        delay(SplashWaitTimeMillis)
        currentOnTimeout()
    }

    /* Landing screen content */
}
```
- 매개변수가 변화 ➡️ 리컴포지션 발생 ➡️ LaunchedEffect 다시 시작
- 경우에 따라 값이 변경되어도 다시 시작하지 않도록 하고 싶을 수 있음
  - `rememberUpdatedState`를 사용하여 업데이트할 수 있는 값의 참조 생성
  - 비용이 많이 들거나 오래 지속되는 작업이 포함될 때 유용 
- 위의 경우, `LaundingScreen`이 Recomposition 되는 경우
  - LaunchedEffect를 취소하고 다시 시작하지 않음
  - 마치 suspend fun 같은 느낌인걸
- 매개변수로 `true`를 쓴 이유
  - call site의 lifecycle과 일치하는 Effect를 만들기 위해
  - 변하지 않는 상수 -> key가 변하지 않음 -> 취소 후 재실행 되지 않음
  - `Unit`을 써도 같은 효과 

### DisposableEffect
- 정리가 필요한 Effect
- key 변경, 컴포지션 종료 후 정리해야 하는 SideEffect 처리
- DisposableEffect key 변경 시
  - Composable이 현재 Effect를 삭제 후 다시 호출하여 설정

```kotlin
@Composable
fun HomeScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onStart: () -> Unit, // Send the 'started' analytics event
    onStop: () -> Unit // Send the 'stopped' analytics event
) {
    // Safely update the current lambdas when a new one is provided
    val currentOnStart by rememberUpdatedState(onStart)
    val currentOnStop by rememberUpdatedState(onStop)

    // If `lifecycleOwner` changes, dispose and reset the effect
    DisposableEffect(lifecycleOwner) {
        // Create an observer that triggers our remembered callbacks
        // for sending analytics events
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                currentOnStart()
            } else if (event == Lifecycle.Event.ON_STOP) {
                currentOnStop()
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    /* Home screen content */
}
```
- `lifecycleOwner`가 변경되면 Effect가 삭제되고 새 lifecycleOwner로 재시작
- `onDispose`절을 최종 문장으로 포함해야 함 

### Compose State를 비Compose 객체와 공유하려면?
- 리컴포지션 성공마다 호출되는 SideEffect에서 갱신
```kotlin
@Composable
fun rememberAnalytics(user: User): FirebaseAnalytics {
    val analytics: FirebaseAnalytics = remember {
        /* ... */
    }

    // On every successful composition, update FirebaseAnalytics with
    // the userType from the current User, ensuring that future analytics
    // events have this metadata attached
    SideEffect {
        analytics.setUserProperty("userType", user.userType)
    }
    return analytics
}
``` 
- user 정보가 갱신될 때마다 setUserProperty를 호출하여 데이터를 갱신하게 된다.

### produceState: 비Compose State -> Compose State
- Flow, LiveData, RxJava 구독 기반 상태를 컴포지션으로 변환하는 코루틴

```kotlin
data class DetailsUiState(
  val cityDetails: ExploreModel? = null,
  val isLoading: Boolean = false,
  val throwError: Boolean = false
)

val uiState by produceState(initialValue = DetailsUiState(isLoading = true)) {
  // In a coroutine, this can call suspend functions or move
  // the computation to different Dispatchers
  val cityDetailsResult = viewModel.cityDetails
  value = if (cityDetailsResult is Result.Success<ExploreModel>) {
    DetailsUiState(cityDetailsResult.data)
  } else {
    DetailsUiState(throwError = true)
  }
}
```
- `cityDetails`의 값에 따라 `uiState` 변경

### derivedStateOf: 하나 이상의 State -> 다른 State
- State에서 다른 State가 계산되거나 파생되는 경우에 사용

```kotlin
val todoTasks = remember { mutableStateListOf<String>() }

val highPriorityTasks by remember(highPriorityKeywords) {
  derivedStateOf { todoTasks.filter { it.containsWord(highPriorityKeywords) } }
}
```

만약 리스트의 첫 요소가 보이지 않게 되면 top 버튼을 띄우고 싶다면?

`val showButton = listState.firstVisibleItemIndex > 0`
- `firstVisibleItemIndex`은 자주 변경됨 ➡️ 자주 Recomposition
- 사실 첫 요소가 보이기만 하면 false, 다른 경우에는 true인데... 두번째 세번째 네번째 ... 가 첫 요소가 될때마다 Recompositon 되어버린다.

```kotlin
val showButton by remember {
    derivedStateOf {
        listState.firstVisibleItemIndex > 0
    }
}
```
- `derivedStateOf`를 이용하여 파상된 **상태**를 만든다
- showButton이 true ↔️ false 상태가 변경될 때만 Recomposition이 발생

### snapshotFlow: State -> Flow
- State를 Cold Flow로 변환
- State가 변경되면 새 값을 emit