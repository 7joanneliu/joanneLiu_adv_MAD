//
//  quote.swift
//  lab4
//
//  Created by Joanne Liu on 3/5/19.
//  Copyright © 2019 Joanne Liu. All rights reserved.
//

import Foundation

struct Quote: Decodable {
    let content: String
    let title: String
}//quote assets

struct QuoteData: Decodable {
    var results = [Quote]()
}// data structure to use JSONDecoder
