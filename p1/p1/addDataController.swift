//
//  addDataController.swift
//  p1
//
//  Created by Joanne Liu on 3/12/19.
//  Copyright © 2019 Joanne Liu. All rights reserved.
//

import RealmSwift

class AddDataController{
    
    var myRealm : Realm! //realm db instance
    var stepData: Results<Step> //collection of Objects
    {
        get{
            return myRealm.objects(Step.self)//Return step objects
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
    
    func getItems()-> [Step]{
        return Array(stepData)//return an item
    }
    
    func addItem(newItem:Step){
        do {
            try self.myRealm.write({
                self.myRealm.add(newItem) //add to realm database
            })
        } catch let error{
            print(error.localizedDescription)
        }
    }
    
    func deleteItem(item: Step){
        do {
            try self.myRealm.write ({
                self.myRealm.delete(item) //delete from realm database
            })
        } catch let error{
            print(error.localizedDescription)
        }
    }
}//StepDataController

