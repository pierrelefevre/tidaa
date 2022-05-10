//
//  CourseInfo.swift
//  wakemeup
//
//  Created by Oscar  Ekenlöw on 2022-01-06.
//

import Foundation

struct CourseInfo: Hashable, Codable {
    var courseCode: String
    var title: String
    var credits: Double
    var creditUnitLabel: String
    var creditUnitAbbr: String
    var educationalLevel: String
}
