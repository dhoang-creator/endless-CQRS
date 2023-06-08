import cats.Monad
import endless.\/
import endless.core.entity.Entity
import org.typelevel.log4cats.Logger


final case class BookingEntity[F[_]: Monad: Logger](
                                                     entity: Entity[BookingState, BookingEvent]
                                                   ) extends BookingAlg[F] {
  def place(booking: Booking): F[AlreadyExists.type \/ Unit] =
    entity.ifUnknownF(entity.write(BookingPlaced(booking)))(_ => AlreadyExists)

  
}
