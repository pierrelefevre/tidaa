//
//  TicTacToeBackground.swift
//  TicTacToeSwiftUI
//
//  Created by Jonas Wåhslén on 2021-11-18.
//

import SwiftUI

struct TicTacToeBackground: View {

    var body: some View {
        GeometryReader { geometry in
            let width: CGFloat = min(geometry.size.width, geometry.size.height)
            let height = width
            
            Circle()
                .stroke(Color(.systemGray4), lineWidth: 20)
                
            
                    Path { path in
                        path.move(
                            to: CGPoint(
                                x: width * 0.95,
                                y: height * 0.20
                            )
                        )

                        LineOne.segments.forEach { segment in
                            path.addLine(
                                to: CGPoint(
                                    x: width * segment.line.x,
                                    y: height * segment.line.y
                                )
                            )
                        }
                    }
                    .fill(.black)
            
            Path { path in
                path.move(
                    to: CGPoint(
                        x: width * 0.95,
                        y: height * 0.20
                    )
                )

                LineTwo.segments.forEach { segment in
                    path.addLine(
                        to: CGPoint(
                            x: width * segment.line.x,
                            y: height * segment.line.y
                        )
                    )
                }
            }
            .fill(.black)
            
            Path { path in
                path.move(
                    to: CGPoint(
                        x: width * 0.95,
                        y: height * 0.20
                    )
                )

                LineThree.segments.forEach { segment in
                    path.addLine(
                        to: CGPoint(
                            x: width * segment.line.x,
                            y: height * segment.line.y
                        )
                    )
                }
            }
            .fill(.black)
            
            Path { path in
                path.move(
                    to: CGPoint(
                        x: width * 0.95,
                        y: height * 0.20
                    )
                )

                LineFour.segments.forEach { segment in
                    path.addLine(
                        to: CGPoint(
                            x: width * segment.line.x,
                            y: height * segment.line.y
                        )
                    )
                }
            }
            .fill(.black)
            
        }
        .padding()

    }
}


func draw(){
    
    
}

struct TicTacToeBackground_Previews: PreviewProvider {
    static var previews: some View {
        TicTacToeBackground()
.previewInterfaceOrientation(.portrait)
    }
}
