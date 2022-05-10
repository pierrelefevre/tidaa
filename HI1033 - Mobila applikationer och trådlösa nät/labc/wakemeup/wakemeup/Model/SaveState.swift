//
//  SaveState.swift
//  Wake me up
//
//  Created by Oscar  Ekenlöw on 2022-01-10.
//

import Foundation

struct SaveState: Hashable, Codable {
    var model: Model
    var courses: [CourseIdentifier]
}
