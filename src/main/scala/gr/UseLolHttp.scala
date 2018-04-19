package gr

import lol.http._

trait UseLolHttp {
  protected def useLolHttp: Unit = {
    // Server.listen(config.getInt("gr.server.port")) { req =>
    //   Ok("Hello world!")
    // }
    // lolhttp内部でnettyを初期化する際、リフレクションを使うメソッドを使用している。そこで実行時エラー
  }
}
