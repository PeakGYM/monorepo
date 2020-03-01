import scala.util.Random
//Http4s
val CirceVersion = "0.13.0"
interp.load.ivy(
  "com.lihaoyi" % s"ammonite-shell_${scala.util.Properties.versionNumberString}" %  ammonite.Constants.version,
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

def fakeName() =  ujson.read(requests.get.stream("https://api.namefake.com/polish-poland/random/"))("name").str

def generateCoaches() = {
  case class Coach(id:Int,name:String)  {
    def toLine =  s"""insert into coach(id,name,"pictureUrl") values('$id','$name','https://thispersondoesnotexist.com/image');"""
  }

  val coachIds = 1 to 30 toList
  val data = coachIds.map(id => Coach(id,fakeName()).toLine).reduce(_ + "\n" + _)


  os.write.over(pwd / "coaches.sql", data)
}


def generateClients() = {
  case class Client(id:Int,name:String,height:Int)  {
    def toLine =  s"""insert into client(id,name,height,picture_url) values('$id','$name',$height,'https://thispersondoesnotexist.com/image');"""
  }

  val coachIds = 1 to 30 toList
  val data = coachIds.map(id => Client(id,fakeName(),150+ Random.nextInt(40)).toLine).reduce(_ + "\n" + _)

  os.write.over(pwd / "clients.sql", data)
}



def main() = {
  println("Started")
  // generateCoaches()
  generateClients()
  println("Exit")
}

main()
