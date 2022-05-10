//
//  SearchResult.swift
//  wakemeup
//
//  Created by Oscar  Ekenlöw on 2022-01-06.
//

import Foundation

struct SearchResult: Codable, Hashable {
    var searchHits: [SearchedCourse]
}
