package www.components


import com.raquo.laminar.api.L.*

case class Button(color: Button.Color) {
  private val colorClass = color match {
    case Button.Color.Red => "bg-red-500"
    case Button.Color.Green => "bg-green-500"
    case Button.Color.Blue => "bg-blue-500"
  }

  def apply() = {
    button(
      cls(colorClass),
      color.toString
    )
  }
}

object Button {

  import scala.quoted.*

  enum Color {
    case Red, Green, Blue
  }

  type ColorMod = Color.type => Color

  // Overloaded apply that accepts the function
  inline def apply(inline color: ColorMod): Button =
    ${ evalColorMod('color) }

  private def evalColorMod(f: Expr[ColorMod])(using Quotes): Expr[Button] = {
    import quotes.reflect.*
    f.asTerm match {
      case Lambda(List(ValDef(_, _, _)), Select(_, name)) =>
        val colorClassSymbol = TypeRepr.of[Color].typeSymbol
        val colorCompanionSymbol = colorClassSymbol.companionModule
        val colorRef = Ref(colorCompanionSymbol)
        val colorExpr = Select.unique(colorRef, name).asExprOf[Color]
        '{ new Button($colorExpr) }
      case Inlined(_, _, Block(List(DefDef(methodName, params, tpe, rhs)), _)) =>
        rhs match {
          case Some(Select(_, name)) =>
            val colorClassSymbol = TypeRepr.of[Color].typeSymbol
            val colorCompanionSymbol = colorClassSymbol.companionModule
            val colorRef = Ref(colorCompanionSymbol)
            val colorExpr = Select.unique(colorRef, name).asExprOf[Color]
            '{ new Button($colorExpr) }
          case _ =>
            report.errorAndAbort("Expected Select in DefDef body")
        }
      case _ =>
        report.errorAndAbort(s"Expected lambda with member access like _.Red, got: ${f.asTerm}")
    }
  }
}