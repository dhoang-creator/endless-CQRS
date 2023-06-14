package service

import Repository.{Booking, BookingCommand, LatLon}
import akka.http.scaladsl.model.RemoteAddress.Unknown
import cats.Monad
import endless.\/
import endless.core.entity.Entity
import org.typelevel.log4cats.Logger

/*
  There's a lot of red here, are these method calls now deprecated?
 */

/*
  Reader-Writer exposed via Entity type class:
    trait Entity[F[_], S, E] extends StateReader[F, S] with EventWriter[F, E] with Monad[F]
    wherein:
    - StateReader allows for read: F[S]
    - EventWriter provides the ability to persist events: writer(events: E*): F[Unit]
 */
final case class BookingEntity[F[_]: Monad: Logger](
                                                     entity: Entity[BookingState, BookingEvent]
                                                   ) extends BookingCommand[F] {

  def place(booking: Booking): F[AlreadyExists.type \/ Unit] =
    entity.ifUnknownF(entity.write(BookingPlaced(booking)))(_ => AlreadyExists)

  def getBooking: F[Unknown.type \/ Booking] =
    entity.ifKnown(_.definition)(Unknown)

  def setRoute(steps: List[LatLon]): F[Unknown.type \/ Unit] =
    entity.ifKnownF(entity.write(RouteSet(steps))(Unknown))

  def getRoute: F[Unknown.type \/ List[LatLon]] =
    entity.ifKnown(_.route)(Unknown)
}
