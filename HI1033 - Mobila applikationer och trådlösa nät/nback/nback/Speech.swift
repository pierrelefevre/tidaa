//
//  Speech.swift
//  TicTacToeSwiftUI
//
//  Created by Jonas Wåhslén on 2021-11-21.
//

import Foundation
import AVFoundation

class Speech{
    let synthesizer = AVSpeechSynthesizer()
    
    func speech(tile:Int){
        if(tile == 1){
            let crossVoice = AVSpeechUtterance(string: "Circle")
            synthesizer.stopSpeaking(at: AVSpeechBoundary.immediate )
            synthesizer.speak(crossVoice)
           
        }else if(tile == 2){
            let crossVoice = AVSpeechUtterance(string: "Cross")
            synthesizer.stopSpeaking(at: AVSpeechBoundary.immediate )
            synthesizer.speak(crossVoice)
        }
        DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
            print("Timer fired!")
        }
    }
    
}


