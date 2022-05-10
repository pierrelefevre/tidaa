//
//  Model.swift
//  wakemeup
//
//  Created by Pierre Le Fevre on 2021-12-22.
//

import Foundation

struct Model: Codable, Hashable {
    var minutesBeforeDeparture: Int
    var minutesBeforeArrival: Int
    var lastWakeUp: Date
    var lastUpdated: Date
    var courses: [CourseEvent]
}
