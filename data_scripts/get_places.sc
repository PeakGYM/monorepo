//Http4s
val http4sVersion = "0.21.1"
val CirceVersion = "0.13.0"
interp.load.ivy(
  "com.lihaoyi" % s"ammonite-shell_${scala.util.Properties.versionNumberString}" %  ammonite.Constants.version,
  // "org.typelevel" % "mouse_2.12" % "0.21",
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion,
  "org.http4s"   %% "http4s-circe"        % http4sVersion,
    "io.circe"     %% "circe-generic"       % CirceVersion,
  "io.circe"     %% "circe-optics"        % CirceVersion,
  "com.lihaoyi" %% "requests" % "0.2.0","it.justwrote" %% "scala-faker" % "0.3"
)

@
val shellSession = ammonite.shell.ShellSession()
import shellSession._
// Loggging
import scala.concurrent.duration._
import scala.util.{Failure,Success}
import ammonite.ops._
import ammonite.shell._
import scala.concurrent._
import scala.language.postfixOps
import scala.concurrent.ExecutionContext.global
import cats.effect._

import org.http4s.client.blaze._
import org.http4s.client._
import org.http4s.EntityDecoder
import org.http4s.client.dsl.io._

import io.circe._
import io.circe.syntax._
import io.circe.generic.auto._
import org.http4s._
import org.http4s.circe.jsonOf
import _root_.io.circe

import cats.effect.Blocker
import java.util.concurrent._

implicit val cs= IO.contextShift(global)
val blockingPool = Executors.newFixedThreadPool(10)
val blocker = Blocker.liftExecutorService(blockingPool)
val httpClient: Client[IO] = JavaNetClientBuilder[IO](blocker).create



val key = (read! Path( "/home/owner/.private/google-api-key" )).trim


def getGyms(page:Int,pageToken :Option[String]) :Unit = {



  val radiusMeters= 30* 1000
  val (lat,lng) = (50.026532,19.9489974)

  val args = Map(
    "key" -> key,
    "location" -> s"$lat,$lng",
    "radius" -> radiusMeters.toString,
    "keyword" -> "gym",
    "type" -> "gym",
    // "input" -> "gym",
    // "inputtype" -> "textquery",
    // "locationbias" -> s"circle:$radiusMeters@$lat,$lng",
    // "fields" -> "photos,formatted_address,name,rating,opening_hours,geometry"
  )

  val params:String = args.map{case (key,value) => s"$key=$value"}.reduce(_ + "&"+ _)


  // val url ="https://maps.googleapis.com/maps/api/place/findplacefromtext/json?" + params
  // val url ="https://maps.googleapis.com/maps/api/place/nearbysearch/json?" + params
  val url = "https://api-wwh.codevillains.me/assets/ala"


  case class Location(lat:Double,lng:Double)
  case class Geometry(location:Location)
  case class Photo(photo_reference:String)
  case class Entry(name:String,photos:List[Photo], geometry:Geometry,rating:Double)
  case class NearbyResponse(next_page_token:Option[String], results:List[Entry])
  // implicit val dec: Decoder[NearbyResponse] = deriveDecoder
  implicit val daec: EntityDecoder[IO,Json] = jsonOf[IO,Json]

  val result=   httpClient.expect[Json](url).unsafeRunSync().as[NearbyResponse].toOption.get

  // use `client` here and return an `IO`.
  // the client will be acquired and shut down
  // automatically each time the `IO` is run.
  val id= result.results.head.photos.head.photo_reference
  println(getPhoto(id))
  println(result.next_page_token)//.next_page_token)
  // os.write.over(pwd / "mapsquery.json",data)


}

def getPhoto(id:String) = {
  val w=300
  val url =s"https://maps.googleapis.com/maps/api/place/photo?key=$key&photoreference=$id&maxwidth=$w"

  import cats.Id


  val r = requests.get(url,maxRedirects=0,check=false)
  r.headers("location").head

//       httpClient.fetch(Request[IO](uri= Uri.unsafeFromString(url))) {
//     case Status.Successful(r) =>
//           println("ala")
//       val location = r.headers.find(_.name.toString.toLowerCase() == "location").get.value
//       location
//   case r =>
//           println("ola")
//     throw new IllegalArgumentException("fuuck")
// }.unsafeRunSync()


}




def main() = {
  getGyms(0,None)
  println("exited")
}

main()
