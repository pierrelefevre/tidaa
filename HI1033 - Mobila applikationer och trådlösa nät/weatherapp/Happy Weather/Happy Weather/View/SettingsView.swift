//
//  SettingsView.swift
//  Happy Weather
//
//  Created by Pierre Le Fevre on 2021-11-16.
//

import SwiftUI

struct SettingsView: View {
    @EnvironmentObject var modelData: ModelData
    
    var body: some View {
        NavigationView{
            List{
                Picker("Update frequency", selection: $modelData.updateFrequency) {
                    Text("1 minute").tag(1)
                    Text("10 minutes").tag(10)
                    Text("30 minutes").tag(30)
                    Text("60 minutes").tag(60)
                }.onChange(of: modelData.updateFrequency) {freq in
                    modelData.save()
                }
            }
            .navigationBarTitle("Settings")
        }
    }
}

struct SettingsView_Previews: PreviewProvider {
    static var previews: some View {
        SettingsView().environmentObject(ModelData())
    }
}
