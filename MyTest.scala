package mytest

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._



class MyTest extends Simulation {

  val httpProtocol = http
    .baseUrl("http://computer-database.gatling.io")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  val headers_0 = Map("Upgrade-Insecure-Requests" -> "1")
	
// 1. просмотр страницы
/*
  val scn = scenario("Open_Page") 
    .exec(http("Open_Page")
		.get("/")
		.headers(headers_0)
		.check(status.is(200))
		)

// 2. добавление записи

   val csvFeeder = csv("123.csv").random

   val scn = scenario("test")
	   .feed(csvFeeder)
       .exec(http("Add_pc")
		.post("/computers")
		.formParam("name", "${name}")
		.formParam("introduced", "")
		.formParam("discontinued", "")
		.formParam("company", "")) 
*/
	
// 3. удаление записи
	
  val del = scenario("test")
    .pause(5)
    .exec(http("test")
	  .get("/computers")
	  .check(regex("""(?<=<td><a href=").*(?=">)""").find.saveAs("computerURL")) 
	)
    .exec(http("Del_pc")
      .post("${computerURL}/delete")
	)
		
//end-----------------------------------------------

		
//сценарии

  //setUp(scn.inject(atOnceUsers(20)).protocols(httpProtocol))
  setUp(del.inject(constantUsersPerSec(1) during (20 seconds)))
//

//поиск максимума
  setUp(
  scn.inject(
    rampUsersPerSec(0) to 50 during (5 minutes),
    constantUsersPerSec(50) during (30 minutes), //1	
    rampUsersPerSec(50) to 100 during (5 minutes),
    constantUsersPerSec(100) during (30 minutes), //2
	rampUsersPerSec(100) to 150 during (5 minutes),
    constantUsersPerSec(150) during (30 minutes), //3
	rampUsersPerSec(150) to 200 during (5 minutes),
    constantUsersPerSec(200) during (30 minutes), //4
	rampUsersPerSec(200) to 0 during (2 minutes),
    )).maxDuration(90 minutes).protocols(httpProtocol)
//

//тест стабильности
  setUp(
  scn.inject(
    rampUsersPerSec(0) to 150 during (5 minutes),
    constantUsersPerSec(150) during (300 minutes), //5h
    rampUsersPerSec(150) to 0 during (2 minutes),
    )).maxDuration(310 minutes).protocols(httpProtocol)


}

