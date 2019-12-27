 package computerdatabase

    import io.gatling.core.Predef._
    import io.gatling.http.Predef._
    import scala.util.Random

    import scala.concurrent.duration._
    import io.gatling.commons.validation._
    import io.gatling.core.session.Expression

    class PostRequest extends Simulation {

      val httpProtocol = http
        .baseUrl("http://mysite.com:8080")
        .contentTypeHeader("application/json")

      val headers_http_authentication = Map(
        "Content-Type" -> """application/x-www-form-urlencoded""")

      val headers_http_authenticated = Map(

        "Content-Type" -> """application/json""",
        "Authorization" -> """Bearer ${access_token}"""
      )


      val scn = scenario("Load test")
      .exec(
          http("Get auth token")
            .post("http://site.com:9080/auth/realms/master/protocol/openid-connect/token")
            .formParam("client_id", "app")
            .formParam("username","username")
            .formParam("password","password")
            .formParam("grant_type", "password")
            .headers(headers_http_authentication)
            .check(status.is(200))
            .check(jsonPath("$.access_token").exists.saveAs("access_token"))
        )

      .exec(http("Multipart request")
          .post("/api/create")
          .headers(headers_http_authenticated)
          .formParam("data", """{"type": "NONE", "title": "something"}""")
          .bodyPart(RawFileBodyPart("Face", "/home/5b56e114-c96e-4af3-b262-46d8bc146855.jpg")
            .fileName("/home/5b56e114-c96e-4af3-b262-46d8bc146855.jpg")
            .transferEncoding("binary")).asMultipartForm
          )
     
     .exec(http("QueryParam Request")
          .post("/api/save")
          .headers(headers_http_authenticated)
          .queryParam("Id","3551")
        )

  
      setUp(
        scn.inject(
          atOnceUsers(1)
        )
          .protocols(httpProtocol)
      )

    }
