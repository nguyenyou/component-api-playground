package www

import com.raquo.laminar.api.L.*
import www.components.Button

case class App() {
  def apply(): HtmlElement = {
    div(
      cls("h-screen w-screen flex justify-center items-center"),
      Button(color = _.Red)(),
      Button(color = _.Green)(),
      Button(color = _.Blue)(),
    )
  }
}