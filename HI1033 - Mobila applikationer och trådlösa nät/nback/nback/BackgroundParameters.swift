//
//  BackgroundParameters.swift
//  TicTacToeSwiftUI
//
//  Created by Jonas Wåhslén on 2021-11-18.
//

import CoreGraphics

private struct Lines: Identifiable {
    let name: String
    let Line: [CGPoint]
    var id: String {name}
    
}

private let BackgroundParameters : [Lines] = [
    Lines(name: "first", Line: [CGPoint(x: 0.3, y: 0.00),CGPoint(x: 0.3, y: 1.00), CGPoint(x: 0.35, y: 1.00), CGPoint(x: 0.35, y: 0.00)]),
    Lines(name: "second", Line: [CGPoint(x: 0.65, y: 0.00),CGPoint(x: 0.65, y: 1.00), CGPoint(x: 0.70, y: 1.00), CGPoint(x: 0.65, y: 0.00)]),
    Lines(name: "third", Line: [CGPoint(x: 0.0, y: 0.3),CGPoint(x: 0.0, y: 0.35), CGPoint(x: 1.0, y: 0.35), CGPoint(x: 1.0 , y: 0.30)]),
    Lines(name: "fourth", Line: [CGPoint(x: 0.0, y: 0.65),CGPoint(x: 0.0, y: 0.70), CGPoint(x: 1.0, y: 0.70), CGPoint(x: 1.0, y: 0.65)])
]



