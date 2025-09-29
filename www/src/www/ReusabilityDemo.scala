package www

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom

object ReusabilityDemo {

  // Case class WITHOUT Reusability
  case class PropsWithoutReusability(name: String, count: Int)

  // Case class WITH Reusability
  case class PropsWithReusability(name: String, count: Int)

  object PropsWithReusability {
    implicit val reusability: Reusability[PropsWithReusability] =
      Reusability.derive
  }

  // Component WITHOUT Reusability - will ALWAYS re-render
  val ComponentWithoutReusability = ScalaComponent
    .builder[PropsWithoutReusability]("WithoutReusability")
    .render_P { props =>
      <.div(
        ^.cls := "bg-white border-2 border-red-200 rounded-lg shadow-sm p-5 mb-4",
        <.div(
          ^.cls := "flex items-start gap-3 mb-3",
          <.span(^.cls := "text-2xl", "❌"),
          <.div(
            <.h3(^.cls := "text-lg font-semibold text-slate-800", "Without Reusability"),
            <.p(^.cls := "text-xs text-red-600 font-medium", "Always Re-renders")
          )
        ),
        <.div(^.cls := "h-px bg-slate-200 my-3"),
        <.p(^.cls := "text-sm text-slate-700 font-medium mb-2", s"Name: ${props.name}, Count: ${props.count}"),
        <.div(
          ^.cls := "inline-block bg-red-50 border border-red-200 text-red-700 text-xs px-3 py-1 rounded-full",
          s"Rendered at: ${new java.util.Date()}"
        )
      )
    }
    .build

  // Component WITH Reusability - will skip re-renders when props are equivalent
  val ComponentWithReusability = ScalaComponent
    .builder[PropsWithReusability]("WithReusability")
    .render_P { props =>
      <.div(
        ^.cls := "bg-white border-2 border-green-200 rounded-lg shadow-sm p-5 mb-4",
        <.div(
          ^.cls := "flex items-start gap-3 mb-3",
          <.span(^.cls := "text-2xl", "✅"),
          <.div(
            <.h3(^.cls := "text-lg font-semibold text-slate-800", "With Reusability"),
            <.p(^.cls := "text-xs text-green-600 font-medium", "Skips Re-renders")
          )
        ),
        <.div(^.cls := "h-px bg-slate-200 my-3"),
        <.p(^.cls := "text-sm text-slate-700 font-medium mb-2", s"Name: ${props.name}, Count: ${props.count}"),
        <.div(
          ^.cls := "inline-block bg-green-50 border border-green-200 text-green-700 text-xs px-3 py-1 rounded-full",
          s"Rendered at: ${new java.util.Date()}"
        )
      )
    }
    .configure(Reusability.shouldComponentUpdate)
    .build

  // Parent component state
  case class State(parentRenderCount: Int)

  val sourceCode = """package www

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom

object ReusabilityDemo {

  // Case class WITHOUT Reusability
  case class PropsWithoutReusability(name: String, count: Int)

  // Case class WITH Reusability
  case class PropsWithReusability(name: String, count: Int)

  object PropsWithReusability {
    implicit val reusability: Reusability[PropsWithReusability] =
      Reusability.derive
  }

  // Component WITHOUT Reusability - will ALWAYS re-render
  val ComponentWithoutReusability = ScalaComponent
    .builder[PropsWithoutReusability]("WithoutReusability")
    .render_P { props =>
      <.div(/* Component renders here */)
    }
    .build

  // Component WITH Reusability - will skip re-renders when props are equivalent
  val ComponentWithReusability = ScalaComponent
    .builder[PropsWithReusability]("WithReusability")
    .render_P { props =>
      <.div(/* Component renders here */)
    }
    .configure(Reusability.shouldComponentUpdate)
    .build

  // Parent component state
  case class State(parentRenderCount: Int)

  // Parent component that re-renders but passes the same props
  val ParentComponent = ScalaComponent
    .builder[Unit]("Parent")
    .initialState(State(0))
    .render { $ =>
      <.div(
        <.button(
          ^.onClick --> $.modState(s =>
            s.copy(parentRenderCount = s.parentRenderCount + 1)
          ),
          "Force Parent Re-render"
        ),

        // Both get the SAME prop values every time
        ComponentWithoutReusability(PropsWithoutReusability("Alice", 42)),
        ComponentWithReusability(PropsWithReusability("Bob", 42))
      )
    }
    .build
}"""

  // Parent component that re-renders but passes the same props
  val ParentComponent = ScalaComponent
    .builder[Unit]("Parent")
    .initialState(State(0))
    .render { $ =>
      <.div(
        ^.cls := "flex h-screen bg-gradient-to-br from-slate-50 to-blue-50",

        // Left: Source Code (50%)
        <.div(
          ^.cls := "w-1/2 border-r border-slate-200 flex flex-col",
          <.div(
            ^.cls := "bg-white/80 backdrop-blur-sm border-b border-slate-200 px-6 py-4",
            <.h2(
              ^.cls := "text-xl font-semibold text-slate-700 flex items-center gap-2",
              <.span("📄"),
              <.span("Source Code")
            )
          ),
          <.div(
            ^.cls := "flex-1 overflow-auto p-6",
            <.div(
              ^.cls := "bg-slate-900 rounded-lg shadow-lg overflow-hidden",
              <.pre(
                ^.cls := "text-xs leading-relaxed text-slate-100 p-6 overflow-auto font-mono",
                <.code(sourceCode)
              )
            )
          )
        ),

        // Right: Live Demo (50%)
        <.div(
          ^.cls := "w-1/2 flex flex-col",
          <.div(
            ^.cls := "bg-white/80 backdrop-blur-sm border-b border-slate-200 px-6 py-4",
            <.h2(
              ^.cls := "text-xl font-semibold text-slate-700 flex items-center gap-2",
              <.span("🎮"),
              <.span("Live Demo")
            )
          ),
          <.div(
            ^.cls := "flex-1 overflow-auto p-6",
            <.div(
              ^.cls := "max-w-2xl mx-auto",

              // Title
              <.h1(
                ^.cls := "text-3xl font-bold text-slate-800 mb-6 text-center",
                "Reusability Demo"
              ),

              // Info Alert
              <.div(
                ^.cls := "bg-blue-50 border border-blue-200 rounded-lg p-4 mb-6",
                <.div(
                  ^.cls := "flex gap-3",
                  <.div(^.cls := "text-2xl flex-shrink-0", "ℹ️"),
                  <.div(
                    ^.cls := "text-sm text-slate-700",
                    <.div(^.cls := "font-semibold mb-1", "How it works:"),
                    <.p(
                      "Click the button to force parent re-render with SAME prop values. ",
                      "Notice the timestamps: Without Reusability updates every time, ",
                      "With Reusability keeps the same timestamp!"
                    )
                  )
                )
              ),

              // Button
              <.button(
                ^.cls := "w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-4 px-6 rounded-lg shadow-md hover:shadow-lg transition-all duration-200 mb-6 flex items-center justify-center gap-3",
                ^.onClick --> $.modState(s =>
                  s.copy(parentRenderCount = s.parentRenderCount + 1)
                ),
                <.span(s"🔄 Force Parent Re-render"),
                <.span(
                  ^.cls := "bg-blue-500 px-3 py-1 rounded-full text-sm",
                  s"Count: ${$.state.parentRenderCount}"
                )
              ),

              // Components
              ComponentWithoutReusability(PropsWithoutReusability("Alice", 42)),
              ComponentWithReusability(PropsWithReusability("Bob", 42))
            )
          )
        )
      )
    }
    .build
}
