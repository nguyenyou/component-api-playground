package www

import com.raquo.laminar.api.L.*
import scala.language.implicitConversions
import scala.compiletime.summonInline

case class App() {
  enum Color {
    case Red, Green, Blue
  }
  type ColorMod = Color.type => Color
  val mycolor: ColorMod = _.Red
  val colorobj = mycolor(Color)

  def apply(): HtmlElement = {
    println(colorobj)
    div(
      cls("h-screen w-screen flex justify-center items-center"),
      "Hello, Worldddd!"
    )
  }
}
