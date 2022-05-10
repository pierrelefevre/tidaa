//
//  TestView.swift
//  TicTacToeSwiftUI
//
//  Created by Jonas Wåhslén on 2021-11-18.
//

import SwiftUI

struct TestView: View {
    var body: some View {
        VStack{
            ZStack{
                BackroundView(width: 300, height: 300)
                
                Circle()
                    .stroke(Color(.red), lineWidth: 10)
                    .frame(width: 75, height: 75, alignment: .center)
                    .position(x: 250, y: 50)
                    
                Marker(marker: 1, id: 1)
                    .position(x: 150, y: 250)
                
                Marker(marker: 2, id: 2)
                    .position(x: 50, y: 50)
            }
            .frame(width: 300, height: 300, alignment: .center)
            
            
            ZStack {
                Text(/*@START_MENU_TOKEN@*/"Hello, World!"/*@END_MENU_TOKEN@*/)
                Circle()
                    .stroke(Color(.systemGray4), lineWidth: 20)
            }
            .frame(width: 200, height: 200, alignment: .center)
                
        }
        
        
        
        
      
    }
}

struct TestView_Previews: PreviewProvider {
    static var previews: some View {
        TestView()
    }
}
