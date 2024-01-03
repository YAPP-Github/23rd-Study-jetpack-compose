# CompositionLocal

- 암시적으로 컴포지션을 통해 데이터를 전달하는 도구

```kotlin
@Composable
fun MyApp() {
    // Theme information tends to be defined near the root of the application
    val colors = …
}

// Some composable deep in the hierarchy
@Composable
fun SomeTextLabel(labelText: String) {
    Text(
        text = labelText,
        color = // ← need to access colors here
    )
}
```
- color를 파라미터를 통해 전달하지 않고 하위 요소에 전달
- UI 트리를 통해 데이터 흐름이 발생하는 암시적 방법으로 사용할 수 있는 트리 범위의 명명된 객체 생성
  - 먼소리임?

## MaterialTheme
- CompositionLocal 객체를 제공 
  - MaterialTheme.colors = LocalColors
  - MaterialTheme.typography = LocalTypography
  - MaterialTheme.shapes = LocalShapes
- CompositionLocal 인스턴스의 scope는 컴포지션의 일부분에 지정됨
  - 트리 수준에 따라 다른 값을 제공할 수 있음
  - CompositionLocal의 current 값
    - 같은 컴포지션에서 지정된 부분의 상위 요소가 제공한 가장 가까운 값

### 새 값을 CompositionLocal에 제공하기
```kotlin
@Composable
fun CompositionLocalExample() {
    MaterialTheme { // MaterialTheme sets ContentAlpha.high as default
        Column {
            Text("Uses MaterialTheme's provided alpha")
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text("Medium value provided for LocalContentAlpha")
                Text("This Text also uses the medium value")
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {
                    DescendantExample()
                }
            }
        }
    }
}

@Composable
fun DescendantExample() {
    // CompositionLocalProviders also work across composable functions
    Text("This Text uses the disabled alpha now")
}
```
- CompositionLocalProvider에 Infix 함수 provides를 이용해 값을 연결
  - 새 값이 제공되면 CompositionLocal을 읽는 컴포지션을 Recomposition
- CompositionLocalProvider의 content 람다에서 CompositionLocal.current에 액세스하면
  - provides로 제공된 값을 가져옴

## Custom CompositionLocal
- 암시적으로 컴포지션을 통해 데이터를 전달하는 도구
- 과도하게 사용하지 않는 것이 좋음
  - 컴포저블의 동작 추론이 어려워짐
    - 암시적 의존성을 만들기 때문
    - 컴포저블 호출자가 CompositionLocal 값이 충족되는지 확인해야함
  - 문제 발생 시 디버깅이 어려움
    - 컴포지션을 탐색하여 current 값이 제공된 위치 확인
    - IDE Find usage, Compose layout inspector로 확인

### 언제 사용해야할까
- 기본값이 있어야 함
  - CompositionLocal 값이 제공되지 않으면? 곤란.
- 모든 하위 요소에서 사용하는 경우
  - 일부 하위 요소만 사용하는 경우에는 적합하지 않음
  - = 트리 범위, 하위 계층 구조 범위

### CompositionLocal 만들기
- `compositionLocalOf`
  - Recomposition 중 provide한 값이 변경되면 current 값을 읽는 콘텐츠만 무효화
- `staticCompositionLocalOf`
  - Recomposition 중 provide한 값이 변경되면 CompositionLocal이 제공된 람다 전체가 Recomposition
  - CompositionLocal로 제공된 값이 변경될 가능성이 없다면 성능 이점

### CompositionLocal에 값 제공
```kotlin
val elevations = Elevations(card = 1.dp, default = 1.dp)

CompositionLocalProvider(LocalElevations provides elevations) {
    Card(elevation = LocalElevations.current.card)
}
```
- CompositionLocalProvider로 LocalElevations에 값을 provide
- 하위 컴포넌트인 Card에서 LocalElevation.current에 접근하면
  - Elevations(card = 1.dp, default = 1.dp) 값 제공

### 뇌절하지 않고 고려할 수 있는 대안

1. 명시적 매개변수 전달
```kotlin
// Don't pass the whole object! Just what the descendant needs.
// Also, don't  pass the ViewModel as an implicit dependency using
// a CompositionLocal.
@Composable
fun MyDescendant(myViewModel: MyViewModel) { ... }

// Pass only what the descendant needs
@Composable
fun MyDescendant(data: DataToDisplay) {
    // Display data
}
```
- Composable에는 필요한 것만 전달하는게 좋음
- 정보를 최소한으로 보유하면 컴포저블 분리, 재사용에 이점이 있음

