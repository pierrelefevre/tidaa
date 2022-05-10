//
//  Settings.swift
//  nback
//
//  Created by Oscar  Ekenlöw on 2021-11-26.
//

import Foundation

struct Settings: Codable {
    var n: Int
    var visual: Bool
    var audio: Bool
    var delay: Double
    var numberOfRounds: Int
}
