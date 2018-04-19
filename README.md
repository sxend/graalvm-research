graalvm-research
================

### build and execute

```bash
$ sbt assembly && native-image -jar ./target/scala-2.12/main.jar && ./main
```