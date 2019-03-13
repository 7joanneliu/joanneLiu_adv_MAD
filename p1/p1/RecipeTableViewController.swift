//
//  RecipeTableViewController.swift
//  p1
//
//  Created by Joanne Liu on 3/11/19.
//  Copyright © 2019 Joanne Liu. All rights reserved.
//

import UIKit
import RealmSwift

class RecipeTableViewController: UITableViewController {

    var recipeData = RecipeDataController()
    var recipeItems = [Recipe]()
    
    func render(){//CALLED WHEN DATA CHANGES
        recipeItems = recipeData.getItems() //retrieve
        tableView.reloadData()//updates
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        recipeData.dbSetup() //call Realm setup
        recipeItems = recipeData.getItems() //populate list array
    }
    
    // MARK: - Table view data source
    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return recipeItems.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "RecipeIdentifier", for: indexPath)
        let item = recipeItems[indexPath.row]
        cell.textLabel!.text = item.name
//SET OTHER TABLE THINGS HERE
        return cell
    }
    
//    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
//        let stepItem = recipeItems[indexPath.row]
//        recipeData.recipeItem(item: stepItem)
//        render()
//    }
    
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            let item = recipeItems[indexPath.row]
            recipeData.deleteItem(item: item)
            render()
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }
    }
//    @IBAction func addListItem(_ sender: UIBarItem) {
//    
//        let addalert = UIAlertController(title: "New Test", message: "why.", preferredStyle: .alert)
//        addalert.addTextField(configurationHandler: {(UITextField) in})
//        let cancelAction = UIAlertAction(title: "Cancel", style: .cancel, handler: nil)
//        addalert.addAction(cancelAction)
//        let addItemAction = UIAlertAction(title: "Add", style: .default, handler:{(UIAlertAction)in
//            let newitem = addalert.textFields![0]//get texfield
//            let newitem2 = addalert.textFields![1]//get texfield
//            let newRecipeItem = Recipe() //new List instance
//            let newRecipeItem2 = Step() //new List instance
//
//            newRecipeItem.name = newitem.text! //set name with textfield text
//            newRecipeItem2.stepName = newitem2.text! //set name with textfield text
//            
//            self.recipeData.addItem(newItem: newRecipeItem)
//            self.render()
//        })
//        addalert.addAction(addItemAction)
//        present(addalert, animated:true, completion:nil)
//    }
    class addDataController {
//        let newitem = addalert.textFields![0] //gets textfield
//        let newGroceryItem = Grocery() //create new Grocery instance newGroceryItem.name = newitem.text! //set name with textfield
//        newGroceryItem.bought = false
//        self.groceryData.addItem(newItem: newGroceryItem) self.render()
    }
    

}
