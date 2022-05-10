//
//  CitiesView.swift
//  Happy Weather
//
//  Created by Pierre Le Fevre on 2021-11-12.
//

import SwiftUI

struct CitiesView: View {
    @EnvironmentObject var modelData: ModelData
    @State var searchString: String = ""
    
    var body: some View {
        NavigationView{
            List{
                HStack{
                    Image(systemName: "magnifyingglass")
                    TextField("Search", text: $searchString)
                        .onChange(of: searchString) {
                            modelData.loadCities($0)
                        }
                }
                
                ForEach(modelData.cities){city in
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
                        if modelData.favourites.first(where:  { $0.id == city.id }) != nil{
                            Button(action: {modelData.removeFavourite(city)}) {
                                Image(systemName: "star.fill")
                                    .foregroundColor(.yellow)
                            }.buttonStyle(BorderlessButtonStyle())
                        }else{
                            Button(action: {modelData.addFavourite(city)}) {
                                Image(systemName: "star")
                                    .foregroundColor(.white)
                            }.buttonStyle(BorderlessButtonStyle())
                        }
                    }
                }
            }
            .navigationBarTitle("Search")
        }
    }
}

struct CitiesView_Previews: PreviewProvider {
    static var previews: some View {
        CitiesView()
    }
}
