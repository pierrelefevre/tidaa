import Foundation
import Combine


struct APIModel {
    var route: Route?
    var calendars: [KTHCalendarResult]
    
    var error = ""
    let debug = false
    
    var destionationName: String?
    
    init() {
        calendars = [KTHCalendarResult]()
    }
}
