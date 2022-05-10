import SwiftUI

struct RouteView: View {
    @EnvironmentObject var viewModel : ViewModel
    @Environment(\.colorScheme) var colorScheme
    
    var body: some View {
        NavigationView(){
            VStack(alignment: .leading){
                VStack(alignment: .leading){
                    if(viewModel.apiModel.error != ""){
                        Text(viewModel.apiModel.error).foregroundColor(.red)
                    }
                    if(viewModel.apiModel.route != nil){
                        List{
                            // -----
                            Section{
                                HStack(alignment: .center){
                                    
                                    VStack(alignment: .leading){
                                        Text("\(viewModel.wakeUpTime)").fontWeight(.bold)
                                    }
                                    VStack(alignment: .leading){
                                        Text("Wake up")
                                        
                                    }
                                }
                            }header: {
                                HStack{
                                    Image(systemName: "alarm")
                                    Text("Wake up").padding(.leading, 5)
                                }
                            }.listRowBackground(colorScheme == .dark ? Color.black : Color.white)
                            
                            
                            // -----
                            ForEach(viewModel.apiModel.route?.Trip[0].LegList.Leg ?? [], id: \.self){leg in
                                ListItem(legs: viewModel.apiModel.route?.Trip[0].LegList.Leg ?? [], leg: leg)
                            }
                        }.listStyle(.grouped)
                    }else{
                        HStack(alignment: .center){
                            Spacer()
                            VStack(alignment: .center){
                                Spacer()
                                HStack{
                                    Image(systemName: "arrow.triangle.swap")
                                        .font(.system(size: 100))
                                }
                                .padding(.bottom, 15)
                                if(!viewModel.loadingRoute){
                                    Text("No route")
                                        .font(.headline)
                                }
                                else{
                                    Text("Loading route...")
                                        .font(.headline)
                                }
                                Spacer()
                            }
                            Spacer()
                        }.foregroundColor(.gray)
                    }
                }
                Spacer()
                HStack(alignment: .center) {
                    if(viewModel.alarmPolling) {
                        Button {
                            viewModel.stopAlarm()
                        }label:{
                            Image(systemName: "xmark.circle").foregroundColor(.red)
                            Text("Stop alarm").foregroundColor(.red)
                        }
                    } else {
                        Button {
                            viewModel.setAlarm()
                        }label:{
                            Image(systemName: "alarm")
                            Text("Set alarm")
                        }
                    }
                }
                
                
                Spacer()
                
                HStack{
                    Text("Time before departure:")
                    Picker("Minutes before departure", selection: $viewModel.model.minutesBeforeDeparture) {
                        Text("5 min").tag(5)
                        Text("10 min").tag(10)
                        Text("15 min").tag(15)
                        Text("30 min").tag(30)
                        Text("45 min").tag(45)
                        Text("60 min").tag(60)
                        Text("90 min").tag(90)
                    }
                }
                HStack{
                    Text("Time before arrival:")
                    Picker("Minutes before arrival", selection: $viewModel.model.minutesBeforeArrival) {
                        Text("5 min").tag(5)
                        Text("10 min").tag(10)
                        Text("15 min").tag(15)
                        Text("30 min").tag(30)
                        Text("45 min").tag(45)
                        Text("60 min").tag(60)
                        Text("90 min").tag(90)
                    }
                }
                .navigationBarTitle("Your trip tomorrow")
                .toolbar{
                    NavigationLink {
                        SettingsView().navigationTitle("Settings")
                    } label: {
                        Image(systemName: "gear")
                    }
                }
            }.padding(15)
        }
    }
}


struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        RouteView()
    }
}
