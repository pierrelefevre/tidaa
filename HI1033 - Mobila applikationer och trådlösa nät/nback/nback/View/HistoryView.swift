import SwiftUI

struct HistoryView: View {
    @EnvironmentObject var viewModel: NBackVM
    
    var body: some View {
        List{
            ForEach(viewModel.state?.results ?? [], id: \.self){ result in
                VStack{                    
                    HStack(){
                        Text("Visual: ")
                            .font(.subheadline)
                        Text(String(result.numberRightsVisual))
                            .font(.subheadline)
                            .foregroundColor(.green)
                        Text(String(result.numberWrongsVisual))
                            .font(.subheadline)
                            .foregroundColor(.red)
                        Spacer()
                    }
                    
                    HStack(){
                        Text("Audio: ")
                            .font(.subheadline)
                        Text(String(result.numberRightsAudio))
                            .font(.subheadline)
                            .foregroundColor(.green)
                        Text(String(result.numberWrongsAudio))
                            .font(.subheadline)
                            .foregroundColor(.red)
                        Spacer()
                    }
                    
                    HStack{
                        let delay = String(format: "%.1f",result.delay)
                        Text("Total Rounds: \(result.totalRounds), N: \(result.n), Delay: \(delay) s")
                            .font(.subheadline)
                        Spacer()
                    }
                    
                }
            }
        }
    }
}

struct HistoryView_Previews: PreviewProvider {
    static var previews: some View {
        HistoryView()
    }
}
