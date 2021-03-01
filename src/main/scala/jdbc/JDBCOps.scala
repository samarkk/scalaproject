package jdbc

import slick.jdbc.MySQLProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.DurationInt
import scala.util.{Failure, Success}

object JDBCOps extends App {
  // mydb configuration in application.conf under reesources
  val db = Database.forConfig("mydb")

  // tables futpltbl and prvoltbl written to mysql from spark using spark jdbc connections
  // refer to file C:\Users\SAMAR\OneDrive\sparkprojects\misc_files_scripts\fodf_write_to_mysql_and_furhter.scala
  // file present in sqlstocks package in sparkproject

  val futRows: Future[Seq[Tables.FutpltblRow]] = db.run(Tables.Futpltbl.result)
  /*
  to see the sql statements slick will generate on any result add
  .statements.mkstring("")
   */
  println(Tables.Futpltbl.result.statements.mkString(""))

  futRows.onComplete {
    case Success(value) => value.take(10).foreach(println)
    case _ => println("some problem occurred. check")
  }

  def findFPLsForSymbol(symbol: String): Query[Tables.Futpltbl, Tables.FutpltblRow, Seq] = Tables.Futpltbl.filter(_.symbol === symbol)

  println("Printing out the results for a particular symbol")
  Await.result(db.run(findFPLsForSymbol("TCS").result), 2.seconds).foreach(println)

  //getFutureProfitLossesForRandomStock
  def getFPLsForRandomStock = Tables.Futpltbl.map(_.symbol).distinct

  // make a copy of a particular symbol and insert rows for it to the base table
  db.run(Tables.Futpltbl.filter(ftpl => ftpl.symbol === "TCS").result).onComplete {
    case Success(futpls) => futpls.foreach {
      x =>
        val xFD = x.copy(symbol = Option(x.symbol.get + "FD"))
        db.run(Tables.Futpltbl += xFD)
    }
    case _ => println("some problem. check")
  }

  // update a single field
  Await.result(db.run(
    Tables.Futpltbl.filter(ftpl => ftpl.symbol === "TCSFD" && ftpl.timestamp === "25-JAN-2018").map(_.symbol).update(Option("TCSFDU"))
  ), 2.seconds)

  // update multiple fields
  Await.result(db.run(
    Tables.Futpltbl.filter(ftpl => ftpl.symbol === "TCSFD").map(x => (x.symbol, x.timestamp)).update((Option("TCSFDUM"), Option("01-JAN-2018")))
  ), 2.seconds)

  // delete after filtering to selection
  Await.result(db.run(
    Tables.Futpltbl.filter(ftpl => ftpl.symbol.like("TCSFD%")).delete),
    2.seconds)

  // load the second table to check the joins
  val pvRows: Future[Seq[Tables.PrvoltblRow]] = db.run(Tables.Prvoltbl.result)
  pvRows.onComplete {
    case Success(rows) => rows.take(10).foreach(println)
    case _ => println("Oops. Some problem happened check")
  }

  // monadic joins using flatMap and for comprehensions

  // frow - futpl table row, pvrow - prive volume table row, jquery - joined query
  val jquery = Tables.Futpltbl.flatMap {
    frow =>
      Tables.Prvoltbl.filter {
        pvrow => pvrow.symbol === frow.symbol
      }.map { fpvrow => (frow.symbol, frow.tsp, frow.finStatus, fpvrow.delper) }
  }

  // jrows - joined rows
  db.run(jquery.result).onComplete {
    case Success(jrows) => jrows.take(10).foreach(println)
    case Failure(exception) => println(exception.getMessage)
      println(exception.getStackTrace.mkString("Array(", ", ", ")"))
    case _ => println("oops problem")
  }

  // fjquery - join query using for
  val fjquery = for {
    frow <- Tables.Futpltbl
    pvrow <- Tables.Prvoltbl if frow.symbol === pvrow.symbol
  } yield (frow.symbol, frow.finStatus, pvrow.delper)

  db.run(fjquery.result).onComplete {
    case Success(jrows) => jrows.take(10).foreach(println)
    case Failure(exception) => println(exception.getMessage)
      println(exception.getStackTrace.mkString("Array(", ", ", ")"))
    case _ => println("oops problem")
  }

  // Applicative Queries
  // ajquery - applicativeJoinQuery, ajMappedQuery - fields restricted
  val ajquery = Tables.Futpltbl join Tables.Prvoltbl on ((f: Tables.Futpltbl, pv: Tables.Prvoltbl) => f.symbol === pv.symbol)
  Await.result(db.run(ajquery.result), 2.seconds)

