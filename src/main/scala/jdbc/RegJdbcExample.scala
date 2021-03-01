package jdbc

import java.sql.{Connection, DriverManager}

object RegJdbcExample extends App {

  // connect to the database named "mysql" on port 8889 of localhost
  val url = "jdbc:mysql://asuspc.localdomain:3306/testdb"
  val driver = "com.mysql.cj.jdbc.Driver"
  val username = "root"
  val password = "abcd"
  var connection: Connection = _
  try {
    Class.forName(driver)
    connection = DriverManager.getConnection(url, username, password)
    val statement = connection.createStatement
    val rs = statement.executeQuery("SELECT symbol, fin_status from futpltbl")
    while (rs.next) {
      val symbol = rs.getString("symbol")
      val finstatus = rs.getDouble("fin_status")
      println("symbol = %s, finstatus = %4.2f".format(symbol, finstatus))
    }
  } catch {
    case e: Exception => e.printStackTrace()
  } finally {
    connection.close()
  }

}
