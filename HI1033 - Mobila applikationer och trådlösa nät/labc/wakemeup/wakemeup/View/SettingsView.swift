import SwiftUI

struct SettingsView: View {
    @EnvironmentObject var viewModel : ViewModel
    
    var body: some View {
        VStack(alignment: .leading){
            
            HStack {
                Text("Default origin: ")
                TextField("Origin", text: $viewModel.origin)
                    .onChange(of: viewModel.origin) {
                        viewModel.origin = $0
                    }
                
            }.padding(.vertical, 15)
            
            HStack {
                Text("Default destination:")
                TextField("Destination", text: $viewModel.destination)
                    .onChange(of: viewModel.destination) {
                        viewModel.destination = $0
                    }
            }.padding(.bottom, 15)
            
            
            DatePicker("Last wake up time:", selection: $viewModel.model.lastWakeUp, displayedComponents: [.hourAndMinute]).padding(.bottom, 15)
            
            
            Spacer()
        }.padding(.horizontal, 15)
    }
}

struct SettingsView_Previews: PreviewProvider {
    static var previews: some View {
        SettingsView()
    }
}
