//
//  AddViewController.swift
//  
//
//  Created by Joanne Liu on 3/12/19.
//

import UIKit
import RealmSwift

class AddViewController: UIViewController {
    
    @IBOutlet weak var recipeName: UITextField!
    var recName = String()
    
    var stepData = AddDataController()
    var stepItems = [Step]()
    var IngData = IngredientDataController()
    var IngItems = [Ingredient]()
    
    func render(){//called when data changes
        stepItems = stepData.getItems() //retrieve
        IngItems = IngData.getItems()
//        tableView.reloadData()//updates
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "doneSegue" {
            if ((recipeName.text?.isEmpty) == false){
                recName=recipeName.text!
            }
            let newItem = recipeName.text!
            let newRecipeItem = Recipe()
            newRecipeItem.name=newItem
//            newRecipeItem.steps=[Step]

        }

    }
    @IBAction func addStep(_ sender: Any) {
        let addalert = UIAlertController(title: "New Step", message: "Add new step instructions. Then ingredients, separtated with a comma.", preferredStyle: .alert)
        addalert.addTextField(configurationHandler: {(_ textField : UITextField) in textField.placeholder = "Instruction"})
        addalert.addTextField(configurationHandler: {(_ textField : UITextField) in textField.placeholder = "Ingredients"})
        let cancelAction = UIAlertAction(title: "Cancel", style: .cancel, handler: nil)
        addalert.addAction(cancelAction)
        
        let addItemAction = UIAlertAction(title: "Add", style: .default, handler:{(UIAlertAction)in
            let newstep = addalert.textFields![0]//get texfield
            let newIng = addalert.textFields![1]
//            print("step: \(String(describing: addalert.textFields![0].text))")
//            print("ing: \(String(describing: addalert.textFields![1].text))")
//            print(addalert.textFields![1])
            
//            Person(value: ["Jane", 30, [["Buster", 5], ["Buddy", 6]]])
            let newIngrendientItem = Ingredient()
            let newStepItem = Step(value:[newIngrendientItem]) //new List instance
//            let newIngrendientItem = Ingredient()

            Step().stepName = newstep.text! //set name with textfield tex
            Ingredient().ingredientName = newIng.text! //set name with textfield text
            newStepItem.stepName = newstep.text!
            newIngrendientItem.ingredientName = newIng.text!
            self.stepData.addItem(newItem: newStepItem)
            self.IngData.addItem(newItem1: newIngrendientItem)
            self.render()
        })
        addalert.addAction(addItemAction)
        present(addalert, animated:true, completion:nil)
    }
    
}
