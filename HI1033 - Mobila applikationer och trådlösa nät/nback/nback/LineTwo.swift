//
//  LineTwo.swift
//  TicTacToeSwiftUI
//
//  Created by Jonas Wåhslén on 2021-11-18.
//

import CoreGraphics


struct LineTwo {
    
struct Segment {
    let line: CGPoint
}

static let segments = [
        Segment(
            line:    CGPoint(x: 0.65, y: 0.00)
        ),
        Segment(
            line:    CGPoint(x: 0.65, y: 1.00)
        ),
        Segment(
            line:    CGPoint(x: 0.70, y: 1.00)
        ),
        Segment(
            line:    CGPoint(x: 0.70, y: 0.00)
        ),
        Segment(
            line:    CGPoint(x: 0.65, y: 0.00)
        )
    ]
}
