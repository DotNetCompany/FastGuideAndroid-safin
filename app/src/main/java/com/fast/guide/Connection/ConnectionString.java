package com.fast.guide.Connection;

public class ConnectionString {

    static String conString = "http://37.187.162.221:85/fgsystem/model/";

    public String URL_Category = conString + "getCategory.php";

    public String urlphotocategory = conString + "categoryimage/";

    public  String URL_GET_Detail = conString + "getAllDetails.php";



    // detail map view controller

    public String URL_DETAIL_MAP = conString + "getDetailMap.php";

    public String URL_DetailSubCategory = conString + "getDetailMapBySubCategory.php";



    // detail view controller

    public String URL_DetailByID = conString + "getDetailByID.php";

    public String URL_Contact = conString + "getDetailContact.php";

    public String urlphotoheader = conString + "headerimage/";

    public String urlphotopanorama = conString + "panoramaphoto/";



    //place list view controller

    public String URL_DetailByTitle = conString + "getDetailByTitle.php";

    public String URL_DetailList = conString + "getDetailList.php";

    public String URL_DetailByTitleAndCity = conString + "getDetailByTitleAndCity.php";



    // Search view controller

    public String URL_DetailByTitleWithoutCategory = conString + "getDetailByTitleWithoutCategory.php";



    //subcategoryimage

    public  String URL_SubCategory = conString + "getSubCategoryByCategoryID.php";

    public  String urlphotosubcategory = conString + "subcategoryimage/";



    //galleryimage

    public String URL_Gallery = conString + "getDetailGallery.php";

    public  String urlphotogallery = conString + "galleryimage/";



    //noimageimage

    public String urlphotonoimage = conString + "noimage/noimage.jpg";



    //About View Controller

    public String URL_About = conString + "getAbout.php";

}
