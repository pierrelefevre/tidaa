// LocationManager, using CoreLocation
// Requires following permission:
// Privacy - Location When In Use Usage Description
// Privacy - Location Always and When In Use Usage Description

import Foundation
import CoreLocation

final class LocationManager: NSObject , CLLocationManagerDelegate{
    
    
    @Published var latitude: CLLocationDegrees = 0.0
    @Published  var longitude: CLLocationDegrees = 0.0
    
    var manager = CLLocationManager()
    
    override init(){
        super.init()
        
        self.manager.requestAlwaysAuthorization()
        
        if CLLocationManager.locationServicesEnabled() {
            manager.delegate = self
            manager.desiredAccuracy = kCLLocationAccuracyNearestTenMeters
            manager.startUpdatingLocation()
        }
    }
    
    public func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus
    ) {
        // Handle changes if location permissions
    }
    
    public func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]
    ) {
        if let location = locations.last {
            self.latitude = location.coordinate.latitude
            self.longitude = location.coordinate.longitude
            //print("New location: \(self.latitude) \(self.longitude)")
            // Handle location update
        }
    }
    
    public func locationManager(_ manager: CLLocationManager, didFailWithError error: Error
    ) {
        // Handle failure to get a user’s location
        manager.stopUpdatingLocation()
    }
    
}
