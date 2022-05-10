import SwiftUI

struct SettingsView: View {
    @EnvironmentObject var viewModel: NBackVM
    
    var body: some View {
        List{
            Picker("Update N", selection: $viewModel.settings.n) {
                Text("1 back").tag(1)
                Text("2 back").tag(2)
                Text("3 back").tag(3)
                Text("4 back").tag(4)
            }.onChange(of: viewModel.settings.n) {freq in
                viewModel.saveState()
                print(viewModel.settings.n)
            }
            Slider(value: $viewModel.settings.delay, in: 0.5...5.0, label: {
                Text("Delay")
            }, minimumValueLabel: {
                Text(String(format: "%.1f s", viewModel.settings.delay))
            }, maximumValueLabel: {
                Text("5 s")
            }, onEditingChanged:  { editing in
                viewModel.saveState()}
            )
            Picker("Number of rounds", selection: $viewModel.settings.numberOfRounds) {
                Text("5 rounds").tag(5)
                Text("10 rounds").tag(10)
                Text("15 rounds").tag(15)
                Text("20 rounds").tag(20)
                Text("575663 rounds").tag(575663)
            }.onChange(of: viewModel.settings.numberOfRounds) {freq in
                viewModel.saveState()
            }
            
            Toggle("Visual stimuli", isOn: $viewModel.settings.visual).onChange(of: viewModel.settings.visual){toggle in
                if(!viewModel.settings.audio){
                    viewModel.settings.audio = !toggle
                }
                viewModel.saveState()
            }
            
            Toggle("Audio stimuli", isOn: $viewModel.settings.audio).onChange(of: viewModel.settings.audio){toggle in
                if(!viewModel.settings.visual){
                    viewModel.settings.visual = !toggle
                }
                viewModel.saveState()
            }
        }
    }
}

struct SettingsView_Previews: PreviewProvider {
    static var previews: some View {
        SettingsView().environmentObject(NBackVM())
    }
}
