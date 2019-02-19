//
//  plants.swift
//  lab2
//
//  Created by Joanne Liu on 2/13/19.
//  Copyright © 2019 Joanne Liu. All rights reserved.
//

import Foundation

struct RoomsDataModel : Codable {
    var room : String
    var plants : [String]
}

class RoomDataModelController{
    var allData = [RoomsDataModel]()
    let fileName = "plants"
    
    func loadData(){
        if let pathURL = Bundle.main.url(forResource: fileName, withExtension: "plist"){
            let plistdecoder = PropertyListDecoder()
            do{
                let data = try Data(contentsOf: pathURL)
                allData = try
                    plistdecoder.decode([RoomsDataModel].self, from:data)
            }catch{
                print(error)
            }
        }
    }
    
    func getRooms() -> [String] {
        var rooms = [String]()
        for item in allData{
            rooms.append(item.room)
        }
        return rooms
    }
    
    func getPlants(index:Int) -> [String] {
        return allData[index].plants
    }
    
    func addPlant(index:Int, newPlant:String, newIndex:Int){
        allData[index].plants.insert(newPlant, at: newIndex)
    }
    
    func deletePlant(index:Int, plantIndex:Int){
        allData[index].plants.remove(at: plantIndex)
    }
}
