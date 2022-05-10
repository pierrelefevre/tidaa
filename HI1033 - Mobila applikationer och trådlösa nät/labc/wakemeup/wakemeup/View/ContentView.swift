import SwiftUI

struct ContentView: View {
    @EnvironmentObject var viewModel : ViewModel
    @State private var selection = 1

    var body: some View {
        TabView(selection:$selection) {
            CourseView()
                .tabItem({
                    Label("Courses", systemImage: "magnifyingglass")
                })
                .tag(0)
            
            RouteView().onAppear(perform: {viewModel.handleOnAppear()})
                .tabItem({
                    Label("Route", systemImage: "figure.walk")
                })
                .tag(1)
            
            CalendarView()
                .tabItem({
                    Label("Calendar", systemImage: "calendar")
                })
                .tag(2)
        }
        
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
