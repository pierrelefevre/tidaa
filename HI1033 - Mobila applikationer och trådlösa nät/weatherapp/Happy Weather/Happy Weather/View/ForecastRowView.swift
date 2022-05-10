//
//  ForecastRowView.swift
//  Happy Weather
//
//  Created by Pierre Le Fevre on 2021-11-12.
//

import SwiftUI

struct ForecastRowView: View {
    var datapoint: WeatherVM.WeatherDatapointVM
    
    var body: some View {
        HStack{
            let hour = Int(datapoint.validTime.ISO8601Format().components(separatedBy: "T")[1].components(separatedBy: ":")[0])
            
            if(hour! > 18 || hour! < 7){
                Image("night-"+String(datapoint.symbol))
                        .resizable()
                        .frame(width:72, height: 50)
            }else{
                Image("day-"+String(datapoint.symbol))
                        .resizable()
                        .frame(width:72, height: 50)
            }
            
            let timestamp = datapoint.validTime.formatted().components(separatedBy: ", ")
            
            VStack(alignment: .leading){
                Text(timestamp[0])
                Text(timestamp[1])
                    .fontWeight(.bold)
            }
            .padding(.leading, 2.0)
            
            Spacer()
            Text(String(format: "%.1f", datapoint.temperature) + " °C")
                .font(.title)
        }
    }
}

struct ForecastRowView_Previews: PreviewProvider {
    static var datapoints = ModelData().forecast.timeSeries
    
    static var previews: some View {
        ForecastRowView(datapoint: datapoints[0])
    }
}
