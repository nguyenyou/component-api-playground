package www

import com.raquo.laminar.api.L.*
import scala.compiletime.summonInline
// Generic conversion: apply a function from a singleton type `C` to its singleton value.
// Works for enums, case classes with companions, plain `object`s, etc.
given modToValue[C <: Singleton, T](using v: ValueOf[C]): Conversion[C => T, T] with
  def apply(f: C => T): T = f(v.value)
  
case class Tag(color: Tag.TagMod) {
  val obj: Tag.Color = color
  
  def apply() = {
    println(obj)
    div("Tag")
  }
}


object Tag {
  enum Color {
    case Red, Green, Blue
  }
  type TagMod = Color.type => Color

}