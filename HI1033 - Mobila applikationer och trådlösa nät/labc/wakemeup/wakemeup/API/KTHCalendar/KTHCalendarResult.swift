//
//  KTHCalendarResult.swift
//  wakemeup
//
//  Created by Pierre Le Fevre on 2021-12-22.
//

import Foundation

struct KTHCalendarResult: Hashable, Codable{
    var url: String
    var entries: [KTHCalendarEntry]
}
