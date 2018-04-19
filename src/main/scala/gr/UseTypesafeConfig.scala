package gr

import com.typesafe.config.{ Config, ConfigFactory }

trait UseTypesafeConfig {
  protected val config: Config = ConfigFactory.load

  protected def useTypesafeConfig: Unit = {
    // val config = ConfigFactory.load // ここでロードは出来ない。下記のような実行時エラー。
    // Caused by: com.typesafe.config.ConfigException$BugOrBroken: Context class loader is not set for the current thread; if Thread.currentThread().getContextClassLoader() returns null, you must pass a ClassLoader explicitly to ConfigFactory.load
    println(s"Use Typesafe Config. ${config.getConfig("gr")}")
  }
}
