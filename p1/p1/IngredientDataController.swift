//
//  IngredientDataController.swift
//  p1
//
//  Created by Joanne Liu on 3/12/19.
//  Copyright © 2019 Joanne Liu. All rights reserved.
//

import RealmSwift

class IngredientDataController{
    
    var myRealm : Realm! //realm db instance
    var ingredientData: Results<Ingredient> //collection of Objects
    {
        get{
            return myRealm.objects(Ingredient.self)//Return step objects
        }//get
    }
    
    
    func dbSetup(){
        do{
            myRealm = try Realm()
        } catch let error{
            print(error.localizedDescription)
        }
        print(Realm.Configuration.defaultConfiguration.fileURL!)//print location of Realm db
    }
    
    func getItems()-> [Ingredient]{
        return Array(ingredientData)//return an item
    }
    
    func addItem(newItem1:Ingredient){
        do {
            try self.myRealm.write({
                self.myRealm.add(newItem1) //add to realm database
            })
        } catch let error{
            print(error.localizedDescription)
        }
    }
    
    func deleteItem(item1: Ingredient){
        do {
            try self.myRealm.write ({
                self.myRealm.delete(item1) //delete from realm database
            })
        } catch let error{
            print(error.localizedDescription)
        }
    }
}//StepDataController

