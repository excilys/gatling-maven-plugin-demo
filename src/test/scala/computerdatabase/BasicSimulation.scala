package computerdatabase

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.util.Random

import scala.concurrent.duration._
import io.gatling.commons.validation._
import io.gatling.core.session.Expression

class BasicSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://my-ip-address.com")
    .contentTypeHeader("application/json")

  val headers_http_authentication = Map(
    "Content-Type" -> """application/x-www-form-urlencoded""")

  val headers_http_authenticated = Map(

    "Content-Type" -> """application/json""",
    "Authorization" -> """Bearer ${access_token}"""
  )


  val scn = scenario("Rest Load calls")

    .exec(
    http("POST OAuth Req")
      .post("http://token-provider.com/auth/realms/jhipster/protocol/openid-connect/token")
      .formParam("client_id", "app")
      .formParam("username","user")
      .formParam("password","password")
      .formParam("grant_type", "password")
      .headers(headers_http_authentication)
      .check(status.is(200))
      .check(jsonPath("$.access_token").exists.saveAs("access_token"))
  )
    .exec(http("Rest Request 1")
    .get("/api/account")
    .headers(headers_http_authenticated)
    .check(status.is(200))
  )

    .exec(http("Rest Request 2")
        .get("/api/profile")
        .headers(headers_http_authenticated)
  )

  setUp(
    scn.inject(
      atOnceUsers(1)
    )
      .protocols(httpProtocol)
  )

}
