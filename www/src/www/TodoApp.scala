package www

import japgolly.scalajs.react.*
import japgolly.scalajs.react.vdom.html_<^.*
import org.scalajs.dom

case class TodoApp() {
  def apply(): VdomElement = {
    TodoApp.component(this)
  }
}

object TodoApp {
  type Props = TodoApp

  case class State(
    todos: List[Todo],
    inputText: String,
    nextId: Int
  )

  case class Backend(scope: BackendScope[Props, State]) {

    def handleInputChange(e: ReactEventFromInput): Callback = {
      val value = e.target.value
      scope.modState(_.copy(inputText = value))
    }

    def handleAddTodo: Callback = {
      scope.state.flatMap { s =>
        if (s.inputText.trim.nonEmpty) {
          val newTodo = Todo(s.nextId, s.inputText.trim, completed = false)
          scope.modState(state =>
            state.copy(
              todos = state.todos :+ newTodo,
              inputText = "",
              nextId = state.nextId + 1
            )
          )
        } else {
          Callback.empty
        }
      }
    }

    def handleKeyPress(e: ReactKeyboardEvent): Callback = {
      if (e.key == "Enter") handleAddTodo
      else Callback.empty
    }

    def handleToggle(id: Int): Callback = {
      scope.modState { s =>
        s.copy(todos = s.todos.map { todo =>
          if (todo.id == id) todo.copy(completed = !todo.completed)
          else todo
        })
      }
    }

    def handleDelete(id: Int): Callback = {
      scope.modState { s =>
        s.copy(todos = s.todos.filter(_.id != id))
      }
    }

    def render(props: Props, state: State): VdomElement = {
      <.div(
        ^.className := "min-h-screen flex items-center justify-center bg-gray-100",
        <.div(
          ^.className := "bg-white rounded-lg shadow-lg p-6 w-full max-w-md",
          <.h1(
            ^.className := "text-2xl font-bold mb-4 text-center",
            "Todo App"
          ),
          <.div(
            ^.className := "flex gap-2 mb-4",
            <.input(
              ^.`type` := "text",
              ^.value := state.inputText,
              ^.onChange ==> handleInputChange,
              ^.onKeyPress ==> handleKeyPress,
              ^.placeholder := "Add a new todo...",
              ^.className := "flex-1 px-3 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
            ),
            <.button(
              ^.onClick --> handleAddTodo,
              ^.className := "px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600",
              "Add"
            )
          ),
          TodoList(
            todos = state.todos,
            onToggle = handleToggle,
            onDelete = handleDelete
          )()
        )
      )
    }
  }

  val component = ScalaComponent
    .builder[Props](getClass.getSimpleName)
    .initialState(State(List.empty, "", 1))
    .renderBackend[Backend]
    .build
}