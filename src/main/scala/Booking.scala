import java.time.Instant

final case class LatLon(lat: Double, lon: Double)
final case class Booking(
                        time: Instant,
                        passengerCount: Int,
                        origin: LatLon,
                        destination: LatLon
                        )
