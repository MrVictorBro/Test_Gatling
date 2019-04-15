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
  
  val csvFeeder = csv("123.csv").circular
  
// 1. просмотр страницы
  val openItem = exec(
        http("Open_Page")
		.get("/")
		.headers(headers_0)
		.check(status.is(200)))

// 2. добавление записи
  val addItem = feed(csvFeeder)
    .exec(http("Add_pc")
		.post("/computers")
		.formParam("name", "${name}")
		.formParam("introduced", "")
		.formParam("discontinued", "")
		.formParam("company", ""))  

	
// 3. удаление записи
  val delItem = exec(
        http("Del_pc")
		.get("/computers")
		//.get("/computers?f=hp")
		.check(regex("""(?<=<td><a href=").*(?=">)""").find.saveAs("computerURL")))
    .exec(http("Del_pc2")
		.post("${computerURL}/delete"))
//---

//планировщик
  val snc = scenario("TestCode") randomSwitch(
	 (85d, openItem),
	 (10d, addItem),
	 (5d, delItem)
	 )

// поиск максимума
/*   setUp(snc.inject(
    rampUsersPerSec(0) to 20 during (2 minutes),
    constantUsersPerSec(20) during (10 minutes),
    rampUsersPerSec(20) to 40 during (2 minutes),
    constantUsersPerSec(40) during (10 minutes),
    rampUsersPerSec(40) to 60 during (2 minutes),
    constantUsersPerSec(60) during (10 minutes),
    rampUsersPerSec(60) to 80 during (2 minutes),
    constantUsersPerSec(80) during (10 minutes),
    rampUsersPerSec(80) to 100 during (2 minutes),
    constantUsersPerSec(100) during (10 minutes),
    rampUsersPerSec(100) to 120 during (2 minutes),
    constantUsersPerSec(120) during (10 minutes),
    rampUsersPerSec(120) to 140 during (2 minutes),
    constantUsersPerSec(140) during (10 minutes),
    rampUsersPerSec(140) to 160 during (2 minutes),
    constantUsersPerSec(160) during (10 minutes),
    rampUsersPerSec(160) to 180 during (2 minutes),
    constantUsersPerSec(180) during (10 minutes),
    rampUsersPerSec(180) to 200 during (2 minutes),
    constantUsersPerSec(200) during (10 minutes)
    ).protocols(httpProtocol)).maxDuration(120 minutes) */


// тест стабильности на 110 (92)
  setUp(snc.inject(
    rampUsersPerSec(0) to 92 during (5 minutes),
    constantUsersPerSec(92) during (120 minutes)
    ).protocols(httpProtocol)).maxDuration(130 minutes)
	
//smoke test
/*    setUp(snc.inject(
     rampUsersPerSec(0) to 10 during (30 seconds)
	 ).protocols(httpProtocol)) */  
}
