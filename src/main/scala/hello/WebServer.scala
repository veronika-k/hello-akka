package hello

import scala.concurrent.Future
import scala.util.{Success, Failure}

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

object WebServer extends App {

  val logger = LoggerFactory.getLogger(this.getClass)

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val config = ConfigFactory.load()
  val host = config.getString("http.host")
  val port = config.getInt("http.port")
  val message = config.getString("http.message")

  val route = path("") {
    get {
      logger.info("Received request")
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, message))
    }
  }

  val bindingFuture: Future[ServerBinding] = Http().bindAndHandle(route, host, port)

  bindingFuture.map { serverBinding =>
    logger.info(s"Web server bound to ${serverBinding.localAddress}.")
  }.onComplete {
    case Failure(t) =>
      logger.error(s"Failed to bind to $host:$port!", t)
      system.terminate()
    case Success(_) =>
  }

}
