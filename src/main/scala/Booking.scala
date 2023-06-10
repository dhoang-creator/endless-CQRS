import java.time.Instant

// The below are simply the data models which represent a booking, noteworthy that LatLon is for ride sharing

final case class LatLon(lat: Double, lon: Double)
final case class Booking(
                        time: Instant,
                        passengerCount: Int,
                        origin: LatLon,
                        destination: LatLon
                        )
