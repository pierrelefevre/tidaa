//
//  KTHCalendarEntry.swift
//  wakemeup
//
//  Created by Pierre Le Fevre on 2021-12-30.
//

import Foundation

struct KTHCalendarEntry: Hashable, Codable{
    var url: String
    var start: String
    var end: String
    var title: String
    var info: String?
    var type: String
    var type_name: KTHCalendarTypeName
    var group: String
    var locations: [KTHCalendarLocation]
    
}