2. 컨트롤 역전
```kotlin
@Composable
fun MyComposable(myViewModel: MyViewModel = viewModel()) {
    
    MyDescendant(myViewModel)
    ReusableChild { myViewModel.loadData() }
}

@Composable
fun Child(myViewModel: MyViewModel) {
    Button(onClick = { myViewModel.loadData() }) {
        Text("Load data")
    }
}

@Composable
fun ReusableChild(onLoadClick: () -> Unit) {
  Button(onClick = onLoadClick) {
    Text("Load data")
  }
}
```
- dependency를 하위로 전달하지 않고 상위 요소가 로직을 실행하도록 담당
- 두 컴포저블의 결합도 ⬇️ 재사용성 🆙 
- `@Composable` content() 람다를 사용하는 것도 같은 방식

# Stability
- Recomposition을 스킵할 수 있는지 확인하기 위해 파라미터에 대한 안정성을 결정
  - Compose가 파라미터의 업데이트가 없음을 확신하면 skip
  - 확신을 못하면 부모가 recompose될 때 같이 recompose 됨
- 컬렉션 클래스는 항상 unstable 
  - List, Set, Map
  - immutable 하다고 보장할 수 없음
- `@Immutable`, `@Stable` 어노테이션
  - Compose 컴파일러가 추론한 것을 overriding 하는 것이기 때문에 조심해야함
- kotlinx immutable collections
- Compose 컴파일러가 실행하지 않는 외부 모듈의 클래스는 항상 불안정
  - compose runtime 종속성을 추가
  - 모듈에서 stable하다고 표시
  - UI 모델 클래스에서 클래스를 래핑

---

```kotlin
fun ContactRow(contact: Contact, modifier: Modifier = Modifier) {
    var selected by remember { mutableStateOf(false) }
    Row(modifier) {
        ContactDetails(contact)
        ToggleButton(selected, onToggled = { selected = !selected }
    }
}
```

### Immutable 
```kotlin
data class Contact(val name: String, val number: String)
```
- 새로운 객체를 생성하지 않고서는 변경되지 않음
- `selected` 상태가 변경되면 `ContactRow` 안의 코드가 재구성되어야 하는지 평가
  - `ContactDetails`의 파라미터가 변하지 않았음 ➡️skip

### Mutable
```kotlin
data class Contact(var name: String, var number: String)
```
- Compose가 알지 못하는 사이에 property가 변경될 수 있음
  - `ContactDetails`를 skip 하지 않음
  - `selected` 상태가 변경되면 `ContactDetails`도 재구성

### Compose가 안정성에 대한 결정을 내리는 법

- function
  - Skippable: Recomposition 중에 호출될 때, 파라미터가 변하지 않았다면 skip할 수 있음
  - Restartable: Recomposition이 시작될 수 있는 scope(entry point)
- type
  - Immutable: 객체가 생성된 후 어떤 프로퍼티도 변경될 수 없음
  - Stable: mutable으로 여겨지지만, public property혹은 method 동작이 이전 실행과 다른 결과를 생성하는 경우 Compose runtime에 알림이 전송됨

- Compose compiler
  - 코드를 실행할 때 모든 함수와 타입을 살펴보고, 위 정의에 맞는 tagging 수행
  - Composable의 skippability를 결정하기 위해 파라미터의 타입을 살펴 봄
  - 파라미터가 반드시 immutable일 필요 X
  - state가 변경되면 그 state를 읽는 tree 상에서 가장 가까운 Restartable func을 찾음
    - 여기서부터 Recomposition 다시 시작

```kotlin
data class Contact(val name: String, val number: String)
fun ContactRow(contact: Contact, modifier: Modifier = Modifier) {
    var selected by remember { mutableStateOf(false) }
    Row(modifier) {
        ContactDetails(contact)
        ToggleButton(selected, onToggled = { selected = !selected }
    }
}
```
- `selected`가 변경되면?
  - 가장 가까운 Restartable function: `ContactRow`
  - `Row`는 inline function = 컴파일 되면 바이트 코드 = 함수가 아님, skip도 불가 (코드니까)
  - `ContactDetails`: `contact`가 immutable이어서 skippable로 tag 됨
  - 'ToggleButton': `MutableState`를 파라미터로 받음 -> skippable로 tag되지만, 변했으니까 재구성
  - restartable function이 종료되면 recomposition 종료