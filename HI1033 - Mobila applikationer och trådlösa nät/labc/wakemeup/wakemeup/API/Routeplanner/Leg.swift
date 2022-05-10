//
//  Leg.swift
//  wakemeup
//
//  Created by Pierre Le Fevre on 2021-12-22.
//

import Foundation

public struct Leg: Hashable, Codable{
    var Origin: LegPartial
    var Destination: LegPartial
    
    var idx: String
    var name: String
    var type: String
    var transportCategory: String?
}
