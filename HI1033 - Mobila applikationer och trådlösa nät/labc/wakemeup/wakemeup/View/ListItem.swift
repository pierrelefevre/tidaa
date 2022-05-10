//
//  ListItem.swift
//  wakemeup
//
//  Created by Pierre Le Fevre on 2022-01-06.
//

import SwiftUI

struct ListItem: View {
    @EnvironmentObject var viewModel : ViewModel
    @Environment(\.colorScheme) var colorScheme

    let legs: [Leg]
    let leg: Leg
    var sectionName: String
    
    init(legs: [Leg], leg: Leg){
        self.legs = legs
        self.leg = leg
        self.sectionName = leg.type
        if !leg.name.isEmpty {
            sectionName = leg.name
        }
    }
    
    var body: some View {
        Section{
            HStack(alignment: .center){
                
                VStack(alignment: .leading){
                    let originTime = leg.Origin.time.components(separatedBy: ":")
                    Text("\(originTime[0]):\(originTime[1])").fontWeight(.bold)
                    
                    let destinationTime = leg.Destination.time.components(separatedBy: ":")
                    Text("\(destinationTime[0]):\(destinationTime[1])").fontWeight(.bold)
                    
                }
                VStack(alignment: .leading){
                    if legs.firstIndex(of: leg) == 0{
                        Text("Depart from home").fontWeight(.bold)
                    }else{
                        Text("\(leg.Origin.name.components(separatedBy: "(")[0])")
                    }
                    
                    
                    if leg != legs.last{
                        Text("\(leg.Destination.name.components(separatedBy: "(")[0])")
                    }else{
                        Text("Arrive at \(viewModel.earliestClass?.classroom ?? "")").fontWeight(.bold)
                    }
                }
            }
        }header: {
            HStack{
                if let category = leg.transportCategory{
                    switch category{
                    case "JLT":
                        Image(systemName: "train.side.front.car")
                    case "BLT":
                        Image(systemName: "bus.fill")
                    case "ULT":
                        Image(systemName: "tram.fill.tunnel")
                    default:
                        Image(systemName: "questionmark")
                    }
                }else{
                    Image(systemName: "figure.walk")
                }
                Text(sectionName).padding(.leading, 5)
                
                
            }
        }.listRowBackground(colorScheme == .dark ? Color.black : Color.white)

    }
}

