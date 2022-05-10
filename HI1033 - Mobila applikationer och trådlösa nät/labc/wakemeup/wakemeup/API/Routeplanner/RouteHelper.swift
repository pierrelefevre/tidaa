//
//  RouteHelper.swift
//  wakemeup
//
//  Created by Oscar  Ekenlöw on 2022-01-03.
//

import Foundation

class RouteHelper: ObservableObject {
    
    func loadRoute(origin: [String], roomName: String, time: String,_ debug: Bool?, completion: @escaping (Data?, Error?) -> Void) {
        var route: Route?;
        var error: String;
        
        if(debug != nil && debug!){
            route = ViewModel.loadJSON("resrobot_example.json")
            error = "Warning: Loaded mock JSON of Route"
            return
        }
        
        if(!ViewModel.connected){
            print("Network error - Route")
            error = "Error: Network error when loading route"
            return
        }
        
        
        let coordinates = geocodeRoomName(roomName)
        if coordinates.isEmpty {
            return
        }
        let url = URL(string: "https://api.resrobot.se/v2/trip?format=json&time=\(time)&originCoordLat=\(origin[0])&originCoordLong=\(origin[1])&passlist=0&key=\(APIHelper.API_KEY)&originWalk=1&searchForArrival=1&numF=1&destCoordLat=\(coordinates[0])&destCoordLong=\(coordinates[1])")!

            let task = URLSession.shared.dataTask(with: url) { data, response, error in
            if debug ?? false {
                completion(nil, error)
            } else {
                completion(data, error)
            }
        }
        task.resume()
        
    }
    
    func geocodeRoomName(_ roomName: String) -> [String]{
        let roomLink = getRoomLinkFromRoomName(roomName)
        guard let myURL = URL(string: roomLink) else {
            print("Error: \(roomLink) doesn't seem to be a valid URL")
            return []
        }

        do {
            let htmlString = try String(contentsOf: myURL, encoding: .ascii)
            
            let splitString = htmlString.components(separatedBy: "<meta name=\"latitude\" content=\"")
            let splitString2 = splitString[1].components(separatedBy: "<meta name=\"longitude\" content=\"")
            let latitude = splitString2[0].dropLast().dropLast().dropLast().dropLast().dropLast().dropLast().dropLast()
            let longitude = splitString2[1].components(separatedBy: "\" />")[0]
            let coordinates: [String] = [String(latitude), longitude]
            print("latitude : \(latitude)")
            print("longitude: \(longitude)")
            return coordinates
        } catch let error {
            print("Error: \(error)")
        }
        return []
    }
    
    func getRoomLinkFromRoomName(_ roomName: String) -> String{
        let myURLString = "https://www.kth.se/search?q=\(roomName)&entityFilter=kth-place&filterLabel=Facilities&lang=en&btnText=Search"
        guard let myURL = URL(string: myURLString) else {
            print("Error: \(myURLString) doesn't seem to be a valid URL")
            return ""
        }

        do {
            let htmlString = try String(contentsOf: myURL, encoding: .ascii)
            
            let splitString = htmlString.components(separatedBy: "<h3 class=\"card-title\"><a href=\"")
            let splitString2 = splitString[1].components(separatedBy: "\" data-resultnr=\"0\"")
            let roomLink = splitString2[0]
            print(roomLink)
            return String(roomLink)
        } catch let error {
            print("Error: \(error)")
        }
        return ""
    }
}
