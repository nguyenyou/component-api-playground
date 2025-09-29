package www

import org.scalajs.dom
import japgolly.scalajs.react.*
import japgolly.scalajs.react.vdom.html_<^.*

object Main {
  @main
  def run(): Unit = {
    val container = dom.document.getElementById("app")
    ReactDOMClient
      .createRoot(container)
      .render(ReusabilityDemo.ParentComponent())
  }
}
