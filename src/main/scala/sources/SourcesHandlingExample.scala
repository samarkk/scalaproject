package sources

import java.io.{File, PrintWriter}
import scala.io.Source

object SourcesHandlingExample extends App {
  def iterateSource(fileLoc: String, lineLength: Int): StringBuffer = {
    val fileSource = Source.fromFile(fileLoc)
    val stringBuffer = new StringBuffer()
    fileSource.getLines().foreach {
      line =>
        // carry out ops line by line
        if (line.length > lineLength) {
          stringBuffer.append(line)
          stringBuffer.append("\n")
        }
    }
    fileSource.close()
    stringBuffer
  }

  val shakLoc = "D:/ufdata/shakespeare.txt"
  (10 to 100 by 20).foreach {
    linelength =>
      println(s"lines longer than $linelength: ${
        iterateSource(shakLoc, linelength).length()
      }")
  }

  def withPrintWriter(file: File, op: PrintWriter => Unit) {
    val writer = new PrintWriter(file)
    try {
      op(writer)
    } finally {
      writer.close()
    }
  }

  withPrintWriter(new File("D:/tmp/writefileDemo.txt"),
    pw => {
      pw.println(iterateSource(shakLoc, 60))
    })

  def withPrintWriterCurried(file: File)(op: (PrintWriter) => Unit): Unit = {
    val writer = new PrintWriter(file)
    try {
      op(writer)
    } finally {
      writer.close()
    }
  }

  withPrintWriterCurried(new File("D:/tmp/writefileCurriedDemo.txt")) {
    writer => writer.println(iterateSource(shakLoc, 60))
  }
}
