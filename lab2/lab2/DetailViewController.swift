//
//  DetailViewController.swift
//  lab2
//
//  Created by Joanne Liu on 2/19/19.
//  Copyright © 2019 Joanne Liu. All rights reserved.
//

import UIKit

class DetailViewController: UITableViewController {
    var roomsData = RoomDataModelController()
    var selectedRoom = 0
    var plantList = [String]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        //self.navigationItem.rightBarButtonItem = self.editButtonItem
    }
    
    override func viewWillAppear(_ animated: Bool) {
        plantList = roomsData.getPlants(index: selectedRoom)
    }

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return plantList.count
    }

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "PlantIdentifier", for: indexPath)
        cell.textLabel?.text = plantList[indexPath.row]
        return cell
    }
    
    

    
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    

    
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            plantList.remove(at: indexPath.row)
            roomsData.deletePlant(index: selectedRoom, plantIndex: indexPath.row)
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    

    
    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to toIndexPath: IndexPath) {
        let fromRow = fromIndexPath.row
        let toRow = toIndexPath.row
        let movePlant = plantList[fromRow]
        plantList.remove(at: fromRow)
        plantList.insert(movePlant, at: toRow)
        roomsData.deletePlant(index: selectedRoom, plantIndex: fromRow)
        roomsData.addPlant(index: selectedRoom, newPlant: movePlant, newIndex: toRow)
    }
    

    
    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    
    @IBAction func unwindSegue (_ segue:UIStoryboardSegue){
        if segue.identifier=="doneSegue"{
            let source = segue.source as! AddPlantViewController
            if ((source.addedPlant.isEmpty) == false){
                roomsData.addPlant(index: selectedRoom, newPlant: source.addedPlant, newIndex: plantList.count)
                plantList.append(source.addedPlant)
                tableView.reloadData()
            }
        }
    }
    
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
