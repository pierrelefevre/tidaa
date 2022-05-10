//
//  LineFour.swift
//  TicTacToeSwiftUI
//
//  Created by Jonas Wåhslén on 2021-11-18.
//

import CoreGraphics


struct LineFour {
    
struct Segment {
    let line: CGPoint
}

static let segments = [
        Segment(
            line:    CGPoint(x: 0.00, y: 0.65)
        ),
        Segment(
            line:    CGPoint(x: 0.00, y: 0.70)
        ),
        Segment(
            line:    CGPoint(x: 1.00, y: 0.70)
        ),
        Segment(
            line:    CGPoint(x: 1.00, y: 0.65)
        ),
        Segment(
            line:    CGPoint(x: 0.00, y: 0.65)
        )
    ]
}
