package www

import com.raquo.laminar.api.L.*
import scala.compiletime.summonInline
import scala.language.implicitConversions

transparent inline given modToValue[C <: Singleton, T]: Conversion[C => T, T] =
  (f: C => T) => f(summonInline[ValueOf[C]].value)

case class Button(color: Button.ColorMod) {
  val colorobj = color(Button.Color)
  val obj: Button.Color = color
  
  def apply() = {
    println(colorobj)
    println(obj)
    button("Button")
  }
}

object Button {
  enum Color {
    case Red, Green, Blue
  }
  type ColorMod = Color.type => Color
  
}