//
//  ViewController.swift
//  lab4
//
//  Created by Joanne Liu on 3/5/19.
//  Copyright © 2019 Joanne Liu. All rights reserved.
//

import UIKit

class ViewController: UIViewController, UITextFieldDelegate {
    var quotes = [Quote]()

    @IBAction func newQuote(_ sender: Any) {
        quoteSpace.text = "hi"
//        quoteSpace.text = String(
    }
    @IBOutlet weak var quoteSpace: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
//        loadjson()
        let urlPath = "http://quotesondesign.com/wp-json/posts?filter[orderby]=rand&filter[posts_per_page]=1"
        guard let url = URL(string: urlPath) else {
            print("url error")
            return}
        
        URLSession.shared.dataTask(with: url, completionHandler: {(data, response, error) in
            let httpResponse = response as! HTTPURLResponse; let statusCode = httpResponse.statusCode

            guard statusCode == 200
                else {
                    print("file download error"); return
            }
//            self.quoteSpace.text = "no"

            //download successful
            print("download complete")
//            self.quoteSpace.text = "no"

            do{
                let api = try JSONDecoder().decode(Quote.self, from: data!)
                print(api.title, api.content)

                DispatchQueue.main.async { //parse json asynchronously
                    self.quoteSpace.text = api.title
                    self.quoteSpace.text = "bob"
                }
            }
                
            catch let jsonErr {
                print(jsonErr.localizedDescription)
                return
            }
            
        })
        //must call resume to run session
        .resume()
        
//        self.quoteSpace.text = "yes"

    }
        // Do any additional setup after loading the view, typically from a nib.
        }
    
//    func loadjson(){
//        let urlPath = "http://quotesondesign.com/wp-json/posts?filter[orderby]=rand&filter[posts_per_page]=1"
//        guard let url = URL(string: urlPath) else {
//            print("url error")
//            return
//        }
//        let session = URLSession.shared.dataTask(with: url, completionHandler: {(data, response, error) in
//            let httpResponse = response as! HTTPURLResponse; let statusCode = httpResponse.statusCode
//            guard statusCode == 200
//                else {
//                    print("file download error"); return
//            }
//            //download successful
//            print("download complete")
//            //parse json asynchronously
//
//            do{
//                let api = try JSONDecoder().decode(Quote.self, from: data!)
//                print(api.title, api.content)
//
//                DispatchQueue.main.async {
//                    self.quoteSpace.text = api.content
//                }
//            }
//
//            catch let jsonErr {
//                print(jsonErr.localizedDescription)
//                return
//            }
//
//
//
//        })
//        //must call resume to run session
//        session.resume()
//    }
//
////    func parsejson(_ data: Data){
////        do
////        {
////            let api = try JSONDecoder().decode(QuoteData.self, from:
////                data)
////            print(api)
////            for quote in api.results
////            {
////                quotes.append(quote)
////            }
////        }
////        catch let jsonErr
////        {
////            print(jsonErr.localizedDescription)
////            return
////        }
////        //reload the table data after the json data has been downloaded
//////        tableView.reloadData()
////    }
//
//
//
//}
