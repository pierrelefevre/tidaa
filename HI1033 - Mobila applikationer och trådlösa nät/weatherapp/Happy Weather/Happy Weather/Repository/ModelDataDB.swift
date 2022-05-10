//
//  ModelDataDB.swift
//  Happy Weather
//
//  Created by Pierre Le Fevre on 2021-11-15.
//

import Foundation

struct ModelDataDB: Codable{
    var forecast: WeatherVM
    var selectedCity: CityVM?
    var favourites: [CityVM]
    var lastUpdate: Date
    var updateFrequency: Int
}
