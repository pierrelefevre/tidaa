import SwiftUI

@main
struct wakemeupApp: App {
    @StateObject private var viewModel = ViewModel()
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate

    var body: some Scene {
        WindowGroup {
            ContentView()
                .environmentObject(viewModel)
                .onAppear(perform: {
                    viewModel.handleOnAppear()
                })
        }
    }
}
