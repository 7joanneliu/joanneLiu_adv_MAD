//
//  RecipeDataController.swift
//  p1
//
//  Created by Joanne Liu on 3/11/19.
//  Copyright © 2019 Joanne Liu. All rights reserved.
//

import RealmSwift

class RecipeDataController{
    
    var myRealm : Realm! //realm db instance
    var recipeData: Results<Recipe> //collection of Objects
{
        get{
            return myRealm.objects(Recipe.self)//Return recipe objects
        }//get
    }
    
    
    //aksjldfhuiwhfoas
//    var stepData: Results<Step> //collection of Objects
//    {
//        get{
//            return myRealm.objects(Step.self)//Return step objects
//        }//get
//    }
    //askldjhfawhpf
    
    
    
    func dbSetup(){
        do{
            myRealm = try Realm()
        } catch let error{
            print(error.localizedDescription)
        }
        print(Realm.Configuration.defaultConfiguration.fileURL!)//print location of Realm db
    }
    
    func getItems()-> [Recipe]{
        return Array(recipeData)//return an item
    }
    
    func addItem(newItem:Recipe){
        do {
            try self.myRealm.write({
                self.myRealm.add(newItem) //add to realm database
            })
        } catch let error{
            print(error.localizedDescription)
        }
    }
//    func boughtItem(item: Step){
//        do {
//            try self.myRealm.write ({
//                item.stepName = !item.stepName
//            })
//        }catch let error{
//            print(error.localizedDescription)
//        }
//    }
    
    func deleteItem(item: Recipe){
        do {
            try self.myRealm.write ({
                self.myRealm.delete(item) //delete from realm database
            })
        } catch let error{
            print(error.localizedDescription)
        }
    }
}//RecipeDataController
