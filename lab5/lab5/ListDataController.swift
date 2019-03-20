//
//  ListDataController.swift
//  p1
//
//  Created by Joanne Liu on 3/11/19.
//  Copyright © 2019 Joanne Liu. All rights reserved.
//

import RealmSwift

class ListDataController{

    var myNewRealm : Realm! //realm db instance

    var listData: Results<Listling> //collection of Objects
    {
        get{
            return myNewRealm.objects(Listling.self)//Return grocery objects
        }//get
    }

    func dbSetup(){
        do{
            myNewRealm = try Realm()
        } catch let error{
            print(error.localizedDescription)
        }
        print(Realm.Configuration.defaultConfiguration.fileURL!)//print location of Realm db
    }

    func getItems()-> [Listling]{
        return Array(listData)//return an item
    }

    func addItem(newItem:Listling){
        do {
            try self.myNewRealm.write({
                self.myNewRealm.add(newItem) //add to realm database
            })
        } catch let error{
            print(error.localizedDescription)
        }
    }
    func boughtItem(item: Listling){
        do {
            try self.myNewRealm.write ({
                item.bought = !item.bought
            })
        }catch let error{
            print(error.localizedDescription)
        }
    }

    func deleteItem(item: Listling){
        do {
            try self.myNewRealm.write ({
                self.myNewRealm.delete(item) //delete from realm database
            })
        } catch let error{
            print(error.localizedDescription)
        }
    }


}//ListDataController
