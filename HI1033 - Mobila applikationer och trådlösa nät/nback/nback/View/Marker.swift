//
//  Marker.swift
//  TicTacToeSwiftUI
//
//  Created by Jonas Wåhslén on 2021-11-18.
//

import SwiftUI

struct Marker: View {
    @EnvironmentObject var theViewModel : NBackVM
    @Environment(\.colorScheme) var colorScheme
    
    var marker : Int
    var id : Int
    
    var body: some View {
        HStack{
            if(marker == 1){
                Rectangle()
                    .fill(Color(.blue))
                    .cornerRadius(10)
                    .frame(width: 70, height: 70, alignment: .center)
            }else{
                Rectangle().fill(.background)
            }
        }
        .frame(width: 75, height: 75, alignment: .center)
    }
}

struct Marker_Previews: PreviewProvider {
    static var previews: some View {
        Marker(marker: 2, id: 1)
            .environmentObject(NBackVM())
    }
}
