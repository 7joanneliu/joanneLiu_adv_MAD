//
//  MovieDataModelController.swift
//  Midterm
//
//  Created by Joanne Liu on 3/14/19.
//  Copyright © 2019 Joanne Liu. All rights reserved.
//

import Foundation

class MovieDataModelController {
    var movieData = [MoviesDataModel]()
    let movieFile = "movies"
    
    func loadData() {
        if let pathURL = Bundle.main.url(forResource: movieFile, withExtension: "plist"){
            let plistdecoder = PropertyListDecoder()
            do{
                let data = try Data(contentsOf: pathURL)
                movieData = try plistdecoder.decode([MoviesDataModel].self, from: data)
            }
            catch{
                print(error)
            }
        }
    }
    func getMovies() -> [String] {//maybe change to character one
        var movies = [String]()
        for movie in movieData{
            movies.append(movie.name)
        }
        return movies
    }//getMovie
    
    func getURL(index:Int) -> String{
        return movieData[index].url
    }
    
//    func addMovie(index:Int, newMovie:String, newIndex:Int){
//        movieData[index].name.insert(newMovie, at: newIndex)
//    }
    
//    func deleteMovie(index:Int, movieIndex: Int){
//        movieData[index].name.remove(at: movieIndex)
//    }
    
}
