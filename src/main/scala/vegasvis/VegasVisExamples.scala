package vegasvis
import vegas._
import vegas.data.External._

object VegasVisExamples extends App {
  Vegas("A simple bar chart with embedded data.", width = 600, height = 400).
    //    configScale(round = true, bandSize = 60, barSizeRange = List(20.0, 40.0, 60.0)).
    //    configAxis(axisWidth = 4.0).
    //configScale(true, 20, 40, null, List(1.0, 1.0)).
    withData(Seq(
      Map("a" -> "A", "b" -> 28), Map("a" -> "B", "b" -> 55), Map("a" -> "C", "b" -> 43),
      Map("a" -> "D", "b" -> 91), Map("a" -> "E", "b" -> 81), Map("a" -> "F", "b" -> 53),
      Map("a" -> "G", "b" -> 19), Map("a" -> "H", "b" -> 87), Map("a" -> "I", "b" -> 52))).
    encodeX("a", Ordinal).
    encodeY("b", Quantitative).
    mark(Bar).
    show

  Vegas("Sample Multi Series Line Chart", width = 600, height = 400)
    .withURL(Stocks, formatType = DataFormat.Csv)
    .mark(Line)
    .encodeX("date", Temp)
    .encodeY("price", Quant)
    .encodeColor(
      field = "symbol",
      dataType = Nominal,
      legend = Legend(orient = "left", title = "Stock Symbol"))
    .encodeDetailFields(Field(field = "symbol", dataType = Nominal))
    .show
}
