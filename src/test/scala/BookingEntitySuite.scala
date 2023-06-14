import API.BookingEventApplier
import Repository.{Booking, BookingCommand}
import com.typesafe.config.ConfigException.IO
import endless.core.interpret.EntityT
import munit._

class BookingEntitySuite
    extends munit.CatsEffectSuite {
  private val bookingAlg = BookingEntity(EntityT.instance[IO, BookingCommand, BookingEvent])
  private implicit val eventApplier: BookingEventApplier = new BookingEventApplier

  test("place booking") {
    // below is an effectual testing method which is supposedly within the 'munit' package
    forAllF { booking: Booking =>
      bookingAlg
        .place(booking)
        .run(None)
        .map {
          case Right((events, _)) =>
            assertEquals(
              events,
              Chain(BookingPlaced(booking))
            )
          case Left(error) => fail(error)
        }
    }
  }
}
