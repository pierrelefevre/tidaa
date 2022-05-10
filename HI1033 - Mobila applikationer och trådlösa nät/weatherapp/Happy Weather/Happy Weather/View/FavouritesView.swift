//
//  FavouritesView.swift
//  Happy Weather
//
//  Created by Pierre Le Fevre on 2021-11-13.
//

import SwiftUI

struct FavouritesView: View {
    @EnvironmentObject var modelData: ModelData
    
    var body: some View {
        NavigationView{
            List{
                if modelData.favourites.count == 0{
                    Text("Add favourites to view them here").font(.headline)
                }
                
                ForEach(modelData.favourites){city in
                    HStack{
                        Button(action: {modelData.selectCity(city)}) {
                            VStack(alignment: .leading){
                                HStack{
                                    Text(city.place)
                                        .fontWeight(.bold)
                                    if let county = city.county{
                                        Text(county)
                                    }
                                }
                                Text(city.country)
                            }
                        }.foregroundColor(.white).buttonStyle(BorderlessButtonStyle())
                        Spacer()
                        Button(action: {modelData.removeFavourite(city)}) {
                                Image(systemName: "star.slash")
                                    .foregroundColor(.yellow)
                        }.buttonStyle(BorderlessButtonStyle())
                    }
                }
            }
            .navigationBarTitle("Favourites")
        }
    }
}

struct FavouritesView_Previews: PreviewProvider {
    static var previews: some View {
        FavouritesView()
    }
}