  val ajMappedQuery = {
    Tables.Futpltbl.map(frow => (frow.symbol, frow.tsp, frow.finStatus)) join
      Tables.Prvoltbl on (_._1 === _.symbol)
  }

  Await.result(db.run(ajMappedQuery.result), 2.seconds)

  val ajMappedQueryTuplesUnpacked = {
    Tables.Futpltbl.map(frow => (frow.symbol, frow.tsp, frow.finStatus)) join
      Tables.Prvoltbl on (_._1 === _.symbol)
  }.map {
    case ((sym, tsp, fstat), prvt) => (sym.get, tsp.get, fstat.get, prvt.delper.get)
  }

  Await.result(db.run(ajMappedQueryTuplesUnpacked.result), 2.seconds)

  // applicative left join query
  val aljQuery = Tables.Prvoltbl.joinLeft(Tables.Futpltbl).on(_.symbol === _.symbol)
  Await.result(db.run(aljQuery.result), 2.seconds)

  // applicative left join query filtered to verify for a symbol not in the f and o pl table
  val aljQueryFiltered = Tables.Prvoltbl.joinLeft(Tables.Futpltbl).on(_.symbol === _.symbol).
    filter(jrow => jrow._1.symbol === "HFCL")
  Await.result(db.run(aljQueryFiltered.result), 2.seconds)

  val groupingAggQuery = Tables.Futpltbl.groupBy(_.symbol).map(x =>
    (x._1, x._2.length, x._2.map(t => t.bSPlStatus).sum))

  Await.result(db.run(groupingAggQuery.result), 2.seconds)

  Tables.Futpltbl.result.map(frowTableElementType =>
    frowTableElementType.map(tableRow => (tableRow.symbol, tableRow.finStatus match {
      case Some(x) => if (x < 0) "sellers" else "buyers"
      case _ => "unknown"
    })))
  val caseWhenQuery = Tables.Futpltbl.map(x => (x.symbol, Case
    If (x.finStatus < 0.0) Then "Sellers"
    Else "Buyers"))

  Await.result(db.run(caseWhenQuery.result), 2.seconds)

  // group by symbol and seller/buyer - who won
  // that should give us grousp like (ACC,Buyer) -> (ACC, Buyer), (ACC, Buyer), and so on
  // map it to ACC, Buyer and the length of the group
  Await.result(
    db.run(
      caseWhenQuery.groupBy(x => (x._1, x._2)).map(x => (x._1._1, x._1._2, x._2.length))
        .result), 2.seconds
  )
  // numberOfWins by Seller or Buyer
  val noWinsQuery = caseWhenQuery.groupBy {
    case (sym, who) => (sym, who)
  } map {
    case ((symbol, status), group) => (symbol, status, group.length)
  }

  Await.result(db.run(noWinsQuery.result), 2.seconds)

  def exec[T](action: DBIO[T]): T =
    Await.result(db.run(action), 2.seconds)

  val firstSqlQuery = sql"""select * from futpltbl""".as[Tables.FutpltblRow]
  exec(firstSqlQuery)

  val sqlQueryCaseWhen =
    sql"""select symbol, case when fin_status < 0 then 'sellers'
      else 'buyers' end as whowon from futpltbl""".as[(String, String)]
  exec(sqlQueryCaseWhen)

  val sqlNoWinsQuery = sql"""select f.symbol, f.whowon, count(*) as nowins from
                           ( select symbol, case when fin_status < 0 then 'sellers'
      else 'buyers' end as whowon from futpltbl) f
      group by f.symbol, f.whowon""".as[(String, String, Int)]
  exec(sqlNoWinsQuery)

  val sqlGroupingAndAggregateQuery =
    sql"""
      select expiry_dt, sum(fin_status) as totpl_status
      from futpltbl
      group by expiry_dt
      order by totpl_status desc
      """.as[(String, Float)]

  exec(sqlGroupingAndAggregateQuery)

  val sqlJoinQuery =
    sql"""
          select f.symbol, f.timestamp, p.delper, f.fin_status from futpltbl f
          inner join prvoltbl p
          on f.symbol = p.symbol
          limit 20
    """.as[(String, String, Double, Double)]
  exec(sqlJoinQuery)

  val longJoinSqlQuery = sql"""
          select f.*, p.delper from futpltbl f
          inner join prvoltbl p
          on f.symbol = p.symbol
          limit 20
    """.as[(java.sql.Timestamp, String, String, String, Double, Double, Double, Double, Double, Double, Double)]
  exec(longJoinSqlQuery)

  Thread.sleep(5000)
}
