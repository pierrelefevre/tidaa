//
//  Happy_WeatherApp.swift
//  Happy Weather
//
//  Created by Pierre Le Fevre on 2021-11-12.
//

import SwiftUI

@main
struct Happy_WeatherApp: App {
    @StateObject private var modelData = ModelData()
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                .environmentObject(modelData)
        }
    }
}
