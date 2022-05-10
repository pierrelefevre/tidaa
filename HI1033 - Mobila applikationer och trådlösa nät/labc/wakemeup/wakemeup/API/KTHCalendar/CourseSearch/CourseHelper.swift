//
//  CourseHelper.swift
//  wakemeup
//
//  Created by Oscar  Ekenlöw on 2022-01-06.
//

import Foundation

class CourseHelper {
    func searchCourses(_ course: String, completion: @escaping (Data?, Error?) -> Void) {
        let urlString = "https://www.kth.se/api/kopps/v2/courses/search?text_pattern=\(course)"
        
        let url = URL(string: urlString.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!)!
        let task = URLSession.shared.dataTask(with: url) { data, response, error in
            DispatchQueue.main.async {
                completion(data, error)
            }
        }
        task.resume()
    }
}
