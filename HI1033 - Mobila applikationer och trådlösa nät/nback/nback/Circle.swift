//
//  Circle.swift
//  TicTacToeSwiftUI
//
//  Created by Jonas Wåhslén on 2021-11-18.
//

import SwiftUI

struct Circle: View {
    var progress: Double
       
       var body: some View {
           let progressText = String(format: "%.0f%%", progress * 100)
           let purpleAngularGradient = AngularGradient(
               gradient: Gradient(colors: [
                   Color(red: 200/255, green: 168/255, blue: 240/255),
                   Color(red: 71/255, green: 33/255, blue: 158/255)
               ]),
               center: .center,
               startAngle: .degrees(0),
               endAngle: .degrees(360.0 * progress))
           
           ZStack {
               Circle(progress: 90)
                   .stroke(Color(.systemGray4), lineWidth: 20)
               Circle(progress: 90)
                   .trim(from: 0, to: CGFloat(self.progress))
                   .stroke(
                       purpleAngularGradient,
                       style: StrokeStyle(lineWidth: 20, lineCap: .round))
                   .rotationEffect(Angle(degrees: -90))
                   .overlay(
                       Text(progressText)
                           .font(.system(size: 36, weight: .bold, design:.rounded))
                           .foregroundColor(Color(.systemGray))
                   )
           }
       }
}

struct Circle_Previews: PreviewProvider {
    static var previews: some View {
        Circle()
    }
}
