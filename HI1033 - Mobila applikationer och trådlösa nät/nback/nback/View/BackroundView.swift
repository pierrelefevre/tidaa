//
//  BackroundView.swift
//  TicTacToeSwiftUI
//
//  Created by Jonas Wåhslén on 2021-11-18.
//

import SwiftUI

struct BackroundView: View {
    let width : CGFloat
    let height : CGFloat
    @Environment(\.colorScheme) var colorScheme
    @EnvironmentObject var viewModel : NBackVM
        
    var body: some View {
        ZStack{
            Rectangle()
                .fill(Color(colorScheme == .dark ? .white : .black))
                .cornerRadius(20)
                .frame(width: 10, height: height, alignment: .center)
                .position(x: width/3, y: height/2 )
            
            Rectangle()
                .fill(Color(colorScheme == .dark ? .white : .black))
                .cornerRadius(20)
                .frame(width: 10, height: height, alignment: .center)
                .position(x: width*2/3, y: height/2 )
            
            Rectangle()
                .fill(Color(colorScheme == .dark ? .white : .black))
                .cornerRadius(20)
                .frame(width: width, height: 10, alignment: .center)
                .position(x: width/2, y: height/3 )
            
            Rectangle()
                .fill(Color(colorScheme == .dark ? .white : .black))
                .cornerRadius(20)
                .frame(width: width, height: 10, alignment: .center)
                .position(x: width/2, y: height*2/3)
        }.brightness((viewModel.model.gameIsActive ? 0 : -0.8))
            .animation(.easeInOut(duration: 0.2), value: viewModel.model.gameIsActive ? 0 : -0.8)
        
    }
}

struct BackroundView_Previews: PreviewProvider {
    static var previews: some View {
        BackroundView(width: 300, height: 300)
    }
}
