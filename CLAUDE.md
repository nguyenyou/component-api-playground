# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Scala.js project that uses Mill as the build tool and Vite for frontend development. It demonstrates building UI components with both Laminar and React (via scalajs-react), compiled to JavaScript using Scala.js, and bundled with Vite.

## Build System

**Mill (Scala build tool):**
- Build configuration: `build.mill`
- Scala version: 3.7.3
- Scala.js version: 1.20.1
- Module kind: ESModule
- Module split style: FewestModules

**Key dependencies:**
- Laminar 17.2.1 (reactive UI library)
- scalajs-dom 2.8.1 (DOM bindings)
- scalajs-react 3.0.0-beta12 (React bindings)
- utest 0.9.1 (testing)

## Common Commands

### Development Workflow
```sh
# Compile Scala.js to JavaScript (full optimization)
./mill www.fullLinkJS

# Install JavaScript dependencies
yarn install

# Start Vite dev server with hot reload
yarn dev
```

### Testing
```sh
# Run Scala tests with utest
./mill www.test
```

### Build for Production
```sh
# Full optimization build
./mill www.fullLinkJS

# Vite production build
yarn build

# Preview production build
yarn preview
```

### Other Useful Mill Commands
```sh
# Fast/unoptimized compilation (for faster iteration)
./mill www.fastLinkJS

# Open Scala REPL
./mill www.repl

# Show dependency tree
./mill www.showMvnDepsTree
```

## Architecture

### JavaScript/Scala.js Integration
- Entry point: `main.js` dynamically imports compiled Scala.js based on environment
- Development mode uses `fastLinkJS.dest/main.js` (unoptimized)
- Production mode uses `fullLinkJS.dest/main.js` (optimized)
- Vite bundles everything together

### Scala.js Application Structure
- Main entry: `www/src/www/Main.scala` - contains `@main def run()`
- Currently renders `MainReactApp()()` into `#app` element
- Alternative Laminar app available in `App.scala` (commented out)

### UI Framework Usage
The codebase supports **dual UI frameworks**:

1. **Laminar** (currently commented out):
   - Reactive UI library for Scala.js
   - Component example: `www/src/www/App.scala`
   - Uses case class pattern with `apply()` returning `HtmlElement`

2. **React** (currently active):
   - Entry point: `www/src/www/ReactMain.scala` - `MainReactApp()` function
   - Component example: `www/src/www/ReactApp.scala`
   - Uses ScalaComponent builder pattern from scalajs-react
   - Components follow Props/Backend/render pattern

### Component Pattern (Laminar)
```scala
case class Component(param: Type) {
  def apply(): HtmlElement = {
    div(/* markup */)
  }
}
```

### Component Pattern (React)
```scala
case class Component() {
  def apply(): VdomElement = Component.component(this)
}

object Component {
  type Props = Component
  case class Backend(scope: BackendScope[Props, Unit]) {
    def render(props: Props): VdomElement = <.div(/* markup */)
  }
  val component = ScalaComponent.builder[Props](name).renderBackend[Backend].build
}
```

### Source Organization
- `www/src/www/` - main application code
- `www/src/www/components/` - reusable components (Button example removed/WIP)
- `www/test/src/` - utest test suites
- Tests use JsDom environment (configured via `jsEnvConfig`)