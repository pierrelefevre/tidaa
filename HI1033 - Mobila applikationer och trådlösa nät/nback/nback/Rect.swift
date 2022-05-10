//
//  Rect.swift
//  TicTacToeSwiftUI
//
//  Created by Jonas Wåhslén on 2021-11-18.
//

import SwiftUI

struct Rect: View {
    @State private var open = true
    
    var body: some View {
        VStack {
                    Circle()
                        .scale(self.open ? 0.5 : 1.0)
                        .fill(self.open ? Color(.green) : .red)
                        .frame(width: 200, height: 200)
                        
                        .onTapGesture{
                            self.open.toggle()
                        }

                    // Slow down the animation to see the transition
                    Circle()
                        .scale(self.open ? 0.5 : 1.0)
                        .fill(self.open ? Color(.green) : .red)
                        .frame(width: 200, height: 200)
                
                        .onTapGesture{
                            self.open.toggle()
                        }

                    Path() { path in
                        path.addEllipse(in: CGRect(x: 0, y: 0, width: 200, height: 200))
                    }
                    .scale(self.open ? 0.5 : 1.0)
                    .fill(self.open ? Color(.green) : .red)
                    .frame(width: 200, height: 200)
                    
                    .onTapGesture{
                        self.open.toggle()
                    }

                    Spacer()
                }
            }
    }


struct Rect_Previews: PreviewProvider {
    static var previews: some View {
        Rect()
    }
}
