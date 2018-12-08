package computerdatabase

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class BasicSimulationSample2 extends Simulation {

  val httpProtocol = http
      .baseUrl("http://computer-database.gatling.io") // Here is the root for all relative URLs
      .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Here are the common headers
      .acceptEncodingHeader("gzip, deflate")
      .acceptLanguageHeader("en-US,en;q=0.5")
      .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")


  val usersDef = scala.util.Properties.envOrElse("numberOfUsers", "5")
  val durationDef = scala.util.Properties.envOrElse("durationInSeconds", "30")
  val users = System.getProperty("users", usersDef).toInt
  val duration = System.getProperty("durationInSecs", durationDef).toInt



   object LookupMacbookComputer {

    val lookupMacbookComputer = tryMax(2) { // let's try at max 2 times
      exec(
        http("Lookup Macbook Computer")
          .get("/computers?f=macbook")
        .check(status.is(200))) // we do a check on a condition that's been customized with a lambda. It will be evaluated every time a user executes the request
        .pause(2)
    }.exitHereIfFailed // if the chain didn't finally succeed, have the user exit the whole scenario
  }


    object LookupComputer {

    val lookupComputer = tryMax(2) { // let's try at max 2 times
      exec(
        http("Lookup Computer Page 6")
          .get("/computers/6")
        .check(status.is(200))) // we do a check on a condition that's been customized with a lambda. It will be evaluated every time a user executes the request
        .pause(3)
    }.exitHereIfFailed // if the chain didn't finally succeed, have the user exit the whole scenario
  }


object PostComputer {

    val postComputer = tryMax(2) { // let's try at max 2 times
      exec(
        http("Lookup Computer Page 1")
          .post("/computers")
          .formParam("""name""", """Beautiful Computer""") // Note the triple double quotes: used in Scala for protecting a whole chain of characters (no need for backslash)
          .formParam("""introduced""", """2012-05-30""")
          .formParam("""discontinued""", """""")
          .formParam("""company""", """37""")
        .check(status.is(200))) // we do a check on a condition that's been customized with a lambda. It will be evaluated every time a user executes the request
        .pause(3)
    }.exitHereIfFailed // if the chain didn't finally succeed, have the user exit the whole scenario
  }

   val lookupMacbookComputer = scenario("Lookup Macbook Computer").exec(LookupMacbookComputer.lookupMacbookComputer)
   val lookupComputer = scenario("Lookup Computer").exec(LookupComputer.lookupComputer)
   val postComputer = scenario("Pos Computer").exec(PostComputer.postComputer)

 setUp(
    lookupMacbookComputer.inject(rampUsers(users) during(duration)),
    lookupComputer.inject(rampUsers(users) during(duration)),
    postComputer.inject(rampUsers(users) during(duration)),
  ).protocols(httpProtocol)



}
