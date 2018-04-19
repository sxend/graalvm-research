package gr

object Bootstrap extends AnyRef
  with UseFileWriteInStaticInit
  with UseTypesafeConfig
  with UseSprayJson
  with UseAkkaHttp
  with UseLolHttp
  with UseApacheHttpCore {

  def main(args: Array[String]): Unit = {
    useTypesafeConfig
    useSprayJson
    useAkkaHttp
    useLolHttp
    useApacheHttpCore
  }

}
