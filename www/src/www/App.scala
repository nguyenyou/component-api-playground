package www

import com.raquo.laminar.api.L.*
import scala.language.implicitConversions
import scala.compiletime.summonInline

case class App() {
  def apply(): HtmlElement = {
    div(
      cls("h-screen w-screen flex justify-center items-center"),
      "Hello, Worldddd!",
      Button(color = _.Red)(),
      Tag(color = _.Green)()
    )
  }
}