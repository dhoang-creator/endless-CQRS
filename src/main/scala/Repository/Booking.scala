package Repository

import java.time.Instant

final case class Booking(
                          time: Instant,
                          passengerCount: Int,
                          origin: LatLon,
                          destination: LatLon
                        )
