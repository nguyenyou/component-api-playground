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
        ^.border := "2px solid red",
        ^.padding := "10px",
        ^.margin := "10px",
        <.h3("Without Reusability (Always Re-renders)"),
        <.p(s"Name: ${props.name}, Count: ${props.count}"),
        <.p(^.color := "red", s"Rendered at: ${new java.util.Date()}")
      )
    }
    .build

  // Component WITH Reusability - will skip re-renders when props are equivalent
  val ComponentWithReusability = ScalaComponent
    .builder[PropsWithReusability]("WithReusability")
    .render_P { props =>
      <.div(
        ^.border := "2px solid green",
        ^.padding := "10px",
        ^.margin := "10px",
        <.h3("With Reusability (Skips Re-renders)"),
        <.p(s"Name: ${props.name}, Count: ${props.count}"),
        <.p(^.color := "green", s"Rendered at: ${new java.util.Date()}")
      )
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
        <.h1("scalajs-react Case Class Props Demo"),
        <.p(
          "Click the button to force parent re-render with SAME prop values:"
        ),
        <.button(
          ^.cls := "btn btn-primary",
          ^.onClick --> $.modState(s =>
            s.copy(parentRenderCount = s.parentRenderCount + 1)
          ),
          s"Force Parent Re-render (count: ${$.state.parentRenderCount})"
        ),
        <.hr(),
        <.p(
          ^.fontWeight := "bold",
          "Notice: Without Reusability renders every time, With Reusability keeps the same timestamp!"
        ),

        // Both get the SAME prop values every time
        ComponentWithoutReusability(PropsWithoutReusability("Alice", 42)),
        ComponentWithReusability(PropsWithReusability("Bob", 42))
      )
    }
    .build
}
