package www

import japgolly.scalajs.react.*
import japgolly.scalajs.react.vdom.html_<^.*

case class TodoList(
  todos: List[Todo],
  onToggle: Int => Callback,
  onDelete: Int => Callback
) {
  def apply(): VdomElement = {
    TodoList.component(this)
  }
}

object TodoList {
  type Props = TodoList

  case class Backend(scope: BackendScope[Props, Unit]) {
    def render(props: Props): VdomElement = {
      <.ul(
        ^.className := "w-full",
        if (props.todos.isEmpty) {
          <.li(
            ^.className := "text-center text-gray-500 p-4",
            "No todos yet. Add one above!"
          )
        } else {
          TagMod(
            props.todos.map { todo =>
              TodoItem.component.withKey(todo.id)(
                TodoItem(
                  id = todo.id,
                  text = todo.text,
                  completed = todo.completed,
                  onToggle = props.onToggle,
                  onDelete = props.onDelete
                )
              )
            }*
          )
        }
      )
    }
  }

  val component = ScalaComponent
    .builder[Props](getClass.getSimpleName)
    .renderBackend[Backend]
    .build
}