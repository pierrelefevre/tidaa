//
//  CalendarView.swift
//  wakemeup
//
//  Created by Oscar  Ekenlöw on 2022-01-06.
//

import SwiftUI

struct CalendarView: View {
    @EnvironmentObject var viewModel: ViewModel
    
    var body: some View {
        NavigationView(){
            List() {
                ForEach(Array(viewModel.model.courses.enumerated()), id: \.offset){offset, item in
                    CalendarItem(index: offset)
                }
            }
            .navigationBarTitle("Your calendar")
            .toolbar{
                Button{
                    viewModel.getCalendar()
                }label:{
                    Image(systemName: "arrow.clockwise")
                }
            }
        }
    }
}
