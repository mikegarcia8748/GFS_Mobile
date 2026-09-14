# Rice Mill — Frontend

Compose Multiplatform client for the rice mill management system. One shared codebase targeting **web (Wasm)** for the manager and owner, and **Android** for the mill floor.

See [`../PRD.md`](../PRD.md) for scope and [`../backend/README.md`](../backend/README.md) for the API it talks to.

---

## Stack

| Concern | Choice |
|---|---|
| UI | Compose Multiplatform |
| Targets | `wasmJs` (browser), `android` |
| DI | Koin |
| Networking | Ktor Client + `kotlinx.serialization` |
| State | `StateFlow` collected as Compose state |
| Tests | Kotest + MockK (view models, mocking use cases) |

Both targets are built from `commonMain`. There is no per-platform UI fork; adaptation happens at runtime from the window size class.

---

## Quick start

```bash
# Web — dev server with hot reload
./gradlew :frontend:wasmJsBrowserDevelopmentRun

# Android — install to a connected device or emulator
./gradlew :frontend:installDebug
```

Point the client at a running backend:

```bash
# frontend/local.properties
API_BASE_URL=http://localhost:8080
```

Web dev server comes up on `http://localhost:8081`.

---

## Project structure

```
frontend/
├── commonMain/
│   ├── ui/
│   │   ├── theme/            AppTheme, ColorScheme, Typography, Dimens
│   │   ├── component/        shared composables, each with a preview
│   │   └── screen/           one package per screen, five files each
│   ├── viewmodel/            state holders calling use cases
│   ├── data/                 API client, DTOs, mappers
│   ├── message/              Messages — API error → Res.string
│   ├── di/                   Koin modules
│   └── composeResources/
│       └── values/strings.xml
├── wasmJsMain/               browser entry point only
└── androidMain/              Activity and manifest only
```

Anything in `wasmJsMain` or `androidMain` beyond an entry point is a smell. If a screen needs platform behaviour, express it through an `expect`/`actual` in a narrow interface rather than duplicating the composable.

---

## The screen pattern

Every screen is **five artifacts, always the same five**. This is not negotiable per screen — consistency here is what makes the codebase navigable.

| Artifact | Owns |
|---|---|
| `XScreen` | navigation, the view model, ephemeral UI state |
| `XContent` | layout — nothing else |
| `XUIState` | everything that describes what's on screen |
| `XCallback` | every action that leaves the content |
| `@Preview` | proof that content renders from literals alone |

`XContent` takes **exactly two parameters**, always in this order:

```kotlin
@Composable
fun MillingJobContent(
    callback: MillingJobCallback,
    uiState: MillingJobUIState,
)
```

Reaching for a third parameter means something is misplaced: a value the content displays belongs on `uiState`; an action it triggers belongs on `callback`. The urge to pass `navController` "just for this one button" is the signal that a lambda is missing.

**Navigation lives only in `XScreen`.** The view model emits `MillingJobUiEvent.JobCompleted`; the screen decides that means `navController.navigate(RouteSettlement)`. That keeps the view model free of platform types and unit-testable, and a route change touches one line in one file.

**State vs event.** Would this still be true if the screen were rebuilt from scratch? Yes → `XUIState`. No → `XUiEvent`, collected once in `LaunchedEffect`. A navigation command stored in state re-fires on every recomposition.

**Dialogs are nullable payloads, not booleans.** `var lotToPullOut by remember { mutableStateOf<DepositLot?>(null) }` makes a visible dialog with a stale lot unrepresentable. A boolean plus a separate `selectedLot` does not.

Preview `XContent`, never `XScreen` — and write a preview for each private component too, not just the whole screen. Component previews are where the time is saved: a populated state, an empty state and a long-name state side by side in seconds.

---

## Centralize every value

A literal in a composable is a value with no name and no home.

**`Dimens`** holds every size, spacing, radius, thickness, icon size and elevation. A composable never contains a bare `16.dp`. `MaterialTheme.shapes` is built *from* `Dimens` radii so the two cannot disagree.

