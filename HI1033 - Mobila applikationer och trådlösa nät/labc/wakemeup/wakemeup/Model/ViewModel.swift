import Foundation
import Network
import AVFoundation
import UserNotifications
import MediaPlayer


class ViewModel: ObservableObject{
    @Published var model: Model
    @Published var origin: String = "740021729"
    @Published var destination: String = "T2"
    @Published var coursesRaw: String = ""
    
    @Published var courses: [CourseIdentifier]
    @Published var locationManager = LocationManager()
    
    @Published var apiModel: APIModel
    
    @Published var searchResults: [CourseInfo]
    @Published var alarmPolling: Bool
    @Published var loadingRoute: Bool
    
    @Published var wakeUpTime: String
    
    var earliestClass: CourseEvent?
    var arrivalTime: String?
    
    public static var connected: Bool = false
    let monitor = NWPathMonitor()
    let queue = DispatchQueue(label: "Monitor")
    
    // Helpers
    let calendarHandler = KTHCalendarHelper()
    let routeHandler = RouteHelper()
    let placesHandler = PlacesLookup()
    let courseHelper = CourseHelper()
    var player: AVAudioPlayer?
    var timer: Timer?
    
    public init() {
        monitor.pathUpdateHandler = { path in
            DispatchQueue.main.async {
                if path.status == .satisfied {
                    Self.connected = true
                    print("Connection")
                } else {
                    Self.connected = false
                }
            }
        }
        monitor.start(queue: queue)
        
        model = Model(minutesBeforeDeparture: 15, minutesBeforeArrival: 5, lastWakeUp: Date(), lastUpdated: Date.now, courses: [CourseEvent]())
        alarmPolling = false
        loadingRoute = false
        apiModel = APIModel()
        searchResults = [CourseInfo]()
        courses = [CourseIdentifier]()
        wakeUpTime = ""
        
        UNUserNotificationCenter.current().requestAuthorization(options: [.alert, .badge, .sound]) { success, error in
            if success {
                print("Notification permission OK")
            } else if let error = error {
                print("Notification error: \(error.localizedDescription))")
            }
        }
    }
    
    @objc func pollRoute(){
        self.loadingRoute = true
        self.apiModel.error = ""
        loadCalendar(courses: courses) {
            let timeFormatted = DateFormatter()
            timeFormatted.dateFormat = "HH:mm"
            
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
            
            var roomName: String?
            var date: Date?
            for courseEvent in self.model.courses {
                if courseEvent.active {
                    roomName = courseEvent.classroom
                    self.arrivalTime = courseEvent.startTime
                    let classDate = dateFormatter.date(from: courseEvent.startTime)
                    
                    date = classDate!.addingTimeInterval( TimeInterval(-self.model.minutesBeforeArrival * 60))
                    self.arrivalTime = timeFormatted.string(from: date!)
                    self.earliestClass = courseEvent
                    break
                }
            }
            if roomName != nil {
                self.loadRoute(origin: self.origin, roomName: roomName!) {
                    
                    if self.apiModel.route != nil {
                        // Get current time and its components
                        let now = Date()
                        let calendar = Calendar.current
                        let hour = calendar.component(.hour, from: now)
                        let minutes = calendar.component(.minute, from: now)
                        let today = calendar.component(.day, from: now)
                        
                        // Get route time
                        let routeTimeString = self.apiModel.route!.Trip[0].LegList.Leg[0].Origin.time
                        let routeDateString = self.earliestClass!.startTime.components(separatedBy: " ")[0]
                        let dateFormatter = DateFormatter()
                        dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
                        let routeDate: Date = dateFormatter.date(from: "\(routeDateString) \(routeTimeString)")!
                        let routeDay = calendar.component(.day, from: routeDate)
                        
                        // Get Last time
                        let lastWakeUpHour = calendar.component(.hour, from: self.model.lastWakeUp)
                        let lastWakeUpMinute = calendar.component(.minute, from: self.model.lastWakeUp)
                        let lastHourString = lastWakeUpHour < 10 ? "0\(lastWakeUpHour)" : "\(lastWakeUpHour)"
                        let lastMinuteString = lastWakeUpHour < 10 ? "0\(lastWakeUpMinute)" : "\(lastWakeUpMinute)"
                        let lastTimeString = "\(lastHourString):\(lastMinuteString):00"
                        let lastDate: Date = dateFormatter.date(from: "\(routeDateString) \(lastTimeString)")!
                        
                        // Get chosen time
                        var wakeUpHour = calendar.component(.hour, from: lastDate)
                        var wakeUpMinutes = calendar.component(.minute, from: lastDate)

                        
                        // If today is route day
                        if(today == routeDay && routeDate < lastDate){
                            wakeUpHour = calendar.component(.hour, from: routeDate)
                            wakeUpMinutes = calendar.component(.minute, from: routeDate)
                        }
                        
                        // Format and save the chosen time for UI
                        let hourString = wakeUpHour < 10 ? "0\(wakeUpHour)" : "\(wakeUpHour)"
                        let minuteString = wakeUpMinutes < 10 ? "0\(wakeUpMinutes)" : "\(wakeUpMinutes)"
                        self.wakeUpTime = hourString + ":" + minuteString
                        
                        // Calculate diff and maybe set notification
                        var minutesRemaining = abs((wakeUpHour - hour) * 60)
                        minutesRemaining += abs(wakeUpMinutes - minutes)
                        print("Minutes remaining to wake up: \(minutesRemaining)")
                        if(minutesRemaining <= 2){
                            self.playWakeUp()
                            self.createNotification()
                            self.stopAlarm()
                        }
                    }
                }
            } else {
                self.apiModel.error = "No active courses."
            }
        }
    }
    
