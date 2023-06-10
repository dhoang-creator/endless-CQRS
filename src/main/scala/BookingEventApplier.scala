import akka.http.javadsl.server.Directives.route
import endless.\/
import endless.core.event.EventApplier

class BookingEventApplier extends EventApplier[BookingState, BookingEvent] {
  def apply(state: Option[BookingState], event: BookingEvent): String \/ Option[BookingState] =
    (event match {
      case BookingPlaced(booking) =>
        state
          .toLeft(BookingState(definition = booking, route = Nil))
          .leftMap(_ => "Booking already exists")
      case RouteSet(steps: List[LatLon]) =>
        state
          .toRight("Attempt to set route on unknown booking")
          .map(_.copy(route = steps))
    }).map(Option(_))

}
