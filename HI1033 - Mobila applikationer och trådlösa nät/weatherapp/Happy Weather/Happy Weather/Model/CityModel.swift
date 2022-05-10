//
//  CityModel.swift
//  Happy Weather
//
//  Created by Pierre Le Fevre on 2021-11-12.
//

import Foundation

struct CityModel: Hashable, Codable {
    var geonameid: Int
    var place: String
    var population: Int
    var lon: Double
    var lat: Double
    var municipality: String?
    var county: String?
    var country: String
    var district: String?
}