    func playWakeUp(){
        print("---- PLAYING WAKE UP SOUND ----")
        
        //Play sound
        guard let url = Bundle.main.url(forResource: "alarm", withExtension: "mp3") else { return }
            do {
                try AVAudioSession.sharedInstance().setCategory(.playback, mode: .default)
                try AVAudioSession.sharedInstance().setActive(true)

                /* The following line is required for the player to work on iOS 11. Change the file type accordingly*/
                player = try AVAudioPlayer(contentsOf: url, fileTypeHint: AVFileType.mp3.rawValue)

                /* iOS 10 and earlier require the following line:
                player = try AVAudioPlayer(contentsOf: url, fileTypeHint: AVFileTypeMPEGLayer3) */

                guard let player = player else { return }

                player.play()

            } catch let error {
                print(error.localizedDescription)
            }
    }
    
    func createNotification(){
        print("Creating notification")
        let content = UNMutableNotificationContent()
        content.title = "Wake up!"
        //content.subtitle = "It looks hungry"
        content.body = "Leave in \(model.minutesBeforeDeparture) minutes"
        content.sound = UNNotificationSound.init(named:UNNotificationSoundName(rawValue: "alarm.mp3"))
        
        // show this notification five seconds from now
        let trigger = UNTimeIntervalNotificationTrigger(timeInterval: 5, repeats: false)
        
        let request = UNNotificationRequest(identifier: UUID().uuidString, content: content, trigger: trigger)
        
        // add our notification request
        UNUserNotificationCenter.current().add(request)
        
    }
    
    func linkApiModel(_ apiModel: APIModel){
        self.apiModel = apiModel
    }
    
    
    func loadRoute(origin: String, roomName: String, completeion: @escaping () -> Void) {
        if !Self.connected {
            return
        }
        
        routeHandler.loadRoute(origin: [String(locationManager.latitude), String(locationManager.longitude)], roomName: roomName, time: self.arrivalTime ?? "00:00", false) { data, error in
            if let result = try? JSONDecoder().decode(Route.self, from: data!) {
                DispatchQueue.main.async {
                    self.apiModel.route = result
                    self.loadingRoute = false
                    completeion()
                }
            } else {
                self.apiModel.error = "Couldn't find route"
            }
        }
    }
    
