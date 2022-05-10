//
//  ModelData.swift
//  Happy Weather
//
//  Created by Pierre Le Fevre on 2021-11-12.
//

import Foundation
import Combine
import Network

final class ModelData: ObservableObject
{
    @Published var forecast: WeatherVM = WeatherVM(load("mockWeather.json"))
    @Published var cities: [CityVM] = []
    @Published var selectedCity: CityVM?
    @Published var favourites: [CityVM] = []
    @Published var activeTab = 2
    @Published var lastUpdate: Date = Date()
    @Published var connected: Bool = false
    @Published var updateFrequency: Int = 10
    
    let monitor = NWPathMonitor()
    let queue = DispatchQueue(label: "Monitor")
    
    public init(){
        monitor.pathUpdateHandler = { path in
            DispatchQueue.main.async {
                if path.status == .satisfied {
                    self.connected = true
                } else {
                    self.connected = false
                }
            }
        }
        monitor.start(queue: queue)
    }
    
    public func loadSaved(){
        UserRepository.load()
        
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.25) {
            if let loadSaveState = UserRepository.resultSet{
                print(loadSaveState)
                self.forecast = loadSaveState.forecast
                self.selectedCity = loadSaveState.selectedCity
                self.favourites = loadSaveState.favourites
                self.activeTab = 1
                self.lastUpdate = loadSaveState.lastUpdate
                self.updateFrequency = loadSaveState.updateFrequency
                
                let diff = Int(Date().timeIntervalSince1970 - self.lastUpdate.timeIntervalSince1970)
                let hours = diff / 3600
                let minutes = (diff - hours * 3600) / 60
                
                if(minutes > self.updateFrequency){
                    if let selected = self.selectedCity{
                        self.loadWeather(selected)
                    }
                }
            }else{
                print("Nothing was found when trying user repo")
            }
        }
    }
    
    public func selectCity(_ city: CityVM){
        print(city.place)
        print(city.lon)
        print(city.lat)
        loadWeather(city)
    }
    
    public func setActiveTab(_ tab: Int){
        activeTab = tab
        save()
    }
    
    public func addFavourite(_ city: CityVM){
        favourites.append(city)
        save()
    }
    
    public func removeFavourite(_ city: CityVM){
        favourites.removeAll(where:  { $0.id == city.id })
        save()
    }
    
    public func save(){
        let saveState = ModelDataDB(forecast: self.forecast, selectedCity: self.selectedCity, favourites: self.favourites, lastUpdate: self.lastUpdate, updateFrequency: self.updateFrequency)
        UserRepository.save(saveState)
    }
    
    public func loadWeather(_ city: CityVM){
        if(!connected){
            return
        }
        
        let lon = String(format: "%.6f", city.lon)
        let lat = String(format: "%.6f", city.lat)
        
        let url = URL(string: "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/\(lon)/lat/\(lat)/data.json")!
        
        let task = URLSession.shared.dataTask(with: url) { data, response, error in
            if let result = try? JSONDecoder().decode(WeatherModel.self, from: data!) {
                DispatchQueue.main.async {
                    self.forecast = WeatherVM(result)
                    self.selectedCity = city
                    self.activeTab = 1
                    self.lastUpdate = Date()
                    self.save()
                }
            }else{
                print("Weather URL load not working")
            }
        }
        task.resume()
    }
    
    public func loadCities(_ searchString: String){
        if(!connected){
            return
        }
        let searchStringEncoded = searchString.addingPercentEncoding(withAllowedCharacters: .urlHostAllowed)!
        let url = URL(string: "https://www.smhi.se/wpt-a/backend_solr/autocomplete/search/\(searchStringEncoded)")!
        
        let task = URLSession.shared.dataTask(with: url) { data, response, error in
            if let result = try? JSONDecoder().decode([CityModel].self, from: data!) {
                DispatchQueue.main.async {
                    var newList: [CityVM] = []
                    
                    for city in result{
                        newList.append(CityVM(city))
                    }
                    
                    self.cities = newList
                }
            }else{
                print("Citites URL load not working")
            }
        }
        task.resume()
    }
}

func load<T: Decodable>(_ filename:String) -> T {
    let data: Data
    
    guard let file = Bundle.main.url(forResource: filename, withExtension: nil)
    else{
        fatalError("Couldn't find " + filename + " in main bundle")
    }
    
    do{
        data = try Data(contentsOf: file)
    }
    catch{
        fatalError("Couldn't load \(filename) from main bundle:\n\(error)")
    }
    
    do{
        let decoder = JSONDecoder()
        return try decoder.decode(T.self, from: data)
    }
    catch{
        fatalError("Couldn't parse \(filename) as \(T.self):\n\(error)")
    }
}
