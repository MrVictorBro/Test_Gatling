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

	.exec(http("Load_Page_add_pc")
		.get("/computers/new")
		.headers(headers_0))
	//.pause(7)
	.exec(http("Add_pc")
		.post("/computers")
		.headers(headers_0)
		.formParam("name", "999")
		.formParam("introduced", "")
		.formParam("discontinued", "")
		.formParam("company", ""))  
*/
	
// 3. удаление записи
/*	
	.exec(http("Load_Page_del_pc")
		.get("/computers/616"))
		.pause(2)
	.exec(http("Del_pc")
		.post("/computers/616/delete"))
*/		
//end-----------------------------------------------







// отладка получить ссылку на пк
  val scn = scenario("test") 
    .exec(http("test")
	.get("/computers/")
	.check(css("a:contains('${searchComputerName}')", "href").saveAs("computerURL"))
	)
/*
  .exec(session => {
    val response = session("token").as[String]
    println("------------")
	//println(s"Response body: \n$response")
	println("$token")
	println("------------")
    session
  })
*/




/*
    .exec(http("Find_link")
        .get("/computers/")
        .check(regex("content=\\\"(?<bidkid>[0-9A-Z]*)\\\"").find.saveAs("token"))
		*/
	
	



//
  setUp(scn.inject(atOnceUsers(1)).protocols(httpProtocol))  //запуск одного пользователя

//







//------------------------------
/*  
  setUp(scn.inject(
    rampUsersPerSec(0) to 200 during (5 minutes)
    )).maxDuration(5 minutes).protocols(httpProtocol)
*/
	
		
//-------------------------------------------
/*
  setUp(
  scn.inject(
    rampUsersPerSec(0) to 50 during (5 minutes),			// от 0 до 50
    constantUsersPerSec(50) during (120 seconds),		// стабильно 50 пользователей
	rampUsersPerSec(50) to 0 during (20 seconds)		// с 50 до 0
    )).maxDuration(5 minutes).protocols(httpProtocol)
*/


//------------Справка-----------
/*
  setUp(
  scn.inject(
    nothingFor(4 seconds), // 1 Пауза.
    atOnceUsers(10), // 2 Вводит заданное количество пользователей одновременно.
    rampUsers(10) during (5 seconds), // 3 Вводит заданное количество пользователей с линейной рамкой в течение заданной продолжительности.
    constantUsersPerSec(20) during (15 seconds), // 4 Вводит пользователей с постоянной скоростью, определенной в пользователях в секунду, в течение заданного времени. Потребители будут впрыснуты на регулярных интервалах.
    constantUsersPerSec(20) during (15 seconds) randomized, // 5 Вводит пользователей с постоянной скоростью, определенной в пользователях в секунду, в течение заданного времени. Потребители будут впрыснуты на хаотизированных интервалах.
    rampUsersPerSec(10) to 20 during (10 minutes), // 6 Вводит пользователей от начальной скорости к целевой скорости, определенной в пользователях в секунду, в течение заданного времени. Потребители будут впрыснуты на регулярных интервалах.
    rampUsersPerSec(10) to 20 during (10 minutes) randomized, // 7 Вводит пользователей от начальной скорости к целевой скорости, определенной в пользователях в секунду, в течение заданного времени. Потребители будут впрыснуты на хаотизированных интервалах.
    heavisideUsers(1000) during (20 seconds) // 8 водит заданное число пользователей после гладкого приближения функции шага heaviside, растянутой до заданной продолжительности.
  ).protocols(httpProtocol)
)
*/
 
}

