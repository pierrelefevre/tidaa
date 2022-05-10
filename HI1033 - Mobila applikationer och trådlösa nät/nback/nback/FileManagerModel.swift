//
//  FileManager.swift
//  nback
//
//  Created by Oscar  Ekenlöw on 2021-11-26.
//

import Foundation

class FileManagerModel: ObservableObject {
    public static var saveState: SaveState?
    
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
    
    private static var resultFileURL: URL {
        return documentsFolder.appendingPathComponent("result.data")
    }
    
    static func saveState(_ state: SaveState) {
        DispatchQueue.global(qos: .background).async {
            
            guard let data = try? JSONEncoder().encode(state) else  { fatalError("Error encoding data") }
            do {
                let outfile = Self.resultFileURL
                try data.write(to: outfile)
            } catch {
                print("Error writing to file")
            }
        }
    }
    
    static func loadState() -> Void {
        DispatchQueue.global(qos: .background).async {
            guard let data = try? Data(contentsOf: Self.resultFileURL) else {
#if DEBUG
                DispatchQueue.main.sync {
                    
                }
#endif
                return
            }
            guard let stateInformation = try? JSONDecoder().decode(SaveState.self, from: data) else {
                fatalError("Can't load favorite data.")
            }
            DispatchQueue.main.async {
                Self.saveState = stateInformation
            }
        }
    }
}
