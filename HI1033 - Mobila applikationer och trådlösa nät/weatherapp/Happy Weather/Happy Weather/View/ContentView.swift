//
//  ContentView.swift
//  Happy Weather
//
//  Created by Pierre Le Fevre on 2021-11-12.
//

import SwiftUI

struct ContentView: View {
    @EnvironmentObject var modelData: ModelData
    
    var body: some View {
        TabView(selection: $modelData.activeTab) {
            ForecastView().tabItem {
                VStack{
                    Image(systemName: "cloud.sun.fill")
                    Text("Weather")
                }
            }.tag(1)
            
            CitiesView().tabItem {
                VStack{
                    Image(systemName: "magnifyingglass")
                    Text("Cities")
                }
            }.tag(2)
            
            FavouritesView().tabItem {
                VStack{
                    Image(systemName: "star.fill")
                    Text("Favourites")
                }
            }.tag(3)
            
            SettingsView().tabItem {
                VStack{
                    Image(systemName: "gear")
                    Text("Settings")
                }
            }.tag(4)
        }.onAppear{
            print("ON APPEAR HAS FIRED")
            modelData.loadSaved()
            
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
            .environmentObject(ModelData())
            .environment(\.locale, .init(identifier: "se"))
    }
}
