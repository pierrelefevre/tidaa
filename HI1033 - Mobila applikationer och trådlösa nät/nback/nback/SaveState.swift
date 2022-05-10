//
//  SaveState.swift
//  nback
//
//  Created by Oscar  Ekenlöw on 2021-11-26.
//

import Foundation

struct SaveState: Codable {
    var settings: Settings
    var results: [ResultModel]

}
