package www

import japgolly.scalajs.react.*
import japgolly.scalajs.react.vdom.html_<^.*

case class TodoItem(
  id: Int,
  text: String,
  completed: Boolean,
  onToggle: Int => Callback,
  onDelete: Int => Callback
) {
  def apply(): VdomElement = {
    TodoItem.component(this)
  }
}

object TodoItem {
  type Props = TodoItem

  case class Backend(scope: BackendScope[Props, Unit]) {
    def render(props: Props): VdomElement = {
      <.li(
        ^.className := "flex items-center gap-2 p-2 border-b",
        <.input(
          ^.`type` := "checkbox",
          ^.checked := props.completed,
          ^.onChange --> props.onToggle(props.id),
          ^.className := "w-4 h-4"
        ),
        <.span(
          ^.className := (if (props.completed) "line-through text-gray-500" else ""),
          props.text
        ),
        <.button(
          ^.onClick --> props.onDelete(props.id),
          ^.className := "ml-auto px-2 py-1 bg-red-500 text-white rounded hover:bg-red-600",
          "Delete"
        )
      )
    }
  }

  val component = ScalaComponent
    .builder[Props](getClass.getSimpleName)
    .renderBackend[Backend]
    .build
}