//
//  LineThree.swift
//  TicTacToeSwiftUI
//
//  Created by Jonas Wåhslén on 2021-11-18.
//

import CoreGraphics


struct LineThree {
    
struct Segment {
    let line: CGPoint
}

static let segments = [
        Segment(
            line:    CGPoint(x: 0.00, y: 0.30)
        ),
        Segment(
            line:    CGPoint(x: 0.00, y: 0.35)
        ),
        Segment(
            line:    CGPoint(x: 1.00, y: 0.35)
        ),
        Segment(
            line:    CGPoint(x: 1.00, y: 0.30)
        ),
        Segment(
            line:    CGPoint(x: 0.00, y: 0.30)
        )
    ]
}

