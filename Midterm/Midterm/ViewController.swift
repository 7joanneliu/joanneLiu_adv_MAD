//
//  ViewController.swift
//  Midterm
//
//  Created by Joanne Liu on 3/14/19.
//  Copyright © 2019 Joanne Liu. All rights reserved.
//

import UIKit

class ViewController: UITableViewController {
    var movies = [String]()
    var movieData = MovieDataModelController()
//    var selectedMovie = 0
    
//    override func viewWillAppear(_ animated: Bool) {
//        movies = movieData.getMovies()(index: selectedMovie)
//    }

    override func viewDidLoad() {
        super.viewDidLoad()
        movieData.loadData()
        movies=movieData.getMovies()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "moviesegue" {
            if let indexPath = self.tableView.indexPathForSelectedRow {
                let url = movieData.getURL(index: indexPath.row)
                let name = movies[indexPath.row]
                let controller = (segue.destination as! DetailViewController)
                let indexPath = tableView.indexPath(for: sender as! UITableViewCell)!
                controller.detailItem = url
                controller.title = name
            }
        }
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return movies.count }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "movieIdentifier", for: indexPath)
        cell.textLabel?.text = movies[indexPath.row]
        return cell}
    
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    
    // Override to support editing the table view.
//    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
//        if editingStyle == .delete {
//            //Delete the country from the array
//            movies.remove(at: indexPath.row)
//            //Delete from the data model instance
//            movieData.deleteMovie(index: selectedMovie, countryIndex: indexPath.row)
//            // Delete the row from the table
//            tableView.deleteRows(at: [indexPath], with: .fade)
//        } else if editingStyle == .insert {
//            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
//        }
//    }
    
    
}

