//
//  WeatherModel.swift
//  Happy Weather
//
//  Created by Pierre Le Fevre on 2021-11-12.
//

import Foundation

struct WeatherModel: Hashable, Codable {
    var approvedTime: String
    var referenceTime: String
    var timeSeries: [WeatherDatapointModel]
    
    struct WeatherDatapointModel: Hashable, Codable
    {
        var validTime: String
        var parameters: [WeatherParameterModel]
    }
    
    struct WeatherParameterModel: Hashable, Codable
    {
        var name: String
        var levelType: String
        var level: Int
        var unit: String
        var values: [Double]
    }
    
}
