//
//  Course.swift
//  Wake me up
//
//  Created by Oscar  Ekenlöw on 2022-01-07.
//

import Foundation

struct CourseEvent: Hashable, Codable {
    var name: String
    var code: String
    var startTime: String
    var endTime: String
    var classroom: String
    var info: String
    var type: String
    var active: Bool
    
    mutating func toggle() {
        active = !active
    }
    
    
    init(from entry: KTHCalendarEntry, courseName: String, courseCode: String) {
        self.name = courseName
        self.code = courseCode
        self.startTime = entry.start
        self.endTime = entry.end
        self.classroom = entry.locations.first?.name ?? "No location given"
        self.info = entry.info ?? "No additional information given."
        self.type = entry.type_name.sv
        self.active = true
    }
    
    init(name: String, code: String, startTime: String, endTime: String, classroom: String, info: String, type: String, active: Bool) {
        self.name = name
        self.code = code
        self.startTime = startTime
        self.endTime = endTime
        self.classroom = classroom
        self.info = info
        self.type = type
        self.active = active
    }
    
    init(from entry: KTHCalendarEntry, courseName: String, courseCode: String, active: Bool) {
        self.name = courseName
        self.code = courseCode
        self.startTime = entry.start
        self.endTime = entry.end
        self.classroom = entry.locations.first?.name ?? "No location given"
        self.info = entry.info ?? "No additional information given."
        self.type = entry.type_name.sv
        self.active = active
    }
    
}