    func loadCalendar(courses: [CourseIdentifier], complete: @escaping () -> Void) {
        self.apiModel.error = ""
        if !Self.connected {
            self.apiModel.error = "Error - No internet connection"
            return
        }
        
        calendarHandler.loadCalendar(courses: courses, debug: false) { result in
            if result != nil {
                DispatchQueue.main.async {
                    self.apiModel.calendars = result!
                    self.convertCourses()
                    complete()
                }
            } else {
                self.apiModel.error = "Failed to fetch courses"
            }
        }
    }
    
    func setAlarm() {
        self.apiModel.route = nil
        let date = Date().addingTimeInterval(0)
        timer = Timer(fireAt: date, interval: 60, target: self, selector: #selector(pollRoute), userInfo: nil, repeats: true)
        RunLoop.main.add(timer!, forMode: .common)
        alarmPolling = true
    }
    
    func stopAlarm() {
        if let timer = self.timer{
            timer.invalidate()
        }
        alarmPolling = false
    }
    
    func getCalendar() {
        loadCalendar(courses: courses, complete: {
            self.model.lastUpdated = Date.now
        })
    }
    
    
    func handleOnAppear() {
        loadData()
        print("Loaded Data!")
    }
    
    func saveCourses() {
        FileManagerModel.saveState(model: self.model, courses: self.courses)
    }
    
    func loadData() {
        FileManagerModel.load {
            if let stateInformation = FileManagerModel.state {
                self.model = stateInformation.model
                self.courses = stateInformation.courses
            }
        }
    }
    
    public static func loadJSON<T: Decodable>(_ filename:String) -> T {
        let data: Data
        
        guard let file = Bundle.main.url(forResource: filename, withExtension: nil)
        else{
            fatalError("Couldn't find " + filename + " in main bundle")
        }
        
        do {
            data = try Data(contentsOf: file)
        }
        catch {
            fatalError("Couldn't load \(filename) from main bundle:\n\(error)")
        }
        
        do {
            let decoder = JSONDecoder()
            return try decoder.decode(T.self, from: data)
        }
        catch {
            fatalError("Couldn't parse \(filename) as \(T.self):\n\(error)")
        }
    }
    
    func findCourses(like: String) {
        courseHelper.searchCourses(like, completion: { data, error in
            self.searchResults = [CourseInfo]()
            if let result = try? JSONDecoder().decode(SearchResult.self, from: data!) {
                for hit in result.searchHits {
                    self.searchResults.append(hit.course)
                }
            }
        })
    }
    
    func removeCourse(_ course: CourseIdentifier) {
        if let index = courses.firstIndex(where: {$0.code == course.code}) {
            courses.remove(at: index)
        }
        self.saveCourses()
    }
    
    func addCourse(_ course: CourseInfo) {
        if !courses.contains(where: {$0.code == course.courseCode}) {
            courses.append(CourseIdentifier(name: course.title, code: course.courseCode))
        }
        self.saveCourses()
    }
    
    @objc func convertCourses() -> Void {
        var courseList = [CourseEvent]()
        for calendar in self.apiModel.calendars {
            var coursename = ""
            var courseCode = ""
            for course in self.courses {
                if calendar.url.contains(course.code) {
                    coursename = course.name
                    courseCode = course.code
                }
            }
            
            for calendarEntry in calendar.entries {
                if let elementInList = self.model.courses.first(where: {$0.name == coursename && $0.startTime == calendarEntry.start}) {
                    courseList.append(CourseEvent(from: calendarEntry, courseName: coursename, courseCode: courseCode, active: elementInList.active))
                } else {
                    courseList.append(CourseEvent(from: calendarEntry, courseName: coursename, courseCode: courseCode))}
            }
        }
        let sortedList = courseList.sorted(by: {
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
            if let date1 = dateFormatter.date(from: $0.startTime), let date2 = dateFormatter.date(from: $1.startTime) {
                return date1 < date2
            } else {
                return false
            }
        })
        
        DispatchQueue.main.async {
            self.model.courses = sortedList
            self.saveCourses()
        }
    }
}
