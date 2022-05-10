import SwiftUI

struct TextInfo: View {
    @EnvironmentObject var viewModel : NBackVM
    
    var body: some View {
        VStack{
            let status = $viewModel.result.wrappedValue
            
            HStack{
                Text("Visual: ")
                    .font(.subheadline)
                Text(String(status.numberRightsVisual))
                    .font(.subheadline)
                    .foregroundColor(.green)
                Text(String(status.numberWrongsVisual))
                    .font(.subheadline)
                    .foregroundColor(.red)
                Spacer()
            }
            
            HStack{
                Text("Audio: ")
                    .font(.subheadline)
                Text(String(status.numberRightsAudio))
                    .font(.subheadline)
                    .foregroundColor(.green)
                Text(String(status.numberWrongsAudio))
                    .font(.subheadline)
                    .foregroundColor(.red)
                Spacer()
            }
            HStack{
                let delay = String(format: "%.1f", status.delay)
                Text("N-Back: \(status.n), Delay: \(delay) s")
                    .font(.subheadline)
                Spacer()
            }
            
            HStack{
                Text("Total Rounds: \(viewModel.model.roundCount)/\(viewModel.settings.numberOfRounds)")
                    .font(.subheadline)
                Spacer()
            }

        }.opacity((!viewModel.model.gameIsActive ? 1 : 0.2))
            .animation(.easeInOut(duration: 0.2), value: !viewModel.model.gameIsActive ? 1 : 0.2)
            .transition(.opacity)
    }
}


struct TextInfo_Previews: PreviewProvider {
    static var previews: some View {
        TextInfo()
            .environmentObject(NBackVM())
    }
}
