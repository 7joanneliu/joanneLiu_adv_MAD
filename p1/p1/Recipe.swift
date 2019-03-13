//
//  Recipe.swift
//  p1
//
//  Created by Joanne Liu on 3/11/19.
//  Copyright © 2019 Joanne Liu. All rights reserved.
//

import Foundation
import RealmSwift

class Recipe: Object {
    @objc dynamic var name = ""
        let steps = List<Step>()
}

class Step: Object {
    @objc dynamic var stepName = ""
        let ingredients = List<Ingredient>()
}

class Ingredient: Object {
    @objc dynamic var ingredientName = ""
}
