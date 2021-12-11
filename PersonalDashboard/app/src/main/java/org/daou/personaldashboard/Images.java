package org.daou.personaldashboard;

import android.media.Image;

public class Images {
    private String name;
    private int imageResourceId;
    public static final Images[] sushi = {
             new Images("Bank", R.drawable.bank),
             new Images("Notes", R.drawable.notes),
             new Images("Calendar", R.drawable.calendar),
             new Images("Email", R.drawable.email),

             };

         public Images(String name, int imageResourceId) {
         this.name = name;
         this.imageResourceId = imageResourceId;
         }

         public String getName() {
         return name;
         }

         public int getImageResourceId() {
         return imageResourceId;
         }
 }
