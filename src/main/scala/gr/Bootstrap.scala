package gr

object Bootstrap extends AnyRef
  with UseFileWriteInStaticInit
  with UseTypesafeConfig
  with UseSprayJson
  with UseAkkaHttp
  with UseLolHttp
  with UseApacheHttpCore
  with UseCompoundHttpServer
  with UseJedis {

  def main(args: Array[String]): Unit = {
    useTypesafeConfig
    useSprayJson
    useAkkaHttp
    useLolHttp
    useApacheHttpCore
    useJedis
    useCompoundHttpServer
  }

}
