# Documentation

## Architecture

The application is structured by following the Model-View-ViewModel (MVVM) architectural pattern.
The
[MainActivity](app/src/main/java/at/sfraungruber/assetbrowser/ui/MainActivity.kt) serves as the
application's entry point, responsible for setting up the navigation controller and defining
navigation paths with the initial View.

### MVVM Architecture Breakdown:

#### View

The view is implemented in
the [CoinsScreen](app/src/main/java/at/sfraungruber/assetbrowser/ui/coins/CoinsScreen.kt). It is using
Jetpack Compose for a modern and declarative UI. The UI is updated automatically on changes of
the exposed StateFlow in the ViewModel.

#### ViewModel

The ViewModel implementation is located in
the [CoinsViewModel](app/src/main/java/at/sfraungruber/assetbrowser/ui/coins/CoinsViewModel.kt). It is
responsible for fetching data from the data layer and preparing the data to show it on the UI. This
includes data aggregation, filtering and formatting. To reduce complexity in the ViewModel, the
UseCase pattern is used, to extract this to
the [GetCoinsUseCase](app/src/main/java/at/sfraungruber/assetbrowser/ui/coins/GetCoinsUseCase.kt).

#### Model

The model is responsible for business logic and retrieving data from the network. For networking the
Retrofit with the Kotlin Serialization library is used.

The model includes the following classes:

* [CryptoApi](app/src/main/java/at/sfraungruber/assetbrowser/data/remote/CryptoApi.kt)
* [CurrencyApi](app/src/main/java/at/sfraungruber/assetbrowser/data/remote/CurrencyApi.kt)
* [CoinRepository](app/src/main/java/at/sfraungruber/assetbrowser/data/repository/CoinRepository.kt)
* [CurrencyRepository](app/src/main/java/at/sfraungruber/assetbrowser/data/repository/CurrencyRepository.kt)
* [UserPreferences](app/src/main/java/at/sfraungruber/assetbrowser/data/UserPreferences.kt)

## Theme

Tool to generate the color, font and theme
files: [Material Theme Builder](https://material-foundation.github.io/material-theme-builder/)

## Screen Recording
A screen recording can be found in this [file](screen_recording.mp4).

## WebSocket implementation
A work-in-progress implementation with WebSockets for live updates can be found on the branch `feature/websocket`.


```
gradle validateDummyDebugScreenshotTest
```