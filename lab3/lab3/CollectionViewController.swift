//
//  CollectionViewController.swift
//  lab3
//
//  Created by Joanne Liu on 2/27/19.
//  Copyright © 2019 Joanne Liu. All rights reserved.
//

import UIKit

private let reuseIdentifier = "Cell"
 var leafImages=[String]()

class CollectionViewController: UICollectionViewController, UICollectionViewDelegateFlowLayout {
    
//    var leafImages=[String]()


    override func viewDidLoad() {
        super.viewDidLoad()
        
        for i in 1...20{
            leafImages.append("leaf" + String(i))
        }
    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using [segue destinationViewController].
        // Pass the selected object to the new view controller.
    }
    */

    // MARK: UICollectionViewDataSource

    override func numberOfSections(in collectionView: UICollectionView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }


    override func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of items
        return leafImages.count
    }

    override func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: reuseIdentifier, for: indexPath) as! CollectionViewCell
    
        // Configure the cell
        
        let image = UIImage(named: leafImages[indexPath.row])
        cell.imageView.image=image
    
        return cell
    }
    
    override func collectionView(_ collectionView: UICollectionView, viewForSupplementaryElementOfKind kind: String, at indexPath: IndexPath) -> UICollectionReusableView {
//        var header: CollectionSupplementaryView?

        
        switch kind {
        case UICollectionView.elementKindSectionHeader:
            var header: CollectionSupplementaryView?
            let headerView = collectionView.dequeueReusableSupplementaryView(ofKind: kind, withReuseIdentifier: "Header", for: indexPath)
            
//            headerView.headerLabel.text = "LEAFY"
            header?.headerLabel.text = "LEAFY"
            return headerView
            
        case UICollectionView.elementKindSectionFooter:
            let footerView = collectionView.dequeueReusableSupplementaryView(ofKind: kind, withReuseIdentifier: "Footer", for: indexPath)
            return footerView
            
        default:
            
            assert(false, "Unexpected element kind")
        }
        //SWITCH CODE FOUND HERE: https://stackoverflow.com/questions/29655652/how-to-make-both-header-and-footer-in-collection-view-with-swift
    }
    
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        let image = UIImage(named: leafImages[indexPath.row])
        let newSize = CGSize(width: (image?.size.width)!/6, height: (image?.size.height)!/6)
        UIGraphicsBeginImageContextWithOptions(newSize, false, 1.0)
        let rect = CGRect(x:0, y: 0, width: newSize.width, height: newSize.height)
        image?.draw(in: rect)
        let image2 = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        return (image2?.size)!
    }
    
    func collectionView(_ collectionView: UICollectionView, layoutcollectionViewLayout: UICollectionView, insetForSectionAt section:Int) -> UIEdgeInsets {
        let sectionInsets = UIEdgeInsets(top: 25.0, left: 7.0, bottom: 25.0, right: 7.0)
        return sectionInsets
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
            let indexPath = collectionView?.indexPath(for: sender as! CollectionViewCell)
            let detailVC = segue.destination as! DetailViewController
            detailVC.imageName = leafImages[(indexPath?.row)!]
    }
}
