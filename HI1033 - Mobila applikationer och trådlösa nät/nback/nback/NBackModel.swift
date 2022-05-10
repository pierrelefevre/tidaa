import Foundation

struct NBackModel {
    var gameIsActive = true
    var history: [NBackMove]
    var gameState: [Int]
    var n: Int
    var roundCount = 0
    var lastPos = 0
    
    init(){
        gameState = [0, 0, 0, 0, 0, 0, 0, 0, 0]
        history = []
        gameIsActive = false
        n = 3
    }
    
    mutating func resetGame(){
        gameState = [0, 0, 0, 0, 0, 0, 0, 0, 0]
        history = []
        gameIsActive = false
        roundCount = 0
    }
    
    mutating func doMove() -> NBackMove{
        var randomInt = Int.random(in: 0..<9)
        
        if(history.count > n &&  Int.random(in: 0..<10) < 3){
            randomInt = history[history.count - n].visual
        }
    
        gameState = [0, 0, 0, 0, 0, 0, 0, 0, 0]
        gameState[randomInt] = 1
        
        
        let charInt = Int.random(in: 0..<9)
        let charStart = Int(("a" as UnicodeScalar).value)
        var randomAudioLetter = Character(UnicodeScalar(charStart + charInt)!)
        
        if(history.count > n &&  Int.random(in: 0..<10) < 3){
            randomAudioLetter = Character(history[history.count - n].audio)
        }
        
        let move = NBackMove(visual: randomInt, audio: String(randomAudioLetter))
        history.append(move)
        
        lastPos = randomInt
        return move
    }
    
    func getTile(position: Int) -> Int{
        return gameState[position];
    }
}


