import SwiftUI

// Our custom view modifier to track rotation and
// call our action
struct DeviceRotationViewModifier: ViewModifier {
    let action: (UIDeviceOrientation) -> Void
    
    func body(content: Content) -> some View {
        content
            .onReceive(NotificationCenter.default.publisher(for: UIDevice.orientationDidChangeNotification)) { _ in
                action(UIDevice.current.orientation)
            }
    }
}

// A View wrapper to make the modifier easier to use
extension View {
    func onRotate(perform action: @escaping (UIDeviceOrientation) -> Void) -> some View {
        self.modifier(DeviceRotationViewModifier(action: action))
    }
}

struct ContentView: View {
    @EnvironmentObject var viewModel : NBackVM
    @State private var orientation = UIDeviceOrientation.portrait
    @State private var disabled = true
    
    @State private  var visualMatch = false
    @State private  var audioMatch = false
    
    var body: some View {
        NavigationView {
            VStack {
                if orientation.isLandscape {
                    HStack {
                        VStack {
                            Button {
                                viewModel.startGame()
                            } label: {
                                Image(systemName: "play.circle")
                                Text("Start")
                            }
                            .padding()
                            .disabled(viewModel.model.gameIsActive)
                            
                            Button {
                                visualMatch = viewModel.visualMatch()
                                print("Visual \(visualMatch)")
                            } label: {
                                Image(systemName: "eye")
                                Text("Visual")
                            }
                            .padding()
                            .disabled(viewModel.model.roundCount - 1 < viewModel.model.n || !viewModel.model.gameIsActive || viewModel.alreadyClickedVisual || !viewModel.settings.visual)
                            .background(viewModel.alreadyClickedVisual ? (!visualMatch ? .red : .green) : .clear)
                        }
                        
                        
                        VStack {
                            Spacer()

                            CircularProgressView(progress: Double(viewModel.model.roundCount)/Double(viewModel.settings.numberOfRounds)).frame(width: 100, height: 100).opacity((viewModel.model.gameIsActive ? 1 : 0.2))
                                .animation(.easeInOut(duration: 0.2), value: viewModel.model.gameIsActive ? 1 : 0.2)
                                .transition(.opacity)
                            
                            Spacer()
                            
                            TextInfo()
                            
                            Spacer()

                        }.padding(20)
                        
                        BoardView()
                        
                        VStack {
                            Button {
                                viewModel.stopGame()
                            } label: {
                                VStack {
                                    Image(systemName: "stop.circle")
                                    Text("Stop")
                                }
                            }
                            .padding()
                            .disabled(!viewModel.model.gameIsActive)
                            
                            Button {
                                audioMatch = viewModel.audioMatch()
                                print("Audio \(audioMatch)")
                            } label: {
                                VStack {
                                    Image(systemName: "waveform")
                                    Text("Audio")
                                }
                            }
                            .padding()
                            .disabled(viewModel.model.roundCount - 1 < viewModel.model.n || !viewModel.model.gameIsActive || viewModel.alreadyClickedAudio || !viewModel.settings.audio)
                            .background(viewModel.alreadyClickedAudio ? (!audioMatch ? .red : .green) : .clear)
                        }
                    }
                } else {
                    VStack {
                        BoardView()
                        HStack {
                            Button {
                                viewModel.startGame()
                            } label: {
                                VStack {
                                    Image(systemName: "play.circle")
                                    Text("Start")
                                }
                            }
                            .padding()
                            .disabled(viewModel.model.gameIsActive)
                            
                            Button {
                                viewModel.stopGame()
                            } label: {
                                VStack {
                                    Image(systemName: "stop.circle")
                                    Text("Stop")
                                }
                            }
                            .padding()
                            .disabled(!viewModel.model.gameIsActive)
                            
                        }
                        
                        HStack{
                            CircularProgressView(progress: Double(viewModel.model.roundCount)/Double(viewModel.settings.numberOfRounds)).frame(width: 100, height: 100).opacity((viewModel.model.gameIsActive ? 1 : 0.2))
                                .animation(.easeInOut(duration: 0.2), value: viewModel.model.gameIsActive ? 1 : 0.2)
                                .transition(.opacity)
                            
                            Spacer(minLength: 50)
                            
                            TextInfo()
                            
                        }.padding(20)
                        
                        HStack{
                            Button {
                                visualMatch = viewModel.visualMatch()
                                print("Visual \(visualMatch)")
                            } label: {
                                VStack{
                                    Image(systemName: "eye")
                                    Text("Visual")
                                }
                            }
                            .padding()
                            .disabled(viewModel.model.roundCount - 1 < viewModel.model.n || !viewModel.model.gameIsActive || viewModel.alreadyClickedVisual || !viewModel.settings.visual)
                            .background(viewModel.alreadyClickedVisual ? (!visualMatch ? .red : .green) : .clear)
                            
                            
                            Button {
                                audioMatch = viewModel.audioMatch()
                                print("Audio \(audioMatch)")
                            } label: {
                                VStack {
                                    Image(systemName: "waveform")
                                    Text("Audio")
                                }
                                
                            }
                            .padding()
                            .disabled(viewModel.model.roundCount - 1 < viewModel.model.n || !viewModel.model.gameIsActive || viewModel.alreadyClickedAudio || !viewModel.settings.audio)
                            .background(viewModel.alreadyClickedAudio ? (!audioMatch ? .red : .green) : .clear)
                        }
                    }
                }
            }
            .toolbar {
                HStack{
                    NavigationLink {
                        SettingsView().navigationTitle("Settings")
                    } label: {
                        Image(systemName: "gear")
                    }.disabled(viewModel.model.gameIsActive)
                    NavigationLink {
                        HistoryView().navigationTitle("History")
                    } label: {
                        Image(systemName: "list.bullet")
                    }.disabled(viewModel.model.gameIsActive)
                    
                }
            }
            .navigationTitle("N-Back").navigationBarTitleDisplayMode(.inline)
            .onRotate { newOrientation in
                let lastOrientation = orientation
                if newOrientation.isFlat || newOrientation == UIDeviceOrientation.portraitUpsideDown {
                    orientation = lastOrientation
                }
                else {
                    orientation = newOrientation
                }
            }
            .onAppear(perform: {
                viewModel.loadState()
            })
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
            .environmentObject(NBackVM())
    }
}
