Monogatari v1.0
==========
Monogatari is the simple static website generator written on Java. (use Java8)


LICENSE
-------
Monogatari  Copyright (C) 2014  Dmitry Kuznetsov aka kudzmi  
This program comes with ABSOLUTELY NO WARRANTY!  
This is free software, and you are welcome to redistribute it;  
see the source for copying conditions.  
 
Structure
---------
 
 Here is the structure:

1. **config** - config files in JSON format
2. **raw_data** - directory with content of your site. Create sub dir with name 'post_N', where N is number from 1 to N.
    * **content.txt** - text of the post.
    * **preview.jpg** - image that you will see at index page.
    * any other files or images, you want to see in the post.
3. **templates** - the templates of the site. You can create custom templates.    
4. **release_content** - this is you website. Just copy it to your web server.

Config parameters
-----------------

**GLOBAL**

* *site_name* - the name of you site;
* *copyright* - this is what you will see in the footer;
* *template* - what theme from templates dit to use;
* *favicon* - favicon file name;
* *image_width* - width of images in the post's body;
* *release_dir_path* - path of the directory with generated web site;
* *raw_data_path* - path of the directory with raw data.

**INDEX**

* *title* - title on the main page;
* *posts_per_page* - maximum links per page.

Markers
-------

* **[lang]** - page language;
* **[article]** - post's body;
* **[images]** - list of images (use # as separator);
* **[page_title]** - title of the web-page;
* **[post_title]** - title of the post;
* **[post_date]** - date and time of the post. Just a string. Use any format, you want;
* **[site_name]** - your web-site name;
* **[page_nav]** - index page navigator block;
* **[page_copyright]** - copyright block;
* **[index_table]** - table with links at the main page;
* **[index_title]** - title of the main page.