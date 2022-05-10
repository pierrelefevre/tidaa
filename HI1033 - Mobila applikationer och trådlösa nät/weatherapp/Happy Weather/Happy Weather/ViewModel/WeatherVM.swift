//
//  WeatherVM.swift
//  Happy Weather
//
//  Created by Pierre Le Fevre on 2021-11-12.
//

import Foundation

struct WeatherVM: Hashable, Codable {
    var approvedTime: Date
    var referenceTime: Date
    var timeSeries: [WeatherDatapointVM]
    
    public init(_ raw: WeatherModel){
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-MM-dd'T'HH:mm:ssZ"
        
        self.approvedTime = dateFormatter.date(from: raw.approvedTime)!
        self.referenceTime = dateFormatter.date(from: raw.referenceTime)!
        
        var newList: [WeatherDatapointVM] = []
        for datapoint in raw.timeSeries{
            newList.append(WeatherDatapointVM(datapoint))
        }
        self.timeSeries = newList
    }
    
    struct WeatherDatapointVM: Hashable, Codable
    {
        var validTime: Date
        var parameters: [WeatherParameterVM]
        var temperature: Double
        var symbol: Int
        
        
        public init(_ raw: WeatherModel.WeatherDatapointModel){
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "yyyy-MM-dd'T'HH:mm:ssZ"
            
            self.validTime = dateFormatter.date(from: raw.validTime)!
            
            var newList: [WeatherParameterVM] = []
            for parameter in raw.parameters{
                newList.append(WeatherParameterVM(parameter))
            }
            self.parameters = newList
            
            self.temperature = raw.parameters.first(where: { $0.name == "t" })!.values[0]
            
            self.symbol = Int(String(format: "%.0f", raw.parameters.first(where: {$0.name == "Wsymb2"})!.values[0]))!
        }
    }
    
    struct WeatherParameterVM: Hashable, Codable
    {
        var name: String
        var levelType: String
        var level: Int
        var unit: String
        var values: [Double]
        
        public init(_ raw: WeatherModel.WeatherParameterModel){
            self.name = raw.name
            self.levelType = raw.levelType
            self.level = raw.level
            self.unit = raw.unit
            self.values = raw.values
        }
    }
    
}
