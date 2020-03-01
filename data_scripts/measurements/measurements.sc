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

def create(i:Int) =  requests.post(s"https://api-wwh.codevillains.me/faker/measurements/$i/$i")

def main() = {
  println("Started")
  // generateCoaches()


  1 to 30 foreach(create)

  println("Exit")
}

main()