**`Res.string`** holds every visible word, including content descriptions. Declared in `composeResources/values/strings.xml`, read with `stringResource(Res.string.key)`. This matters more than usual here: the interface mixes English UI chrome with domain terms the staff actually use — *palay*, *bigas*, *darak*, *ipa*, *utang*, *kaban*. Those words go in resources like any other string, so the vocabulary stays consistent and a Tagalog locale is a file rather than a refactor.

**`Messages`** maps backend error codes to friendly, localized text in one place. The API returns contracts for machines; the UI shows sentences for people. Raw API strings never reach a user.

---

## Adaptivity

One `AdaptiveScaffold` resolves the window size class once and picks the navigation shell — bottom bar on a phone, rail on medium, drawer on a wide browser. Screens render their content and never check a width.

The two contexts are genuinely different and the layout should respect that:

- **Mill floor (Android, staff)** — a shared device, used standing up, often with one hand while handling sacks. Large touch targets, big numeric entry, minimal typing. The intake and settlement screens are the whole job; everything else can be buried.
- **Home / office (web, manager and owner)** — reading and review. Tables, statements, reports. Dense is fine here; it is the wrong instinct on the floor.

Drive hover affordances from `collectIsHoveredAsState()` so they simply never activate on touch rather than needing a platform check. Guarantee 48dp minimum on anything interactive via `Dimens.touchTargetMin`.

---

## Motion

Prefer `spring()` over `tween()` for anything state-driven — a spring redirects smoothly when the target changes mid-flight, where a tween restarts or snaps. `DampingRatioLowBouncy` with `StiffnessMediumLow` reads as calm for most UI.

Keep it subtle: a press scales to 0.97, not 0.85. Ten micro-interactions on one screen should not compete. Nothing should block interaction, and perceived settling stays well under 300ms.

---

## Domain notes for the UI

A few places where the business rules shape the screens, and where a generic CRUD instinct will produce the wrong thing:

**Milling is two-stage.** Intake and output are separate encounters, often hours apart. The job list needs a visible "awaiting output" state — a job stranded between stages is a lot of palay nobody is billing for. Do not model this as a single form.

**Both sacks and kilos, always.** Every weight-bearing screen captures sack count *and* kilograms. Neither is derived. Do not add a helpful "kg per sack" autofill; the relationship is not fixed and a convenience default here becomes a billing error.

**Settlement is a mix, not a choice.** The settlement screen must let cash, chaff, rice and utang combine in one transaction, with a running total against the fee. A radio-button "payment method" is the wrong shape and will force staff to lie to the system.

**Prices shown must be the versioned ones.** When displaying a past job, show the rate it was billed at. Never recompute a historical figure from today's rate table.

**Balances are read-only in the UI.** A balance is derived from ledger entries. There is no edit affordance anywhere — corrections are a separate, Manager-gated action that creates a reversing entry.

---

## Roles

The UI hides what a role cannot do: Staff sees no rate editing, no void, no balance adjustment.

**This is convenience, not security.** Every one of those is enforced at the backend route guard. Never treat a hidden button as a control — see the backend README.

---

## Testing

View models are tested with use cases mocked, asserting state transitions. Test names state the rule, the scenario and the outcome:

```
settlement total - credits exceed the fee - exposes surplus rather than clamping to zero
```

Previews compile with the module, so a stale preview is a build error. That is the point — they are the cheapest test a screen has. Keep them `private`, wrap them in `AppTheme`, and collect them in a `// region Previews` block at the bottom of the file.

---

## Reviewing a screen

Check these in order. Each is a place the pattern usually breaks first.

1. Does `XContent` take anything besides `callback` and `uiState`?
2. Is there navigation, a use-case call, or a coroutine launch inside `XContent`?
3. Does `XUIState` carry anything one-shot — a navigate flag, a show-toast-once boolean?
4. Are dialogs driven by a boolean paired with a separate nullable payload?
5. Do private components receive the whole `uiState` or the whole callback instead of the handful of values they draw?
6. Does every component have a preview, and do the previews still compile?

---

## Contributing

- Run `./gradlew :frontend:test` and confirm previews compile before pushing.
- Commits are authored under your own git identity. No AI co-author trailers or generated-with footers.
