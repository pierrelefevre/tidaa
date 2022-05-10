//
//  CalendarItem.swift
//  Wake me up
//
//  Created by Pierre Le Fevre on 2022-01-10.
//

import SwiftUI

struct CalendarItem: View {
    @EnvironmentObject var viewModel: ViewModel
    @State var index: Int
    @State var opacity: Double = 0
    
    init(index: Int){
        self.index = index
    }
    
    func loadStartTime() -> String{
        var startTime: String
        startTime = viewModel.model.courses[index].startTime
        _ = startTime.popLast()
        _ = startTime.popLast()
        _ = startTime.popLast()
        return startTime
    }
    
    func loadEndTime() -> String{
        var endTimeComponents: [String]
        
        endTimeComponents = viewModel.model.courses[index].endTime.components(separatedBy: " ")
        _ = endTimeComponents[1].popLast()
        _ = endTimeComponents[1].popLast()
        _ = endTimeComponents[1].popLast()
        return endTimeComponents[1]
    }
    
    var body: some View {
        Section{
            VStack(alignment: .leading){
                HStack{
                    Text(viewModel.model.courses[index].code).fontWeight(.bold)
                    Spacer()
                    Text("\(self.loadStartTime()) - \(self.loadEndTime())")
                }
                
                Text(viewModel.model.courses[index].name).font(.subheadline)
                if(viewModel.model.courses[index].info != "No additional information given."){
                    Text(viewModel.model.courses[index].info).font(.subheadline)
                }
                
                HStack(alignment: .center){
                    Text("\(viewModel.model.courses[index].classroom), \(viewModel.model.courses[index].type)")
                    Toggle("", isOn: $viewModel.model.courses[index].active)
                        .onChange(of: viewModel.model.courses[index].active, perform: { value in
                            viewModel.saveCourses()
                            withAnimation(.easeInOut(duration: 0.2), {
                                self.opacity = viewModel.model.courses[index].active ? 1.0 : 0.4
                            })
                        })
                }
            }
            .foregroundColor(.primary)
            .padding(.vertical, 10)
        }.opacity(opacity).onAppear{
            opacity = viewModel.model.courses[index].active ? 1.0 : 0.4
        }
    }
}
