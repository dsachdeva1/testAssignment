

Design Used :-

I have used MVVM ( Model View ViewModel design pattern to create the app).
Used Android Architectural components as a core component of the app.
(LiveData, ViewModel )
they are lifecycle aware components so even if activity recreated due to configuration changed.. data will be saved in viewmodel class.

Repository is used to separate the data fetching stuff apart from UI..
VM will request from Repository for next set of images..
Now its on Repository how it fetches the images ( from cache or from network).



UI ( View) will be observing data in VM. so whenever we load new data, view will get notified and it updates its ui accordingly.


Images will be cached as ImMemory and Disk cache

InMemory cache has been allocated 20% of the app size.

First the images will be checked in Memory cache and if unavailable then checked in diskCache..

otherwise network call will be made to download it and cache it..

ImageLoader.java - singleton class to manage cache and load url into Imageview.

It will take url and imageView and it will get the bitmap of image ( from cache or from network)..
then resize the bitmap acc to view size and then load it into the imageView.



Not able to write more Unit test cases...
