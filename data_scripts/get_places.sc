import scala.util.Random
//Http4s
val http4sVersion = "0.21.1"
val CirceVersion = "0.13.0"
interp.load.ivy(
  "com.lihaoyi" % s"ammonite-shell_${scala.util.Properties.versionNumberString}" %  ammonite.Constants.version,
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion,
  "org.http4s"   %% "http4s-circe"        % http4sVersion,
    "io.circe"     %% "circe-generic"       % CirceVersion,
  "io.circe"     %% "circe-optics"        % CirceVersion,
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


case class Gym(name:String,lat:Double,lng:Double,imageUrl:Option[String])

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

  val tokenParam = pageToken.map(t => s"&pagetoken=$t").getOrElse("")

  val params:String = args.map{case (key,value) => s"$key=$value"}.reduce(_ + "&"+ _) + tokenParam


  // val url ="https://maps.googleapis.com/maps/api/place/findplacefromtext/json?" + params
  val url ="https://maps.googleapis.com/maps/api/place/nearbysearch/json?" + params
  // val url = "https://api-wwh.codevillains.me/assets/ala"


  case class Location(lat:Double,lng:Double)
  case class Geometry(location:Location)
  case class Photo(photo_reference:String)
  case class Entry(name:String,photos:Option[List[Photo]], geometry:Geometry,rating:Double)
  case class NearbyResponse(next_page_token:Option[String], results:List[Entry])
  // implicit val dec: Decoder[NearbyResponse] = deriveDecoder
  implicit val daec: EntityDecoder[IO,Json] = jsonOf[IO,Json]

  val jSON =   httpClient.expect[Json](url).unsafeRunSync()

  val result = jSON.as[NearbyResponse].toOption match {
    case  Some(j) => j
    case None =>
      println(jSON.as[NearbyResponse])
      os.write.over(pwd / s"fail.json",jSON.toString)
      ???
  }


  val gyms = result.results.map( r =>
    Gym(name= r.name,lat = r.geometry.location.lat,  lng=  r.geometry.location.lng,imageUrl= r.photos.toList.flatten.headOption.map(_.photo_reference).map(getPhoto))
  ).zipWithIndex.map{case (gym,i) => gymLine(i,gym)}

  val data = gyms.reduce(_ + "\n" + _)
  os.write.over(pwd / s"insert_$page.sql",data)



  result.next_page_token match {
    case Some(token ) =>
      println(s"Starting page ${page +1}")
      getGyms(page+1,Some(token))
    case None => println("ending")
  }

}

def getPhoto(id:String) = {
  val w=300
  val url =s"https://maps.googleapis.com/maps/api/place/photo?key=$key&photoreference=$id&maxwidth=$w"
  val r = requests.get(url,maxRedirects=0,check=false)
  r.headers("location").head
}


def coachIds() = scala.util.Random.shuffle((1 to 30).toList.map(_.toString)).take(1+Random.nextInt(6)) // TODO:bcm:



def gymLine(i:Int,g:Gym) ={
  val coaches = "[" + coachIds().map(c => s""""$c",""" ).mkString.stripSuffix(",") + "]"
  val img = g.imageUrl.map(i => s"'$i'").getOrElse("NULL")
  s"""insert into gym(id, name, location,imgurl, "coachIds") values ('$i', '${g.name}', ST_SetSRID(ST_Point(${g.lat}, ${g.lng}), 4326),$img , '$coaches');"""
}




def main() = {
  getGyms(0,None)
  println("exited")
}

main()
