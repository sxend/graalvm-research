package gr

case class Metadata(id: String, timestamp: Long)
case class Payload(message: String)
case class Entity(metadata: Metadata, payload: Payload)