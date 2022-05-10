//
//  FileManager.swift
//  nback
//
//  Created by Oscar  Ekenlöw on 2021-11-26.
//

import Foundation

class FileManagerModel: ObservableObject {
    public static var state: SaveState?
    
    private static var documentsFolder: URL {
        do {
            return try FileManager.default.url(for: .documentDirectory,
                                                  in: .userDomainMask,
                                                  appropriateFor: nil,
                                                  create: false)
        } catch {
            fatalError("Can't find documents directory!")
        }
    }
    
    private static var stateFile: URL {
        return documentsFolder.appendingPathComponent("state.json")
    }
    
    static func saveState(model: Model, courses: [CourseIdentifier]) {
        let newState = SaveState(model: model, courses: courses)
        Dispatch.DispatchQueue.global(qos: .background).async {
            
            guard let data = try? JSONEncoder().encode(newState) else  { fatalError("Error encoding data") }
            do {
                let outfile = Self.stateFile
                try data.write(to: outfile)
                print("saved")
            } catch {
                print("Error writing to file")
            }
        }
    }
    
    
    static func load(completeion: @escaping () -> Void) {
        Dispatch.DispatchQueue.global(qos: .background).async {
            guard let data = try? Data(contentsOf: Self.stateFile) else {
#if DEBUG
                Dispatch.DispatchQueue.main.sync {
                    
                }
#endif
                return
            }
            guard let state = try? JSONDecoder().decode(SaveState.self, from: data) else {
                print("Can't load history.")
                return
            }
            Dispatch.DispatchQueue.main.async {
                print("Found file")
                Self.state = state
                completeion()
            }
        }
    }
}
