package gr

object Bootstrap extends AnyRef //  with SprayJsonSupport with DefaultJsonProtocol
  with UseTypesafeConfig with UseSprayJson with UseAkkaHttp with UseLolHttp {
  // val f = new java.io.File("/tmp/sample.txt")
  // val pw = new java.io.PrintWriter(new java.io.BufferedWriter(new java.io.FileWriter(f)))
  // pw.append("write:" + System.currentTimeMillis()) // ここはnative-image時にのみ評価される。
  // pw.close()
  println("print file.") // ここもnative-image時にのみ評価される。実行時には評価されない。

  def main(args: Array[String]): Unit = {
    useTypesafeConfig
    useSprayJson
    useAkkaHttp
    useLolHttp
  }

}

case class Entity(payload: String)