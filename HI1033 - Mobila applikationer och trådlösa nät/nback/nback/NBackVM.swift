import Foundation
import AVFoundation
import UIKit

class NBackVM : ObservableObject  {
    let synthesizer = AVSpeechSynthesizer()
    @Published var model = NBackModel()
    @Published var settings = Settings(n: 1, visual: true, audio: true, delay: 2.0, numberOfRounds: 10)
    var result: ResultModel
    var timer: Timer?
    var timerIsOn = false
    @Published var isCorrect = true
    var alreadyClickedAudio = false
    var alreadyClickedVisual = false
    
    var isLoaded = false
    
    var state: SaveState?
    
    @Published var markers : [aMarker] = initMarkers()
    
    func startTimer(){
        timer = Timer.scheduledTimer(timeInterval: settings.delay, target: self, selector: #selector(fireTimer), userInfo: nil, repeats: true)
    }
    
    init() {
        result = ResultModel(n: 1, delay: 1)
        state = nil
    }
    
    func startGame(){
        model.resetGame()
        model.gameIsActive = true
        if(!timerIsOn){
            timerIsOn = true
            startTimer()
        }
        result = ResultModel(n: settings.n, delay: settings.delay)
    }
    
    func stopGame(){
        speech("Game stopped")
        model.gameIsActive = false
        timer?.invalidate()
        timerIsOn = false
        
        
        result.totalRounds = model.roundCount
        state!.results.insert(result, at: 0)
        
        print("Saving")
        saveState()
        model.resetGame()
        resetMarkers()
    }
    
    func resetMarkers(){
        markers = initMarkers()
    }
    
    func aMove(){
        let move = model.doMove()
        resetMarkers()
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.3) {
            self.resetMarkers()
        }
        if(settings.visual){
            markers[move.visual].state = 1
        }
        if(settings.audio){
            speech(move.audio)
        }
    }
    
    @objc func fireTimer() {
        print("Timer fired! \(model.roundCount)")
        alreadyClickedAudio = false
        alreadyClickedVisual = false
        
        let generator = UISelectionFeedbackGenerator()
        generator.selectionChanged()

        
        if model.roundCount >= settings.numberOfRounds {
            stopGame()
        } else {
            model.roundCount += 1
            
            aMove()
            
            if !model.gameIsActive {
                timer?.invalidate()
                timerIsOn = false
            }
        }
        
    }
    
    func getTile(position: Int) -> Int{
        return model.gameState[position];
    }
    
    func checkStatus() -> ResultModel{
        return result
    }
    
    func speech(_ text: String){
        let crossVoice = AVSpeechUtterance(string: text)
        crossVoice.voice = AVSpeechSynthesisVoice(identifier: "com.apple.ttsbundle.Samantha-compact")
        crossVoice.rate = AVSpeechUtteranceDefaultSpeechRate * 1.3
        synthesizer.stopSpeaking(at: AVSpeechBoundary.immediate)
        synthesizer.speak(crossVoice)
    }
    
    func audioMatch() -> Bool {
        isCorrect = model.history[model.roundCount - 1].audio == model.history[model.roundCount - 1 - model.n].audio
        if isCorrect{
            result.numberRightsAudio += 1
        }else{
            result.numberWrongsAudio += 1
        }
        alreadyClickedAudio = true
        return isCorrect
    }
    
    func visualMatch() -> Bool {
        isCorrect = model.history[model.roundCount - 1].visual == model.history[model.roundCount - 1 - model.n].visual
        if isCorrect{
            result.numberRightsVisual += 1
        }else{
            result.numberWrongsVisual += 1
        }
        alreadyClickedVisual = true
        return isCorrect
    }
    
    func loadState() {
        FileManagerModel.loadState()
        
        print("before async")
        DispatchQueue.main.asyncAfter(deadline: .now()  + 0.5) {
            print("after async")
            if let state = FileManagerModel.saveState {
                self.state = state
                self.settings = state.settings
                self.model.n = self.settings.n
                self.result.n = self.settings.n
                self.result.delay = self.settings.delay
                print("Loaded settings from file")
            } else {
                self.state = SaveState(settings: self.settings, results: [self.result])
                FileManagerModel.saveState(self.state!)
            }
            self.isLoaded = true
        }
    }
    
    func saveState() {
        state!.settings = settings
        FileManagerModel.saveState(self.state!)
    }
    
}

func initMarkers() -> [aMarker] {
    return [
        aMarker(id: 0, state: 0, x: 0, y: 0),
        aMarker(id: 1, state: 0, x: 1, y: 0),
        aMarker(id: 2, state: 0, x: 2, y: 0),
        aMarker(id: 3, state: 0, x: 0, y: 1),
        aMarker(id: 4, state: 0, x: 1, y: 1),
        aMarker(id: 5, state: 0, x: 2, y: 1),
        aMarker(id: 6, state: 0, x: 0, y: 2),
        aMarker(id: 7, state: 0, x: 1, y: 2),
        aMarker(id: 8, state: 0, x: 2, y: 2)
    ]
}

struct aMarker: Hashable, Codable, Identifiable {
    var id: Int
    var state : Int
    var x: Int
    var y: Int
}
