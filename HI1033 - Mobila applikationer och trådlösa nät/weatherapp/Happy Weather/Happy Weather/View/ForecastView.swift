//
//  ForecastView.swift
//  Happy Weather
//
//  Created by Pierre Le Fevre on 2021-11-12.
//

import SwiftUI

struct ForecastView: View {
    @EnvironmentObject var modelData: ModelData
    
    var body: some View {
        NavigationView{
            if let selected = modelData.selectedCity{
                List{
                    ForEach(0 ..< modelData.forecast.timeSeries.count){i in
                        ForecastRowView(datapoint: modelData.forecast.timeSeries[i])
                    }
                    Text("Last updated \(modelData.lastUpdate.formatted(.dateTime))")
                    Text("Approved at \(modelData.forecast.approvedTime.formatted(.dateTime))")
                }.navigationBarTitle(selected.place)
                    .toolbar{
                        if modelData.favourites.first(where:  { $0.id == selected.id }) != nil{
                            Button(action: {modelData.removeFavourite(selected)}) {
                                Image(systemName: "star.fill")
                                    .foregroundColor(.yellow)
                            }.buttonStyle(BorderlessButtonStyle())
                        }else{
                            Button(action: {modelData.addFavourite(selected)}) {
                                Image(systemName: "star")
                                    .foregroundColor(.white)
                            }.buttonStyle(BorderlessButtonStyle())
                        }
                    }
            }else{
                List{
                    Button(action: {modelData.loadSaved()}) {
                        Text("Try load saved data")
                    }
                }.navigationBarTitle("Weather")
            }
        }
    }
}

struct ForecastView_Previews: PreviewProvider {
    static var previews: some View {
        ForecastView()
            .environmentObject(ModelData())
    }
}
