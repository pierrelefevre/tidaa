//
//  CityVM.swift
//  Happy Weather
//
//  Created by Pierre Le Fevre on 2021-11-12.
//

import Foundation

struct CityVM: Hashable, Codable, Identifiable {
    var id: Int
    var place: String
    var population: Int
    var lon: Double
    var lat: Double
    var municipality: String?
    var county: String?
    var country: String
    var district: String?
    
    public init(_ raw: CityModel){
        self.id = raw.geonameid
        self.place = raw.place
        self.population = raw.population
        self.lon = raw.lon
        self.lat = raw.lat
        self.municipality = raw.municipality
        self.county = raw.county
        self.country = raw.country
        self.district = raw.district
    }
}
