# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Napoleon's Schedule is a Kotlin Multiplatform (KMP) educational scheduling app (Android + iOS), replacing the eRSHU system with a Compose Multiplatform UI. Repository-based data layer, Decompose for navigation, Koin for DI.

## Modules

- **composeApp** — UI, navigation, data layer, DI. Android (minSdk 24, target/compileSdk 36) + iOS. Kotlin 2.2.0 with `-Xcontext-parameters`. Firebase analytics/crashlytics (Android only).
- **leonui** — shared Compose component library (buttons, cards, dialogs, inputs, date pickers). Highest fan-in module in the codebase (`LeonText`, `LeonButton`, `LeonIconButton`, `LeonComponentPreview` are used everywhere) — check usages before changing public signatures. Depends on `datetime`.
- **datetime** — shared date/time utilities.
- **leonres** — multiplatform string resources via **Compose Resources** (not libres — that's an old convention, migrated off it). Strings in `leonres/src/commonMain/composeResources/values/strings.xml` (+ localized variants), generated as `Res.string.*`; use `getSyncString` (`com.therxmv.leonres.ResourcesExtensions`) for non-composable contexts.

A `base/` dir exists at repo root but is empty and not in `settings.gradle.kts` — ignore it.

## Key Tech

- Compose Multiplatform + Material3
- **Navigation: Decompose** (component-based) — Voyager is still a dependency but unused, don't use it for new navigation
- **DI: Koin**, modules in `di/`: `appModule`, `navigationModule`, `dataModule`, `coroutinesModule` (+ platform `analyticsModule`, `dataStoreModule`)
- Ktor for networking, DataStore for local persistence

## Navigation

Decompose: `RootComponent`/`NavigationRootComponent` manage a `ChildStack` (screens) and a `ChildSlot` (overlays).
- `ChildDestination.BottomNav`: Dashboard, Schedule, Profile (tab bar)
- `ChildDestination.FullScreen`: EditProfile/CreateProfile, Exams, Rating (modal)
- `SlotDestination`: Timetable — rendered as a dialog overlay, **not** a Child screen

New destinations get wired into `Child.Factory` / `Slot.Factory` in `NavigationModule.kt`.

## UI Pattern

Each feature under `composeApp/.../ui/<feature>/` follows: `Screen.kt` (entry), `component/Component.kt` (logic, holds `ComponentContext`), `UiData.kt`, `UiEvent.kt`, optional `UiEffect.kt`, `content/Content.kt`.

Features: Dashboard, Schedule, Profile, EditProfile, Rating, Exams, Timetable.
Shared non-feature UI lives in `base/ui/`.

## Data Layer

- **Result handling**: `Result<T>` (`Success`/`Failure`) + `Reason` (`Error`: Offline/CantFindData/SlowInternet/Generic, or `Fallback`: cached data) in `data/source/remote/result/`. Use `Result.of()` for new network calls — it's the established convention, not ad hoc try/catch.
- **Repositories**: interface/impl pairs registered in `DataModule` — `FacultyRepository`, `ProfileRepository`, `SpecialtyRepository`, `TimetableRepository`, `RatingRepository`, `InfoRepository`, plus `AnalyticsRepository` (platform-specific, iOS is a no-op).
- **Remote**: `NapoleonService`/`NapoleonApi` (real), `NapoleonMockService` (swappable mock, both implement `NapoleonApi`), DTOs in `data/source/remote/napoleon/dto/`.
- **Local**: `DataStoreSource` for cached/offline data (schedule, exams); `RemindersLocalSource`; `RemindersApi` is expect/actual (Android: `AlarmManager`, iOS: local notifications).

## Development Guidelines

1. New screen: follow the Screen/Component/UiData/UiEvent pattern above.
2. New navigation destination: add to `ChildDestination` or `SlotDestination`, wire into `NavigationModule.kt`.
3. New data: repository interface + impl, register in `DataModule`.
4. Platform-specific code: `androidMain`/`iosMain` with `expect`/`actual`.
5. Network calls: wrap with `Result.of()`.

## Common Commands

```bash
./gradlew assembleDebug     # build debug APK
./gradlew installDebug      # build + install
./gradlew test              # unit tests
./gradlew lint               # lint
./gradlew embedAndSignAppleFrameworkForXcode  # build framework for Xcode
```
iOS app itself is built/run via Xcode (`iosApp/iosApp.xcodeproj`).

## Notes

- ProGuard is used on release builds (`proguard-rules.pro`) — test obfuscated builds before releasing.
- Firebase (analytics/crashlytics) is Android-only; iOS has no-op implementations.
