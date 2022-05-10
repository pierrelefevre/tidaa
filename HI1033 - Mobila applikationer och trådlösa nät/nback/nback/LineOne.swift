//
//  LineOne.swift
//  TicTacToeSwiftUI
//
//  Created by Jonas Wåhslén on 2021-11-18.
//

import CoreGraphics

struct LineOne {
    
    struct Segment {
        let line: CGPoint
    }

    static let segments = [
            Segment(
                line:    CGPoint(x: 0.3, y: 0.00)
            ),
            Segment(
                line:    CGPoint(x: 0.3, y: 1.00)
            ),
            Segment(
                line:    CGPoint(x: 0.35, y: 1.00)
            ),
            Segment(
                line:    CGPoint(x: 0.35, y: 0.00)
            ),
            Segment(
                line:    CGPoint(x: 0.3, y: 0.00)
            )
        ]
}
