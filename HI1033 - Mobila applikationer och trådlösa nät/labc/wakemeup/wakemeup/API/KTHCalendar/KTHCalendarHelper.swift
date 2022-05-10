//
//  KTHCalendarHelper.swift
//  wakemeup
//
//  Created by Oscar  Ekenlöw on 2022-01-03.
//

import Foundation

class KTHCalendarHelper {
    func loadCourseCalendar(_ courseName: String, completion: @escaping (Data?, Error?) -> Void) {
        let url = URL(string: "https://www.kth.se/api/schema/v2/course/\(courseName)?endTime=2022-05-05")!
        
        let task = URLSession.shared.dataTask(with: url) { data, response, error in
            DispatchQueue.main.async {
                completion(data, error)
            }
        }
        task.resume()
    }
    
    func findFirst(calendars: [KTHCalendarResult]) -> KTHCalendarEntry? {
        var earliestDate: Date
        var earliestEntry: KTHCalendarEntry?
        
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
        
        earliestDate = dateFormatter.date(from: "2001-01-01 01:01:01")!
        
        var setFirstDate = true
        for calendar in calendars {
            for entry in calendar.entries {
                
                let entryDate = dateFormatter.date(from: entry.start)!
                if setFirstDate {
                    earliestDate = entryDate
                    setFirstDate = false
                }
                
                if(entryDate <= earliestDate){
                    earliestDate = entryDate
                    earliestEntry = entry
                }
            }
        }
        
        if let entry = earliestEntry {
            return entry
        }
       
        // Currently loads all calendars too slowly
        return nil
    }
    
    func loadCalendar(courses: [CourseIdentifier], debug: Bool?, complete: @escaping ([KTHCalendarResult]?) -> Void) -> Void {
        var error = ""
        
        if(debug ?? false){
            let calendar: KTHCalendarResult? = ViewModel.loadJSON("KTHCalendarExample.json")
            error = "Warning: Loaded mock JSON of Calendar"
            
            
            if calendar != nil {
                var calendars = [KTHCalendarResult]()
                calendars.append(calendar!)
                complete(calendars)
                return
            }
            complete(nil)
        }
        
        if(!ViewModel.connected){
            print("Network error - Calendar")
            error = "Error: Network error when loading calendar"
            complete(nil)
        }
        
        var calendars = [KTHCalendarResult]()
        let group = DispatchGroup()
        for course in courses {
            group.enter()
            loadCourseCalendar(course.code) { data, error in
                group.leave()
                if let result = try? JSONDecoder().decode(KTHCalendarResult.self, from: data!) {
                    calendars.append(result)
                }
            }
        }
        
        group.notify(queue: .main) {
            complete(calendars)
            print("--- Done fetching calendars ---")
        }
        return
    }
}
