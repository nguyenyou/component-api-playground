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

  def highlightedSourceCode: VdomElement = {
    <.div(
      <.span(^.cls := "syntax-keyword", "package"), " www\n\n",
      <.span(^.cls := "syntax-keyword", "import"), " japgolly.scalajs.react._\n",
      <.span(^.cls := "syntax-keyword", "import"), " japgolly.scalajs.react.vdom.html_<^._\n",
      <.span(^.cls := "syntax-keyword", "import"), " org.scalajs.dom\n\n",

      <.span(^.cls := "syntax-keyword", "object"), " ", <.span(^.cls := "syntax-type", "ReusabilityDemo"), " {\n\n",
      "  ", <.span(^.cls := "syntax-comment", "// Case class WITHOUT Reusability"), "\n",
      "  ", <.span(^.cls := "syntax-keyword", "case class"), " ", <.span(^.cls := "syntax-type", "PropsWithoutReusability"),
      "(name: ", <.span(^.cls := "syntax-type", "String"), ", count: ", <.span(^.cls := "syntax-type", "Int"), ")\n\n",

      "  ", <.span(^.cls := "syntax-comment", "// Case class WITH Reusability"), "\n",
      "  ", <.span(^.cls := "syntax-keyword", "case class"), " ", <.span(^.cls := "syntax-type", "PropsWithReusability"),
      "(name: ", <.span(^.cls := "syntax-type", "String"), ", count: ", <.span(^.cls := "syntax-type", "Int"), ")\n\n",

      "  ", <.span(^.cls := "syntax-keyword", "object"), " ", <.span(^.cls := "syntax-type", "PropsWithReusability"), " {\n",
      "    ", <.span(^.cls := "syntax-keyword", "implicit val"), " reusability: ", <.span(^.cls := "syntax-type", "Reusability"),
      "[", <.span(^.cls := "syntax-type", "PropsWithReusability"), "] =\n",
      "      ", <.span(^.cls := "syntax-type", "Reusability"), ".", <.span(^.cls := "syntax-function", "derive"), "\n",
      "  }\n\n",

      "  ", <.span(^.cls := "syntax-comment", "// Component WITHOUT Reusability - will ALWAYS re-render"), "\n",
      "  ", <.span(^.cls := "syntax-keyword", "val"), " ", <.span(^.cls := "syntax-type", "ComponentWithoutReusability"), " = ",
      <.span(^.cls := "syntax-type", "ScalaComponent"), "\n",
      "    .", <.span(^.cls := "syntax-function", "builder"), "[", <.span(^.cls := "syntax-type", "PropsWithoutReusability"),
      "](", <.span(^.cls := "syntax-string", "\"WithoutReusability\""), ")\n",
      "    .", <.span(^.cls := "syntax-function", "render_P"), " { props =>\n",
      "      <.", <.span(^.cls := "syntax-function", "div"), "(", <.span(^.cls := "syntax-comment", "/* Renders with red border */"), ")\n",
      "    }\n",
      "    .", <.span(^.cls := "syntax-function", "build"), "\n\n",

      "  ", <.span(^.cls := "syntax-comment", "// Component WITH Reusability - will skip re-renders"), "\n",
      "  ", <.span(^.cls := "syntax-keyword", "val"), " ", <.span(^.cls := "syntax-type", "ComponentWithReusability"), " = ",
      <.span(^.cls := "syntax-type", "ScalaComponent"), "\n",
      "    .", <.span(^.cls := "syntax-function", "builder"), "[", <.span(^.cls := "syntax-type", "PropsWithReusability"),
      "](", <.span(^.cls := "syntax-string", "\"WithReusability\""), ")\n",
      "    .", <.span(^.cls := "syntax-function", "render_P"), " { props =>\n",
      "      <.", <.span(^.cls := "syntax-function", "div"), "(", <.span(^.cls := "syntax-comment", "/* Renders with green border */"), ")\n",
      "    }\n",
      "    .", <.span(^.cls := "syntax-function", "configure"), "(", <.span(^.cls := "syntax-type", "Reusability"),
      ".", <.span(^.cls := "syntax-function", "shouldComponentUpdate"), ")\n",
      "    .", <.span(^.cls := "syntax-function", "build"), "\n\n",

      "  ", <.span(^.cls := "syntax-comment", "// Parent component state"), "\n",
      "  ", <.span(^.cls := "syntax-keyword", "case class"), " ", <.span(^.cls := "syntax-type", "State"),
      "(parentRenderCount: ", <.span(^.cls := "syntax-type", "Int"), ")\n\n",

      "  ", <.span(^.cls := "syntax-comment", "// Parent component that re-renders but passes same props"), "\n",
      "  ", <.span(^.cls := "syntax-keyword", "val"), " ", <.span(^.cls := "syntax-type", "ParentComponent"), " = ",
      <.span(^.cls := "syntax-type", "ScalaComponent"), "\n",
      "    .", <.span(^.cls := "syntax-function", "builder"), "[", <.span(^.cls := "syntax-type", "Unit"),
      "](", <.span(^.cls := "syntax-string", "\"Parent\""), ")\n",
      "    .", <.span(^.cls := "syntax-function", "initialState"), "(", <.span(^.cls := "syntax-type", "State"),
      "(", <.span(^.cls := "syntax-number", "0"), "))\n",
      "    .", <.span(^.cls := "syntax-function", "render"), " { $ =>\n",
      "      <.", <.span(^.cls := "syntax-function", "div"), "(\n",
      "        <.", <.span(^.cls := "syntax-function", "button"), "(\n",
      "          ^.onClick --> $.modState(s =>\n",
      "            s.copy(parentRenderCount = s.parentRenderCount + ", <.span(^.cls := "syntax-number", "1"), ")\n",
      "          ),\n",
      "          ", <.span(^.cls := "syntax-string", "\"Force Parent Re-render\""), "\n",
      "        ),\n\n",
      "        ", <.span(^.cls := "syntax-comment", "// Both get the SAME prop values every time"), "\n",
      "        ", <.span(^.cls := "syntax-type", "ComponentWithoutReusability"), "(", <.span(^.cls := "syntax-type", "PropsWithoutReusability"),
      "(", <.span(^.cls := "syntax-string", "\"Alice\""), ", ", <.span(^.cls := "syntax-number", "42"), ")),\n",
      "        ", <.span(^.cls := "syntax-type", "ComponentWithReusability"), "(", <.span(^.cls := "syntax-type", "PropsWithReusability"),
      "(", <.span(^.cls := "syntax-string", "\"Bob\""), ", ", <.span(^.cls := "syntax-number", "42"), "))\n",
      "      )\n",
      "    }\n",
      "    .", <.span(^.cls := "syntax-function", "build"), "\n",
      "}"
    )
  }

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
                ^.cls := "text-sm leading-relaxed text-slate-100 p-6 overflow-auto font-mono",
                <.code(highlightedSourceCode)
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
