//
//  ListTableViewController.swift
//  p1
//
//  Created by Joanne Liu on 3/11/19.
//  Copyright © 2019 Joanne Liu. All rights reserved.
//

import UIKit
import RealmSwift

class ListTableViewController: UITableViewController {
    var listData = ListDataController()
    var listItems = [Listling]()

    func render(){//called when data changes
        listItems = listData.getItems() //retrieve
        tableView.reloadData()//updates
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        listData.dbSetup() //call Realm setup
        listItems = listData.getItems() //populate list array
    }

    // MARK: - Table view data source
    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return listItems.count
    }

    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath)
        let item = listItems[indexPath.row]
        cell.textLabel!.text = item.name
        cell.accessoryType = item.bought ? .checkmark : .none //set checkmark if bought
//        if (tableView.cellForRow(at: indexPath)?.accessoryType == UITableViewCell.AccessoryType.checkmark){
//            cell.textLabel?.textColor = UIColor.lightGray
//        }
        return cell
    }

    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let boughtItem = listItems[indexPath.row]
        listData.boughtItem(item: boughtItem)
        render()
    }

    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }

    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            let item = listItems[indexPath.row]
            listData.deleteItem(item: item)
            render()
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }
    }
    @IBAction func addListItem(_ sender: UIBarItem) {
        let addalert = UIAlertController(title: "Add Item", message: "New item to packing list", preferredStyle: .alert)
        addalert.addTextField(configurationHandler: {(UITextField) in})
        let cancelAction = UIAlertAction(title: "Cancel", style: .cancel, handler: nil)
        addalert.addAction(cancelAction)
        let addItemAction = UIAlertAction(title: "Add", style: .default, handler:{(UIAlertAction)in
        let newitem = addalert.textFields![0]//get texfield
        let newListItem = Listling() //new List instance
        newListItem.name = newitem.text! //set name with textfield text
        newListItem.bought = false
        self.listData.addItem(newItem: newListItem)
        self.render()
        })
        addalert.addAction(addItemAction)
        present(addalert, animated:true, completion:nil)
    }

}
