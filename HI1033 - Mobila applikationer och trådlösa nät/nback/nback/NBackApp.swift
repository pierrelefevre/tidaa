import SwiftUI

@main
struct NBackApp: App {
    @StateObject private var viewModel = NBackVM()
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                .environmentObject(viewModel)
        }
    }
}
