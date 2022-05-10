import Foundation

class UserRepository: ObservableObject {
    public static var resultSet: ModelDataDB?
    
    private static var documentsFolder: URL {
        do {
            return try FileManager.default.url(for: .documentDirectory, in: .userDomainMask, appropriateFor: nil, create: false)
        } catch {
            fatalError("Can't find documents directory.")
        }
    }
    
    private static var fileURL: URL {
        return documentsFolder.appendingPathComponent("weather.data")
    }
    
    
    public static func load(){
        DispatchQueue.global(qos: .background).async {
            guard let data = try? Data(contentsOf: Self.fileURL) else {return}
            guard let modelData = try? JSONDecoder().decode(ModelDataDB.self, from: data) else {return}
            
            DispatchQueue.main.async {
                self.resultSet = modelData
            }
            
        }
    }
    
    public static func save(_ modelData: ModelDataDB) {
        DispatchQueue.global(qos: .background).async {
            guard let data = try? JSONEncoder().encode(modelData) else { fatalError("Error encoding data") }
            do {
                let outfile = Self.fileURL
                try data.write(to: outfile)
            } catch {
                fatalError("Can't write to file")
            }
        }
    }
}
