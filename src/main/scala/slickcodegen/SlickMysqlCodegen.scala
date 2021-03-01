package slickcodegen


object SlickMysqlCodeGen extends App {
  val profile = "slick.jdbc.MySQLProfile"
  val jdbcDriver = "com.mysql.cj.jdbc.Driver"
  val url = "jdbc:mysql://asuspc.localdomain:3306/testdb"
  slick.codegen.SourceCodeGenerator.main(
    Array(profile, jdbcDriver, url, "src/main/scala/jdbc", "", "root", "abcd")
  )
}
