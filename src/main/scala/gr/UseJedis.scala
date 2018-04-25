package gr

import redis.clients.jedis.Jedis

trait UseJedis {
  def useJedis: Unit = {
    //    val jedis = new Jedis("localhost")
    //    jedis.set("foo", "bar")
    //    println(s"foo: ${jedis.get("foo")}")
  }
}
