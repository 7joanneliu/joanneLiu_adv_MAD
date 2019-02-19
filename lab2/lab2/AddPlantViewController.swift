//
//  AddPlantViewController.swift
//  lab2
//
//  Created by Joanne Liu on 2/19/19.
//  Copyright © 2019 Joanne Liu. All rights reserved.
//

import UIKit

class AddPlantViewController: UIViewController {
    @IBOutlet weak var plantTextfield: UITextField!
    var addedPlant = String()
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "doneSegue"{
            if((plantTextfield.text?.isEmpty) == false){
                addedPlant=plantTextfield.text!
            }
        }
    }
    override func viewDidLoad() {
        super.viewDidLoad()
    }

}
