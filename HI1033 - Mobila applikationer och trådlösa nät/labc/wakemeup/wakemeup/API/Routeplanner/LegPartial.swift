//
//  LegPartial.swift
//  wakemeup
//
//  Created by Pierre Le Fevre on 2021-12-22.
//

import Foundation

public struct LegPartial: Hashable, Codable{
    
    var name: String
    var type: String
    var id: String
    var lon: Double
    var lat: Double
    var time: String
    var date: String
    
   /* "name": "Masmo T-bana (Huddinge kn)",
    "type": "ST",
    "id": "740021729",
    "extId": "740021729",
    "lon": 17.880336,
    "lat": 59.249682,
    "routeIdx": 2,
    "time": "16:51:00",
    "date": "2021-12-22"*/
}
