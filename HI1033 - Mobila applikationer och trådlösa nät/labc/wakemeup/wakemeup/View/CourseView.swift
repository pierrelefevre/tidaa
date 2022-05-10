//
//  CourseView.swift
//  wakemeup
//
//  Created by Oscar  Ekenlöw on 2022-01-06.
//

import SwiftUI

struct CourseView: View {
    @EnvironmentObject var viewModel: ViewModel
    @State private var course = ""
    var body: some View {
        VStack{
            HStack {
                Image(systemName: "magnifyingglass")
                
                TextField("Course", text: $course)
                    .onChange(of: course) { partialName in
                        viewModel.findCourses(like: partialName)
                    }
                    .textFieldStyle(.roundedBorder)
                    .disableAutocorrection(true)
            }.padding(.all, 10)
            
            if course.isEmpty && viewModel.courses.isEmpty{
                HStack(alignment: .center){
                    Spacer()
                    VStack(alignment: .center){
                        Spacer()
                        HStack{
                            Image(systemName: "graduationcap")
                                .font(.system(size: 100))
                        }
                        .padding(.bottom, 15)
                        Text("Search for courses")
                            .font(.headline)
                        Spacer()
                    }
                    Spacer()
                }.foregroundColor(.gray)
            }
            List() {
                if course.isEmpty && !viewModel.courses.isEmpty {
                    Section{
                        Text("My courses").font(.title2).fontWeight(.bold)
                            .padding(.vertical, 10)
                        ForEach(viewModel.courses, id: \.self) { course in
                            Button {
                                viewModel.removeCourse(course)
                            } label: {
                                HStack{
                                    Text("\(course.code) \(course.name)")
                                    Spacer()
                                    Image(systemName: "trash").foregroundColor(.red)
                                }
                                .foregroundColor(.primary)
                                .padding(.vertical, 10)
                            }
                        }
                    }
                } else {
                    ForEach(viewModel.searchResults, id: \.self) { result in
                        Section{
                            Button {
                                viewModel.addCourse(result)
                                course = ""
                            } label: {
                                VStack(alignment: .leading){
                                    Text(result.courseCode)
                                    Text(result.title).font(.subheadline)
                                    Text("\(String(result.credits)) HP").font(.subheadline)
                                }
                                .foregroundColor(.primary)
                                .padding(.vertical, 10)
                            }
                        }
                    }
                }
            }
        }}
}

