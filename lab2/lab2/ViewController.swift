//
//  ViewController.swift
//  lab2
//
//  Created by Joanne Liu on 2/13/19.
//  Copyright © 2019 Joanne Liu. All rights reserved.
//

import UIKit

class ViewController: UITableViewController {
    var roomList = [String]()
    var roomsData = RoomDataModelController()

    override func viewDidLoad() {
        super.viewDidLoad()
        roomsData.loadData()
        roomList=roomsData.getRooms()
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int{
        return roomList.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "PlantIdentifier", for: indexPath)
        cell.textLabel?.text = roomList[indexPath.row]
        return cell
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "plantsegue"{
            let detailVC = segue.destination as! DetailViewController
            let indexPath = tableView.indexPath(for: sender as! UITableViewCell)!
            detailVC.title = roomList[indexPath.row]
            detailVC.roomsData = roomsData
            detailVC.selectedRoom = indexPath.row
        }
    }
}

