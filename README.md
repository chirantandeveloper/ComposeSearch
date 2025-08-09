# ComposeSearch

[![](https://jitpack.io/v/chirantandeveloper/ComposeSearch.svg)](https://jitpack.io/#chirantandeveloper/ComposeSearch)

A lightweight and customizable **Search Suggestion TextField** built using **Jetpack Compose**.  
It provides instant suggestions as you type, highlighting matching text and supporting a “No Result Found” state.

---
![image alt](https://github.com/chirantandeveloper/ComposeSearch/blob/6bf796d3ad869be7191212a2cdb426ce9643c2b0/IMG_20250806_003156.jpg)
## Features
- 🔍 **Instant Suggestions** – Filters items dynamically while typing.
- 🎨 **Customizable UI** – Control text color, highlight color, corner radius, and border colors.
- ✨ **Highlighted Matches** – Matching text is visually emphasized.
- 🖱 **Click to Select** – Selecting an item auto-fills the search field.
- 📝 **No Result Handling** – Displays a message when no matching item is found.
- 📦 **Easy Integration** – Single dependency via JitPack.

## Usage & Customization
```kotlin
@Composable
fun DemoScreen() {
    val items = listOf("Apple", "Banana", "Cherry", "Date", "Elderberry")
    
    SearchSuggestionTextField(
        items = items,
        placeholder = "Search fruits...",
        onItemSelected = { selected ->
            // Handle item selection
            Log.d("Search", "Selected item: $selected")
        }
    )
}
```

## Installation

Add the JitPack repository to your `settings.gradle.kts`:
```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```
## Then add the dependency to your `build.gradle.kts`:
```kotlin
dependencies {
    implementation("com.github.chirantandeveloper:ComposeSearch:1.0.0")
}
```

## Customization
- **cornerRadius** – Change field and popup shape.
- **focusedBorderColor** – Color of the border when focused.
- **unfocusedBorderColor** – Color of the border when not focused.
- **textColor** – Color of the text inside the field and popup items.
- **highlightColor** – Color used for highlighting matched text.

## Roadmap
- Support for debounce filtering
- Option for custom item layouts.
- Keyboard search action callback.

## License
MIT License

## Author
Chirantan Chaudhury
